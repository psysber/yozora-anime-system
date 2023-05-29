package com.yozora.anime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
@TableName("novel_users")
public class NovelUsersEntity implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */

	@Email(message = "邮箱格式不正确")
	private String email;
	/**
	 *
	 */
	@NotNull(message = "用户名不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户只能由大小写字母、数字和下划线组成")
	@Size(min = 4, max = 20, message = "用户名长度应为4~20个字符")
	private String username;
	/**
	 *
	 */
	@NotNull(message = "密码不能为空")

	// 更复杂的密码规则可以使用正则表达式进行匹配
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()-=_+;':\",./<>?])(?=\\S+$).{6,32}$", message = "密码必须包含字母、数字和特殊符号且长度是6-32")
	private String password;


	private String level;
	/**
	 *
	 */
	private String sign;
	/**
	 *
	 */
	private Long uid;

	private String img;

	private String nickname;
	/**
	 *
	 */
	private Date createdAt;
	/**
	 *
	 */
	private Date updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
