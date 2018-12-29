package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.UserMapper;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserImpl extends BaseServiceImpl<User, UserMapper, java.lang.Integer> implements IUser {
    @Autowired
    private UserMapper userMapper;

    protected UserMapper getDao() {
        return userMapper;
    }
}