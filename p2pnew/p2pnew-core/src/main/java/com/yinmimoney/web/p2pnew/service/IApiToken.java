package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IApiToken extends BaseService<ApiToken, java.lang.Integer> {
	
	/**
	 * 
	 * @Title processApiToken
	 * @Description app登录处理token
	 * @author wzq
	 * @date 2018年6月7日 下午1:44:03
	 * @param deviceId 设备id
	 * @param deviceName 设备名
	 * @param _token token
	 * @param admin 用户信息
	 * @return void
	 */
	public void processApiToken(String deviceId, String deviceName, String _token, Admin admin);

	/**
	 * 
	 * @Title updateApiTokenStatus
	 * @Description 更新token状态
	 * @author wzq
	 * @date 2018年6月8日 上午11:09:38
	 * @param apiToken
	 * @param enumApiTokenStatus
	 * @return void
	 */
	public void updateApiTokenStatus(ApiToken apiToken, EnumApiTokenStatus enumApiTokenStatus);

}