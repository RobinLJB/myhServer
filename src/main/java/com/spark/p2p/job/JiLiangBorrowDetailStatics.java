
package com.spark.p2p.job;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.ApplicationUtil;
import com.sparkframework.lang.Convert;

public class JiLiangBorrowDetailStatics extends QuartzJobBean {

	private static Log log = LogFactory.getLog(JiLiangBorrowDetailStatics.class);
	public static StatisticsService ss;
	private static MemberService memberService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		ss = ApplicationUtil.getBean(StatisticsService.class);
		memberService = ApplicationUtil.getBean(MemberService.class);
		log.info("--------------统计计量用户借款详情....-------------");
		try {
			statisticTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("-----------------------------统计计量用户借款详情.....--------------");
	}
	
	public void statisticTask() throws Exception{
		//获取计量推广的用户的借款详情
		List<Map<String, String>> jiliangList=memberService.queryJiLiang();
		for(int i=0;i<jiliangList.size();i++){
			Map<String, String> jilaingMap=jiliangList.get(i);
			String onlyKey=jilaingMap.get("onlyKey");
			int jiliangId=Convert.strToInt(jilaingMap.get("id"), 0);
			int successBorrowSum=0;
			int successBorrowMemberSum=0;
			int repaySum=0;
			int overdueSums=0;
			int dieSums=0;
			List<Map<String, String>> memberList=memberService.queryMemberByJiliangNo(onlyKey);
			for(int j=0;j<memberList.size();j++){
				Map<String, String> memberMap=memberList.get(i);
				int alreadyBorrowSum=Convert.strToInt(memberMap.get("alreadyBorrowSum"), 0);
				int alreadyRepaySum=Convert.strToInt(memberMap.get("alreadyRepaySum"), 0);
				int overdueSum=Convert.strToInt(memberMap.get("overdueSum"), 0);
				int dieSum=Convert.strToInt(memberMap.get("dieSum"), 0);
				successBorrowSum=successBorrowSum+alreadyBorrowSum;
				if(alreadyBorrowSum!=0) {
					successBorrowMemberSum+=1;
				}
				repaySum=repaySum+alreadyRepaySum;
				overdueSums=overdueSums+overdueSum;
				dieSums=dieSums+dieSum;
			}
			memberService.updateJiliangBorrowDetail(jiliangId,successBorrowMemberSum,successBorrowSum,repaySum,overdueSums,dieSums);
		}
		
	}
	
}
