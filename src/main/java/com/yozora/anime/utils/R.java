package com.yozora.anime.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
/**
 * 响应信息
 *
 * @param <T>
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "通用PI返回",description = "Common Api Response")
public class R<T> implements Serializable {
	private static final long serialVersionUID = 6457758587738313507L;
	private T data;
	private Integer code;
	private String msg;

	public static <T> R<T> ok() {
		return restResult(null, RET.SUCCESS, RET.SUCCESS.getMessage());
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, RET.SUCCESS, RET.SUCCESS.getMessage());
	}

	public static <T> R<T> ok(T data, RET code) {
		return restResult(data, code, code.getMessage());
	}


	public static <T> R<T> failed() {
		return restResult(null, RET.FAILED, RET.FAILED.getMessage());
	}

	public static <T> R<T> failed(RET code) {
		return restResult(null, code, code.getMessage());
	}

	public static <T> R<T> failed(T data, RET code) {
		return restResult(data, code, code.getMessage());
	}

	public static <T> R<T> failed(T data, RET code, String msg) {
		return restResult(data, code, msg);
	}

	private static <T> R<T> restResult(T data, RET code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code.getCode());
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}

