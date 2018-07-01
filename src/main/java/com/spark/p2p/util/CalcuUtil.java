/**
 * 
 */
package com.spark.p2p.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.p2p.entity.Repayment;

/**
 * 还款计算器工具类
 * 
 *
 */
public class CalcuUtil {
	// 格式化保留两位数
	public static DecimalFormat df_two = new DecimalFormat("#0.00");
	// 格式化保留4位数
	public static DecimalFormat df_four = new DecimalFormat("#0.0000");
	// 日期格式化
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	// 月利率
	private double monthRate = 0;
	// 到期还款本息总额
	private double totalPI = 0;
	// 总利息
	private double totalInterest = 0;
	// 总本金
	private double totalPrincipal = 0;

	/**
	 * @MethodName: add
	 * @Param: AmountUtil
	 * @Author:
	 * @Date: 2013-4-20 下午05:15:18
	 * @Return:
	 * @Descb: 日期累加
	 * @Throws:
	 */
	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	/**
	 * 等额本息方式还款
	 * @param borrowSum 借款(投资)总额
	 * @param yearRate 年利率
	 * @param cycle 周期
	 * @return
	 */
	public List<Repayment> repayAverage(double borrowSum, double yearRate, int cycle) {
		List<Repayment> list = new ArrayList<Repayment>();
		double monthRate = Double.valueOf(yearRate * 0.01) / 12.0;
		double monthlyPI = Double.valueOf(borrowSum * monthRate * Math.pow((1 + monthRate), cycle))
				/ Double.valueOf(Math.pow((1 + monthRate), cycle) - 1);
		monthlyPI = Double.valueOf(df_two.format(monthlyPI));
		double monthlyInterest = 0;
		double monthlyPrincipal = 0;
		// 总本息
		totalPI = monthlyPI * cycle;
		// 总本金
		totalPrincipal = borrowSum;
		// 总利息
		totalInterest = totalPI - totalPrincipal;
		Date currTime = null;
		double principalBalance = borrowSum;
		double interestBalance = 0;
		double hasPaiedPrincipal = 0;
		double hasPaiedInterest = 0;
		// 剩余待还本息
		double remainedPI = Double.valueOf(df_two.format(totalPI));
		Date date = new Date();
		for (int n = 1; n <= cycle; n++) {
			Repayment repay = new Repayment();
			currTime = add(date, Calendar.MONTH, n);
			monthlyInterest = Double.valueOf(df_two.format(principalBalance * monthRate));
			monthlyPrincipal = Double.valueOf(df_two.format(monthlyPI - monthlyInterest));
			principalBalance = Double.valueOf(df_two.format(principalBalance - monthlyPrincipal));

			if (n == cycle) {
				monthlyPI = remainedPI;
				monthlyPrincipal = borrowSum - hasPaiedPrincipal;
				monthlyInterest = monthlyPI - monthlyPrincipal;
			}
			hasPaiedPrincipal += monthlyPrincipal;
			hasPaiedInterest += monthlyInterest;
			remainedPI = Double.valueOf(df_two.format(remainedPI - monthlyPI));
			interestBalance = Double.valueOf(df_two.format(remainedPI - principalBalance));
			// principalBalance =
			if (n == cycle) {
				remainedPI = 0;
				principalBalance = 0;
				interestBalance = 0;
			}
			double periodAmount = monthlyPrincipal + monthlyInterest;
			repay.setPeriod(n + "/" + cycle);
			repay.setRepayDate(sf.format(currTime));
			repay.setAmount(periodAmount);
			repay.setPrincipal(Double.parseDouble(df_two.format(monthlyPrincipal)));
			repay.setInterest(Double.parseDouble(df_two.format(monthlyInterest)));
			repay.setPrincipalBalance(Double.parseDouble(df_two.format(principalBalance)));
			repay.setInterestBalance(Double.parseDouble(df_two.format(interestBalance)));
			repay.setMonthlyRate(monthRate * 100);

			repay.setPaiedPrincipal(hasPaiedPrincipal); 
			repay.setPaiedInterest(hasPaiedInterest);
			list.add(repay);
		}
		return list;
	}

