package com.spark.p2p.service.admin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.sun.org.apache.xpath.internal.operations.Mod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.PublicSuffixDomainFilter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.ShuJuMoHeService;
import com.spark.p2p.shujumohe.HttpUtils;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.FileUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service
public class BorrowAdminService extends BaseService {

	public static final Log log = LogFactory.getLog(BorrowAdminService.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private BorrowService borrowService;

	// 生成魔盒数据报告的url
	public String reportTaskaddr = ShuJuMoHeService.commonUrl + "/report.task.query/v2";
	// 生成的手机报告
	public String callReportReslut = null;
	public static String queryParam = ShuJuMoHeService.queryParam;
	static Map<String, String> headers = null;

	public DataTable queryForFristAudit(DataTableRequest params, long roleid) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("borrowStatus", "=");
		map.put("fristSubmitTime", "between");
		String time = params.getSearchValue("fristSubmitTime");
		if (roleid == 9) {
			params.addColumn("borrowStatus", "1");
		}
		if (time.length() > 13) {
			params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		}
		/*
		 * if(StringUtils.isEmpty(time)) { // 如果当前时间为空默认查询 七日数据 避免数据库查询速度慢
		 * 必须要将fristSubmitTime也重放一遍避免为空 后边不走条件
		 * params.addColumn("fristSubmitTime",DateUtil.getDate());
		 * params.addColumn("min_fristSubmitTime",
		 * DateUtil.YYYY_MM_DD.format(DateUtil.dateAddDay(new Date(), -1)) +
		 * " 00:00:00"); params.addColumn("max_fristSubmitTime",DateUtil.getDate() +
		 * " 23:59:59"); }
		 */
		return pageEnableSearch(params, "all_borrow_view", "*", map, "id desc");
	}

	// 后台操作员认领操作
	@Transactional(rollbackFor = Exception.class)
	public long toMyClaim(long adminId, int borrowId) throws Exception {
		long ret = 0;
		// 修改对应的借款状态
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("borrow_main");
		m.set("borrowStatus", 2);
		m.set("claimTime", sdf.format(now));
		ret = m.update(borrowId);
		// 增加后台人员的认领记录
		if (ret > 0) {
			Model ma = new Model("back_audit_log");
			ma.set("admin_id", adminId);
			ma.set("borrow_id", borrowId);
			ma.set("claim_time", sdf.format(now));
			ret = ma.insert();
		}
		return ret;
	}

