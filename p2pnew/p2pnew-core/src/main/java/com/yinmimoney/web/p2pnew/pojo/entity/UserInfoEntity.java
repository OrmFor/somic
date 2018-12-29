package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserInfoEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String applyNo;
	private java.lang.String mobilePhone;
	private java.lang.String householdProvince;
	private java.lang.String householdCity;
	private java.lang.String householdArea;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date idCardExpiryDateBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date idCardExpiryDateEnd;
	private java.lang.String address;
	private java.lang.String photo;
	private java.lang.String video;
	private java.lang.String credit;
	private java.lang.String creditPhoto;
	private java.lang.String court;
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
    public java.lang.String getMobilePhone() {
        return mobilePhone;
    }
	public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public java.lang.String getHouseholdProvince() {
        return householdProvince;
    }
	public void setHouseholdProvince(java.lang.String householdProvince) {
        this.householdProvince = householdProvince;
    }
    public java.lang.String getHouseholdCity() {
        return householdCity;
    }
	public void setHouseholdCity(java.lang.String householdCity) {
        this.householdCity = householdCity;
    }
    public java.lang.String getHouseholdArea() {
        return householdArea;
    }
	public void setHouseholdArea(java.lang.String householdArea) {
        this.householdArea = householdArea;
    }
    public java.util.Date getIdCardExpiryDateBegin() {
        return idCardExpiryDateBegin;
    }
	public void setIdCardExpiryDateBegin(java.util.Date idCardExpiryDateBegin) {
        this.idCardExpiryDateBegin = idCardExpiryDateBegin;
    }
    public java.util.Date getIdCardExpiryDateEnd() {
        return idCardExpiryDateEnd;
    }
	public void setIdCardExpiryDateEnd(java.util.Date idCardExpiryDateEnd) {
        this.idCardExpiryDateEnd = idCardExpiryDateEnd;
    }
    public java.lang.String getAddress() {
        return address;
    }
	public void setAddress(java.lang.String address) {
        this.address = address;
    }
    public java.lang.String getPhoto() {
        return photo;
    }
	public void setPhoto(java.lang.String photo) {
        this.photo = photo;
    }
    public java.lang.String getVideo() {
        return video;
    }
	public void setVideo(java.lang.String video) {
        this.video = video;
    }
    public java.lang.String getCredit() {
        return credit;
    }
	public void setCredit(java.lang.String credit) {
        this.credit = credit;
    }
    public java.lang.String getCreditPhoto() {
        return creditPhoto;
    }
	public void setCreditPhoto(java.lang.String creditPhoto) {
        this.creditPhoto = creditPhoto;
    }
    public java.lang.String getCourt() {
        return court;
    }
	public void setCourt(java.lang.String court) {
        this.court = court;
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