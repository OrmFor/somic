package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.UserApplySpouseInfoMapper;
import com.yinmimoney.web.p2pnew.pojo.UserApplySpouseInfo;
import com.yinmimoney.web.p2pnew.service.IUserApplySpouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserApplySpouseInfoImpl extends BaseServiceImpl<UserApplySpouseInfo, UserApplySpouseInfoMapper, java.lang.Integer> implements IUserApplySpouseInfo {
    @Autowired
    private UserApplySpouseInfoMapper userApplySpouseInfoMapper;

    protected UserApplySpouseInfoMapper getDao() {
        return userApplySpouseInfoMapper;
    }
}