package com.yozora.anime.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yozora.anime.dao.BannerDao;
import com.yozora.anime.entity.BannerEntity;
import com.yozora.anime.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("bannerService")
@RequiredArgsConstructor
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {

}
