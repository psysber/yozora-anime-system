package com.yozora.anime.spider;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.overzealous.remark.Remark;
import com.yozora.anime.dao.NovelDao;

import com.yozora.anime.entity.NovelChapterEntity;
import com.yozora.anime.entity.NovelEntity;

import com.yozora.anime.service.NovelChapterService;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Crawler(name = "wenku8")
@RequiredArgsConstructor
public class Wenku8Spider extends BaseSeimiCrawler {

    private  final NovelDao lightNovelDao;

    @Override
    public String[] startUrls() {
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.orderByDesc("last_update");
         List<NovelEntity> lightNovelEntities = lightNovelDao.selectList(queryWrapper);
        String [] urls=new String[lightNovelEntities.size()];
        int i=0;
        String up="0";
        for (NovelEntity ln : lightNovelEntities){
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
        return urls;
    }
   private static Set<String> novelTags=new HashSet<>();
    @Override
    public void start(Response response) {
        try {
            //getNovelInfo(response);
           // getChapterInfo(response);
            updateContent();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void getNovelInfo(Response response) throws ParseException {
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

            String tagXpt="//*[@id=\"content\"]/div[1]/table[2]/tbody/tr/td[2]/span[1]/b/text()";
            List<Object> tags=doc.sel(tagXpt);
            if(tags.size()!=0){
                String elem =  tags.get(0).toString();
                String tag = elem.replace("作品Tags：","");
                lightNovelEntity.setTag(tag);
            }


            lightNovelDao.insert(lightNovelEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private final NovelChapterService novelChapterService;

    //获取章节信息
    public void getChapterInfo(Response response){
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
                novelChapterService.saveOrUpdate(novelChapterEntity);

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
                novelChapterService.saveOrUpdate(novelChapterEntity);


            }


        }

    }

    public void  saveUpdateContent(Response response){
        JXDocument doc= response.document();
        try {
            Object sel = doc.sel("//div[@id='contentmain']");
            // 将获取到的内容从HTML格式转换为Markdown格式
            Remark remark = new Remark();
            //cd.setContent(remark.convert(sel.toString()));
            String url = response.getUrl();
            String id = getCode(url);
            String convert = remark.convert(sel.toString());
            NovelChapterEntity chapterEntity = novelChapterService.getById(id);

            if(null==chapterEntity){
                chapterEntity=new NovelChapterEntity();
            }
            chapterEntity.setId(id);
            chapterEntity.setContent(convert);
            chapterEntity.setContent(convert);
            novelChapterService.saveOrUpdate(chapterEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void updateContent(){
        QueryWrapper<NovelChapterEntity> queryWrapper=new QueryWrapper();
        queryWrapper.isNull(true, "content");
        //queryWrapper.orderByDesc("last_update");
        List<NovelChapterEntity> content = novelChapterService.list(queryWrapper);

        for (NovelChapterEntity  nc: content){
            String id = nc.getId();
            if(id.matches("[0-9]+")){
                String bookId = nc.getBookId();
                String contentId = getContentId(bookId);
                String url = "https://www.wenku8.net/novel/" + contentId + "/" + bookId + "/" + id + ".htm";
                push(new Request(url,Wenku8Spider::saveUpdateContent));
            }
        }


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

