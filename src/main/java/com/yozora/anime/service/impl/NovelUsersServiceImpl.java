package com.yozora.anime.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.yozora.anime.dao.NovelUsersDao;
import com.yozora.anime.entity.NovelUsersEntity;
import com.yozora.anime.service.NovelUsersService;

@RequiredArgsConstructor
@Service("novelUsersService")
public class NovelUsersServiceImpl extends ServiceImpl<NovelUsersDao, NovelUsersEntity> implements NovelUsersService , UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String nu) throws UsernameNotFoundException {
        NovelUsersEntity novelUsersEntity=new NovelUsersEntity();
        novelUsersEntity.setUsername(nu);
        NovelUsersEntity user = getOne(Wrappers.query(novelUsersEntity));
        if (user == null) {
            throw new UsernameNotFoundException("UserName or Password Error");
        }
        return user;
    }
}

