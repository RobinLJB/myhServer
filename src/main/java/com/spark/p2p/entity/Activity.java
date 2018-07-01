package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Activity {
	private long id;
	private String title;
	@Field("code_prefix")
	private String codePrefix;
	private int type;
	@Field("face_value")
	private double faceValue;
	@Field("usable_period")
	private int usablePeriod;
	@Field("min_invest_amt")
	private double minInvestAmt;
	@Field("create_time")
	private String createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCodePrefix() {
		return codePrefix;
	}
	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
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
	public int getUsablePeriod() {
		return usablePeriod;
	}
	public void setUsablePeriod(int usablePeriod) {
		this.usablePeriod = usablePeriod;
	}
	public double getMinInvestAmt() {
		return minInvestAmt;
	}
	public void setMinInvestAmt(double minInvestAmt) {
		this.minInvestAmt = minInvestAmt;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getNoExpired() {
		return noExpired;
	}
	public void setNoExpired(int noExpired) {
		this.noExpired = noExpired;
	}
	public String getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Field("no_expired")
	private int noExpired;
	@Field("expired_time")
	private String expiredTime;
	private int mode;
	private int status;
}
