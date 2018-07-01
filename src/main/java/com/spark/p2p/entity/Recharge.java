package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Recharge {
	private long id;
	private long uid;
	private int type;
	private double amount;
	private double fee;
	private int status;
	@Field("merchant_bill_no")
	private String merchantBillNo;
	@Field("transaction_bill_no")
	private String transactionBillNo;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	
}
