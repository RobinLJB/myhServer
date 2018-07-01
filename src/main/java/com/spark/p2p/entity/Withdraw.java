package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Withdraw {
	private long id;
	private long uid;
	private double amount;
	private double fee;
	private int status;
	@Field("add_time")
	private String addTime;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getAddIp() {
		return addIp;
	}
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMerchantBillNo() {
		return merchantBillNo;
	}
	public void setMerchantBillNo(String merchantBillNo) {
		this.merchantBillNo = merchantBillNo;
	}
	public String getTransactionBillNo() {
		return transactionBillNo;
	}
	public void setTransactionBillNo(String transactionBillNo) {
		this.transactionBillNo = transactionBillNo;
	}
	@Field("add_ip")
	private String addIp;
	private String remark;
	@Field("merchant_bill_no")
	private String merchantBillNo;
	@Field("transaction_bill_no")
	private String transactionBillNo;
}
