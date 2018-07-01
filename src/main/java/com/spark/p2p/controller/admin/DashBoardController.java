package com.spark.p2p.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.Menu;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

/**
 * 后台通知，统计仪表数据
 * @author yanqizheng
 *
 */
@RequestMapping("/admin")
@Controller
public class DashBoardController extends BaseAdminController{
	@Autowired
	private StatisticsService ss;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("dashboard/index")
	public String index(HttpServletRequest request) throws Exception{
		//今日会员
		String date = DateUtil.getDate();
		Map<String,Integer> memberMap=ss.getMemberData(date);
		request.setAttribute("memberMap", memberMap);
		//今日续期
		Map<String,String> renewalrMap=ss.getRenewalInfo(date);
		request.setAttribute("renewalrMap", renewalrMap);
		//今日拒绝
		Map<String,String> rejuseMap=ss.getRejuseInfo(date);
		request.setAttribute("rejuseMap", rejuseMap);
		//今日提交审核数量
		Map<String,String> applyTimesMap=ss.getTodayApplyBorrowSum(date);
		request.setAttribute("applyTimesMap", applyTimesMap);
		//今日借款总额
		Map<String,String> borrowMap=ss.getBenjinByStatus(date,8);
		request.setAttribute("borrowMap", borrowMap);
		//今日还款总额
		Map<String,String> repayMap=ss.getREPAYiNFO(date);
		request.setAttribute("repayMap", repayMap);
		//今日逾期总额
		Map<String,String> overdueMap=ss.getBenjinByStatus(date,9);
		request.setAttribute("overdueMap", overdueMap);
		//待办提醒
		List<Map<String,String>> daibanList=ss.queryDaibanBymid(getAdmin().getId(),1);
		request.setAttribute("daibanList", daibanList);
		//逾期提醒
		List<Map<String,String>> overdueList=ss.overdueList();
		for(int i=0;i<overdueList.size();i++){
			int mid=Convert.strToInt(overdueList.get(i).get("member_id"), 0);
			int borrowDate=Convert.strToInt(overdueList.get(i).get("borrowDate"), 0);
			int addBorrowDay=Convert.strToInt(overdueList.get(i).get("addBorrowDay"), 0);
			overdueList.get(i).put("overduedays",String.valueOf(borrowDate+addBorrowDay));
			Member m=memberService.findMember(mid);
			if(m != null){
				overdueList.get(i).put("realname",m.getRealName());
				overdueList.get(i).put("phone",m.getMobilePhone());
			}
			
		}
		request.setAttribute("overdueList", overdueList);
		Menu m=new Menu();
		m.setTitle("一级菜单");
		List<Menu> fl=new ArrayList<Menu>();
			Menu sm=new Menu();
			sm.setTitle("二级菜单");
			List<Menu> sl=new ArrayList<Menu>();
				Menu tm=new Menu();
				tm.setTitle("三级菜单");
				sl.add(tm);
			sm.setSubMenu(sl);
			fl.add(sm);
		m.setSubMenu(fl);
		request.setAttribute("dfsdfsd", m);
		return view("dashboard");
		
	}
	
	/**
	 * 一周放款
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("chart/rechargeWithdraw")
	public @ResponseBody MessageResult loadRechargeWithdrawChart() throws Exception{
		Calendar calendar = Calendar.getInstance();
		String dateEnd = DateUtil.YYYY_MM_DD.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		String dateBegin = DateUtil.YYYY_MM_DD.format(calendar.getTime());
		dateBegin = dateBegin+" 00:00:00";
		dateEnd = dateEnd+" 23:59:59";
		List<Map<String,String>> list = ss.queryStatisticsByDate("", dateBegin, dateEnd);
		List<String> labels = new ArrayList<String>();
		List<Double> rechargeData = new ArrayList<Double>();
		List<Double> withdrawData = new ArrayList<Double>();
		List<Integer> applyData = new ArrayList<Integer>();
		List<Integer> rejuseData = new ArrayList<Integer>();
		for(Map<String,String> item:list){
			Date date = DateUtil.YYYY_MM_DD.parse(item.get("add_time"));
			labels.add(DateUtil.getWeekDay(date));
			rechargeData.add(Convert.strToDouble(item.get("borrow_amount"), 0));
			withdrawData.add(Convert.strToDouble(item.get("repay_amount"), 0));
			applyData.add(Convert.strToInt(item.get("apply_count"), 0));
			rejuseData.add(Convert.strToInt(item.get("rejuse_count"), 0));
		}
		Map<String,List> result = new HashMap<String,List>();
		result.put("labels", labels);
		result.put("rechargeData", rechargeData);
		result.put("withdrawData", withdrawData);
		result.put("applyData", applyData);
		result.put("rejuseData", rejuseData);
		return success(result);
	}
	
	
	/**
	 * 一周用户投资
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("chart/invest")
	public @ResponseBody MessageResult loadInvestChart() throws Exception{
		Calendar calendar = Calendar.getInstance();
		String dateEnd = DateUtil.YYYY_MM_DD.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		String dateBegin = DateUtil.YYYY_MM_DD.format(calendar.getTime());
		List<Map<String,String>> list = ss.queryStatisticsByDate("invest_amount,date", dateBegin,dateEnd);
		Map<String,List> result = new HashMap<String,List>();
		List<String> labels = new ArrayList<String>();
		List<Double> investData = new ArrayList<Double>();
		for(Map<String,String> item:list){
			Date date = DateUtil.YYYY_MM_DD.parse(item.get("date"));
			labels.add(DateUtil.getWeekDay(date));
			investData.add(Convert.strToDouble(item.get("invest_amount"), 0));
		}
		result.put("labels", labels);
		result.put("investData", investData);
		return success(result);
	}
}
