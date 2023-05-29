package com.yozora.anime.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yozora.anime.entity.NovelChapterEntity;
import com.yozora.anime.service.NovelChapterService;
import com.yozora.anime.dao.NovelChapterDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;


@Service("novelChapterService")
@RequiredArgsConstructor
public class NovelChapterServiceImpl extends ServiceImpl<NovelChapterDao, NovelChapterEntity> implements NovelChapterService {

    private final NovelChapterDao novelChapterDao;

    public List<NovelChapterEntity> selectNovelChapterEntityList(String bookId){
        QueryWrapper<NovelChapterEntity> wrapper=new QueryWrapper();
        wrapper.select("id","p_id","chaper_Name").eq("book_id",bookId);
        return novelChapterDao.selectList(wrapper);
    }


}
