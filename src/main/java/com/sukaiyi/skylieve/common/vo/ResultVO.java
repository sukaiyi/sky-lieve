package com.sukaiyi.skylieve.common.vo;

import lombok.Data;

/**
 * @author sukaiyi
 */
@Data
public class ResultVO {

	private int code;
	private String message;
	private Object obj;

	public ResultVO(int code, String message, Object obj) {
		this.code = code;
		this.message = message;
		this.obj = obj;
	}

	public static ResultVO ok(String msg, Object obj) {
		return new ResultVO(0, msg, obj);
	}

	public static ResultVO ok(Object obj) {
		return new ResultVO(0, null, obj);
	}

	public static ResultVO error(int code, String message) {
		return new ResultVO(code, message, null);
	}
}
