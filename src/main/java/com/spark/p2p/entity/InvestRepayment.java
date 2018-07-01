package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class InvestRepayment {
	private long id;
	private long investor;
	private long repaymentId;
	private long loanId;
	private double principal;
	private double interest;
	private double principalBalance;
	private double interestBalance;
	private int status;
	private String repayDate;
	private long investId;
	@Field("repayPeriod")
	private String period;
	
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
	public long getRepaymentId() {
		return repaymentId;
	}
	public void setRepaymentId(long repaymentId) {
		this.repaymentId = repaymentId;
	}
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public long getInvestId() {
		return investId;
	}
	public void setInvestId(long investId) {
		this.investId = investId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
}
