package com.yozora.anime.entity;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-04-15 13:46:15
 */
@Data
@TableName("novel_chapter")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NovelChapterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 * 章节名称
	 */
	private String chaperName;
	/**
	 *
	 */
	private String bookId;
	/**
	 *
	 */
	private String content;
	/**
	 *
	 */
	private String pId;

	@TableField(exist = false)
	private List<NovelChapterEntity> children;
}
