package com.yozora.anime.spider;


import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.overzealous.remark.Remark;
import com.yozora.anime.dao.NovelDao;
import com.yozora.anime.dao.NovelChapterDao;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.entity.NovelChapterEntity;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Crawler(name = "basic")
public class TestRunSpider extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {

       /* List<LightNovelEntity> lightNovelEntities = lightNovelDao.selectList(null);
        String [] urls=new String[lightNovelEntities.size()];
        int i=0;
        String up="0";
        for (LightNovelEntity ln : lightNovelEntities){
           String id=ln.getId();
            if(id.length()>3){
                up=ln.getId().substring(0,1);
            }else {
                up="0";
            }
            String url="https://www.wenku8.net/novel/"+up+"/"+id+"/index.htm";
            urls[i]=url;
            i++;
        }
        return urls;*/
     /*   String[] urls=new String[3436];
        for (int i = 0; i < urls.length; i++) {
            String url="https://www.wenku8.net/book/"+(i+1)+".htm";
            urls[i]=url;
        }
        return urls;*/
        List<String> list = updateContent();
       return  list.toArray(new String[0]);
    }

    @Autowired
    private NovelChapterDao novelChapterDao;

    @Autowired
    private NovelDao lightNovelDao;

    @Override
    public void start(Response response) {

        try {

           //aliNovelInfo(response);
           //getChapterInfo(response);
            getContent(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aliNovelInfo(Response response) throws ParseException {
        JXDocument doc=response.document();
        try {

            //基本信息
            String head="//div[@id='content']//div//table//tbody/tr";
            String title= doc.sel(head+"/td/table/tbody/tr/td/span/b/text()").toString();
            List<Object> info=  doc.sel(head+"[2]//td//text()");
            if(info.size()==0){
                return;
            }
            info.remove(info.size()-1);
            NovelEntity lightNovelEntity=new NovelEntity();
            lightNovelEntity.setTitle(title);
            lightNovelEntity.setPublishing(originValue(info.get(0)));
            lightNovelEntity.setAuthor(originValue(info.get(1)));
            lightNovelEntity.setNovelStatus(originValue(info.get(2)));
            if(info.size()>3){
                lightNovelEntity.setLastUpdate(new SimpleDateFormat("yyyy-MM-dd").parse(originValue(info.get(3))));
                lightNovelEntity.setNovelLen(info.get(4).toString());
            }
            String imgXpt="//*[@id=\"content\"]/div[1]/table[2]/tbody/tr/td[1]/img/@src";
            String img = (String) doc.sel(imgXpt).get(0);
            lightNovelEntity.setImg(img);
            lightNovelEntity.setId(getCode(response.getUrl()));

            String infoXpt= "//*[@id='content']/div[1]/table[2]/tbody/tr/td[2]/span[6]/text()";
            List<Object> sel = doc.sel(infoXpt);
            if(sel.size()==1) {
                String content = sel.get(0).toString();
                lightNovelEntity.setInfo(content);
            }


            lightNovelDao.insert(lightNovelEntity);
          /*  //列表
            String indexXpt="//*[@id='content']/div[1]/div[4]/div/span[1]/fieldset/div/a/@href";
            String indexLink = (String) doc.sel(indexXpt).get(0);*/


            //push(Request.build(indexLink, TestRunSpider::getNovelIndex));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getChapterInfo(Response response) throws ParseException {
        JXDocument document = response.document();
        System.out.println(response.getUrl());
        String titleXpt="//table//tbody//tr//td";
        List<Object> docs = document.sel(titleXpt);

        String currentVolumeTitle = null;
        NovelChapterEntity novelChapterEntity=null;
        String id="";
        String bookId=getID(response.getUrl());
        for (Object row : docs) {
            Element elements= (Element) row;

            if (elements.select("td.vcss").size() > 0) {
                currentVolumeTitle = elements.select("td.vcss").get(0).text();
                novelChapterEntity=new NovelChapterEntity();
                novelChapterEntity.setChaperName(currentVolumeTitle);
                id=uuid();
                novelChapterEntity.setBookId(bookId);
                novelChapterEntity.setId(id);
                novelChapterDao.insert(novelChapterEntity);

            }

            if (currentVolumeTitle != null && elements.select("td.ccss a").size() > 0) {
                Element link = elements.select("td.ccss a").get(0);
                String chapterTitle = link.text();
                String chapterLink = link.attr("href");
                novelChapterEntity=new NovelChapterEntity();
                novelChapterEntity.setPId(id);
                novelChapterEntity.setBookId(bookId);
                novelChapterEntity.setId(chapterLink.replaceAll(".htm",""));
                novelChapterEntity.setChaperName(chapterTitle);
                novelChapterDao.insert(novelChapterEntity);
                String tId="0";
                if(bookId.length()>3){
                    tId=bookId.substring(0,1);
                }else {
                    tId="0";
                }
                //https://www.wenku8.net/novel/1/1000/29093.htm
                String url="https://www.wenku8.net/novel/"+tId+"/"+bookId+"/"+chapterLink;
                push(new Request(url, TestRunSpider::getContent));


            }


        }

    }


    public List<String> updateContent(){
        QueryWrapper<NovelChapterEntity> queryWrapper=new QueryWrapper();
        queryWrapper.isNull(true, "content");
        queryWrapper.eq("book_id","1");
        //queryWrapper.orderByDesc("book_Id");
        List<NovelChapterEntity> content = novelChapterDao.selectList(queryWrapper);
        List<String> urls=new ArrayList<>();
        for (NovelChapterEntity  nc: content){
            String id = nc.getId();
            if(id.matches("[0-9]+")){
                String bookId = nc.getBookId();
                String contentId = getContentId(bookId);
                urls.add("https://www.wenku8.net/novel/"+contentId+"/"+bookId+"/"+id+".htm");
            }
        }
        return urls;

    }
    public String getContentId(String bookId){
        String tId="0";
        if(bookId.length()>3){
            tId=bookId.substring(0,1);
        }else {
            tId="0";
        }

        return tId;
    }

/*    public void getNovelIndex(Response response){
        JXDocument doc = response.document();
        try {
            String chapterXpt="//table//tbody//tr//td";
            List<Object> list = doc.sel(chapterXpt);

            String responseUrl = response.getUrl();
            String pid="";
            for(Object items: list){
                Document document = Jsoup.parse(items.toString());
                Elements tag = document.getElementsByTag("a");
                NovelChapterEntity novelChapterEntity=new NovelChapterEntity();

                if(tag.size()==0){
                    pid=uuid();
                    String value=document.text();
                    novelChapterEntity.setChaperName(value);
                    novelChapterEntity.setId(pid);
                    novelChapterEntity.setBookId(getID(responseUrl));
                    novelChapterDao.insert(novelChapterEntity);
                }else {
                    NovelChapterEntity cd=new NovelChapterEntity();
                    cd.setPId(novelChapterEntity.getId());
                    novelChapterEntity.setBookId(getID(responseUrl));
                    Elements a = tag.tagName("a");
                    String href = a.attr("href");
                    String value= a.text();
                    String id=href.replace(".htm","");
                    cd.setId(id);
                    cd.setPId(pid);
                    String url = responseUrl;
                    url= url.replace("index.htm","");

                    url=url+href;
                    cd.setChaperName(value);
                    novelChapterDao.insert(cd);
                    push(Request.build(url, TestRunSpider::getContent));
                }



            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute request: ", e);
        }
    }




    public void  getContent(Response response){
        JXDocument doc= response.document();
        try {
            Object sel = doc.sel("//div[@id='contentmain']");
            // 将获取到的内容从HTML格式转换为Markdown格式
            Remark remark = new Remark();
            //cd.setContent(remark.convert(sel.toString()));
            String url = response.getUrl();
            String id = getCode(url);
            String convert = remark.convert(sel.toString());
            NovelChapterEntity novelChapterEntity=new NovelChapterEntity();
            novelChapterEntity.setId(id);
            novelChapterEntity.setContent(convert);
            System.out.println(id);
            novelChapterDao.updateById(novelChapterEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String getID(String url){

        Pattern pattern = Pattern.compile("/(\\d+)/index.htm");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


    */


    public void  getContent(Response response){
        JXDocument doc= response.document();
        try {
            Object sel = doc.sel("//div[@id='contentmain']");
            // 将获取到的内容从HTML格式转换为Markdown格式
            Remark remark = new Remark();
            //cd.setContent(remark.convert(sel.toString()));
            String url = response.getUrl();
            String id = getCode(url);
            String convert = remark.convert(sel.toString());
            NovelChapterEntity chapterEntity = novelChapterDao.selectById(id);

            if(null==chapterEntity){
                chapterEntity=new NovelChapterEntity();
                chapterEntity.setId(id);
                chapterEntity.setContent(convert);
                novelChapterDao.insert(chapterEntity);
            }else {
                chapterEntity=new NovelChapterEntity();
                chapterEntity.setId(id);
                chapterEntity.setContent(convert);
                chapterEntity.setContent(convert);
                novelChapterDao.updateById(chapterEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getID(String url){

    Pattern pattern = Pattern.compile("/(\\d+)/index.htm");
    Matcher matcher = pattern.matcher(url);
    if (matcher.find()) {
        return matcher.group(1);
    } else {
        return null;
    }
}
private  String uuid(){
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replace("-", "").substring(0, 8);
}
private String getCode(String text){

    //text=  text.replace("https://www.wenku8.net/book/","");
    String regex = "(\\d+)\\.htm";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    if (matcher.find()) {
        return matcher.group(1);
    } else {
        return null;
    }
}
private String originValue(Object value){
    return value.toString().split("：")[1];
}

}
