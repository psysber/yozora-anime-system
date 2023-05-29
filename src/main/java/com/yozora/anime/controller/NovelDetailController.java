package com.yozora.anime.controller;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.NovelDetailEntity;
import com.yozora.anime.service.NovelDetailService;
import com.yozora.anime.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-29 11:00:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/novelDetail")
@Api(value = "novelDetail",tags = "小说详情")
public class NovelDetailController {

    private final NovelDetailService lightNovelDetailService;
    private final String[] sortArray={"total_score","comment_score","collect_score","update_score","hit_score"};



    private void addOrder(Page<NovelDetailEntity> page, int value) {
            if(value<0||value>4) return;
            OrderItem orderItem = OrderItem.desc(sortArray[value]);
            page.addOrder(orderItem);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		NovelDetailEntity lightNovelDetail = lightNovelDetailService.getById(id);
        return R.ok(lightNovelDetail);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @RequestMapping("/save")
    public R save(@RequestBody NovelDetailEntity lightNovelDetail){
		lightNovelDetailService.save(lightNovelDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @RequestMapping("/update")
    public R update(@RequestBody NovelDetailEntity lightNovelDetail){
		lightNovelDetailService.updateById(lightNovelDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		lightNovelDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
