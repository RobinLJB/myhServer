package com.spark.p2p.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spark.p2p.Enum.GeneralizeMobileTypeEnum;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

/**
 * 平台数据统计类
 * 
 * @author yanqizheng
 *
 */
@Service
public class StatisticsService extends BaseService {

	/**
	 * 会员数据统计
	 * 
	 * @param date
	 * @return
	 */
	public Map<String, Integer> getMemberData(String date) {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		int totalCount = new Model("member").where("create_time <= ?", timeEnd).count();
		int newRegCount = new Model("member").where("create_time between ? and ?", timeBegin, timeEnd).count();

		// map.put("newMember", newRegCount);
		// return map;

		int newRegIosCount = new Model("member").where("create_time between ? and ? and type =?", timeBegin, timeEnd,
				GeneralizeMobileTypeEnum.IOS.getType()).count();
		int newRegAndroidCount = new Model("member").where("create_time between ? and ? and type =?", timeBegin,
				timeEnd, GeneralizeMobileTypeEnum.ANDORID.getType()).count();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("totalMember", totalCount);
		map.put("newMemberIos", newRegIosCount);
		map.put("newMemberAndroid", newRegAndroidCount);
		map.put("newMember", newRegCount);
		return map;

	}

	/**
	 * 今日借款总额
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getBenjinByStatus(String date, int status) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model memberModel = new Model("borrow_main");
		int borrowSum = 0;
		int borrowTotal = 0;
		List<Map<String, String>> borrowssList = null;
		if (status == 8) {
			borrowssList = memberModel.where("secondAuditTime <= ? and   borrowStatus in (8,9,10)  ", timeEnd).select();
		} else {
			borrowssList = memberModel.where("secondAuditTime <= ? and   borrowStatus =  ?", timeEnd, status).select();
		}

		for (int i = 0; i < borrowssList.size(); i++) {
			borrowTotal = borrowTotal + Convert.strToInt(borrowssList.get(i).get("benJin"), 0);
		}
		List<Map<String, String>> borrowList = null;
		if (status == 8) {
			borrowList = memberModel
					.where("secondAuditTime between ? and ? and   borrowStatus in(8,9,10) ", timeBegin, timeEnd)
					.select();
		} else {
			borrowList = memberModel
					.where("secondAuditTime between ? and ? and   borrowStatus =  ?", timeBegin, timeEnd, status)
					.select();
		}

		for (int i = 0; i < borrowList.size(); i++) {
			borrowSum = borrowSum + Convert.strToInt(borrowList.get(i).get("benJin"), 0);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrowSum", borrowSum + "");
		map.put("times", borrowList.size() + "");
		map.put("borrowTotal", borrowTotal + "");
		map.put("ttimes", borrowssList.size() + "");
		return map;
	}

	/**
	 * 今日还款总额
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getREPAYiNFO(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model memberModel = new Model("repay_main");
		int borrowSum = 0;
		int borrowTotal = 0;

		List<Map<String, String>> borrowssList = memberModel.where("repayTime <= ? ", timeEnd).select();
		for (int i = 0; i < borrowssList.size(); i++) {
			borrowTotal = borrowTotal + (int) Convert.strToDouble(borrowssList.get(i).get("amount"), 0);
		}
		List<Map<String, String>> borrowList = memberModel.where("repayTime between ? and ? ", timeBegin, timeEnd)
				.select();
		for (int i = 0; i < borrowList.size(); i++) {
			borrowSum = borrowSum + (int) Convert.strToDouble(borrowList.get(i).get("amount"), 0);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrowSum", borrowSum + "");
		map.put("times", borrowList.size() + "");
		map.put("borrowTotal", borrowTotal + "");
		map.put("ttimes", borrowssList.size() + "");
		return map;
	}

	/**
	 * 今日申请总数
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getTodayApplyBorrowSum(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model memberModel = new Model("borrow_main");
		int borrowSum = 0;
		int borrowTotal = 0;
		List<Map<String, String>> borrowList = memberModel.where("fristSubmitTime between ? and ? ", timeBegin, timeEnd)
				.select();
		for (int i = 0; i < borrowList.size(); i++) {
			borrowSum = borrowSum + Convert.strToInt(borrowList.get(i).get("benJin"), 0);
		}
		List<Map<String, String>> borrowaaList = memberModel.where("fristSubmitTime <= ? ", timeEnd).select();
		for (int i = 0; i < borrowaaList.size(); i++) {
			borrowTotal = borrowTotal + Convert.strToInt(borrowaaList.get(i).get("benJin"), 0);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrowSum", borrowSum + "");
		map.put("applyTimes", borrowList.size() + "");
		map.put("borrowTotal", borrowTotal + "");
		map.put("totalTimes", borrowaaList.size() + "");
		return map;
	}

	/**
	 * 今日续期情况
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getRenewalInfo(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model memberModel = new Model("renewal_record");
		double borrowSum = 0;
		double borrowTotal = 0;
		List<Map<String, String>> borrowsasList = memberModel.where("renewalTime <= ? ", timeEnd).select();
		for (int i = 0; i < borrowsasList.size(); i++) {
			borrowTotal = borrowTotal + Convert.strToDouble(borrowsasList.get(i).get("renewalFee"), 0);
		}
		List<Map<String, String>> borrowList = memberModel.where("renewalTime between ? and ? ", timeBegin, timeEnd)
				.select();
		for (int i = 0; i < borrowList.size(); i++) {
			borrowSum = borrowSum + Convert.strToDouble(borrowList.get(i).get("renewalFee"), 0);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrowSum", borrowSum + "");
		map.put("renewalTimes", borrowList.size() + "");
		map.put("borrowTotal", borrowTotal + "");
		map.put("rrTimes", borrowsasList.size() + "");
		return map;
	}

	/**
	 * 今日拒绝
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getRejuseInfo(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model memberModel = new Model("borrow_main");
		double borrowSum = 0;
		double borrowTotal = 0;
		List<Map<String, String>> borroaswList = memberModel
				.where("fristSubmitTime <= ? and borrowStatus in(3,7)", timeEnd).select();
		for (int i = 0; i < borroaswList.size(); i++) {
			borrowTotal = borrowTotal + Convert.strToDouble(borroaswList.get(i).get("benJin"), 0);
		}
		List<Map<String, String>> borrowList = memberModel
				.where("fristSubmitTime between ? and ? and borrowStatus in(3,7)", timeBegin, timeEnd).select();
		for (int i = 0; i < borrowList.size(); i++) {
			borrowSum = borrowSum + Convert.strToDouble(borrowList.get(i).get("benJin"), 0);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrowSum", borrowSum + "");
		map.put("renewalTimes", borrowList.size() + "");
		map.put("borrowTotal", borrowTotal + "");
		map.put("tlTimes", borroaswList.size() + "");
		return map;
	}

	/**
	 * 待办事件
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryDaibanBymid(Long id, int status) throws Exception {
		return new Model("back_audit_log").where("admin_id = ? and audit_status= ? ", id, status).select();

	}

	/**
	 * 获取平台基本信息统计 1、会员数量 2、累计投资 3、待收 4、用户累计收益
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * 分析每日数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> analyseDailyData(String date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户统计
		Model modela = new Model("day_statistics");
		Map<String, Integer> memberMap = getMemberData(date);
		modela.set("member_total", memberMap.get("totalMember"));
		modela.set("member_register", memberMap.get("newMember"));
		map.putAll(memberMap);
		// 放款统计
		Map<String, String> borrowMap = getBenjinByStatus(date, 8);
		modela.set("borrow_amount", borrowMap.get("borrowSum"));
		modela.set("total_borrow_amount", borrowMap.get("borrowTotal"));
		modela.set("borrow_times", borrowMap.get("times"));
		modela.set("total_borrow_times", borrowMap.get("ttimes"));
		map.putAll(borrowMap);
		// 申请统计
		Map<String, String> applyMap = getTodayApplyBorrowSum(date);
		modela.set("total_apply_times", applyMap.get("totalTimes"));
		modela.set("apply_count", applyMap.get("applyTimes"));
		map.putAll(applyMap);
		// 还款统计
		Map<String, String> repayMap = getREPAYiNFO(date);
		modela.set("repay_amount", repayMap.get("borrowSum"));
		modela.set("total_repay_amount", repayMap.get("borrowTotal"));
		modela.set("repay_times", repayMap.get("times"));
		modela.set("total_repay_times", repayMap.get("ttimes"));
		map.putAll(repayMap);
		// 续期统计
		Map<String, String> renewalrMap = getRenewalInfo(date);
		modela.set("renewal_amount", renewalrMap.get("borrowSum"));
		modela.set("total_renewal_amount", renewalrMap.get("borrowTotal"));
		modela.set("renewal_times", renewalrMap.get("renewalTimes"));
		modela.set("total_renewal_times", renewalrMap.get("rrTimes"));
		map.putAll(renewalrMap);

		// 拒绝统计
		Map<String, String> rejuseMap = getRejuseInfo(date);
		modela.set("rejuse_count", rejuseMap.get("renewalTimes"));
		modela.set("total_rejuse_count", rejuseMap.get("tlTimes"));
		map.putAll(rejuseMap);
		// 逾期统计
		Map<String, String> overdueMap = getBenjinByStatus(date, 9);
		modela.set("overdue_amount", overdueMap.get("borrowSum"));
		modela.set("total_overdue_amount", overdueMap.get("borrowTotal"));
		modela.set("overdue_count", overdueMap.get("times"));
		modela.set("total_overdue_count", overdueMap.get("ttimes"));
		map.putAll(overdueMap);
		String[] dateParts = date.split("-");
		modela.set("year", dateParts[0]);
		modela.set("month", dateParts[1]);
		modela.set("day", dateParts[2]);
		modela.set("date", date);
		modela.set("add_time", DateUtil.getDateTime());
		modela.insert();

		return map;
	}

	public Map<String, Object> getBorrowData(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model model = new Model("loan");
		Map<String, String> totalMap = model.field("sum(amount) as totalAmount")
				.where("status in (3,4,5) and fullscale_time <= ?", timeEnd).find();
		int totalCount = model.count();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalBorrowCount", totalCount);
		result.put("totalBorrowAmt", totalMap.get("totalAmount"));
		Map<String, String> dailyMap = model.field("sum(amount) as totalAmount")
				.where("status in (3,4,5) and fullscale_time between ? and ?", timeBegin, timeEnd).find();
		int dailyCount = model.count();
		result.put("dailyBorrowCount", dailyCount);
		Double dailyTotalAmt = Convert.strToDouble(dailyMap.get("totalAmount"), 0);
		result.put("dailyBorrowAmt", dailyTotalAmt);
		return result;
	}

	/**
	 * 投资数据统计
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInvestData(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model investModel = new Model("v_invest_detail");
		Map<String, String> totalMap = investModel.field("sum(amount) as totalAmount")
				.where("loanStatus in (2,3,4,5) and invest_time <= ?", timeEnd).find();
		Double totalInvestAmt = Convert.strToDouble(totalMap.get("totalAmount"), 0);
		Map<String, String> dailyMap = investModel
				.where("loanStatus in (2,3,4,5) and invest_time between ? and ?", timeBegin, timeEnd).find();
		int investCount = new Model("loan_invest").where("invest_time between ? and ?", timeBegin, timeEnd).count();
		Map<String, String> countMap = new Model("loan_invest").field("COUNT( DISTINCT investor ) as count")
				.where("invest_time between ? and ?", timeBegin, timeEnd).find();
		int investorCount = Convert.strToInt(countMap.get("count"), 0);
		Double dailyInvestAmt = Convert.strToDouble(dailyMap.get("totalAmount"), 0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalInvestAmt", totalInvestAmt);
		map.put("dailyInvestAmt", dailyInvestAmt);
		map.put("dailyInvestCount", investCount);
		map.put("dailyInvestorCount", investorCount);
		return map;
	}

	/**
	 * 充值数据统计
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getRechargeData(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model investModel = new Model("member_recharge");
		Map<String, String> totalMap = investModel.field("sum(amount) as totalAmount")
				.where("status = 2 and add_time <= ?", timeEnd).find();
		Double totalRechargeAmt = Convert.strToDouble(totalMap.get("totalAmount"), 0);
		Map<String, String> dailyMap = investModel.where("status = 2 and add_time between ? and ?", timeBegin, timeEnd)
				.find();
		Double dailyRechargeAmt = Convert.strToDouble(dailyMap.get("totalAmount"), 0);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("totalRechargeAmt", totalRechargeAmt);
		map.put("dailyRechargeAmt", dailyRechargeAmt);
		return map;
	}

	/**
	 * 提现数据统计
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getWithdrawData(String date) throws Exception {
		String timeBegin = date + " 00:00:00";
		String timeEnd = date + " 23:59:59";
		Model investModel = new Model("member_withdraw");
		Map<String, String> totalMap = investModel.field("sum(amount) as totalAmount")
				.where("status = 2 and add_time <= ?", timeEnd).find();
		Double totalWithdrawAmt = Convert.strToDouble(totalMap.get("totalAmount"), 0);
		Map<String, String> dailyMap = investModel.where("status = 2 and add_time between ? and ?", timeBegin, timeEnd)
				.find();
		Double dailyWithdrawAmt = Convert.strToDouble(dailyMap.get("totalAmount"), 0);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("totalWithdrawAmt", totalWithdrawAmt);
		map.put("dailyWithdrawAmt", dailyWithdrawAmt);
		return map;
	}

	/**
	 * 获取当前待收信息
	 * 
	 * @return
	 * @throws Exception
	 */

