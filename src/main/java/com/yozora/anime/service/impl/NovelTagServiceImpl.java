package com.yozora.anime.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yozora.anime.dao.NovelTagDao;
import com.yozora.anime.entity.NovelTagEntity;
import com.yozora.anime.service.NovelTagService;

@RequiredArgsConstructor
@Service("novelTagService")
public class NovelTagServiceImpl extends ServiceImpl<NovelTagDao, NovelTagEntity> implements NovelTagService {

    private final NovelTagDao novelTagDao;


    @Override
    public List<String> selectNovelTags() {
        return novelTagDao.selectNovelTags();
    }
}
