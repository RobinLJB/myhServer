package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class MemberDebt {
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Field("invest_id")
	private long investId;
	
	@Field("loan_id")
	private long loanId;
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	

	@Field("remain_amount")
	private long remainAmount;
	public long getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(long remain_amount) {
		this.remainAmount = remain_amount;
	}

	@Field("origin_amount")
	private long originAmount;
	public long getOriginAmount() {
		return originAmount;
	}
	public void setOriginAmount(long originAmount) {
		this.originAmount = originAmount;
	}
	
	private double amount;
	
	@Field("debt_id")
	private long debtId;
	
	@Field("add_time")
	private String addTime;
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	@Field("status")
	private int status;
	public long getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Field("audit_time")
	private String auditTime;
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	@Field("audit_opinion")
	private String auditOpinion;
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	@Field("progress_scale")
	private double progressScale;
	public double getProgressScale() {
		return progressScale;
	}
	public void setProgressScale(double progressScale) {
		this.progressScale = progressScale;
	}
	
	public long getInvestId() {
		return this.investId;
	}
	public void setInvestor(long investId) {
		this.investId = investId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getDebtId() {
		return debtId;
	}
	public void setDebtId(long debtId) {
		this.debtId = debtId;
	}
	
}
