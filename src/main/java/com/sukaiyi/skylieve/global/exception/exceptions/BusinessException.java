package com.sukaiyi.skylieve.global.exception.exceptions;

import com.sukaiyi.skylieve.global.exception.exceptions.code.ExceptionCode;

/**
 * @author sukaiyi
 */
public class BusinessException extends Exception {
	private int code = ExceptionCode.OTHER;

	public BusinessException() {
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
