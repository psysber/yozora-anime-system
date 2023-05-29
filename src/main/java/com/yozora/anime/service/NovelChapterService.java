package com.yozora.anime.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yozora.anime.entity.NovelChapterEntity;

import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-15 13:46:15
 */

public interface NovelChapterService extends IService<NovelChapterEntity> {

    List<NovelChapterEntity> selectNovelChapterEntityList(String bookId);
}

