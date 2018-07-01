package com.spark.p2p.controller.ucenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Pagination;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.FinanceService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.DataTable;
import com.sparkframework.lang.Convert;


/**
 * 会员资金
 * 1、充值、提现
 * 2、资金记录
 * 
 * @author yanqizheng
 *
 */
@Controller
@RequestMapping("/ucenter/finance")
public class FinanceController extends UCenterController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private MemberService mbService;
	/**
	 * 充值页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("recharge")
	public String recharge(HttpServletRequest request) throws Exception{
		Map<String,Double> finance = financeService.findMemberFinance(getUser().getId());
		finance.put("total", finance.get("usableBalance") + finance.get("freezeBalance")+ finance.get("dueinSum"));
		Map<String,String> memberInfo = new HashMap<String,String>();
		if(getUser().getRole()==1){
			memberInfo =  mbService.findMemberInfo(getUser().getId());
			if(memberInfo==null){
				return "redirect:/ucenter/safety/realname.html";
			}
		}else{
			memberInfo =  mbService.findMemberCompany(getUser().getId());
			if(memberInfo==null||memberInfo.get("status")!="2"){
				return "redirect:/ucenter/info.html";
			}
		} 
		request.setAttribute("finance", finance);	
		return view("finance-recharge");
	}
	
	/**
	 * 提现页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("withdraw")
	public String withdraw(HttpServletRequest request) throws Exception{
		//账户金额财务信息
		Member user = getUser();
		//账户金额财务信息
		Map<String,Double> finance = financeService.findMemberFinance(user.getId());
		finance.put("total", finance.get("usableBalance") + finance.get("freezeBalance")+ finance.get("dueinSum"));
		Map<String,String> memberInfo = new HashMap<String,String>();
		if(getUser().getRole()==1){
			memberInfo =  mbService.findMemberInfo(getUser().getId());
			if(memberInfo==null){
				return "redirect:/ucenter/safety/realname.html";
			}
		}else{
			memberInfo =  mbService.findMemberCompany(getUser().getId());
			if(memberInfo==null||memberInfo.get("status")!="2"){
				return "redirect:/ucenter/info.html";
			}
		}
		request.setAttribute("finance", finance);	
		return view("finance-withdraw");
	}
	
	/**
	 * 资金概况
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("fundrecord")
	public String fundrecord(HttpServletRequest request) throws Exception{
		Member user = getUser();
		Map<String,Double> finance = financeService.findMemberFinance(user.getId());
		finance.put("total", finance.get("usableBalance") + finance.get("freezeBalance")+ finance.get("dueinSum"));
		request.setAttribute("finance", finance);			

		Pagination page = getPage();
		request.setAttribute("finance2", financeService.queryFundrecordList(page,getUser().getId()));
		request.setAttribute("page", page);
		return view("finance-fundrecord");
	}
	
 
	/* 资金概况筛选*/
	@RequestMapping({"queryFundrecordList"})
	public DataTable creativelist() throws Exception{
		return dataTable((params) -> financeService.queryFundrecordList(params,getUser().getId()));
	}

	/*@RequestMapping(value="queryBank")
	public @ResponseBody JSONObject queryBankInfo(){
		String bankId = request("bankId");
		String province = request("province");
		String city = request("city");
		return new JSONObject("{}");
	}*/
}
