package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.SConfig;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ISConfig extends BaseService<SConfig, java.lang.Integer> {

	SConfig getByNid(String nid);

	String getValueByNid(String nid);

}