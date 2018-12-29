package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class NoticeTypeEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer type;
	private String nid;
	private Integer noticeType;
	private String name;
	private String titleTemplet;
	private String templet;
	private Integer send;
	private Integer canSwitch;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	private String addIp;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private String updateIp;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }
	public void setType(Integer type) {
        this.type = type;
    }
    public String getNid() {
        return nid;
    }
	public void setNid(String nid) {
        this.nid = nid;
    }
    public Integer getNoticeType() {
        return noticeType;
    }
	public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }
    public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
    public String getTitleTemplet() {
        return titleTemplet;
    }
	public void setTitleTemplet(String titleTemplet) {
        this.titleTemplet = titleTemplet;
    }
    public String getTemplet() {
        return templet;
    }
	public void setTemplet(String templet) {
        this.templet = templet;
    }
    public Integer getSend() {
        return send;
    }
	public void setSend(Integer send) {
        this.send = send;
    }
    public Integer getCanSwitch() {
        return canSwitch;
    }
	public void setCanSwitch(Integer canSwitch) {
        this.canSwitch = canSwitch;
    }
    public String getRemark() {
        return remark;
    }
	public void setRemark(String remark) {
        this.remark = remark;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public String getAddIp() {
        return addIp;
    }
	public void setAddIp(String addIp) {
        this.addIp = addIp;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateIp() {
        return updateIp;
    }
	public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }
}