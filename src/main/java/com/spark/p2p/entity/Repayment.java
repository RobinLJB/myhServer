package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Repayment {
	private long id;
	private long uid;
	private long loanId;
	private String period;
	private String startInterestDate;
	private String repayDate;
	private double amount;
	private double principal;
	private double interest;
	private double principalBalance;
	private double interestBalance;
	private double monthlyRate;
	
	@Field("overdue_fee")
	private double overdueFee;
	@Field("overdue_day")
	private int overdueDay;
	
	@Field("paied_principal")
	private double paiedPrincipal;
	@Field("paied_interest")
	private double paiedInterest;
	@Field("paied_overdue_fee")
	private double paiedOverdueFee;
	
	private int status;
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getStartInterestDate() {
		return startInterestDate;
	}
	public void setStartInterestDate(String startInterestDate) {
		this.startInterestDate = startInterestDate;
	}

	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	

	public double getOverdueFee() {
		return this.overdueFee;
	}
	public void setOverdueFee(double overdueFee) {
		this.overdueFee = overdueFee;
	}
	
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getPrincipalBalance() {
		return principalBalance;
	}
	public void setPrincipalBalance(double principalBalance) {
		this.principalBalance = principalBalance;
	}
	public double getInterestBalance() {
		return interestBalance;
	}
	public void setInterestBalance(double interestBalance) {
		this.interestBalance = interestBalance;
	}
	public double getMonthlyRate() {
		return monthlyRate;
	}
	public void setMonthlyRate(double monthlyRate) {
		this.monthlyRate = monthlyRate;
	}
	public double getPaiedPrincipal() {
		return paiedPrincipal;
	}
	public void setPaiedPrincipal(double paiedPrincipal) {
		this.paiedPrincipal = paiedPrincipal;
	}
	public double getPaiedInterest() {
		return paiedInterest;
	}
	public void setPaiedInterest(double paiedInterest) {
		this.paiedInterest = paiedInterest;
	}

	public double getPaiedOverdueFee() {
		return this.paiedOverdueFee;
	}
	public void setPaiedOverdueFee(double paiedOverdueFee) {
		this.paiedOverdueFee = paiedOverdueFee;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String toString(){
		return String.format("{period:%s,repayDate:%s,amount:%f,principal:%f,interest:%f,principalBalance:%f,interestBalance:%f}", period,repayDate,amount,principal,interest,principalBalance,interestBalance);
	}
}
