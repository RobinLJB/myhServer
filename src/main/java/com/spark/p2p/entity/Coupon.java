package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Coupon {
	private long id;
	private long uid;
	private String username;
	@Field("real_name")
	private String realName;
	private String mobilePhone;
	private String token;
	private int type;
	@Field("face_value")
	private double faceValue;
	@Field("min_invest_amt")
	private double minInvestAmt;
	@Field("expired_time")
	private String expiredTime;
	@Field("create_time")
	private String createTime;
	private int status;
	@Field("act_id")
	private long actId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public double getMinInvestAmt() {
		return minInvestAmt;
	}
	public void setMinInvestAmt(double minInvestAmt) {
		this.minInvestAmt = minInvestAmt;
	}
	public long getActId() {
		return actId;
	}
	public void setActId(long actId) {
		this.actId = actId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}
	public String getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}
	@Field("used_time")
	private String usedTime;
}
