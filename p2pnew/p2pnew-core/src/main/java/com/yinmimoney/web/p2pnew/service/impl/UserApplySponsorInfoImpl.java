package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.UserApplySponsorInfoMapper;
import com.yinmimoney.web.p2pnew.pojo.UserApplySponsorInfo;
import com.yinmimoney.web.p2pnew.service.IUserApplySponsorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserApplySponsorInfoImpl extends BaseServiceImpl<UserApplySponsorInfo, UserApplySponsorInfoMapper, java.lang.Integer> implements IUserApplySponsorInfo {
    @Autowired
    private UserApplySponsorInfoMapper userApplySponsorInfoMapper;

    protected UserApplySponsorInfoMapper getDao() {
        return userApplySponsorInfoMapper;
    }
}