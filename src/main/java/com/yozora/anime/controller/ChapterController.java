package com.yozora.anime.controller;


import com.yozora.anime.entity.NovelChapterEntity;
import com.yozora.anime.service.NovelChapterService;
import com.yozora.anime.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chapter")
@Api(value = "chapter", tags = "章节")
public class ChapterController{
    private final NovelChapterService chapterService;

    @ApiOperation(value = "根据id获取实体",notes = "getOne")
    @GetMapping("/id/{id}")
    public R<NovelChapterEntity> getOne(@PathVariable String  id){
        return R.ok(chapterService.getById(id));
    }

    @ApiOperation(value = "根据bookId查询章节列表")
    @GetMapping("/bookid/{bookId}")
    public R<List> getListByBookId(@PathVariable String bookId){
        return R.ok(chapterService.selectNovelChapterEntityList(bookId));
    }
}
