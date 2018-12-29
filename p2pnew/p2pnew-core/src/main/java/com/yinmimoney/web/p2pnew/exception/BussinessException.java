package com.yinmimoney.web.p2pnew.exception;

/**
 * 
 * @Description 业务处理异常类
 * @author wzq
 * @date 2018年5月31日 下午1:47:36
 */
public class BussinessException extends RuntimeException {

	private Integer code;

	private String message;

	public BussinessException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
