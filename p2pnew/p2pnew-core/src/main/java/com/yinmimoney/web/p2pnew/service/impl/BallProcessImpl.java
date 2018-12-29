package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.BallProcessMapper;
import com.yinmimoney.web.p2pnew.pojo.BallProcess;
import com.yinmimoney.web.p2pnew.service.IBallProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class BallProcessImpl extends BaseServiceImpl<BallProcess, BallProcessMapper, Integer> implements IBallProcess {
    @Autowired
    private BallProcessMapper ballProcessMapper;

    protected BallProcessMapper getDao() {
        return ballProcessMapper;
    }
}