package com.spark.p2p.service;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.entity.Recharge;
import com.spark.p2p.entity.Reward;
import com.spark.p2p.entity.Withdraw;
import com.spark.p2p.event.MemberEvent;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.Parameter;
import com.sparkframework.sql.ParameterDirection;
import com.sparkframework.sql.model.Model;

@Service
public class FinanceService extends BaseService {
	private static Log log = LogFactory.getLog(FinanceService.class);
	@Autowired
	private MemberEvent memberEvent;
	/**
	 * 提交充值之前，记录充值信息
	 * 
	 * @param userid
	 * @param amount
	 * @param merBillNo
	 * @param type
	 * @param dateTime
	 * @param remoteIp
	 * @throws SQLException
	 */
	public void addRecharge(long userid, double amount, String merBillNo, int type, String remoteIp)
			throws SQLException {
		Model m = new Model("member_recharge");
		m.set("uid", userid);
		m.set("amount", amount);
		m.set("merchant_bill_no", merBillNo);
		m.set("type", type);
		m.set("add_time", DateUtil.getDateTime());
		m.set("add_ip", remoteIp);
		m.set("status", 0);
		m.insert();
	}

	/**
	 * 修改用户资金(money为负数代表扣除余额)
	 * 
	 * @param userid
	 * @param money
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addUserMoney(long userid, double money) throws NumberFormatException, Exception {
		Model m = new Model("member");
		Double usable_balance = Double.parseDouble(m.find(userid).get("usable_balance"));
		m.set("usable_balance", money + usable_balance);
		m.update(userid);
	}

	/**
	 * 添加提现申请记录
	 * 
	 * @param userid
	 * @param amount
	 * @param merFee
	 * @param merDate
	 * @param merBillNo
	 * @param remoteIp
	 * @throws SQLException
	 */
	public long addWithdraw(long userid, double amount, double merFee, String orderId, String remoteIp)
			throws SQLException {
		Model m = new Model("member_withdraw");
		m.set("uid", userid);
		m.set("amount", amount);
		m.set("fee", merFee);
		m.set("add_time", DateUtil.getDateTime());
		m.set("add_ip", remoteIp);
		m.set("merchant_bill_no", orderId);
		return m.insert();
	}

