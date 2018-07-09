package com.spark.p2p.job;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;


public class StatisticsInvateAndSelfBorrow extends QuartzJobBean {

	private static Log log = LogFactory.getLog(BorrowDailyCheck.class);
	public static StatisticsService ss;
	private static BorrowService borrowService;
	private static MemberService memberService;
	
	/*
	 * 实现二个功能
	 * 1：实现统计会员的借款统计功能
	 * 2：实现推广的借款统计功能
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		ss = ApplicationUtil.getBean(StatisticsService.class);
		memberService = ApplicationUtil.getBean(MemberService.class);
		borrowService = ApplicationUtil.getBean(BorrowService.class);
		try {
			List<Map<String,String>> memberList=DB.query("select * from member  order by id asc");
			for(int i=0;i<memberList.size();i++){
				int mid=Convert.strToInt(memberList.get(i).get("id"), 0);
				int alreadyBorrowSum=0;
				int alreadyRepaySum=0;
				int overdueSum=0;
				int dieSum=0;
				List<Map<String,String>> borrowList=DB.query("select * from borrow_main where member_id="+mid+" order by id asc");
				for(int j=0;j<borrowList.size();j++){
					int borrowStatus=Convert.strToInt(borrowList.get(j).get("borrowStatus"), 0);
					int pressStatus=Convert.strToInt(borrowList.get(j).get("pressStatus"), 0);
					
					if(borrowStatus>=8&&borrowStatus!=12){
						
						alreadyBorrowSum=alreadyBorrowSum+Convert.strToInt(borrowList.get(j).get("benJin"), 0);
					}
					if(borrowStatus==9){
						overdueSum=overdueSum+Convert.strToInt(borrowList.get(j).get("benJin"), 0);
					}
					if(pressStatus==4){
						dieSum=dieSum+Convert.strToInt(borrowList.get(j).get("benJin"), 0);
					}
					if(borrowStatus==10){
						alreadyRepaySum=alreadyRepaySum+Convert.strToInt(borrowList.get(j).get("benJin"), 0);
					}
				}
				//保存
				if(borrowList.size()>0){
					memberService.updateMemberBorrowStatistics(mid,alreadyBorrowSum,alreadyRepaySum,overdueSum,dieSum);
				}
				
			}
			
			
			
			//普遍会员
			List<Map<String,String>> membersList=DB.query("select * from member  order by id asc");
			for(int i=0;i<membersList.size();i++){
				int activesum=0;
				int invateLoadsum=0;
				int invateRepaysum=0;
				int invateOverduesum=0;
				int invateDieSum=0;
				List<Map<String,String>> relationList=DB.query("select * from member_ralation where memberId="+Convert.strToInt(membersList.get(i).get("id"), 0)+"  order by id asc");
				for(int j=0;j<relationList.size();j++){
					if("1".equals(relationList.get(j).get("status"))){
						activesum=activesum+1;
					}
					int afterMid=Convert.strToInt(relationList.get(j).get("afterMemberId"), 0);
					List<Map<String,String>> borrowList=DB.query("select * from borrow_main where  member_id="+afterMid+" order by id asc");
					for(int k=0;k<borrowList.size();k++){
						int borrowStatus=Convert.strToInt(borrowList.get(k).get("borrowStatus"), 0);
						int pressStatus=Convert.strToInt(borrowList.get(k).get("pressStatus"), 0);
						if(borrowStatus>=8){
							invateLoadsum=invateLoadsum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(borrowStatus==9){
							invateOverduesum=invateOverduesum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(pressStatus==4){
							invateDieSum=invateDieSum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(borrowStatus==10){
							invateRepaysum=invateRepaysum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
					}
				}
				memberService.updateMemberInviteDate(Convert.strToInt(membersList.get(i).get("id"), 0),activesum, invateLoadsum,invateRepaysum,invateOverduesum,invateDieSum);
			}
			
			//推广团队
			List<Map<String,String>> groupList=DB.query("select * from jiliang_extension  order by id asc");
			for(int i=0;i<groupList.size();i++){
				String key=groupList.get(i).get("onlyKey");
				String id=groupList.get(i).get("id");
				int activesum=0;
				int invateLoadsum=0;
				int invateRepaysum=0;
				int invateOverduesum=0;
				int invateDieSum=0;
				List<Map<String,String>> relationList=DB.query("select * from member_ralation where jiLiangNo= \'"+key+"\'  order by id asc");
				for(int j=0;j<relationList.size();j++){
//					if("1".equals(relationList.get(j).get("status"))){
//						activesum=activesum+1;
//					}
					
					int afterMid=Convert.strToInt(relationList.get(j).get("afterMemberId"), 0);
					List<Map<String,String>> borrowList=DB.query("select * from borrow_main where  member_id="+afterMid+" order by id asc");
					for(int k=0;k<borrowList.size();k++){
						int borrowStatus=Convert.strToInt(borrowList.get(k).get("borrowStatus"), 0);
						int pressStatus=Convert.strToInt(borrowList.get(k).get("pressStatus"), 0);
						if(borrowStatus>=8){
							activesum=activesum+1;
							invateLoadsum=invateLoadsum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(borrowStatus==9){
							invateOverduesum=invateOverduesum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(pressStatus==4){
							invateDieSum=invateDieSum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
						if(borrowStatus==10){
							invateRepaysum=invateRepaysum+Convert.strToInt(borrowList.get(k).get("benJin"), 0);
						}
					}
					
				}
				memberService.updateGroupInviteDate(Convert.strToInt(id, 0),activesum,invateLoadsum,invateRepaysum,invateOverduesum,invateDieSum);
			}
		
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}