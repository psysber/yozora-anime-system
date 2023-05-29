package com.yozora.anime.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yozora.anime.entity.NovelTagEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-16 17:12:15
 */
public interface NovelTagService extends IService<NovelTagEntity> {

    List<String> selectNovelTags();
}

