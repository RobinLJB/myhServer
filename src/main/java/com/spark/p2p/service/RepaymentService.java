package com.spark.p2p.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spark.p2p.entity.InvestRepayment;
import com.spark.p2p.entity.Repayment;
import com.spark.p2p.service.admin.BaseService;
import com.sparkframework.sql.model.Model;

/**
 * 还款处理
 * @author yanqizheng
 *
 */
@Service
public class RepaymentService extends BaseService {
	
	/**
	 * 还款明细列表
	 * @param repayId
	 * @return
	 * @throws Exception
	 */
	public List<InvestRepayment> queryRepaymentDetail(long repayId) throws Exception{
		return new Model("loan_invest_repayment")
				.where("repayId = ?",repayId)
				.select(InvestRepayment.class);
	}
	
	public List<Map<String,String>> queryInvestRepayment(long repayId) throws Exception{
		return new Model("v_invest_repayment")
				.where("repayId = ?",repayId)
				.select();
	}
	
	public List<InvestRepayment> queryInvestRepayment(long investor,long loanId) throws Exception{
		return new Model("loan_invest_repayment")
				.where("investor = ? and invest_id = ?",investor,loanId)
				.select(InvestRepayment.class);
	}
	
	
	
}
