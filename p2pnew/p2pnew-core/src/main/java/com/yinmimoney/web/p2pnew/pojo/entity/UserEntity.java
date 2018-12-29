package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String realName;
	private java.lang.Integer sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date birthday;
	private java.lang.String idCard;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date registerTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getUserCode() {
        return userCode;
    }
	public void setUserCode(java.lang.String userCode) {
        this.userCode = userCode;
    }
    public java.lang.String getRealName() {
        return realName;
    }
	public void setRealName(java.lang.String realName) {
        this.realName = realName;
    }
    public java.lang.Integer getSex() {
        return sex;
    }
	public void setSex(java.lang.Integer sex) {
        this.sex = sex;
    }
    public java.util.Date getBirthday() {
        return birthday;
    }
	public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }
    public java.lang.String getIdCard() {
        return idCard;
    }
	public void setIdCard(java.lang.String idCard) {
        this.idCard = idCard;
    }
    public java.util.Date getRegisterTime() {
        return registerTime;
    }
	public void setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}