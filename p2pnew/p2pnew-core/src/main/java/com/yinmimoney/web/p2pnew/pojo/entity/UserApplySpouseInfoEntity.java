package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserApplySpouseInfoEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String applyNo;
	private java.lang.String spouseRealName;
	private java.lang.String spouseMobilePhone;
	private java.lang.String spouseIdCard;
	private java.lang.String spousePhoto;
	private java.lang.String spouseCompany;
	private java.lang.String spouseCompanyPhone;
	private java.lang.String spouseCompanyAddress;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
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
    public java.lang.String getApplyNo() {
        return applyNo;
    }
	public void setApplyNo(java.lang.String applyNo) {
        this.applyNo = applyNo;
    }
    public java.lang.String getSpouseRealName() {
        return spouseRealName;
    }
	public void setSpouseRealName(java.lang.String spouseRealName) {
        this.spouseRealName = spouseRealName;
    }
    public java.lang.String getSpouseMobilePhone() {
        return spouseMobilePhone;
    }
	public void setSpouseMobilePhone(java.lang.String spouseMobilePhone) {
        this.spouseMobilePhone = spouseMobilePhone;
    }
    public java.lang.String getSpouseIdCard() {
        return spouseIdCard;
    }
	public void setSpouseIdCard(java.lang.String spouseIdCard) {
        this.spouseIdCard = spouseIdCard;
    }
    public java.lang.String getSpousePhoto() {
        return spousePhoto;
    }
	public void setSpousePhoto(java.lang.String spousePhoto) {
        this.spousePhoto = spousePhoto;
    }
    public java.lang.String getSpouseCompany() {
        return spouseCompany;
    }
	public void setSpouseCompany(java.lang.String spouseCompany) {
        this.spouseCompany = spouseCompany;
    }
    public java.lang.String getSpouseCompanyPhone() {
        return spouseCompanyPhone;
    }
	public void setSpouseCompanyPhone(java.lang.String spouseCompanyPhone) {
        this.spouseCompanyPhone = spouseCompanyPhone;
    }
    public java.lang.String getSpouseCompanyAddress() {
        return spouseCompanyAddress;
    }
	public void setSpouseCompanyAddress(java.lang.String spouseCompanyAddress) {
        this.spouseCompanyAddress = spouseCompanyAddress;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}