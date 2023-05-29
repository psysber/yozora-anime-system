package com.yozora.anime.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.entity.NovelTagEntity;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-15 13:46:15
 */
public interface NovelService extends IService<NovelEntity> {
    List<NovelEntity> selectLightNovelWithDetails(String keyword, String sortField, String order,
                                                  Page<NovelEntity> page,
                                                  List<String> tags,
                                                  String status
    );

  List<NovelTagEntity> selectNovelByTags();
}
