package com.yozora.anime.dao;

import com.yozora.anime.entity.NovelCollectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yozora.anime.entity.NovelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-11 22:11:43
 */
@Mapper
public interface NovelCollectDao extends BaseMapper<NovelCollectEntity> {
    List<NovelEntity> selectCollection(@Param(value = "userId") Long userId);
}
