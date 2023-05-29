package com.yozora.anime.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.yozora.anime.entity.NovelCollectEntity;
import com.yozora.anime.entity.NovelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-11 22:11:43
 */
public interface NovelCollectService extends IService<NovelCollectEntity> {

     Page<NovelEntity>selectCollection(Page<NovelEntity> page) ;

}

