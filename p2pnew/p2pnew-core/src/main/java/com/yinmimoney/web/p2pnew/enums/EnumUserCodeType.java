package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 生成用户编号类型枚举类
 * @author wzq
 * @date 2018年6月7日 上午11:34:43
 */
public enum EnumUserCodeType {

	/** 后台管理员 **/
	TYPE_ADMIN("a", "后台管理员");

	private String type;

	private String name;

	private EnumUserCodeType(String type, String name) {
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