	/**
	 * 添加用户资金记录
	 * 
	 * @param uid
	 * @param amount
	 * @param type
	 * @param opcode
	 * @param remark
	 * @return
	 */
	public long addMemberFundrecord(long uid, double sum, int type, int opcode, String remark) {
		long ret = 1;
		try {
			Parameter param1 = new Parameter(Types.BIGINT, ParameterDirection.IN, uid);
			Parameter param2 = new Parameter(Types.DECIMAL, ParameterDirection.IN, sum);
			Parameter param3 = new Parameter(Types.INTEGER, ParameterDirection.IN, type);
			Parameter param4 = new Parameter(Types.INTEGER, ParameterDirection.IN, opcode);
			Parameter param5 = new Parameter(Types.VARCHAR, ParameterDirection.IN, remark);
			Parameter[] params = new Parameter[] { param1, param2, param3, param4, param5 };
			DB.executeProcedure("p_add_fundrecord", params);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return ret;
	}

	// 查询投资信息
	public int checkInvestBill(long bid, long uid) {
		Model m = new Model("loan_invest");
		return m.where("investor = ? and loan_id = ?", uid, bid).count();
	}

	/**
	 * 资金概况筛选
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryFundrecordList(Pagination page, long uid) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("  userId =" + uid);
		List<Map<String, String>> list = PageList(page, "member_fundrecord", "*", sb.toString(), "id desc");
		fillPage(page, "member_fundrecord", "*", sb.toString(), "id desc");
		return list;
	}

	public DataTable queryFundrecordList(DataTableRequest params, long uid) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "=");
		map.put("userId", "=");
		params.addColumn("userId", uid + "");
		map.put("add_time", "between");
		String time = params.getSearchValue("add_time");
		if (time.length() > 13) {
			params.addColumn("min_add_time", time.substring(0, 10) + " 00:00:00");
			params.addColumn("max_add_time", time.substring(13) + " 23:59:59");
		}
		return pageEnableSearch(params, "member_fundrecord", "*", map, "id desc");
	}

	public Map<String, Double> findMemberFinance(long uid) throws Exception {
		Map<String, String> memberMap = new HashMap<String, String>();
		Model model = new Model("member");
		memberMap = null;
		//Map<String, String> dueinMap = new Model("loan_invest_repayment").where("investor = ? and status = 1", uid)
		//		.field("sum(principal + interest) as dueinSum").find();
		
		//投标的待收金额
		String sql = "SELECT  IFNULL( sum(a.principal + a.interest) , 0 ) as dueinSum "
				+ " from loan_invest_repayment as a, loan_invest as b "
				+ " where a.investor = " + uid + " and a.status = 1 and a.invest_id = b.id and b.is_debt=1";
		double loanDueinSum = Double.valueOf(DB.query(sql).get(0).get("dueinSum"));
		
		
		Map<String, Double> retMap = new HashMap<String, Double>();
		log.info(memberMap);
		retMap.put("usableBalance", Convert.strToDouble("0", 0));
		retMap.put("freezeBalance", Convert.strToDouble("0", 0));
		retMap.put("dueinSum", loanDueinSum);
		return retMap;
	}

	/**
	 * 充值处理
	 * 
	 * @param orderId
	 * @param amount
	 * @param status
	 * @param tradeNo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public MessageResult processRechargeOrder(String orderId, Double amount, int status, String tradeNo)
			throws Exception {
		Model m = new Model("member_recharge");
		Recharge recharge = m.where("merchant_bill_no=?", orderId).find(Recharge.class);
		if (recharge == null) {
			return MessageResult.error(400, "订单不存在");
		} else if (recharge.getStatus() == 2) {
			return MessageResult.error(500, "订单已处理");
		}
		if (status == 2) {
			m.set("status", 2);
		} else {
			m.set("status", -1);
		}
		m.set("transaction_bill_no", tradeNo);
		m.set("update_time", DateUtil.getDateTime());
		long ret = m.where("merchant_bill_no=?", orderId).update();
		if (status == 2 && ret > 0) {
			ret = new Model("member").where("id = ?", recharge.getUid()).setInc("usable_balance", amount);
			if (ret > 0) {
				this.addMemberFundrecord(recharge.getUid(), amount, 1, 1000, "");
				memberEvent.onFinance(recharge.getUid(),amount,"FINANCE_RECHARGE");
			} else {
				DB.rollback();
				return MessageResult.error(500, "处理失败");
			}	
		}
		
		return MessageResult.success();
	}

	public long addFreezeMoney(long uid, double amount) throws SQLException {
		return DB.exec(
				"update member set usable_balance = usable_balance - ? , freeze_balance = freeze_balance + ? where id = ? and usable_balance >= ?",
				amount, amount, uid, amount);
	}

	public long decFreezeMoney(long userid, double money) throws Exception {
		Model m = new Model("member");
		return m.where("id = ?", userid).setDec("freeze_balance", money);
	}

	public long unFreezeMoney(long uid, double amount) throws SQLException {
		return DB.exec ( 
				"update member set usable_balance = usable_balance + ? , freeze_balance = freeze_balance - ? where id = ?",
				amount, amount, uid ) ; 
	}

	@Transactional(rollbackFor = Exception.class)
	public MessageResult processWithdrawOrder(String orderId, Double amount, int status, String tradeNo)
			throws Exception {
		Model m = new Model("member_withdraw");
		Withdraw withdraw = m.where("merchant_bill_no = ?", orderId).find(Withdraw.class);
		if (withdraw == null) {
			return MessageResult.error(400, "订单不存在");
		} else if (withdraw.getStatus() != 0 && withdraw.getStatus() != 1) {
			return MessageResult.error(500, "订单已处理");
		}
		long ret = 0;
		ret = m.set("status", status).set("transaction_bill_no", tradeNo).where("id = ?", withdraw.getId()).update();
		if (status == 1) {
			// 处理中
			ret = addFreezeMoney(withdraw.getUid(), withdraw.getAmount());
			if (ret > 0) {
				addMemberFundrecord(withdraw.getUid(), amount, 3, 1200, "提现处理中");
			}
		} else if (status == 2) {
			// 提现成功
			ret = decFreezeMoney(withdraw.getUid(), withdraw.getAmount());
			if (ret > 0) {
				addMemberFundrecord(withdraw.getUid(), amount, 2, 1202, "提现成功");
				memberEvent.onFinance(withdraw.getUid(),amount,"FINANCE_WITHDRAW");
			}	
		} else if (status == -1) {
			// 提现失败，不处理
		} else if (status == 5) {
			// 提现退票
			ret = unFreezeMoney(withdraw.getUid(), withdraw.getAmount());
			if (ret > 0) {
				addMemberFundrecord(withdraw.getUid(), amount, 4, 1203, "提现退票");
				memberEvent.onFinance(withdraw.getUid(),amount,"FINANCE_WITHDRAW_ERROR");
			}		
		}
		return MessageResult.success();
	}


	


	/**
	 * 更新奖励状态
	 * 
	 * @param rewardId
	 * @param status
	 * @return
	 * @throws SQLException
	 */

	
	
	/**
	 * 
	 * @param uid
	 * @param amount
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public long sendSavingPotReward(long uid,double amount,String date) throws NumberFormatException, Exception{
		String[] dateParts = date.split("-");
		long ret = new Model("xbao_interest_record")
				.set("uid", uid)
				.set("interest", amount)
				.set("date", date)
				.set("year", dateParts[0])
				.set("month", dateParts[1])
				.set("day", dateParts[2])
				.insert();
		if(ret > 0 && amount > 0){
			addUserMoney(uid, amount);
			addMemberFundrecord(uid,amount,1,4040,"");
		}
		return ret;
	}
	
	/**
	 * 按日期范围查找存钱罐收益
	 * @param uid
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> querySavingPotByDateRange(long uid,String begin,String end) throws Exception{
		return new Model("xbao_interest_record")
				.field("uid,date,interest as amount")
				.where("uid = ? and date between ? and ?",uid,begin,end)
				.select();
	}
	
	
}
