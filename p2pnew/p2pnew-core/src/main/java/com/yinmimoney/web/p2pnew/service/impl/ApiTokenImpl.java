package com.yinmimoney.web.p2pnew.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.dao.ApiTokenMapper;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.util.DateUtil;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Service
public class ApiTokenImpl extends BaseServiceImpl<ApiToken, ApiTokenMapper, java.lang.Integer> implements IApiToken {
    @Autowired
    private ApiTokenMapper apiTokenMapper;

    protected ApiTokenMapper getDao() {
        return apiTokenMapper;
    }

	@Override
	public void processApiToken(String deviceId, String deviceName, String _token, Admin admin) {
		// 新设备登录，更新原来token为另外设备登录状态
		ApiToken condition = new ApiToken();
		condition.setUserCode(admin.getUserCode());
		condition.and(Expressions.ne("deviceId", deviceId));
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		ApiToken update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByCondition(update, condition);
		// 同设备登录，更新token状态为失效
		condition = new ApiToken();
		condition.setUserCode(admin.getUserCode());
		condition.setDeviceId(deviceId);
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_DISABLED.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByCondition(update, condition);
		// 插入新token信息
		ApiToken insert = new ApiToken();
		insert.setDeviceId(deviceId);
		insert.setDeviceName(deviceName);
		insert.setToken(_token);
		insert.setUserCode(admin.getUserCode());
		insert.setDateAdd(DateUtil.getNow());
		insert.setDateUpdate(DateUtil.getNow());
		apiTokenMapper.insertSelective(insert);
	}

	@Override
	public void updateApiTokenStatus(ApiToken apiToken, EnumApiTokenStatus enumApiTokenStatus) {
		ApiToken update = new ApiToken();
		update.setId(apiToken.getId());
		update.setStatus(enumApiTokenStatus.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByPrimaryKeySelective(update);
	}

}