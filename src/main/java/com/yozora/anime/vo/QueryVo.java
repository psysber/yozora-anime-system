package com.yozora.anime.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class QueryVo<E> {
    private Page<E> page;
    private E  entity;

}
