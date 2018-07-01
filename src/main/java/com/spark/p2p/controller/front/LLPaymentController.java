package com.spark.p2p.controller.front;

import java.io.UnsupportedEncodingException; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.LLPayService; 
import com.spark.p2p.service.admin.BorrowAdminService;
import com.sparkframework.sql.model.Model;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianpay.api.util.TraderRSAUtil;
import com.lianpay.bean.BusinessNoticeBean;
import com.lianpay.bean.NotifyResponseBean;
import com.lianpay.constant.PaymentStatusEnum;
import com.lianpay.util.LLPayUtil;
import com.lianpay.util.SignUtil;
import com.spark.p2p.config.PaymentConfig;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.service.MemberService; 
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert; 


@Controller
public class LLPaymentController extends BaseController {
	private Logger log  = LoggerFactory.getLogger(LLPaymentController.class);
	private PaymentConfig config = new PaymentConfig();
	@Autowired
	private MemberService memberService;
	@Autowired
	private LLPayService payService;
	
	@Autowired
	private BorrowAdminService borrowAdminService;
	@Autowired
	private BorrowService borrowService;
	
	/**
	 * 认证结果
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/llpay/auth/result")
	public String authResult(HttpServletRequest request) throws SQLException{
		String status = request("status");
		String result = request("result");
		try {
			result = new String(result.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
		if(StringUtils.isNotEmpty(status) && status.equals("0000")){
			JSONObject json = JSON.parseObject(result);
			log.info("result={}",json.toString());
			log.info("user_id:{},agreeno:{}",json.getString("user_id"),json.getString("agreeno"));
			long uid = Convert.strToLong(json.getString("user_id"), 0);
			String agreeNo = json.getString("agreeno");
			request.setAttribute("agreeno", agreeNo);
			memberService.updateMemberAgreeNo(uid, agreeNo);
			result = "绑定银行卡成功";
		}
		
		System.out.println("result = " + result);
		request.setAttribute("status", Convert.strToInt(status, 0));
		request.setAttribute("result", result);
		//Object weixinBindBankCard = this.request.getSession().getAttribute("weixinBindBankCard");
		//this.request.setAttribute("weixinBindBankCard", weixinBindBankCard);
		return view("auth/llpay-result");
	}
	
	@RequestMapping(value = "/llpay/pay/notify", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public NotifyResponseBean receiveNotify(@RequestBody String json) throws Exception {
		BusinessNoticeBean businessNoticeBean = JSON.parseObject(json,BusinessNoticeBean.class);
		log.info("notify request:" + businessNoticeBean.toString());
		System.out.println("notify request:" + businessNoticeBean.toString());
		NotifyResponseBean responseBean = new NotifyResponseBean();
		boolean signCheck = LLPayUtil.checkSign(json,config.YT_PUB_KEY,config.MD5_KEY);
		if (!signCheck) {
			// 传送数据被篡改，可抛出异常，再人为介入检查原因
			log.error("返回结果验签异常,可能数据被篡改");
			// 回调内容先验签，再处理相应逻辑
			responseBean.setRet_code("9999");
			responseBean.setRet_msg("未知异常");
			return responseBean;
		}
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(now);
		System.out.println(businessNoticeBean);
		System.out.println( "result_pay = " + businessNoticeBean.getResult_pay() + ", orderId = " + businessNoticeBean.getNo_order() );

		if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getValue())) {
			// 商户更新订单为成功，处理自己的业务逻辑
			String orderId =businessNoticeBean.getNo_order();
			String  oidPayBill=businessNoticeBean.getOid_paybill();
			
			this.borrowAdminService.updateBorrowStatusByOrderNo(orderId, 8);
			
		} else if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_FAILURE.getValue())) {
			// 商户更新订单为失败，处理自己的业务逻辑
			String orderId =businessNoticeBean.getNo_order();
			String  oidPayBill = businessNoticeBean.getOid_paybill();
			
			//如果支付失败，把borrowStatus置为5，重新复审
			this.borrowAdminService.updateBorrowStatusByOrderNo(orderId, 5);
		} else { 
			// TODO 返回订单为退款状态 ，商户可以更新订单为失败或者退款
			// 退款这种情况是极小概率情况下才会发生的，个别银行处理机制是先扣款后再打款给用户时，
			// 才检验卡号姓名信息的有效性，当卡号姓名信息有误发生退款，实际上钱没打款到商户。
			// 这种情况商户代码上也可不做考虑，如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对
			// 退款情况，异步通知会通知两次，先通知成功，后通知退款（极小概率情况下才会发生的）
		}
		//回调内容先验签，再处理相应逻辑
		responseBean.setRet_code("0000");
		responseBean.setRet_msg("交易成功");
		return responseBean;
	}
	
	
	@RequestMapping(value = "/llpay/repayment/notify", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public NotifyResponseBean repaymentNotify(@RequestBody String json) throws Exception {
		log.info(json);
		BusinessNoticeBean businessNoticeBean = JSON.parseObject(json,BusinessNoticeBean.class);
		log.info("repayment/notify request:" + businessNoticeBean.toString());
		System.out.println("repayment/notify request:" + businessNoticeBean.toString());
		
		NotifyResponseBean responseBean = new NotifyResponseBean();
		boolean signCheck = LLPayUtil.checkSign(json,config.YT_PUB_KEY,config.MD5_KEY);
		if (!signCheck) {
			// 传送数据被篡改，可抛出异常，再人为介入检查原因
			log.error("返回结果验签异常,可能数据被篡改");
			responseBean.setRet_code("9999");
			responseBean.setRet_msg("未知异常");
			return responseBean;
		}
		System.out.println("repaymentNotify | orderId = " + businessNoticeBean.getNo_order());
		log.info("repaymentNotify | orderId = " + businessNoticeBean.getNo_order());
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(now);
		if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getValue())) {
			// 商户更新订单为成功，处理自己的业务逻辑
			String orderId =businessNoticeBean.getNo_order();
			String  oidPayBill=businessNoticeBean.getOid_paybill();
			
			System.out.println("repayBorrowSuccess | orderId = " + orderId);
			log.info("repayBorrowSuccess | orderId = " + orderId);
			this.borrowService.repayBorrowSuccess(orderId, "127.0.0.1");
			
		} else if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_FAILURE.getValue())) {
			// 商户更新订单为失败，处理自己的业务逻辑
			String orderId =businessNoticeBean.getNo_order();
			String  oidPayBill=businessNoticeBean.getOid_paybill();
			
			System.out.println("repayBorrowFailed | orderId = " + orderId);
			log.info("repayBorrowFailed | orderId = " + orderId);
			this.borrowService.repayBorrowFailed(orderId, "127.0.0.1");
			
		} else {
			// TODO 返回订单为退款状态 ，商户可以更新订单为失败或者退款
			// 退款这种情况是极小概率情况下才会发生的，个别银行处理机制是先扣款后再打款给用户时，
			// 才检验卡号姓名信息的有效性，当卡号姓名信息有误发生退款，实际上钱没打款到商户。
			// 这种情况商户代码上也可不做考虑，如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对
			// 退款情况，异步通知会通知两次，先通知成功，后通知退款（极小概率情况下才会发生的）
		}
		String orderId=businessNoticeBean.getNo_order();
		new Model("borrow_main").where("repayOrderNo=?",orderId).setField("payStatus",0);
		//回调内容先验签，再处理相应逻辑
		responseBean.setRet_code("0000");
		responseBean.setRet_msg("交易成功");
		return responseBean;
	}
	
	public String createForwardHtml(String gateway,Map<String,String> map) {
		StringBuffer sb = new StringBuffer(
				"<html><head <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><script type='text/javascript'>window.onload=function(){document.getElementById('submitForm').submit();}</script></head><body>");
		// 表单内容  URLEncoder.encode(value, "utf-8")
		sb.append("<form action='" + gateway + "'  id='submitForm' method='post'>");
		map.forEach((key,value) -> {
			try {
				sb.append("<input type='hidden' name='"+key+"'  value='" + value + "'  />");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		sb.append("</form></body></html>");
		return sb.toString();
	}
	
	
	@RequestMapping("/llpay/pay")
	@ResponseBody
	public MessageResult testPay() throws Exception{
		String orderId = GeneratorUtil.getOrderId("EX");
		System.out.println(orderId);
		MessageResult mr = payService.submitPay(orderId, 0.1, "闫其政", "6228480052180065113", "中国银行");
		return mr;
	}
}
