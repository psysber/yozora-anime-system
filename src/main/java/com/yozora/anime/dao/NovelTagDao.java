package com.yozora.anime.dao;

import com.yozora.anime.entity.NovelTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-16 17:12:15
 */
@Mapper
public interface NovelTagDao extends BaseMapper<NovelTagEntity> {

    List<String> selectNovelTags();


}
