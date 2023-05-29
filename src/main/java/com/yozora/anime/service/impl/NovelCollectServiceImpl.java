package com.yozora.anime.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yozora.anime.dao.NovelCollectDao;
import com.yozora.anime.entity.NovelCollectEntity;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.entity.NovelUsersEntity;
import com.yozora.anime.service.NovelCollectService;
import com.yozora.anime.service.NovelUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("novelCollectService")
public class NovelCollectServiceImpl extends ServiceImpl<NovelCollectDao, NovelCollectEntity> implements NovelCollectService {

    private final NovelCollectDao novelCollectDao;
    private final NovelUsersService usersService;
    @Override
    public Page<NovelEntity> selectCollection(Page<NovelEntity> page) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null!=principal){
            NovelUsersEntity usersEntity = usersService.getOne(Wrappers.<NovelUsersEntity>query().eq("username", principal));
            page.setRecords(novelCollectDao.selectCollection(usersEntity.getId()));
            return page;
        }
        return null;
    }
}
