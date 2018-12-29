package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserApplySponsorInfoEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String applyNo;
	private java.lang.String sponsorRealName;
	private java.lang.String sponsorMobilePhone;
	private java.lang.String sponsorIdCard;
	private java.lang.String sponsorPhotoInfo;
	private java.lang.String sponsorCompany;
	private java.lang.String sponsorCompanyPhone;
	private java.lang.String sponsorCompanyAddress;
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
    public java.lang.String getSponsorRealName() {
        return sponsorRealName;
    }
	public void setSponsorRealName(java.lang.String sponsorRealName) {
        this.sponsorRealName = sponsorRealName;
    }
    public java.lang.String getSponsorMobilePhone() {
        return sponsorMobilePhone;
    }
	public void setSponsorMobilePhone(java.lang.String sponsorMobilePhone) {
        this.sponsorMobilePhone = sponsorMobilePhone;
    }
    public java.lang.String getSponsorIdCard() {
        return sponsorIdCard;
    }
	public void setSponsorIdCard(java.lang.String sponsorIdCard) {
        this.sponsorIdCard = sponsorIdCard;
    }
    public java.lang.String getSponsorPhotoInfo() {
        return sponsorPhotoInfo;
    }
	public void setSponsorPhotoInfo(java.lang.String sponsorPhotoInfo) {
        this.sponsorPhotoInfo = sponsorPhotoInfo;
    }
    public java.lang.String getSponsorCompany() {
        return sponsorCompany;
    }
	public void setSponsorCompany(java.lang.String sponsorCompany) {
        this.sponsorCompany = sponsorCompany;
    }
    public java.lang.String getSponsorCompanyPhone() {
        return sponsorCompanyPhone;
    }
	public void setSponsorCompanyPhone(java.lang.String sponsorCompanyPhone) {
        this.sponsorCompanyPhone = sponsorCompanyPhone;
    }
    public java.lang.String getSponsorCompanyAddress() {
        return sponsorCompanyAddress;
    }
	public void setSponsorCompanyAddress(java.lang.String sponsorCompanyAddress) {
        this.sponsorCompanyAddress = sponsorCompanyAddress;
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