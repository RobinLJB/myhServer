package com.spark.p2p.job;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

public class BorrowDailyCheck extends QuartzJobBean {

	private static Log log = LogFactory.getLog(BorrowDailyCheck.class);
	public static StatisticsService ss;
	private static BorrowService borrowService;
	private static MemberService memberService;
	
	public BorrowDailyCheck(){
		if(memberService == null){
			memberService = new MemberService();
		}
		if(borrowService == null){
			borrowService = new BorrowService();
		}
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		ss = ApplicationUtil.getBean(StatisticsService.class);
		memberService = ApplicationUtil.getBean(MemberService.class);
		borrowService = ApplicationUtil.getBean(BorrowService.class);
		log.info("--------------计算逾期开始....-------------");
		try {
			statisticTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("-----------------------------计算逾期结束.....--------------");
	}
	
	public void statisticTask() throws Exception { 
		//查询所有借款处于：复审通过的，改变状态，进入还款期间
		List<Map<String,String>> secondAuditPassList = borrowService.queryAllBorrowStatus(8);
		for(int i=0; i < secondAuditPassList.size(); i++) { 
			Map<String,String> tempMap = new HashMap<String,String>(); 
			tempMap = secondAuditPassList.get(i);
			Member member = memberService.findMember(Convert.strToInt(tempMap.get("member_id"), 0));
			String realLoanTime = tempMap.get("realLoanTime");
			realLoanTime = realLoanTime.substring(0, 10) + " 00:00:01";
			Date startDay = DateUtil.strToDate(realLoanTime);
			int days = (int) DateUtil.diffDays( startDay, new Date() ) ;
			int borrowDays = Convert.strToInt( tempMap.get("borrowDate"), 0 );
			int addBorrowDay = Convert.strToInt(tempMap.get("addBorrowDay"), 0);
			int dayGap = days - borrowDays - addBorrowDay;
			if(dayGap <= 0) { 
				//说明借款处在正常的还款期间内
				//borrowService.updateBorrowStatus(Convert.strToInt(tempMap.get("id"), 0), 8);
			} else {
				
				borrowService.updateBorrowStatus(Convert.strToInt(tempMap.get("id"), 0), 9);
				//查看是否有部分还款
				Map<String,String> lastRepayMap = borrowService.findLastPay(Convert.strToInt(tempMap.get("id"), 0));
				double amount = 0;
				double remainfee = 0;
				if(lastRepayMap == null) { 
					amount = Convert.strToInt(tempMap.get("benJin"), 0); 
//					Map<String, String> borrowProductMap=borrowService.findProductByDayAndMount(Convert.strToInt(tempMap.get("benJin"), 0),Convert.strToInt(tempMap.get("borrowDate"), 0));
//					double xinFee=Convert.strToDouble(borrowProductMap.get("xinfee"), 0);
//					double shouFee=Convert.strToDouble(borrowProductMap.get("shoufee"), 0);
//					double serviceFee=Convert.strToDouble(borrowProductMap.get("servicefee"), 0);
//					double otherFee=Convert.strToDouble(borrowProductMap.get("otherFee"), 0);
					
					DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
					// 计算相关费用
					/*double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
					double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
					double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;*/
					double xinFee = 0;
					double shouFee = 0;
					double serviceFee = 0;
					xinFee = Double.valueOf(df.format(xinFee));
					shouFee = Double.valueOf(df.format(shouFee));
					serviceFee = Double.valueOf(df.format(serviceFee));
					
					remainfee = remainfee + xinFee + shouFee + serviceFee ;
				} else {
					amount = Convert.strToDouble(lastRepayMap.get("remainBenjin"), 0);
					remainfee = Convert.strToDouble(lastRepayMap.get("remainFee"), 0);
				}
				double precents = Double.valueOf(new Model("constant_variable").where("name= ?", "OVERDUEFEE").find().get("value") );
				double overdueFee = amount * precents * dayGap;
				//String content="你的借款（借款金额："+tempMap.get("benJin")+"元），已经逾期了"+dayGap+"天，逾期费用为："+overdueFee+"元";
				//SMSUtil.sendContent(member.getMobilePhone(),content);
				
				String benJin = tempMap.get("benJin");
				String content = SMSUtil.limitoverBorrow(benJin,dayGap,overdueFee);
				SMSUtil.sendContent(member.getMobilePhone(),content);
				
				borrowService.borrowOverdueHandles(tempMap, dayGap, overdueFee, amount, remainfee);
			}
		}
		
		List<Map<String,String>> overdueList=borrowService.queryAllBorrowStatus(9);
		for(int i=0; i < overdueList.size(); i++) { 
			Map<String,String> tempMap = new HashMap<String,String>();
			tempMap = overdueList.get(i);
			String realLoanTime = tempMap.get("realLoanTime");
			realLoanTime = realLoanTime.substring(0, 10)+" 00:00:01";
			Date startDay=DateUtil.strToDate(realLoanTime);
			int days = (int) DateUtil.diffDays(startDay,new Date()) ;
			int borrowDays = Convert.strToInt(tempMap.get("borrowDate"), 0);
			int addBorrowDay = Convert.strToInt(tempMap.get("addBorrowDay"), 0);
			int dayGap = days - borrowDays - addBorrowDay;
			Map<String,String> lastRepayMap=borrowService.findLastPay(Convert.strToInt(tempMap.get("id"), 0));
			double amount = 0;
			double remainfee = 0;
			if(lastRepayMap == null) {
				amount=Convert.strToInt(tempMap.get("benJin"), 0);
//				Map<String, String> borrowProductMap=borrowService.findProductByDayAndMount(Convert.strToInt(tempMap.get("benJin"), 0),Convert.strToInt(tempMap.get("borrowDate"), 0));
//				double xinFee=Convert.strToDouble(borrowProductMap.get("xinfee"), 0);
//				double shouFee=Convert.strToDouble(borrowProductMap.get("shoufee"), 0);
//				double serviceFee=Convert.strToDouble(borrowProductMap.get("servicefee"), 0);
//				double otherFee=Convert.strToDouble(borrowProductMap.get("otherFee"), 0);
				DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
				// 计算相关费用
				double xinFee = 0;
				double shouFee = 0;
				double serviceFee =0;
				/*double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
				double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
				double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;*/
				xinFee = Double.valueOf(df.format(xinFee));
				shouFee = Double.valueOf(df.format(shouFee));
				serviceFee = Double.valueOf(df.format(serviceFee));
				remainfee = remainfee + xinFee + shouFee + serviceFee ;
			}else{
				amount = Convert.strToDouble(lastRepayMap.get("remainBenjin"), 0);
				remainfee = Convert.strToDouble(lastRepayMap.get("remainFee"), 0);
			}
			double overdueFee=amount*0.03*dayGap;
			
			borrowService.borrowOverdueHandles(tempMap,dayGap,overdueFee,amount,remainfee);
		}
		
	}
	
}
