package com.yinmimoney.web.p2pnew.enums;

import cc.s2m.web.utils.webUtils.StaticProp;

/**
 * 
 * @Description redis缓存key枚举类
 * @author wzq
 * @date 2018年6月26日 下午3:22:37
 */
public enum EnumRedisKeys {

	/** 管理员登录 */
	ADMIN_SESSION("admin_", "管理员后台登录"),

	/** 用户登录 */
	USER_SESSION("user_", "用户登录"),

	/** 图形验证码 */
	PIC_CODE("picCode_", "图形验证码"),

	/** 短信验证码 */
	MOBILE_CODE("mobileCode_", "短信验证码"),

	/** CSRF Token */
	CSRF_TOKEN("csrftoken_", "防止CSRF攻击的token"),

	/** REST Token */
	REST_TOKEN("resttoken_", "REST调用接口"),

	/** 登陆锁定 */
	USERNAME_LOCK("lock_username_", "登陆次数超过锁定登陆"),

	/** 交易密码锁定 */
	PAY_PWD_LOCK("pay_pwd_lock_", "交易密码输入错误"),

	/** 累计投资 */
	TOTAL_TENDER("total_tender", "累计投资"),

	/** 用户总数 */
	TOTAL_USER("total_user", "用户总数"),

	/** 收益累计 */
	TOTAL_EARN("total_earn", "收益累计"),

	/** 发标总数 */
	TOTAL_BORROW("total_borrow", "发标总数"),

	/** 用户当月提现次数 */
	USER_COUNT_CASH_MONTH("user_count_cash_month_", "用户当月提现次数");

	private String key; // 前缀

	private String profile; // 备注

	private EnumRedisKeys(String key, String profile) {
		this.key = key;
		this.profile = profile;
	}

	public String getKey() {
		return StaticProp.cookieID + "_" + key;
	}

	public String getProfile() {
		return profile;
	}
}
