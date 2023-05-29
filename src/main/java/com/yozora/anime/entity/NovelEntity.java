package com.yozora.anime.entity;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @date 2023-04-15 13:46:15
 */
@Data
@TableName("novel")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NovelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *标题
	 */
	private String title;
	/**
	 *作者
	 */
	private String author;
	/**
	 *状态
	 */
	private String novelStatus;
	/**
	 *出版社
	 */
	private String publishing;
	/**
	 *最后更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date lastUpdate;
	/**
	 *小说长度
	 */
	private String novelLen;

	/**
	 * img
	 */
	private String img;

	/**
	 * 简介信息
	 */
	private String info;

	/**
	 * 小说tag
	 */
	private String tag;
	/**
	 * 章节信息
	 */
	@TableField(exist = false)
	private List<NovelChapterEntity> chapters;



}
