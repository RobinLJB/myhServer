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


/**
 * 金额计算工具类
 * @author Administrator
 *
 */
public class AmountUtil {
	  //格式化保留两位数
	  private DecimalFormat df_two = new DecimalFormat("#0.00");
	  //格式化保留4位数
	  private DecimalFormat df_four = new DecimalFormat("#0.0000");
	  //日期格式化
	  private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	  //月利率
	  private double monthRate = 0;
	  //月还利息
	  private double monPayRate = 0;
	  //月还本金
	  private double monPayAmount = 0;
	  //到期还款本息总额
	  private double totalAmount = 0;
	  //月还本息
	  private double totalSum = 0;
	  //本金余额
	  private double principalBalance = 0;
	  //利息余额
	  private double interestBalance = 0;
	  //总利息
	  private double totalInterest = 0;
	  //当前时间
	  private Date currTime;
	  //剩余本金
	  private double stillAmount = 0;
	  //月还款
	  private double monPay = 0;
	  //所借本金
	  private double amount = 0;
	  //本息余额
	  private double payRemain = 0;
	  //本金
	  private double payAmount = 0;
	  //投资管理费
	  private double iManageFee = 0;
	  //投资收益总额
	  private double earnAmount = 0;
	  //奖励
	  private double rewardSum = 0;
	  //获取日期
	  private Date date = new Date();
	  //返回的结果集
	  private List<Map<String,Object>> mapList = null;
	  //map记录
	  private Map<String,Object> map = null;
	  
	  
	/**   
	 * @MethodName: add  
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午05:15:18
	 * @Return:    
	 * @Descb: 日期累加
	 * @Throws:
	*/
	private static Date add(Date date,int type,int value){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(type, value);
			return calendar.getTime();
	 }
	  
	
	/**   
	 * @MethodName: formatNumber  
	 * @Param: AmountUtil  
	 * @Return:    
	 * @Descb: 格式化输出为.0的数字
	 * @Throws:
	*/
	public static String formatNumber(String number){
		if(number.startsWith("."))
			return "0"+number;
		return number;
	}
	/**   
	 * @MethodName: rateSecondsSum  
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额
	 * @Param: AmountUtil  
	 * @Return:    
	 * @Descb: 秒还借款
	 * @Throws:
	*/
	public List<Map<String,Object>> rateSecondsSum(double borrowSum,double yearRate,int deadline){
		  mapList = new ArrayList<Map<String,Object>>();
		  map = new HashMap<String,Object>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  totalInterest = Double.valueOf(df_two.format(borrowSum*monthRate));
		  totalAmount = borrowSum + totalInterest;
		  map.put("repayPeriod", "1/1");
		  map.put("repayDate", sf.format(date));
		  map.put("stillPrincipal", df_two.format(borrowSum));
		  map.put("stillInterest", df_two.format(totalInterest));
		  map.put("principalBalance", 0);
		  map.put("interestBalance", 0);
		  map.put("mRate", df_four.format(monthRate*100));
		  map.put("totalSum", df_two.format(totalAmount));
		  map.put("totalAmount", df_two.format(totalAmount));
		  mapList.add(map);
		  return mapList;
	}
	
	
	/**   
	 * @MethodName: earnSecondsSum  
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Return:    
	 * @Descb: 秒还还款收益
	 * @Throws:
	*/
	public Map<String,String> earnSecondsSum(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int viewMode){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		totalInterest = borrowSum*monthRate;
		totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		totalInterest = Double.valueOf(df_four.format(totalInterest));
		iManageFee = totalInterest*0.1;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		rewardSum = excitationSum*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		rewardSum = Double.valueOf(df_two.format(rewardSum));
		earnAmount = realAmount + totalInterest+rewardSum-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
		     msg.append("投资秒还借款月利率："+df_four.format(monthRate*100));
		     msg.append("%<br/>其中投资金额：￥"+realAmount+"元<br/>");
		     msg.append("收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
		     msg.append("扣除投资管理费：￥"+iManageFee+"元<br/>");
		     msg.append("收益总额：￥"+earnAmount+"元");
		}else{
			msg.append("投标"+realAmount+"元,年利率："+yearRate);
		    msg.append("%,期限"+deadline+"个月,可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		}
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/** 等额本息
	 * @MethodName: rateCalculateMonth
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额
	 * @Param: AmountUtil  
	 * @Return:    
	 * @Descb: 按月等额本息还款列表计算
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateMonth(double borrowSum,double yearRate,int deadline,int isDayThe){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			   monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			   monPay = Double.valueOf(df_two.format(monPay));
			   amount = borrowSum;
			   monPayRate = 0;
			   monPayAmount = 0;
			   totalAmount = monPay*deadline;
			   payRemain = Double.valueOf(df_two.format(totalAmount));
			   for(int n=1;n<=deadline;n++){
				   map = new HashMap<String,Object>();
				   currTime = add(date,Calendar.MONTH,n);
				   monPayRate = Double.valueOf(df_two.format(amount*monthRate));
				   monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
				   amount = Double.valueOf(df_two.format(amount-monPayAmount));
				   
				   if(n == deadline){
					   monPay =payRemain;
					   monPayAmount = borrowSum - payAmount;
					   monPayRate = monPay - monPayAmount;
				   }
				   payAmount += monPayAmount;
				   payRemain = Double.valueOf(df_two.format(payRemain-monPay));
				   principalBalance = amount;
				   interestBalance = Double.valueOf(df_two.format(payRemain - principalBalance));
				   if(n == deadline){
					   payRemain = 0;
					   principalBalance = 0;
					   interestBalance = 0;
				   }
				   totalSum =monPayAmount +monPayRate;
				   map.put("repayPeriod", n+"/"+deadline);
				   map.put("repayDate", sf.format(currTime));
				   map.put("stillPrincipal", df_two.format(monPayAmount));
			       map.put("principalBalance", df_two.format(principalBalance));
				   map.put("interestBalance", df_two.format(interestBalance));
				   map.put("stillInterest", df_two.format(monPayRate));
				   map.put("mRate", df_four.format(monthRate*100));
				   map.put("totalSum", df_two.format(totalSum));
				   map.put("totalAmount", df_two.format(totalAmount));
				   mapList.add(map); 
			   }
		  }else{
			  //天标
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		return mapList;
	}
	
	
	/**   
	 * @MethodName: earnCalculateMonth
	 *  msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午11:07:46
	 * @Return:    
	 * @Descb: 按月等额还款收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateMonth(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = (yearRate*0.01)/12;
		if(isDayThe == 1){
			//月标
			monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			monPay = Double.valueOf(df_two.format(monPay));
			amount = borrowSum;
			monPayRate = 0;
			monPayAmount = 0;
			totalAmount = monPay*deadline;
			payRemain = Double.valueOf(df_two.format(totalAmount));
			for(int n=1;n<=deadline;n++){
				map = new HashMap<String,Object>();
				currTime = add(date,Calendar.MONTH,n);
				monPayRate = Double.valueOf(df_two.format(amount*monthRate));
				monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
				amount = Double.valueOf(df_two.format(amount-monPayAmount));
				if(n == deadline){
					monPay =payRemain;
					monPayAmount = borrowSum - payAmount;
					monPayRate = monPay - monPayAmount;
				}
				payAmount += monPayAmount;
				payRemain = Double.valueOf(df_two.format(payRemain-monPay));
				if(n == deadline){
				   payRemain = 0;
				}
				totalInterest = totalInterest + monPayRate;
			}  
			totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m= "天";
		  }
		iManageFee = totalInterest*manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
		    msg.append("投资期数"+deadline+m+",月利率："+df_four.format(monthRate*100)+"%<br/>");
		    msg.append("投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
		    msg.append("到期扣除投资管理费：￥"+iManageFee+"元<br/>");
		    msg.append("到期收益总额：￥"+earnAmount+"元");
		}else{
			msg.append("投标"+realAmount+"元,年利率："+yearRate);
		    msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		}
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**   
	 * @MethodName: rateCalculateSum
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额  
	 * @Param: AmountUtil  
	 * @Return:
	 * @Descb: 按月先息后本
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateSum(double borrowSum,double yearRate,int deadline,int isDayThe){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = (yearRate*0.01)/12;
		  if(isDayThe == 1){
			  //月标
			  //每月利息
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = monPayRate*deadline;
			  interestBalance = totalInterest;
			  for(int n = 1;n<=deadline;n++){
				  map = new HashMap<String,Object>();
				  currTime = add(date,Calendar.MONTH,n);
				  if(n == deadline){
					  stillAmount = borrowSum;
					  map.put("stillPrincipal", df_two.format(borrowSum));
					  map.put("principalBalance", 0);
					  map.put("interestBalance", 0);
				  }else{
					  interestBalance = interestBalance- monPayRate;
			          map.put("stillPrincipal", 0);
			          map.put("principalBalance", df_two.format(borrowSum));
					  map.put("interestBalance", df_two.format(interestBalance));
				  }
				  totalSum = stillAmount + monPayRate;
				  totalAmount = borrowSum + totalInterest;
				  map.put("repayPeriod", n+"/"+deadline);
				  map.put("repayDate", sf.format(currTime));
				  map.put("stillInterest", df_two.format(monPayRate));
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalSum));
				  map.put("totalAmount", df_two.format(totalAmount));
				  mapList.add(map);
			  }
		  }
		  else{
			  map = new HashMap<String,Object>();
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		  return mapList;
	 }
	
	/**   
	 * @MethodName: earnCalculateSum
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Return:    
	 * @Descb: 先息后本收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateSum(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
		if(isDayThe == 1){
			  //月标
			  totalInterest = monPayRate*deadline;
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  totalInterest = (monPayRate*(Double.valueOf(realAmount)/Double.valueOf(borrowSum)))/30.0;
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m= "天";
		}
		iManageFee = totalInterest*0.1;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			msg.append("月利率："+df_four.format(monthRate*100)+"%,");
			msg.append("投资期数"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
			msg.append("到期扣除投资管理费：￥"+iManageFee+"元<br/>");
			msg.append("到期收益总额：￥"+earnAmount+"元");
		}
		else{
			msg.append("投标"+realAmount+"元,年利率："+yearRate);
			msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		}
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**   
	 * @MethodName: rateCalculateOne
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额  
	 * @Param: AmountUtil  
	 * @Return:    
	 * @Descb: 一次性还款付息
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateOne(double borrowSum,double yearRate,int deadline,int isDayThe){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = monPayRate*deadline;
			  totalAmount = borrowSum + totalInterest;
			  currTime = add(date,Calendar.MONTH,deadline);
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }else{
			  map = new HashMap<String,Object>();
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		  return mapList;
	  }
	  
	
	/**   
	 * @MethodName: earnCalculateOne  
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费 
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil 
	 * @Return:    
	 * @Descb: 一次性还款收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateOne(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
		if(isDayThe == 1){
			  //月标
			  totalInterest = monPayRate*deadline;
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  totalInterest = (monPayRate*(Double.valueOf(realAmount)/Double.valueOf(borrowSum)))/30.0;
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m= "天";
		  }
		iManageFee = totalInterest * manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			   msg.append("月利率："+df_four.format(monthRate*100));
			   msg.append("%,投资期数"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest));
			   msg.append("元<br/>到期扣除投资管理费：￥"+iManageFee+"元<br/>");
			   msg.append("到期收益总额：￥"+earnAmount+"元");
		    }else{
			   msg.append("投标"+realAmount+"元,年利率："+yearRate);
		       msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		    }
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
}