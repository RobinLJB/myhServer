package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service
public class OverdueBorrowService extends BaseService {
	
	/**
	 * 未分配，已分配
	 * @param params
	 * @param isDistribute
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryWaitForDistribution(DataTableRequest params ,int isDistribute) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("isDistribute", "=");
		params.addColumn("isDistribute", isDistribute+"");	
		
		//map.put("fristSubmitTime", "between");
		//String time = params.getSearchValue("fristSubmitTime");
		//if (time.length() > 13) {
		//	params.addColumn("min_fristSubmitTime", time.substring(0, 10) + " 00:00:00");
		//	params.addColumn("max_fristSubmitTime", time.substring(13) + " 23:59:59");
		//}
		return pageEnableSearch(params, "v_overdue_distribute", "*", map, "id desc");
	}

	

	@Transactional(rollbackFor = Exception.class)
	public long updateOverdueDistribution(int adminId, int borrowId,int auditId) throws Exception {
		long ret=0;
		Model ma = new Model("borrow_main");
		ma.set("isDistribute", 1);
		ma.set("pressStatus", 1);
		ret=ma.update(borrowId);
		if(ret>0){
			Model mas = new Model("back_audit_log");
			mas.set("overdue_distribution_time", DateUtil.getDateTime());
			mas.set("overdue_adminId", adminId);
			ret=mas.update(auditId);
		}else{
			throw new Exception();
		}
		return ret;
	}



	/**
	 * 我的逾期催收
	 * @param params
	 * @param isDistribute
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryDistributedToMe(DataTableRequest params ,long overdue_adminId,int pressStatus ) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("real_name", "like");
		map.put("mobilePhone", "like");
		map.put("pressStatus", "=");
		map.put("overdue_adminId", "=");
		params.addColumn("overdue_adminId", overdue_adminId+"");	
		params.addColumn("pressStatus", pressStatus+"");	
		
		
		map.put("overdue_distribution_time", "between");
		String time = params.getSearchValue("overdue_distribution_time");
		if (time.length() > 13) {
			params.addColumn("min_overdue_distribution_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_overdue_distribution_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "v_my_distribute", "*", map, "id desc");
	}
	
	public Map<String, String> findBackAuditByBId(long aid) throws Exception {
		return new Model("back_audit_log").where("id=?  ",aid).find();
	}


	@Transactional(rollbackFor = Exception.class)
	public long addPressRecord(int borrowId, long adminId, int phoneCall, int repayStatus,String auditOpinion,String memberId) throws Exception {
		long ret=0;
		Model ma = new Model("overdue_press_record");
		ma.set("adminId", adminId);
		ma.set("borrowId", borrowId);
		ma.set("phoneCall", phoneCall);
		ma.set("repayStatus", repayStatus);
		ma.set("review", auditOpinion);
		ma.set("pressTime", DateUtil.getDateTime());
		ret=ma.insert();
		if(ret>0){
			Model mas = new Model("borrow_main");
			Model member = new Model("member");
			Map<String, String> map=new Model("overdue_record").where("borrow_id=?  ",borrowId).find();
			int benjin=(int)Convert.strToDouble(map.get("benjin"), 0);
			int remainFee=(int)Convert.strToDouble(map.get("remainFee"), 0);
			int mid=(int)Convert.strToInt(memberId, 0);
			Map<String, String> mmap=new Model("member").where("id=?  ",mid).find();
			int dieSum=Convert.strToInt(mmap.get("dieSum"), 0);
			int overdueSum=Convert.strToInt(mmap.get("overdueSum"), 0);
			int alreadyRepaySum=Convert.strToInt(mmap.get("alreadyRepaySum"), 0);
			if(repayStatus==1){//已还
				//减去member逾期金额，增加已还金额
				member.set("alreadyRepaySum", alreadyRepaySum+benjin+remainFee);
				member.set("overdueSum",overdueSum-benjin);
				mas.set("pressStatus", 3);
				mas.set("borrowStatus", 10);
				mas.set("finalRepayTime", DateUtil.getDateTime());
			}else if(repayStatus==2){
				mas.set("pressStatus", 2);
			}else{//死账
				//减去member逾期金额，增加死账金额
				member.set("dieSum", dieSum+benjin+remainFee);
				member.set("overdueSum",overdueSum-benjin);
				member.set("member_status", 3);
				member.update(Convert.strToInt(memberId, 0));
				mas.set("pressStatus", 4);
			}
			ret=mas.update(borrowId);
		}else{
			throw new Exception();
		}
		return ret;
		
	}



	public List<Map<String, String>> queryPressRecord(int strToInt) throws Exception {
		return new Model("overdue_press_record").where("borrowId=?  ",strToInt).select();
	}

}
