package com.yozora.anime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author singni
 * @email singni@outlook.com
 * @date 2023-05-11 22:11:43
 */
@Data
@TableName("novel_collect")
public class NovelCollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	private String bookId;
	/**
	 *
	 */
	private Long userId;

	private Integer status;
	/**
	 *
	 */
	private Date createdAt;
	/**
	 *
	 */
	private Date updatedAt;

}