	/**
	 * 获取还款信息
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */

	/**
	 * 查询统计数据
	 * 
	 * @param params
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public DataTable queryAllStatistics(DataTableRequest params, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(params.getSearchValue("date"))) {
			String dataStr = params.getSearchValue("date");
			String[] parts = dataStr.split("~");
			map.put("date", "between");
			params.addColumn("min_date", parts[0]);
			params.addColumn("max_date", parts[1]);
		}
		DataTable dt = null;
		try {
			dt = pageEnableSearch(params, "sys_statistics", "*", map, "id desc");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dt;
	}

	public List<Map<String, String>> queryStatisticsByDate(String fields, String dateBegin, String dateEnd)
			throws Exception {
		return new Model("day_statistics")

				.where("add_time between ? and ?", dateBegin, dateEnd).order("id asc").select();
	}

	public List<Map<String, String>> queryCheckMemberInfo(int status) throws Exception {
		return new Model("member_workauth").where("auditStatus=?", status).order("id asc").limit(1).select();
	}

	public List<Map<String, String>> checkCompanyInfoList(int status) throws Exception {
		return new Model("member_company_audit").where("status=?", status).order("id asc").limit(1).select();
	}

	public List<Map<String, String>> transferList(int transfer) throws Exception {
		return new Model("v_member_debt").where("status=?", transfer).order("id asc").limit(1).select();
	}

	public List<Map<String, String>> applicationList() throws Exception {
		return new Model("v_loan_application").where("status=1").order("id asc").limit(1).select();
	}

	public List<Map<String, String>> fristCheckList() throws Exception {
		return new Model("v_loan_detail").where("status=1").order("id asc").limit(1).select();
	}

	public List<Map<String, String>> finalCheckList() throws Exception {
		return new Model("v_loan_detail").where("status=3").order("id asc").limit(1).select();
	}

	public List<Map<String, String>> overdueList() throws Exception {
		return new Model("borrow_main").where("borrowStatus=9").order("id asc").select();
	}

	public DataTable queryDailyList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("add_time", "between");
		String time = params.getSearchValue("add_time");
		if (time.length() > 13) {
			params.addColumn("min_add_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_add_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "day_statistics", "*", map, "id desc");
	}

	public DataTable queryBorrowApplyList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");

		/*
		 * if(borrowStatus>0){ map.put("borrowStatus", "=");
		 * params.addColumn("borrowStatus", borrowStatus+""); }else{
		 * 
		 * }
		 */
		map.put("borrowStatus", "=");
		map.put("fristSubmitTime", "between");
		String time = params.getSearchValue("fristSubmitTime");
		if (time.length() > 13) {
			params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		}

