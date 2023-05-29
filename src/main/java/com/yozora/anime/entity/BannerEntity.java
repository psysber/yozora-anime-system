package com.yozora.anime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data

@ApiModel
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@TableName("banner")
public class BannerEntity implements Serializable {
    @TableId
    private Integer id;
    private String img;
    /**
     * 类型 1:首页banner 2: 首页副banner 3:专题banner
     */
    private Integer type;
    private String bookId;

}
