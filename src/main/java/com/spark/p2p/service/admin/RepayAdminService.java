package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service("repayAdminService")
public class RepayAdminService extends BaseService {

	public List<Map<String,String>> queryAllRepayExcel(String condition) throws Exception {
		return new Model("v_borrow_period").where(condition).order("finalRepayTime desc").select();
	}

	// 逾期或者在正常还款期间
	public DataTable queryNotRepayBorrow(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();

			map.put("real_name", "like");
			map.put("mobilePhone", "like");
			map.put("adminUsername", "like");
			map.put("borrowStatus", "=");
			//params.addColumn("borrowStatus", borrowStatus + "");

			map.put("appointmentTime", "between");
			String time = params.getSearchValue("appointmentTime");
			if (time.length() > 13) {
				params.addColumn("min_appointmentTime", time.substring(0, 10) + " 00:00:00");
				params.addColumn("max_appointmentTime", time.substring(13) + " 23:59:59");
			}
		return pageEnableSearch(params, "v_borrow_period", "*", map, "realLoanTime desc");
	}

	public DataTable queryAlreadyRepayBorrow(DataTableRequest params, int borrowStatus) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("adminUsername", "like");
		map.put("borrowStatus", "=");
		params.addColumn("borrowStatus", borrowStatus + "");
		map.put("secondAuditTime", "between");
		String time = params.getSearchValue("secondAuditTime");
		if (time.length() > 13) {
			params.addColumn("min_secondAuditTime", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_secondAuditTime", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "v_borrow_audit", "*", map, "id desc");
	}

	@Transactional(rollbackFor = Exception.class)
	public long manulFullRepayDetail(int bid, int status, String auditOpinion, long adminId, double realamount,
			double remainBenjins, double overdueFees, int mid, String ip) throws Exception {
		// 更改借款状态
		long ret = 0;
		// 修改对应的借款状态
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//status=3说明是全额还款
		if (status == 3) {
			Model m = new Model("borrow_main");
			m.set("borrowStatus", 10);
			m.set("repayType", 2);
			m.set("finalRepayTime", sdf.format(now));
			m.update(bid);

			Model mo = new Model("member");
			mo.set("member_status", 1);
			mo.update(mid);

			//后台主动还款完成将苹果账号使用状态置一,还未测试
			Model ma=new Model("iphone_auth_info");
			ma.set("iphone_key",1);
			ma.where("member_id=? and iphone_key=0", mid).update();
		}

		Model mo = new Model("member");
		int alreadyRepaySum = Convert.strToInt(new Model("member").where("id=?", mid).find().get("alreadyRepaySum"), 0);
		mo.set("alreadyRepaySum", alreadyRepaySum + (int) realamount);
		mo.update(mid);

		// 增加还款记录
		Model maq = new Model("repay_main");
		maq.set("borrowId", bid);
		maq.set("adminid", adminId);
		maq.set("amount", realamount);
		maq.set("remainBenjin", remainBenjins);
		maq.set("overdueFee", overdueFees);
		maq.set("review", auditOpinion);
		maq.set("repayTime", sdf.format(now));
		maq.set("ip", ip);
		maq.set("repayType", status);

		maq.insert();

		// 增加手动还款的记录
		Model ma = new Model("back_manul_log");
		ma.set("adminId", adminId);
		ma.set("borrowId", bid);
		ma.set("review", auditOpinion);
		ma.set("repayType", status);
		ma.set("amount", realamount);
		ma.set("repayTime", sdf.format(now));
		ma.insert();

		// 增加资金记录
		Model mass = new Model("fund_record");
		mass.set("borrowId", bid);
		mass.set("occurTime", sdf.format(now));
		mass.set("amount", realamount);
		mass.set("ip", ip);
		mass.set("type", 2);
		mass.set("remark", auditOpinion);
		return mass.insert();
	}

	public List<Map<String, String>> queryApartRepayList(int bid) throws Exception {
		return new Model("v_repay_detail").where("borrowId=?  ", bid).select();
	}

	public Map<String, String> findLastPay(int bid) throws Exception {

		return new Model("repay_main").where("borrowId =  ?", bid).order("id desc").find();
	}

	public Map<String, String> findBorrowByid(int bid) throws Exception {

		return new Model("borrow_main").where("id =  ?", bid).order("id desc").find();
	}

	@Transactional(rollbackFor = Exception.class)
	public long manulOverdueRepay(int bid, String status, String review, Long id, String realamount, String remoteIp,
			long adminId) throws Exception {
		long ret = 0;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 更新借款状态
		Model ms = new Model("borrow_main");
		ms.set("repayType", 2);
		ms.set("borrowStatus", 10);
		ms.set("finalRepayTime", sdf.format(now));
		ret = ms.update(bid);
		// 更新资金记录
		Model msoo = new Model("fund_record");
		msoo.set("ip", remoteIp);
		msoo.set("type", 5);
		msoo.set("occurTime", sdf.format(now));
		msoo.set("borrowId", bid);
		msoo.set("remark", "后台逾期还款");
		msoo.set("amount", realamount);
		ret = msoo.insert();
		// 更新逾期记录
		Map<String, String> map = findOverdueByBid(bid);
		if (map != null) {
			Model mso = new Model("overdue_record");
			mso.set("realRepayAmount", Convert.strToDouble(realamount, 0));
			mso.set("realRepayTime", sdf.format(now));
			mso.set("ip", remoteIp);
			mso.set("adminId", adminId);
			ret = mso.update(Convert.strToInt(map.get("id"), 0));
		}

		Model mso = new Model("back_manul_log");
		mso.set("borrowId", bid);
		mso.set("review", review);
		mso.set("repayType", 1);
		mso.set("amount", Convert.strToDouble(realamount, 0));
		mso.set("repayTime", sdf.format(now));
		mso.set("ip", remoteIp);
		mso.set("adminId", adminId);
		ret = mso.insert();

		return ret;
	}

	public Map<String, String> findOverdueByBid(int bid) throws Exception {
		return new Model("overdue_record").where("borrow_id =  ?", bid).find();
	}

}
