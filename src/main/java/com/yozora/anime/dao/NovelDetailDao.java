package com.yozora.anime.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.NovelDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-29 09:46:33
 */
@Mapper
public interface NovelDetailDao extends BaseMapper<NovelDetailEntity> {

}
