package com.yozora.anime.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.BannerEntity;
import com.yozora.anime.service.BannerService;
import com.yozora.anime.utils.R;
import com.yozora.anime.vo.QueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banner")
@Api(tags = "banner图", value = "banner")
public class BannerController{

    private final BannerService bannerService;


    @PostMapping("/list")
    @ApiOperation(value = "分页查询列表")
    private R list(@RequestBody QueryVo<BannerEntity> entityQueryVo){
        return  R.ok(bannerService.page(entityQueryVo.getPage(),Wrappers.query(entityQueryVo.getEntity())));
    }
}
