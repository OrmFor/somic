package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.AdminMapper;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdminImpl extends BaseServiceImpl<Admin, AdminMapper, java.lang.Integer> implements IAdmin {
    @Autowired
    private AdminMapper adminMapper;

    protected AdminMapper getDao() {
        return adminMapper;
    }
}