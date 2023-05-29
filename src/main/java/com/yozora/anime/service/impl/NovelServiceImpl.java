package com.yozora.anime.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yozora.anime.dao.NovelDao;
import com.yozora.anime.dao.NovelTagDao;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.entity.NovelTagEntity;
import com.yozora.anime.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("lightNovelService")
@RequiredArgsConstructor
public class NovelServiceImpl extends ServiceImpl<NovelDao, NovelEntity> implements NovelService {

    private final NovelDao novelDao;

    private final NovelTagDao novelTagDao;
    @Override
    public List<NovelEntity> selectLightNovelWithDetails(String keyword, String sortField,String order,
                                                         Page<NovelEntity> page,
                                                         List<String> tags,
                                                         String status
    ) {


        return novelDao.selectLightNovelWithDetails(page,sortField,keyword,order,tags,status);
    }


    public  List<NovelTagEntity> selectNovelByTags(){

        List<String> list = novelTagDao.selectNovelTags();
        List<String> collect = list.stream().limit(20).collect(Collectors.toList());
        List<NovelTagEntity> entities=new ArrayList<>();
        for(String str:collect){

            NovelTagEntity novelTagEntity=new NovelTagEntity();
            novelTagEntity.setTag(str);
            novelTagEntity.setNovels(  novelDao.selectNovelByTags(str));
            entities.add(novelTagEntity);
        }
        return entities;
    }

}
