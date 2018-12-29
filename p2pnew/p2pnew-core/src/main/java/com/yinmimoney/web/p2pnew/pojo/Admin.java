package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.AdminEntity;

public class Admin extends AdminEntity {
	private static final long serialVersionUID = 1L;

	private String statusStr;// 状态

	public String getStatusStr() {
		if (getStatus() == null) {
			return statusStr;
		}
		switch (getStatus()) {
		case 0:
			statusStr = "正常";
			break;
		case 1:
			statusStr = "锁定";
			break;
		case 2:
			statusStr = "离职";
			break;
		default:
			statusStr = "-";
			break;
		}
		return statusStr;
	}

}