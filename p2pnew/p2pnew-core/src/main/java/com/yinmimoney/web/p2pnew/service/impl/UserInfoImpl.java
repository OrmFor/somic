package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.UserInfoMapper;
import com.yinmimoney.web.p2pnew.pojo.UserInfo;
import com.yinmimoney.web.p2pnew.service.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserInfoImpl extends BaseServiceImpl<UserInfo, UserInfoMapper, java.lang.Integer> implements IUserInfo {
    @Autowired
    private UserInfoMapper userInfoMapper;

    protected UserInfoMapper getDao() {
        return userInfoMapper;
    }
}