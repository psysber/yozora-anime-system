package com.yozora.anime.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.yozora.anime.entity.NovelTagEntity;
import com.yozora.anime.service.NovelTagService;
import com.yozora.anime.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.yozora.anime.vo.QueryVo;



/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-16 17:12:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@Api(value = "novelTag",tags = "")
public class NovelTagController {

    private final NovelTagService novelTagService;

    /**
     * 列表
     */

    @ApiOperation(value="分页查询",notes="分页查询")
    @PostMapping("/page")
    public R list(@RequestBody QueryVo<NovelTagEntity> entityQueryVo){
        return  R.ok(novelTagService.page(entityQueryVo.getPage(),Wrappers.query(entityQueryVo.getEntity())));

    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		NovelTagEntity novelTag = novelTagService.getById(id);
        return R.ok(novelTag);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @RequestMapping("/save")
    public R save(@RequestBody NovelTagEntity novelTag){
		novelTagService.save(novelTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @RequestMapping("/update")
    public R update(@RequestBody NovelTagEntity novelTag){
		novelTagService.updateById(novelTag);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		novelTagService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @ApiOperation(value = "获取所有标签")
    @GetMapping("/list")
    public R list(){
        return R.ok(novelTagService.selectNovelTags());
    }
}