		return pageEnableSearch(params, "all_borrow_view", "*", map, "id desc");
	}

	public DataTable queryBorrowAuditFailList(DataTableRequest params, int borrowStatus) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");

		if (borrowStatus > 0) {
			map.put("borrowStatus", "=");
			params.addColumn("borrowStatus", borrowStatus + "");
		} else {

		}
		if (borrowStatus == 3) {
			map.put("fristAuditTime", "between");
			String time = params.getSearchValue("fristAuditTime");
			if (time.length() > 13) {
				params.addColumn("min_fristAuditTime", time.substring(0, 10) + " 00:00:00");
				params.addColumn("max_fristAuditTime", time.substring(13) + " 23:59:59");
			}
		}
		if (borrowStatus == 7) {
			map.put("secondAuditTime", "between");
			String time = params.getSearchValue("secondAuditTime");
			if (time.length() > 13) {
				params.addColumn("min_secondAuditTime", time.substring(0, 10) + " 00:00:00");
				params.addColumn("max_secondAuditTime", time.substring(13) + " 23:59:59");
			}
		}

		return pageEnableSearch(params, "v_borrow_audit", "*", map, "id desc");
	}

	public DataTable queryloanBorrowList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("borrowStatus", ">=");
		params.addColumn("borrowStatus", "8");

		map.put("secondAuditTime", "between");
		String time = params.getSearchValue("secondAuditTime");
		if (time.length() > 13) {
			params.addColumn("min_secondAuditTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_secondAuditTime", time.substring(13) + " 23:59:59");
		}

		return pageEnableSearch(params, "v_borrow_audit", "*", map, "id desc");
	}

	public DataTable queryRenewalList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");

		map.put("renewalTime", "between");
		String time = params.getSearchValue("renewalTime");
		if (time.length() > 13) {
			params.addColumn("min_renewalTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_renewalTime", time.substring(13) + " 23:59:59");
		}

		return pageEnableSearch(params, "v_renewal_record", "*", map, "id desc");
	}

	public DataTable queryRepayList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("repayType", "=");

		map.put("repayTime", "between");
		String time = params.getSearchValue("repayTime");
		if (time.length() > 13) {
			params.addColumn("min_repayTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_repayTime", time.substring(13) + " 23:59:59");
		}

		return pageEnableSearch(params, "v_repay_detail", "*", map, "id desc");
	}

	public DataTable queryOverdueList(DataTableRequest params, int status) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("repayType", "=");
		if (status > 0) {
			map.put("pressStatus", "=");
			params.addColumn("pressStatus", status + "");
		}
		map.put("appointmentTime", "between");
		String time = params.getSearchValue("appointmentTime");
		if (time.length() > 13) {
			params.addColumn("min_appointmentTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_appointmentTime", time.substring(13) + " 23:59:59");
		}

		return pageEnableSearch(params, "v_overdue_detail", "*", map, "id desc");
	}

}
