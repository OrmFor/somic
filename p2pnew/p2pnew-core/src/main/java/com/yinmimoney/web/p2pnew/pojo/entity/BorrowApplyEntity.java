package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class BorrowApplyEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String applyNo;
	private java.lang.Integer carType;
	private java.lang.String carBrand;
	private java.lang.String carModel;
	private java.lang.String carVinNo;
	private java.lang.Integer insurance;
	private java.math.BigDecimal carPrice;
	private java.math.BigDecimal evaluatePrice;
	private java.lang.String loanBank;
	private java.lang.Integer loanType;
	private java.math.BigDecimal loanMoney;
	private java.math.BigDecimal loanScale;
	private java.math.BigDecimal firstPaymentMoney;
	private java.math.BigDecimal firstPaymentScale;
	private java.lang.Integer loanTime;
	private java.math.BigDecimal periodRepaymentMoney;
	private java.math.BigDecimal loanApr;
	private java.math.BigDecimal allRepaymentMoney;
	private java.math.BigDecimal firstRepaymentMoney;
	private java.math.BigDecimal actualRepaymentMoney;
	private java.lang.Integer status;
	private java.lang.String operateEstablish;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date establishTime;
	private java.lang.String trialRemark;
	private java.lang.String trialOperator;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date trialTime;
	private java.lang.String reviewOperator;
	private java.lang.String reviewRemark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date reviewTime;
	private java.lang.String finalRemark;
	private java.lang.String operateReview2;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date finalTime;
	private java.lang.String transferRemark;
	private java.lang.String operateReview3;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date transferTime;
	
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
    public java.lang.Integer getCarType() {
        return carType;
    }
	public void setCarType(java.lang.Integer carType) {
        this.carType = carType;
    }
    public java.lang.String getCarBrand() {
        return carBrand;
    }
	public void setCarBrand(java.lang.String carBrand) {
        this.carBrand = carBrand;
    }
    public java.lang.String getCarModel() {
        return carModel;
    }
	public void setCarModel(java.lang.String carModel) {
        this.carModel = carModel;
    }
    public java.lang.String getCarVinNo() {
        return carVinNo;
    }
	public void setCarVinNo(java.lang.String carVinNo) {
        this.carVinNo = carVinNo;
    }
    public java.lang.Integer getInsurance() {
        return insurance;
    }
	public void setInsurance(java.lang.Integer insurance) {
        this.insurance = insurance;
    }
    public java.math.BigDecimal getCarPrice() {
        return carPrice;
    }
	public void setCarPrice(java.math.BigDecimal carPrice) {
        this.carPrice = carPrice;
    }
    public java.math.BigDecimal getEvaluatePrice() {
        return evaluatePrice;
    }
	public void setEvaluatePrice(java.math.BigDecimal evaluatePrice) {
        this.evaluatePrice = evaluatePrice;
    }
    public java.lang.String getLoanBank() {
        return loanBank;
    }
	public void setLoanBank(java.lang.String loanBank) {
        this.loanBank = loanBank;
    }
    public java.lang.Integer getLoanType() {
        return loanType;
    }
	public void setLoanType(java.lang.Integer loanType) {
        this.loanType = loanType;
    }
    public java.math.BigDecimal getLoanMoney() {
        return loanMoney;
    }
	public void setLoanMoney(java.math.BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }
    public java.math.BigDecimal getLoanScale() {
        return loanScale;
    }
	public void setLoanScale(java.math.BigDecimal loanScale) {
        this.loanScale = loanScale;
    }
    public java.math.BigDecimal getFirstPaymentMoney() {
        return firstPaymentMoney;
    }
	public void setFirstPaymentMoney(java.math.BigDecimal firstPaymentMoney) {
        this.firstPaymentMoney = firstPaymentMoney;
    }
    public java.math.BigDecimal getFirstPaymentScale() {
        return firstPaymentScale;
    }
	public void setFirstPaymentScale(java.math.BigDecimal firstPaymentScale) {
        this.firstPaymentScale = firstPaymentScale;
    }
    public java.lang.Integer getLoanTime() {
        return loanTime;
    }
	public void setLoanTime(java.lang.Integer loanTime) {
        this.loanTime = loanTime;
    }
    public java.math.BigDecimal getPeriodRepaymentMoney() {
        return periodRepaymentMoney;
    }
	public void setPeriodRepaymentMoney(java.math.BigDecimal periodRepaymentMoney) {
        this.periodRepaymentMoney = periodRepaymentMoney;
    }
    public java.math.BigDecimal getLoanApr() {
        return loanApr;
    }
	public void setLoanApr(java.math.BigDecimal loanApr) {
        this.loanApr = loanApr;
    }
    public java.math.BigDecimal getAllRepaymentMoney() {
        return allRepaymentMoney;
    }
	public void setAllRepaymentMoney(java.math.BigDecimal allRepaymentMoney) {
        this.allRepaymentMoney = allRepaymentMoney;
    }
    public java.math.BigDecimal getFirstRepaymentMoney() {
        return firstRepaymentMoney;
    }
	public void setFirstRepaymentMoney(java.math.BigDecimal firstRepaymentMoney) {
        this.firstRepaymentMoney = firstRepaymentMoney;
    }
    public java.math.BigDecimal getActualRepaymentMoney() {
        return actualRepaymentMoney;
    }
	public void setActualRepaymentMoney(java.math.BigDecimal actualRepaymentMoney) {
        this.actualRepaymentMoney = actualRepaymentMoney;
    }
    public java.lang.Integer getStatus() {
        return status;
    }
	public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.lang.String getOperateEstablish() {
        return operateEstablish;
    }
	public void setOperateEstablish(java.lang.String operateEstablish) {
        this.operateEstablish = operateEstablish;
    }
    public java.util.Date getEstablishTime() {
        return establishTime;
    }
	public void setEstablishTime(java.util.Date establishTime) {
        this.establishTime = establishTime;
    }
    public java.lang.String getTrialRemark() {
        return trialRemark;
    }
	public void setTrialRemark(java.lang.String trialRemark) {
        this.trialRemark = trialRemark;
    }
    public java.lang.String getTrialOperator() {
        return trialOperator;
    }
	public void setTrialOperator(java.lang.String trialOperator) {
        this.trialOperator = trialOperator;
    }
    public java.util.Date getTrialTime() {
        return trialTime;
    }
	public void setTrialTime(java.util.Date trialTime) {
        this.trialTime = trialTime;
    }
    public java.lang.String getReviewOperator() {
        return reviewOperator;
    }
	public void setReviewOperator(java.lang.String reviewOperator) {
        this.reviewOperator = reviewOperator;
    }
    public java.lang.String getReviewRemark() {
        return reviewRemark;
    }
	public void setReviewRemark(java.lang.String reviewRemark) {
        this.reviewRemark = reviewRemark;
    }
    public java.util.Date getReviewTime() {
        return reviewTime;
    }
	public void setReviewTime(java.util.Date reviewTime) {
        this.reviewTime = reviewTime;
    }
    public java.lang.String getFinalRemark() {
        return finalRemark;
    }
	public void setFinalRemark(java.lang.String finalRemark) {
        this.finalRemark = finalRemark;
    }
    public java.lang.String getOperateReview2() {
        return operateReview2;
    }
	public void setOperateReview2(java.lang.String operateReview2) {
        this.operateReview2 = operateReview2;
    }
    public java.util.Date getFinalTime() {
        return finalTime;
    }
	public void setFinalTime(java.util.Date finalTime) {
        this.finalTime = finalTime;
    }
    public java.lang.String getTransferRemark() {
        return transferRemark;
    }
	public void setTransferRemark(java.lang.String transferRemark) {
        this.transferRemark = transferRemark;
    }
    public java.lang.String getOperateReview3() {
        return operateReview3;
    }
	public void setOperateReview3(java.lang.String operateReview3) {
        this.operateReview3 = operateReview3;
    }
    public java.util.Date getTransferTime() {
        return transferTime;
    }
	public void setTransferTime(java.util.Date transferTime) {
        this.transferTime = transferTime;
    }
}