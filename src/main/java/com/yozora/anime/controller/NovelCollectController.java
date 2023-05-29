package com.yozora.anime.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.NovelCollectEntity;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.service.NovelCollectService;
import com.yozora.anime.service.NovelService;
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
 * @date 2023-05-11 22:11:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/novelcollect")
@Api(value = "novelCollect",tags = "")
public class NovelCollectController {

    private final NovelCollectService novelCollectService;
    private final NovelService novelService;
    /**
     * 列表
     */

    @ApiOperation(value="分页查询",notes="分页查询")
    @PostMapping("/page")
    public R list(@RequestBody Page<NovelEntity> page){

        return  R.ok(novelCollectService.selectCollection(page));

    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		NovelCollectEntity novelCollect = novelCollectService.getById(id);
        return R.ok(novelCollect);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @RequestMapping("/save")
    public R save(@RequestBody NovelCollectEntity novelCollect){
		novelCollectService.save(novelCollect);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @RequestMapping("/update")
    public R update(@RequestBody NovelCollectEntity novelCollect){
		novelCollectService.updateById(novelCollect);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		novelCollectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    @ApiOperation(value = "保存或取消")
    @PostMapping("/saveOrupdate")
    public R saveOrupdate(@RequestBody NovelCollectEntity novelCollectEntity){
        novelCollectService.saveOrUpdate(novelCollectEntity,Wrappers.<NovelCollectEntity>query()
                .eq("user_id", novelCollectEntity.getUserId())
                .eq("book_id", novelCollectEntity.getBookId()));
        return R.ok();

    }

    @ApiOperation(value = "查询状态")
    @PostMapping("/getStatus")
    public R getStatus(@RequestBody NovelCollectEntity novelCollectEntity){

        NovelCollectEntity collectEntity = novelCollectService.getOne(Wrappers.<NovelCollectEntity>query()
                .eq("user_id", novelCollectEntity.getUserId())
                .eq("book_id", novelCollectEntity.getBookId()));

        return R.ok(collectEntity);

    }
}
