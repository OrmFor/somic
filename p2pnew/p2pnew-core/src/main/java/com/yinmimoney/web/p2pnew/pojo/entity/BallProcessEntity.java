package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class BallProcessEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String processName;
	private java.math.BigDecimal processMoney;
	private Integer flag;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getProcessName() {
        return processName;
    }
	public void setProcessName(String processName) {
        this.processName = processName;
    }
    public java.math.BigDecimal getProcessMoney() {
        return processMoney;
    }
	public void setProcessMoney(java.math.BigDecimal processMoney) {
        this.processMoney = processMoney;
    }
    public Integer getFlag() {
        return flag;
    }
	public void setFlag(Integer flag) {
        this.flag = flag;
    }
}