package com.yinmimoney.web.p2pnew.util;

public enum StatusCode {

	SUCCESS(0, "success"), //

	ERROR(-1, "服务器内部错误"), //

	ERROR_403(403, "禁止访问"), //
	ERROR_404(404, "资源不存在"), //
	ERROR_405(405, "请求方法不支持"), //

	ERROR_REQUEST_JSON(600, "请求参数错误，需要以JSON格式发送参数"), //
	REQUIRE_PARAM(601, "缺少参数"), //
	REQUIRE_TOKEN(602, "缺少token"), //
	REQUIRE_DATA_ERROR(603, "数据错误"), //
	REQUIRE_PARAM_ERROR(604, "参数错误"), //
	REQUIRE_PARAM_DECIPHERING_ERROR(605, "参数解密失败"), //

	CODE_ERROR_PIC(700, "图片验证码错误"), //
	CODE_ERROR_MOBILE(701, "短信验证码错误"), //
	CODE_ERROR_MOBILE_TYPE(702, "请求类型不存在"), //
	CODE_EXPIRED_MOBILE(703, "短信验证码已失效，请重新发送"), //
	CODE_SEND_LIMIT_MOBILE(704, "短信验证码发送次数过多，请稍后发送"), //

	ERROR_USER_OTHER_DEVICE_IS_LOGIN(3716, "用户另外一台设备登录，请重新登录"), //
	ERROR_USER_TOKEN_EXPIRED(3717, "用户信息过期，请重新登录"), //
	ERROR_USER_TOKEN_DISABLED(3718, "用户信息失效，请重新登录"),//
	;

	private int code;
	private String msg;

	StatusCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
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
}
