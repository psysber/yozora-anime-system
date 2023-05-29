package com.yozora.anime.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-16 17:12:15
 */
@Data
@TableName("novel_tag")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NovelTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String bookId;
	/**
	 *
	 */
	private String tag;
	/**
	 *
	 */
	private Date createAt;
	/**
	 *
	 */
	private Date updateAt;

	@TableField(select = false)
	private List<NovelEntity> novels;
}
