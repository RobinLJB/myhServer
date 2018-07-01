package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.sql.model.Model;

@Service("adminFinanceService")
public class FinanceService extends BaseService {
	/**
	 * 用户资金列表
	 * 
	 * @return
	 */
	public DataTable queryFinanceList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		return pageEnableSearch(params, "member", "*", map, "id desc");
	}

	/**
	 * 资金记录列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryFinanceByUserid(DataTableRequest params, long id) throws SQLException {
		params.addColumn("userId", String.valueOf(id));
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "=");
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		return pageEnableSearch(params, "member_fundrecord", "*", map, "id desc");
	}

	
	/**
	 * 充值记录列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryRechargeList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("add_time", "between");
		String time = params.getSearchValue("add_time");
		if (StringUtils.isNotEmpty(time)&&time.length() > 13) {
			params.addColumn("min_add_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_add_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "v_member_recharge", "*", map, "id desc");
	}

	
	/**
	 * 提现记录列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryWithdrawList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("fundMode", "=");
		map.put("add_time", "between");
		params.addColumn("fundMode", "提现成功");
		String time = params.getSearchValue("add_time");
		if (time.length() > 13) {
			params.addColumn("min_add_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_add_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "v_fundrecord", "*", map, "id desc");
	}

	
	/**
	 * 投资记录列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryInvestList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("invest_time", "between");
		String time = params.getSearchValue("invest_time");
		if (time.length() > 13) {
			params.addColumn("min_invest_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_invest_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "v_invest_detail", "*", map, "id desc");
	}

	public DataTable queryBorrowByStatusAndAdminId(DataTableRequest params, long mid) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", "=");
		params.addColumn("mid", mid+"");	
		return pageEnableSearch(params, "withdraw_commision", "*", map, "id desc");
	}
	
	

	public long insertCommisionWithdrawRecord(double withdrawSum, String withdrawReview, long adminId, int mid,String cardNo,
			double commision,String remoteIp) throws SQLException {
		Model mc = new Model("withdraw_commision");
		mc.set("adminid",adminId);
		mc.set("mid",mid);
		mc.set("amount",withdrawSum);
		mc.set("withdrawReview",withdrawReview);
		mc.set("handleTime",DateUtil.getDateTime());
		mc.set("handleIp",withdrawSum);
		mc.set("cardNo",cardNo);
		mc.set("status",1);
		mc.insert();
		Model mcs = new Model("member");
		mcs.set("commisionSum",commision-withdrawSum);
		return mcs.update(mid);
		
	}

	
	public List<Map<String, String>> queryCommisionSum() throws Exception {
		return new Model("commision_record").select();
	}

	public Map<String, String> findConstantValue() throws Exception {
		return new Model("constant_variable").where("name= ?","COMMISIONFEE").find();
	}

	
	public DataTable queryplatCommisionList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "like");
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		//map.put("invest_time", "between");
		//String time = params.getSearchValue("invest_time");
		//if (time.length() > 13) {
		//	params.addColumn("min_invest_time", time.substring(0, 10) + " 00:00:00");
		//	params.addColumn("max_invest_time", time.substring(13) + " 23:59:59");
		//}
		return pageEnableSearch(params, "v_commision_record", "*", map, "id desc");
	}
	
	
	public DataTable jiLiangSubMemberBorrowDetailList(DataTableRequest params, long mid) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_id", "=");
		params.addColumn("member_id", mid+"");	
		return pageEnableSearch(params, "borrow_main", "*", map, "id desc");
	}
	
}
