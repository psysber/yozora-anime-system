package com.yozora.anime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-29 09:46:33
 */
@Data

@ApiModel
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@TableName("novel_detail")
public class NovelDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String bookId;
	/**
	 * 评论数
	 */
	private Long commentNum;
	/**
	 * 收藏数
	 */
	private Long collectNum;
	/**
	 * 点击数
	 */
	private Long hitNum;

	/**
	 * 状态
	 */
	private int status;

	/**
	 *创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date createAt;

	/**
	 *最后更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date updateAt;

}
