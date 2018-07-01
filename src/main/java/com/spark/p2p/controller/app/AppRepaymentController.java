package com.spark.p2p.controller.app;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.spark.p2p.service.IphoneAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.LLPayService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.DB;

@Controller
@ResponseBody
@RequestMapping("/app/uc/repay")
public class AppRepaymentController extends AppBaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private RepayAdminService repayAdminService;

	@Autowired
	private LLPayService llpayService;
	@Autowired
	private IphoneAuthService iphoneAuthService;
	
	/**
	 * 还款（正常和逾期）
	 * 
	 * @return
	 */
	@RequestMapping({ "normalRepay" })
	public @ResponseBody MessageResult normalRepay() throws Exception {
		Member member = getMember();

		long memberId = member.getId();
		// 获取扣款的银行卡，扣款金额
		/*Map<String, String> bankCardMap = this.memberService.findBankCard(memberId);*/
		/*if (bankCardMap == null) {
			return error("您还未认证银行卡");
		}
		String bankCardNo = bankCardMap.get("cardNo");*/
		System.out.println("memberId = " + memberId);
		Map<String, String> borrowMap = borrowService.findBorrowByMemberAndStatus8_9(memberId ); 
		if(borrowMap == null){
			return error("您没有待还款的借款");
		}
		System.out.println(borrowMap);
		// 正常还款或者逾期还款
		int status = Integer.valueOf(borrowMap.get("borrowStatus"));
		long bid = Long.valueOf(borrowMap.get("id")); 

		double deduceSum = this.borrowService.getRepaySum(bid);

		
		//调用连连支付来还款
		String orderId = memberId + "" +DateUtil.YYYYMMDDMMHHSSSSS(new Date()) ;
		String agreeNo = member.getAgreeNo();
		String repaymentNo = "borrow_id_" + bid;
		//double amount = deduceSum / 1000;//测试金额除以1000
		double amount = deduceSum;
		String appointmentTime = borrowMap.get("appointmentTime");//DateUtil.dateToString(new Date()).substring(0,10);
		long ret=borrowService.initBorrowRepayOrderNo(bid,orderId);
		if(ret<=0){
			return error("付款未完成");
		}
		MessageResult mr = this.llpayService.autoRepayment(orderId, memberId, agreeNo,
				repaymentNo, amount, appointmentTime);
		if(mr.getCode() == 0){//扣款成功
			//扣款成功，更新苹果账号的apple_key为1，防止再取到
			ret=iphoneAuthService.updateIphoneKeyById(memberId);
		}
		return mr;
		

	}

	/**
	 * 申请续期----返回续期费用
	 * 只需要传borrowDays参数
	 * @return
	 */
	@RequestMapping({ "renewalRepay" })
	public @ResponseBody MessageResult renewalRepay() throws Exception {

		
		Member member = this.getMember();
		long memberId = member.getId();
		Map<String, String> borrowMap = this.borrowService.findBorrowByMemberAndStatus8_9(memberId);
		if ( borrowMap == null ) {
			return error("没有待还款的借款");
		}
		if(borrowMap.get("borrowStatus").equals("9")){
			return error("您的借款已经逾期，不能再申请续期");
		}
		
		String borrowId = borrowMap.get("id");
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		if (borrowDays == 0) {
			return error("请选择续期天数");
		}
		int amount = 0; 
		Map<String, String> repayMap = repayAdminService.findLastPay(Convert.strToInt(borrowId, 0));
		if (repayMap != null) {
			amount = (int) Convert.strToDouble(repayMap.get("remainBenjin"), 0);
		} else {
			amount = Convert.strToInt(borrowMap.get("benJin"), 0);
		}

		DecimalFormat df = new DecimalFormat("######0.00"); // 保留两位小数
		double xinFee = 0;
		double shouFee = 0;
		double serviceFee = Double.valueOf(borrowMap.get("serviceFee"));
		double otherFee = 0;
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));

		// 计算相关费用
		//String dateBegins = this.borrowService.getNDaysBehindStr(borrowMap.get("appointmentTime"), borrowDays);
		//System.out.println(dateBegins);
		MessageResult mr = new MessageResult();
		Map<String, String> map = new HashMap<String, String>();
		map.put("xinFee", df.format(xinFee));
		map.put("shouFee", df.format(shouFee));
		map.put("serviceFee", df.format(serviceFee));
		map.put("otherFee", df.format(otherFee));
		map.put("borrowId", "" + borrowId);
		map.put("daoqiday",borrowMap.get("appointmentTime"));
		//6.16 返回app还款时间仍按原来约定 
		//map.put("daoqiday", dateBegins);

		/*map.put("bankCardNo", borrowMap.get("bankCardNo"));*/
		map.put("realLoanTime",borrowMap.get("realLoanTime"));//放款时间
		mr.setCode(0);
		mr.setData(map);
		return mr;

	}

	/**
	 * 确认续期---扣除续期费用
	 * 
	 * @return
	 */
	@RequestMapping({ "confirmRenewalRepay" })
	public @ResponseBody MessageResult confirmRenewalRepay() throws Exception {
		Member member = getMember();

		long memberId = member.getId();
		System.out.println("memberId = " + memberId);
		// 获取扣款的银行卡，扣款金额
		/*Map<String, String> bankCardMap = this.memberService.findBankCard(memberId);
		if(bankCardMap == null){
			return error("您还未认证银行卡");
		}
		String bankCardNo = bankCardMap.get("cardNo");*/


		double deduceSum = 0;
		int borrowDays = Integer.valueOf( request("borrowDays") );
		
		
		Map<String, String> borrowMap = borrowService.findBorrowByMemberAndStatus8_9(memberId ); 
		if(borrowMap == null){
			return error("您没有待还款的结款");
		}  
		if(borrowMap.get("borrowStatus").equals("9")){
			return error("您的借款已经逾期，不能再申请续期");
		}
		
		long bid = Long.valueOf(borrowMap.get("id"));
		int benJin = Integer.valueOf(borrowMap.get("benJin"));
		DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
		double xinFee = 0;
		double shouFee = 0;
		//续期手续费应该和借款手续费一致
		double serviceFee =Double.valueOf(borrowMap.get("serviceFee"));
		double otherFee = 0;  
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
		//扣款金额
		deduceSum  =  xinFee + shouFee + serviceFee  ;
		//测试除以1000
		//deduceSum = 1.0 * deduceSum / 1000;

		//调用连连支付来还款
		String orderId = memberId + "" +DateUtil.YYYYMMDDMMHHSSSSS(new Date()) ;
		String agreeNo = member.getAgreeNo();
		String repaymentNo = "borrow_id_" + bid;
		String appointmentTime = borrowMap.get("appointmentTime").substring(0, 10);//DateUtil.dateToString(new Date()).substring(0,10);
		MessageResult mr = null ;
		
		
		
		//调用连连支付扣款
		/*mr = this.llpayService.autoRepayment(orderId, memberId, agreeNo,
				repaymentNo, deduceSum*1.0/1000, appointmentTime);*/
		mr = this.llpayService.autoRepayment(orderId, memberId, agreeNo,
				repaymentNo, deduceSum, appointmentTime);
		if(mr.getCode() == 0){ //连连支付扣款成功
			
			//重新设置还款计划 还款金额不变、还款时间改变
	    	String appointmentTime1 = this.borrowService.getNDaysBehindStr(appointmentTime, borrowDays) ;
	    	double amountToPay = benJin/* + Double.valueOf(borrowMap.get("totalFee"))*/ ;
	    	////////////测试数据！
	    	//amountToPay = 1.0 * amountToPay / 1000;
	    	
	    	Map<String, String> pl = new HashMap<String, String>();
			pl.put("date", appointmentTime1 );
			pl.put("amount", amountToPay + "" ); 
			com.alibaba.fastjson.JSONArray plans = new com.alibaba.fastjson.JSONArray();
			plans.add(pl);
			mr = this.llpayService.authApply(memberId, agreeNo, repaymentNo, plans);
			//mr = this.llpayService.resetRepaymentPlan(memberId, agreeNo, repaymentNo, plans);
			if(mr.getCode() != 0){
				DB.rollback();
				return mr;
			}
			// 保存扣款记录
			borrowService.insertFundRecord(member.getId(), Convert.strToInt(borrowMap.get("id"), 0)/*, bankCardNo*/,
					deduceSum + "", "扣除续期费用", getRemoteIp(), 3);
			// 发送短信
			//SMSUtil.sendContent(member.getMobilePhone(), "您的编号" + borrowMap.get("borrowNo") + "申请，申请续期" + borrowDays
			//		+ "天成功，续期费用" + deduceSum + "元，扣款卡号" + bankCardNo + "，请到时正常还款后继续续期！");
			
			String borrowNo = borrowMap.get("borrowNo");
			String content = SMSUtil.renewalBorrow(borrowNo,borrowDays,deduceSum/*,bankCardNo*/);
			SMSUtil.sendContent(member.getMobilePhone(), content);
			
			// 改变借款状态,记录申请逾期记录
			long ret = borrowService.borrowOverdueHandle(borrowMap, deduceSum, borrowDays, getRemoteIp()/*, bankCardNo*/);
		}else{
			DB.rollback();
		}
		return mr;

	}

	/**
	 * 逾期还款---显示需要还款多少
	 * 
	 * @return
	 */
	@RequestMapping({ "overdueRepay" })
	public @ResponseBody MessageResult overdueRepay() throws Exception {
		Member member = getMember();
		if (member == null) {
			return error(400, "token失效");
		}
		member = memberService.findMember(member.getId());
		String borrowId = request("borrowId");
		if (ValidateUtil.isnull(borrowId)) {
			return error("请输入借款编号");
		}
		Map<String, String> borrowMap = borrowService.findBorrowById(Convert.strToInt(borrowId, 0));
		// 判断是否已经部分还款
		Map<String, String> repayMap = borrowService.findLastPay(Convert.strToInt(borrowMap.get("id"), 0));
		String benjin = "";
		String overdueFee = "";
		if (repayMap == null) {
			benjin = borrowMap.get("benJin");
			overdueFee = borrowMap.get("overdueFee");
		} else {
			benjin = repayMap.get("remainBenjin");
			overdueFee = repayMap.get("afterFee");
		}
		MessageResult mr = new MessageResult();
		Map<String, String> map = new HashMap<String, String>();
		int borrowDate = Convert.strToInt(borrowMap.get("borrowDate"), 0);
		int addBorrowDay = Convert.strToInt(borrowMap.get("addBorrowDay"), 0);
		String secondAuditTime = borrowMap.get("secondAuditTime");
		Date startDay = DateUtil.strToDate(secondAuditTime);
		int days = (int) DateUtil.diffDays(startDay, new Date());
		map.put("remainDay", "" + (borrowDate + addBorrowDay - days));
		map.put("benJin", "" + benjin);
		map.put("overdueFee", "" + overdueFee);
		map.put("borrowId", "" + borrowId);
		mr.setCode(0);
		mr.setData(map);
		return mr;
	}


	// 收款
	public Map<String, String> koukuan(String bankno, String cityno, String bankname, String bankCardNo,
			String realname, String remarks, int amountFen, String idcardNo, String mobile, String projectid, int type)
					throws Exception {

		String path = "https://fht.fuiou.com/req.do";
		String merid = "0003720F0395576";
		String reqtype = "sincomeforreq";// 收款
		String types = "";
		if (type == 1) {
			types = "05";
		}
		if (type == 2) {
			types = "06";
		}
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" + "<incomeforreq>"
				+ "<ver>2.00</ver>" + "<merdt>" + DateUtil.getDateYMD() + "</merdt>" + "<orderno>"
				+ System.currentTimeMillis() + "911</orderno>" + "<bankno>" + bankno + "</bankno>" + "<cityno>" + cityno
				+ "</cityno>"// 付款
				+ "<accntno>" + bankCardNo + "</accntno>" + "<accntnm>" + realname + "</accntnm>" + "<amt>" + amountFen
				+ "</amt>" + "<entseq>test</entseq>" + "<memo>备注</memo>" + "<mobile>" + mobile + "</mobile>"
				+ "<certtp>0</certtp>" + "<certno>" + idcardNo + "</certno>"

				+ "<txncd>" + types + "</txncd>"// 9.5 付款业务定义说明01 借款下发 02 逾期还款03
												// 债权转让04 其他
		// 9.6 代收业务定义说明06 贷款还款07 逾期还款08 债权转让09 其他
				+ "<projectid>" + projectid + "</projectid>" + "</incomeforreq>";
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + "sincomeforreq" + "|" + xml;// 付款
		String mac = Encrypt.MD5(macSource).toUpperCase();
		String param = "merid=" + merid + "&reqtype=" + reqtype + "&xml=" + xml + "&mac=" + mac;
		return httpclientPost(path, param);
	}


}
