package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class GatewayLog {
	private long id;
	@Field("uid")
	private long uid;
	@Field("merchant_bill_no")
	private String merchantBillNo;
	@Field("biz_type")
	private String bizType;
	@Field("req_time")
	private String reqTime;
	private String request;
	private String response;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMerchantBillNo() {
		return merchantBillNo;
	}
	public void setMerchantBillNo(String merchantBillNo) {
		this.merchantBillNo = merchantBillNo;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
}
