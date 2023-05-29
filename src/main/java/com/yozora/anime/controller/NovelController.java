package com.yozora.anime.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yozora.anime.entity.NovelEntity;
import com.yozora.anime.entity.NovelTagEntity;
import com.yozora.anime.service.NovelDetailService;
import com.yozora.anime.service.NovelService;
import com.yozora.anime.service.NovelChapterService;
import com.yozora.anime.service.NovelTagService;
import com.yozora.anime.utils.R;
import com.yozora.anime.vo.QueryVo;
import com.yozora.anime.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lightNovel")
@Api(value = "nobel",tags = "小说")
public class NovelController {

    private final NovelService novelService;
    private final NovelChapterService novelChapterService;

    private final NovelTagService novelTagService;

    /**
     * 分页
     * @return
     */
    @ApiOperation(value="分页查询",notes="分页查询")
    @PostMapping("/page")
    public R getPage(@RequestBody SearchVo<NovelEntity> searchVo){
        Page<NovelEntity> page = searchVo.getPage();

        page.setRecords(novelService.selectLightNovelWithDetails(
                searchVo.getKeyword(),
                searchVo.getSortField(),
                searchVo.getOrder(),
                page,
                searchVo.getTags(),
                searchVo.getStatus()
        ));
        return R.ok(page);
    }

    @ApiOperation(value = "获取详细信息",notes = "getOne")
    @GetMapping("/id/{id}")
    public R getOne(@PathVariable String id){
        NovelEntity novelEntity = novelService.getById(id);
        novelEntity.setChapters(novelChapterService.selectNovelChapterEntityList(id));
        return R.ok(novelEntity);
    }

    @ApiOperation(value = "根据ids获取列表",notes = "List")
    @PostMapping("/list")
    public R List(@RequestBody List<String> ids){
        return  R.ok(novelService.list(
                Wrappers.<NovelEntity>query().in("id",ids)));
    }
    @ApiOperation("获取标签")
    @GetMapping("/novelsByTags")
    public R novelsByTags(){

        return R.ok(novelService.selectNovelByTags());

    }

    @ApiOperation("获取最后更新小说")
    @GetMapping("/lastUpdate")
    public R NovelLastUpdate(){
        Page<NovelEntity>page=new Page<>();
        page.setSize(60);
        page.setCurrent(1);
        page.addOrder(OrderItem.desc("last_update"));

        return R.ok(novelService.page(page,Wrappers.<NovelEntity>query().select("id","title")));
    }
}
