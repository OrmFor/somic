package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.BorrowApplyMapper;
import com.yinmimoney.web.p2pnew.pojo.BorrowApply;
import com.yinmimoney.web.p2pnew.service.IBorrowApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class BorrowApplyImpl extends BaseServiceImpl<BorrowApply, BorrowApplyMapper, java.lang.Integer> implements IBorrowApply {
    @Autowired
    private BorrowApplyMapper borrowApplyMapper;

    protected BorrowApplyMapper getDao() {
        return borrowApplyMapper;
    }
}