	// 初审
	/*
	 * @Transactional(rollbackFor = Exception.class) public long
	 * updateBorrowStatus(int bid, int status, long adminId, String review,) throws
	 * Exception { long ret = 0; // 修改对应的借款状态 Date now = new Date();
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Model m =
	 * new Model("borrow_main"); m.set("borrowStatus", status);
	 * m.set("fristAuditTime", sdf.format(now)); ret = m.update(bid); if (ret < 0) {
	 * return -1; }
	 * 
	 * Model ma = new Model("back_audit_log"); ma.set("audit_status", status);
	 * ma.set("frist_audit_time", sdf.format(now)); ma.set("frist_review", review);
	 * 
	 * Map<String, String> baMap = findBackAuditByBId(bid); if (baMap != null) { //
	 * 增加后台人员的认领记录 ret = ma.update(Convert.strToLong(baMap.get("id"), 0)); } else {
	 * return -1; }
	 * 
	 * return ret; }
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateBorrowStatus(int bid, int benJin, int paidFee, int status, long adminId, String review)
			throws Exception {
		long ret = 0;
		// 修改对应的借款状态
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("borrow_main").set("borrowStatus", status).set("fristAuditTime", sdf.format(now))
				.set("benJin", benJin).set("serviceFee", (benJin - paidFee)).set("totalFee", (benJin - paidFee));
		ret = m.update(bid);
		if (ret < 0) {
			return -1;
		}

		Model ma = new Model("back_audit_log");
		ma.set("audit_status", status);
		ma.set("frist_audit_time", sdf.format(now));
		ma.set("frist_review", review);

		Map<String, String> baMap = findBackAuditByBId(bid);
		if (baMap != null) {
			// 增加后台人员的认领记录
			ret = ma.update(Convert.strToLong(baMap.get("id"), 0));
		} else {
			return -1;
		}

		return ret;
	}

	// 复审
	public long updateBorrowStatusByBid(int id, String review, long adminId, int status) throws SQLException {
		Model m = new Model("borrow_main");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m.set("borrowStatus", status);
		m.set("secondAuditTime", sdf.format(now));
		m.where("id=?", id).update();

		Model ms = new Model("back_audit_log");
		ms.set("second_audit_time", sdf.format(now));
		ms.set("second_review", review);
		ms.set("second_admin_id", adminId);
		long ret = ms.where("borrow_id=?", id).update();
		return ret;
	}

	// 付款(复审放款)通知 更新状态
	public long updateBorrowStatusByOrderNo(String orderNo, int borrowStatus) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, String> map = borrowService.findBorrowByOrderno(orderNo);
		if (map != null) {
			Member member = memberService.findMember(Convert.strToLong(map.get("member_id"), 0));
			Map<String, String> memberRelationMap = memberService
					.findMemberRelation(Convert.strToLong(map.get("member_id"), 0));
			if (memberRelationMap != null) {
				// 更新会员的佣金数，以及佣金记录
				if (Convert.strToInt(memberRelationMap.get("status"), 0) == 0) {
					Member getMember = null;
					String jiLiangNo = null;
					if ("0".equals(memberRelationMap.get("memberId"))) {
						// 计量邀请
						jiLiangNo = memberRelationMap.get("jiLiangNo");
						Map<String, String> jiliangmap = memberService.findJiliangByNo(jiLiangNo);
						int successBorrowMemberSum = Convert.strToInt(jiliangmap.get("successBorrowMemberSum"), 0);
						int jiliangid = Convert.strToInt(jiliangmap.get("id"), 0);
						memberService.updateJiliangSuccessSum(jiliangid, successBorrowMemberSum + 1);
					} else {
						// 会员邀请
						getMember = memberService.findMember(Convert.strToInt(memberRelationMap.get("memberId"), 0));
						// 每个邀请的只能发放一次佣金
						memberService.updateCommision(getMember, jiLiangNo, member, map,
								Convert.strToInt(memberRelationMap.get("id"), 0));

					}
				}
			}

		}

		Model m = new Model("borrow_main");
		m.set("borrowStatus", borrowStatus);
		if (borrowStatus == 8) { // 8 还款期间(确定已收到打款)
			m.set("realLoanTime", sdf.format(new Date()));
		}
		long ret = m.where("payOrderno = ?", orderNo).update();
		return ret;
	}

	public Map<String, String> findBorrowById(long id) throws Exception {
		return new Model("borrow_main").find(id);
	}

	public Map<String, String> findBackAuditByBId(long bid) throws Exception {
		return new Model("back_audit_log").where("borrow_id=?  ", bid).find();
	}

	public Map<String, String> findBankCardById(long id) throws Exception {
		return new Model("member_bank").where("id=?  ", id).find();
	}

	public DataTable queryBorrowByStatus(DataTableRequest params, long borrowStatus, int flag) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("adminUsername", "like");
		map.put("icloud_imgurl", "like");
		if (flag == 0) {
			map.put("borrowStatus", "=");
		} else {
			map.put("borrowStatus", ">=");
		}
		if (borrowStatus != 12) {
			map.put("isCancel", "=");
		}
		map.put("secondAuditTime", "between");
		String time = params.getSearchValue("secondAuditTime");
		if (time.length() > 13) {
			params.addColumn("min_secondAuditTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_secondAuditTime", time.substring(13) + " 23:59:59");
		}
		map.put("fristSubmitTime", "between");
		time = time + params.getSearchValue("fristSubmitTime");
		if (time.length() > 13) {
			params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		}
		if (borrowStatus != 12) {
			params.addColumn("isCancel", "0");
		}
		params.addColumn("borrowStatus", borrowStatus + "");

		return pageEnableSearch(params, "all_borrow_view", "*", map, "id desc");
	}

	@Transactional(rollbackFor = Exception.class)
	public long updateSecondBorrowStatus(int borrowId, long mid, /* int bankId, */ int borrowStatus, int benjin,
			int borrowDate, String review, long adminId, String ip, String projectid) throws Exception {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model ma = new Model("borrow_main");
		ma.set("borrowStatus", borrowStatus);
		ma.set("loanStatus", 2);
		ma.set("secondAuditTime", sdf.format(now));
		ma.set("realLoanTime", sdf.format(now));
		ma.set("benjin", benjin);
		ma.set("projectid", projectid);
		ma.set("borrowDate", borrowDate);
		Date appiontDate = DateUtil.dateAddDay(now, borrowDate);
		ma.set("appointmentTime", DateUtil.dateToString(appiontDate));
		Map<String, String> borrowProductMap = findProductByDayAndMount(benjin, borrowDate);
		double xinFee = Convert.strToDouble(borrowProductMap.get("xinfee"), 0);
		double shouFee = Convert.strToDouble(borrowProductMap.get("shoufee"), 0);
		double serviceFee = Convert.strToDouble(borrowProductMap.get("servicefee"), 0);
		double otherFee = Convert.strToDouble(borrowProductMap.get("otherFee"), 0);
		ma.set("xinFee", xinFee);
		ma.set("shouFee", shouFee);
		ma.set("serviceFee", serviceFee);

		ma.update(borrowId);
		// 记录放款记录
		/* Map<String, String> bankMap = findBankCardById(bankId); */
		Model ms = new Model("fund_record");
		ms.set("occurTime", sdf.format(now));
		ms.set("memberId", mid);
		ms.set("borrowId", borrowId);
		/* ms.set("bankCardNo", bankMap.get("cardNo")); */
		ms.set("amount", benjin);
		ms.set("ip", ip);
		ms.set("type", 1);
		ms.set("remark", "放款成功");
		return ms.insert();
		/*
		 * Map<String, String> asd=findBackAuditByBid(borrowId); Model m = new
		 * Model("back_audit_log"); m.set("second_audit_time", sdf.format(now));
		 * m.set("second_review",review); m.set("audit_status",borrowStatus);
		 * m.set("second_admin_id",adminId); return
		 * m.update(Convert.strToInt(asd.get("id"), 0));
		 */
	}

	public long updateBorrowSendMessage(int i, int borrowId) throws SQLException {
		Model ma = new Model("borrow_main");
		ma.set("isSendMessage", i);
		return ma.update(borrowId);
	}

	public long updateCallDetail(int memberId, String data) throws SQLException {
		Model m = new Model("member");
		m.set("callDetail", data);
		return m.update(memberId);
	}

	public Map<String, String> findBackAuditByBid(long bid) throws Exception {
		return new Model("back_audit_log").where("borrow_id=?  ", bid).find();
	}

	public DataTable queryBorrowByStatusAndAdminId(DataTableRequest params, long adminId, int borrowStatus, int flag)
			throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("benJin", "=");
		if (adminId != 1) {
			map.put("admin_id", "=");
		}
		if (borrowStatus != 12) {
			map.put("isCancel", "=");
		}

		if (flag == 0) {
			map.put("borrowStatus", "=");
		} else {
			map.put("borrowStatus", ">=");
		}
		map.put("fristSubmitTime", "between");
		String time = params.getSearchValue("fristSubmitTime");
		if (time.length() > 13) {
			params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		}
		if (adminId != 1) {
			params.addColumn("admin_id", adminId + "");
		}
		if (borrowStatus != 12) {
			params.addColumn("isCancel", "0");
		}

		params.addColumn("borrowStatus", borrowStatus + "");
		return pageEnableSearch(params, "v_claim_borrow", "*", map, "id desc");
	}

	public DataTable queryBorrowByStatusAndAdminIdsix(DataTableRequest params, long adminId, int borrowStatus, int flag)
			throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("benJin", "=");
		if (adminId != 1) {
			map.put("second_admin_id", "=");
		}

		if (borrowStatus != 12) {
			map.put("isCancel", "=");
		}
		if (borrowStatus != 7) {
			map.put("isSecondFail", "=");
		}

		if (flag == 0) {
			map.put("borrowStatus", "=");
		} else {
			map.put("borrowStatus", ">=");
		}

		map.put("fristSubmitTime", "between");
		String time = params.getSearchValue("fristSubmitTime");
		if (time.length() > 13) {
			params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		}
		if (adminId != 1) {
			params.addColumn("admin_id", adminId + "");
		}
		if (borrowStatus != 12) {
			params.addColumn("isCancel", "0");
		}
		if (borrowStatus != 7) {
			params.addColumn("isSecondFail", "0");
		}
		params.addColumn("borrowStatus", borrowStatus + "");

		return pageEnableSearch(params, "v_claim_borrow", "*", map, "id desc");
	}

	public List<Map<String, String>> queryRenewalRecordByBid(int bid) throws Exception {
		return new Model("renewal_record").where("borrowId=?  ", bid).select();
	}

	public Map<String, String> queryOverdueByBid(int bid) throws Exception {
		return new Model("overdue_record").where("borrow_id=?  ", bid).find();
	}

	public List<Map<String, String>> queryAllBorrowLargeByStatus(int status) throws Exception {
		return new Model("borrow_main").where("borrowStatus >=?  ", status).select();
	}

	public List<Map<String, String>> queryAllBorrowByStatus(int status) throws Exception {
		return new Model("borrow_main").where("borrowStatus =?  ", status).select();
	}

	public Map<String, String> findBorrowProductById(Integer id) throws Exception {
		return new Model("borrow_product").where("id =?  ", id).find();
	}

	public long updateBorrowProduct(Integer id, int amount, int borrowdays, double xinfee, double servicefee,
			double shoufee, double overduefee, int status) throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model ma = new Model("borrow_product");
		ma.set("amount", amount);
		ma.set("updateTime", sdf.format(now));
		ma.set("borrowdays", borrowdays);
		ma.set("xinfee", xinfee);
		ma.set("servicefee", servicefee);
		ma.set("shoufee", shoufee);
		ma.set("overduefee", overduefee);
		ma.set("status", status);
		return ma.update(id);

	}

	public long insertBorrowProduct(int amount, int borrowdays, double xinfee, double servicefee, double shoufee,
			double overduefee, int status) throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model ma = new Model("borrow_product");
		ma.set("amount", amount);
		ma.set("updateTime", sdf.format(now));
		ma.set("borrowdays", borrowdays);
		ma.set("xinfee", xinfee);
		ma.set("servicefee", servicefee);
		ma.set("shoufee", shoufee);
		ma.set("overduefee", overduefee);
		ma.set("status", status);
		return ma.insert();
	}

	public DataTable queryBorrowroduct(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("borrowStatus", "=");
		// map.put("fristSubmitTime", "between");
		// String time = params.getSearchValue("fristSubmitTime");
		// if (time.length() > 13) {
		// params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
		// params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		// }
		return pageEnableSearch(params, "borrow_product", "*", map, "id desc");
	}

	public Map<String, String> findProductByDayAndMount(int amount, int borrowDays) throws Exception {
		return new Model("borrow_product").where("amount = ? and borrowdays=?", amount, borrowDays).find();
	}

	// 复审
	public void secondAuditSuccess(String orderNO, int borrowId, double benjin, double xinFee, double shouFee,
			double serviceFee, int borrowDays, String review, long adminid) throws Exception {
		Model m = new Model("borrow_main");

		/* m.set("payOrderno", orderNO); */
		/*
		 * m.set("benJin", benjin); m.set("xinFee", xinFee); m.set("shouFee", shouFee);
		 * m.set("serviceFee", serviceFee); m.set("totalFee", (xinFee + shouFee +
		 * serviceFee)); m.set("borrowDate", borrowDays);
		 */

		m.set("borrowStatus", 6);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/* m.set("secondAuditTime", sdf.format(now)); */
		m.set("realLoanTime", sdf.format(now));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + borrowDays);
		String appointmentTime = sdf.format(calendar.getTime());
		m.set("appointmentTime", appointmentTime.substring(0, 10));
		m.update(borrowId);

		/*
		 * 暂时不加终审情况 Map<String, String> asd = findBackAuditByBid(borrowId); Model ms =
		 * new Model("back_audit_log"); ms.set("second_audit_time", sdf.format(now));
		 * ms.set("second_review", review); ms.set("second_admin_id", adminid);
		 * ms.update(Convert.strToInt(asd.get("id"), 0));
		 */
	}

	// public void updateBorrowLoanStatus(int borrowId, int borrowStatus, int
	// loanStatus,String orderno,
	// String review, long adminid) throws Exception {
	// Model m = new Model("borrow_main");
	// m.set("borrowStatus", borrowStatus);
	// m.set("loanStatus", loanStatus);
	// Date now = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// m.set("secondAuditTime", sdf.format(now));
	// m.update(borrowId);
	//
	// Map<String, String> asd=findBackAuditByBid(borrowId);
	// Model ms = new Model("back_audit_log");
	// ms.set("second_audit_time", sdf.format(now));
	// ms.set("second_review",review);
	// ms.set("second_admin_id",adminid);
	// ms.update(Convert.strToInt(asd.get("id"), 0));
	// }

	// 终审
	public void finalAuditSuccess(String orderNO, int borrowId, double benjin, double xinFee, double shouFee,
			double serviceFee, int borrowDays, /* String review, */ long adminid) throws Exception {
		Model m = new Model("borrow_main");

		m.set("payOrderno", orderNO);
		m.set("benJin", benjin);
		m.set("xinFee", xinFee);
		m.set("shouFee", shouFee);
		m.set("serviceFee", serviceFee);
		m.set("totalFee", (xinFee + shouFee + serviceFee));
		m.set("borrowDate", borrowDays);

		m.set("borrowStatus", 8);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/* m.set("secondAuditTime", sdf.format(now)); */
		m.set("realLoanTime", sdf.format(now));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + borrowDays);
		String appointmentTime = sdf.format(calendar.getTime());
		m.set("appointmentTime", appointmentTime.substring(0, 10));
		m.update(borrowId);

		/*
		 * 暂时不加终审情况 Map<String, String> asd = findBackAuditByBid(borrowId); Model ms =
		 * new Model("back_audit_log"); ms.set("second_audit_time", sdf.format(now));
		 * ms.set("second_review", review); ms.set("second_admin_id", adminid);
		 * ms.update(Convert.strToInt(asd.get("id"), 0));
		 */
	}

	// public void updateBorrowLoanStatus(int borrowId, int borrowStatus, int
	// loanStatus,String orderno,
	// String review, long adminid) throws Exception {
	// Model m = new Model("borrow_main");
	// m.set("borrowStatus", borrowStatus);
	// m.set("loanStatus", loanStatus);
	// Date now = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// m.set("secondAuditTime", sdf.format(now));
	// m.update(borrowId);
	//
	// Map<String, String> asd=findBackAuditByBid(borrowId);
	// Model ms = new Model("back_audit_log");
	// ms.set("second_audit_time", sdf.format(now));
	// ms.set("second_review",review);
	// ms.set("second_admin_id",adminid);
	// ms.update(Convert.strToInt(asd.get("id"), 0));
	// }
	// public long updateBorrowLoanStatusAndBankCardNo(int strToInt, int i, int j,
	// String currenttime, String string,
	// String project_id, int k,String bankCardNo) throws Exception {
	// long ret=0;
	// Model m = new Model("borrow_main");
	// m.set("borrowStatus", i);
	// m.set("loanStatus", j);
	// m.set("fuyouOrderno", currenttime);
	// m.set("projectid", project_id);
	// m.set("bankCardNo", bankCardNo);
	// Date now = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// m.set("secondAuditTime", sdf.format(now));
	// m.set("fristSubmitTime", sdf.format(now));
	// m.set("claimTime", sdf.format(now));
	// m.set("fristAuditTime", sdf.format(now));
	// m.set("secondAuditTime", sdf.format(now));
	// ret=m.update(strToInt);
	//
	// Model ms = new Model("back_audit_log");
	// ms.set("second_audit_time", sdf.format(now));
	// ms.set("second_review",string);
	// ms.set("admin_id",0);
	// ms.set("second_admin_id",0);
	// ms.set("borrow_id",strToInt);
	//
	// ret=ms.insert();
	// return ret;
	// }

	public List<Map<String, String>> queryBorrowByMid(long mid) throws Exception {
		return new Model("borrow_main").where("member_id=?", mid).select();
	}

	/**
	 * @param
	 * @throws Exception
	 * @throws @Description:
	 */
	public long getData(long memberId, String taskId) throws Exception {

		// 此段代码是为了获取手机通话记录并保存到数据库中去，暂时不用
		/*
		 * lastQueryResult = HttpUtils.executeHttpPost(queryTaskaddr, queryParam,
		 * headers, "task_id=" + taskId); // 获取返回code code =
		 * JSON.parseObject(lastQueryResult).getInteger("code"); data =
		 * JSON.parseObject(JSON.parseObject(lastQueryResult).getString("data")).
		 * getString("task_data"); log.info("结果集" + data);
		 * com.alibaba.fastjson.JSONObject object = JSON.parseObject(data);
		 * com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("call_info");
		 * 
		 * if (code == 0) { Model m = new Model("mobile_talk_detail"); for (Object
		 * object2 : jsonArray) { log.info(object2); Map<String, Object> map = (Map)
		 * object2; // log.info(map.get("total_call_count")); //
		 * log.info(map.get("call_record")); com.alibaba.fastjson.JSONArray jsonArray2 =
		 * (com.alibaba.fastjson.JSONArray) map.get("call_record");//
		 * 获取call_record通话详单里的数据 for (Object object3 : jsonArray2) { //
		 * log.info(object3); Map<String, Object> map2 = (Map) object3; //
		 * log.info(map2.get("call_cost")); m.set("member_id", memberId);
		 * m.set("callAddress", map2.get("call_address"));
		 * log.info(map2.get("call_address")); m.set("callDateTime",
		 * map2.get("call_start_time")); m.set("callTimeLength", map2.get("call_time"));
		 * m.set("callType", map2.get("call_type_name")); m.set("mobileNo",
		 * map2.get("call_other_number")); m.set("createTime", curTime); try { long ret
		 * = m.insert(); log.info("插入数据"); } catch (SQLException e) {
		 * e.printStackTrace(); } } // Map<K, V> map2=(Map)(map.get("call_record")); } }
		 */
		callReportReslut = HttpUtils.executeHttpPost(reportTaskaddr, queryParam, headers, "task_id=" + taskId);
		int code1 = JSON.parseObject(callReportReslut).getInteger("code");
		log.info("***********");
		// data = JSON.parseObject(callReportReslut).getString("data");
		if (code1 != 0 && code1 != 4001) {
			return -1;
		} else if (code1 == 4001) {
			Thread.sleep(15000);// 数据魔盒生成报告需要10秒左右
			callReportReslut = HttpUtils.executeHttpPost(reportTaskaddr, queryParam, headers, "task_id=" + taskId);// 等待10s后重新查询
			if (code1 == 0) {// 生成魔盒报告成功
				String data = JSON.parseObject(callReportReslut).getString("data");
				log.info(data);
				String callReport = gunzip(data);
				log.info(callReport);
				try {
					Model m = new Model("member");
					callReport = FileUtil.filterEmoji(callReport);
					m.set("callDetail", callReport);
					return m.update(memberId);
				} catch (Exception e) {
					// TODO: handle exception
					log.error(e.getMessage(), e);
					e.printStackTrace();
				}
			} else if (code1 == 0) {
				String data = JSON.parseObject(callReportReslut).getString("data");
				data = JSON.parseObject(callReportReslut).getString("data");
				log.info(data);
				String callReport = gunzip(data);
				log.info(callReport);
				try {
					Model m = new Model("member");
					callReport = FileUtil.filterEmoji(callReport);
					m.set("callDetail", callReport);
					return m.update(memberId);
				} catch (Exception e) {
					// TODO: handle exception
					log.error(e.getMessage(), e);
					e.printStackTrace();
				}

			}
		}
		return 0;

	}

	// 将魔盒报告得到的值解压缩成json格式
	@SuppressWarnings("restriction")
	public String gunzip(String compressedStr) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			// 对返回数据BASE64解码
			compressed = Base64.getDecoder().decode(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			// 解码后对数据gzip解压缩
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			// 最后对数据进行utf-8转码
			decompressed = out.toString("utf-8");
		} catch (IOException e) {
		}
		return decompressed;
	}
}
