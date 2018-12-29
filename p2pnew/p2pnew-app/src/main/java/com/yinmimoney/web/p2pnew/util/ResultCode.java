package com.yinmimoney.web.p2pnew.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResultCode implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;
	private String msg;
	private Object data;

	public ResultCode() {
	}

	public ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultCode(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultCode(StatusCode statusCode) {
		this.code = statusCode.getCode();
		this.msg = statusCode.getMsg();
	}

	public ResultCode(StatusCode statusCode, Object o) {
		this.code = statusCode.getCode();
		this.msg = statusCode.getMsg();
		this.data = o;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
