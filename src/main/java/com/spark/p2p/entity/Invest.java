package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Invest {
	private long id;
	private long investor;
	private String username;
	@Field("real_name")
	private String realName;
	@Field("out_name")
	private String outName;
	private String mobilePhone;
	@Field("is_auto_bid")
	private int isAutoBid;
	private double amount;
	@Field("invest_time")
	private String investTime;
	@Field("loan_id")
	private long loanId;
	private double interest;
	private double principal;
	private int status;
	private String merBillNo;
	private String outBillNo;
	@Field("coupon_amount")
	private double couponAmount;
	@Field("coupon_type")
	private int couponType;
	@Field("coupon_id")
	private long couponId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInvestor() {
		return investor;
	}
	public void setInvestor(long investor) {
		this.investor = investor;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getInvestTime() {
		return investTime;
	}
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public int getIsAutoBid() {
		return isAutoBid;
	}
	public void setIsAutoBid(int isAutoBid) {
		this.isAutoBid = isAutoBid;
	}
	public String getMerBillNo() {
		return merBillNo;
	}
	public void setMerBillNo(String merBillNo) {
		this.merBillNo = merBillNo;
	}
	public String getOutBillNo() {
		return outBillNo;
	}
	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
	public double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public int getCouponType() {
		return couponType;
	}
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	
}
