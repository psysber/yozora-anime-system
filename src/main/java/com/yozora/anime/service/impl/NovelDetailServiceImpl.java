package com.yozora.anime.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.yozora.anime.dao.NovelDetailDao;
import com.yozora.anime.entity.NovelDetailEntity;
import com.yozora.anime.service.NovelDetailService;

import java.util.List;


@Service("lightNovelDetailService")
@RequiredArgsConstructor
public class NovelDetailServiceImpl extends ServiceImpl<NovelDetailDao, NovelDetailEntity> implements NovelDetailService {

}