	/**
	 * 按月付息，到期还本方式
	 * @param borrowSum 借款(投资)总额
	 * @param yearRate 年利率
	 * @param cycle 周期
	 * @return
	 */
	public List<Repayment> repayMonthlyInterest(double borrowSum, double yearRate, int cycle) {
		List<Repayment> list = new ArrayList<Repayment>();
		monthRate = (yearRate * 0.01) / 12;
		// 每月利息
		double monthlyInterest = Double.valueOf(df_two.format(borrowSum * monthRate));
		// 总利息
		totalInterest = monthlyInterest * cycle;
		// 总本息
		totalPI = borrowSum + totalInterest;
		// 总本金
		totalPrincipal = borrowSum;
		Date date = new Date();
		Date currTime = null;
		double principal = 0;
		double principalBalance = borrowSum;
		double interestBalance = totalInterest;

		for (int n = 1; n <= cycle; n++) {
			Repayment repay = new Repayment();
			currTime = add(date, Calendar.MONTH, n);
			if (n == cycle) {
				principal = borrowSum;
				principalBalance = 0;
				interestBalance = 0;
			} else {
				interestBalance = interestBalance - monthlyInterest;
				principal = 0;
				principalBalance = borrowSum;
			}
			double periodAmount = principal + monthlyInterest;
			repay.setPeriod(n + "/" + cycle);
			repay.setRepayDate(sf.format(currTime));
			repay.setAmount(periodAmount);
			repay.setPrincipal(Double.parseDouble(df_two.format(principal)));
			repay.setInterest(Double.parseDouble(df_two.format(monthlyInterest)));
			repay.setPrincipalBalance(Double.parseDouble(df_two.format(principalBalance)));
			repay.setInterestBalance(Double.parseDouble(df_two.format(interestBalance)));
			list.add(repay);
		}

		return list;
	}

	/**
	 * 到期还本付息方式
	 * @param borrowSum 借款（投资）总额
	 * @param yearRate 年利率
	 * @param cycle 周期
	 * @param cycleType 1：按月，2：按月
	 * @return
	 */
	public List<Repayment> repayOnce(double borrowSum, double yearRate, int cycle, int cycleType) {
		List<Repayment> list = new ArrayList<Repayment>();
		double monthlyRate = Double.valueOf(yearRate * 0.01) / 12.0;
		double dailyRate = Double.valueOf(yearRate * 0.01) / 360;
		Date date = new Date();
		Date currTime = null;
		Repayment repay = new Repayment();
		if (cycleType == 1) {
			// 月标
			double monthlyInterest = borrowSum * monthlyRate;
			totalInterest = Double.valueOf(df_two.format(monthlyInterest * cycle));
			double totalAmount = borrowSum + totalInterest;
			currTime = add(date, Calendar.MONTH, cycle);
			repay.setPeriod("1/1");
			repay.setRepayDate(sf.format(currTime));
			repay.setAmount(totalAmount);
			repay.setPrincipal(Double.parseDouble(df_two.format(borrowSum)));
			repay.setInterest(Double.parseDouble(df_two.format(totalInterest)));
			repay.setPrincipalBalance(0);
			repay.setInterestBalance(0);

		} else {
			// 天标
			double dailyInterest = borrowSum * dailyRate;
			totalInterest = Double.valueOf(df_two.format(dailyInterest * cycle));
			currTime = add(date, Calendar.DATE, cycle);
			double totalAmount = borrowSum + totalInterest;
			repay.setPeriod("1/1");
			repay.setRepayDate(sf.format(currTime));
			repay.setAmount(totalAmount);
			repay.setPrincipal(borrowSum);
			repay.setInterest(totalInterest);
			repay.setPrincipalBalance(0);
			repay.setInterestBalance(0);
		}
		list.add(repay);
		return list;
	}

	public double getTotalPI() {
		return totalPI;
	}

	public void setTotalPI(double totalPI) {
		this.totalPI = totalPI;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}
}