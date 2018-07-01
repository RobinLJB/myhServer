package com.spark.p2p.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.FinanceService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

@Controller("financeAdminController")
@RequestMapping("/admin/funds")
public class FinanceController extends BaseAdminController {

	@Autowired
	private FinanceService financeService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BorrowAdminService borrowService;
	
	/**
	 * 用户资金
	 * @return
	 */
	@RequestMapping("member")
	public String memberFinance() {
		return view("finance/member-index");
	}
	

	/**
	 * 用户资金列表
	 * @return
	 */
	@RequestMapping("member/list")
	public DataTable memberFinanceList() {
		return dataTable((params) -> financeService.queryFinanceList(params));
	}
	
	
	/**
	 * 用户资金记录
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("member/detail/{mid}")
	public String memberRecord(HttpServletRequest request,@PathVariable long mid) throws Exception {
		Member member=memberService.findMember(mid);
//		List<Map<String,String>> list=memberService.queryBankCardList(mid);
//		request.setAttribute("list", list);
		/*Map<String,String> bank = this.memberService.findBankCard(mid);
		this.request.setAttribute("bank", bank);*/
		request.setAttribute("member", member);
		return view("finance/member-detail");
	}
	
	
	/**
	 * 用户佣金提现列表
	 * @return
	 */
	@RequestMapping("member/detail/list/{mid}")
	public DataTable memberFinanceDetailList(@PathVariable long mid) {
		return dataTable((params) -> financeService.queryBorrowByStatusAndAdminId(params,mid));
	}
	
	
	/**
	 * 用户佣金提现操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "member/withdraw")
	public @ResponseBody MessageResult handleWithdraw() throws Exception {
		double withdrawSum = Convert.strToDouble(request("withdrawSum"), 0);
		int mid = Convert.strToInt(request("mid"), 0);
		Member member=memberService.findMember(mid);
		if(withdrawSum>member.getCommisionSum()){
			return error("请输入小于总额的金额");
		}
		String withdrawReview=request("withdrawReview");
		String cardNo=request("cardNo");
		long result = 0l;
		Admin admin=getAdmin();
		result=financeService.insertCommisionWithdrawRecord(withdrawSum,withdrawReview,admin.getId(),mid,cardNo,member.getCommisionSum(),getRemoteIp());
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}
	
	
	/**
	 * 平台佣金的发放
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("commision")
	public String platCommision(HttpServletRequest request) throws Exception {
		List<Map<String, String>> list=financeService.queryCommisionSum();
		int fee=Convert.strToInt(financeService.findConstantValue().get("value"), 0);
		request.setAttribute("fee", fee*list.size());
		return view("finance/commision-index");
	}
	

	/**
	 * 平台佣金发放列表
	 * @return
	 */
	@RequestMapping("commision/list")
	public DataTable platCommisionList() {
		return dataTable((params) -> financeService.queryplatCommisionList(params));
	}
	
	
	
	/**
	 * 全部放款
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("allBorrow")
	public String allBorrow(HttpServletRequest request) throws Exception {
		List<Map<String, String>> list=borrowService.queryAllBorrowLargeByStatus(8);
		int benjin=0;
		for(int i=0;i<list.size();i++){
			benjin=benjin+Convert.strToInt(list.get(i).get("benJin"), 0);
		}
		request.setAttribute("benjin", benjin);
		request.setAttribute("count", list.size());
		return view("finance/allBorrow-index");
	}
	
	
	
	
	/**
	 * 全部放款
	 */
	@RequestMapping(value = "allBorrow/list")
	public DataTable forSecondAuditList() {
		return dataTable((params) -> borrowService.queryBorrowByStatus(params,8,1));
	}
	
	
	
	
	
	
	/**
	 * 正在还款中
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("allBorrowInRepay")
	public String allBorrowIn(HttpServletRequest request) throws Exception {
		List<Map<String, String>> list=borrowService.queryAllBorrowByStatus(8);
		int benjin=0;
		for(int i=0;i<list.size();i++){
			benjin=benjin+Convert.strToInt(list.get(i).get("benJin"), 0);
		}
		request.setAttribute("benjin", benjin);
		request.setAttribute("count", list.size());
		return view("finance/allBorrowInRepay-index");
	}
	
	
	
	
	/**
	 * 正在还款中
	 */
	@RequestMapping(value = "allBorrowInRepay/list")
	public DataTable allBorrowInList() {
		return dataTable((params) -> borrowService.queryBorrowByStatus(params,8,0));
	}
	
	
	
	/**
	 * 已经逾期
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("allOverdueBorrow")
	public String allOverdueBorrow(HttpServletRequest request) throws Exception {
		List<Map<String, String>> list=borrowService.queryAllBorrowByStatus(9);
		int benjin=0;
		for(int i=0;i<list.size();i++){
			benjin=benjin+Convert.strToInt(list.get(i).get("benJin"), 0);
		}
		request.setAttribute("benjin", benjin);
		request.setAttribute("count", list.size());
		return view("finance/allOverdueBorrow-index");
	}
	
	
	
	
	/**
	 * 正在还款中
	 */
	@RequestMapping(value = "allOverdueBorrow/list")
	public DataTable allOverdueBorrowList() {
		return dataTable((params) -> borrowService.queryBorrowByStatus(params,9,0));
	}
	
	

	

	

	

	

	

	


	
	
}
