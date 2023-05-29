package com.yozora.anime.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.NovelEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.JdbcType;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-15 13:46:15
 */
@Mapper
public interface NovelDao extends BaseMapper<NovelEntity> {



    List<NovelEntity> selectLightNovelWithDetails(Page<NovelEntity> page, @Param("sortField") String sortField,
                                                  @Param("keyword") String keyword,
                                                  @Param("order") String order,
                                                  @Param("tags") List<String> tags,
                                                  @Param("status") String status
    );


    List<NovelEntity> selectNovelByTags(@Param("tag")String tag);
}
