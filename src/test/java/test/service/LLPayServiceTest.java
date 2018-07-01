package test.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpay.api.util.TraderRSAUtil;
import com.lianpay.bean.BusinessNoticeBean;
import com.lianpay.bean.NotifyResponseBean;
import com.lianpay.util.LLPayUtil;
import com.lianpay.util.SignUtil;
import com.spark.p2p.config.PaymentConfig;
import com.spark.p2p.service.LLPayService;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;

import test.base.BaseServiceTest;

public class LLPayServiceTest extends BaseServiceTest{
	@Autowired
	private LLPayService service;
	private PaymentConfig config = new PaymentConfig();
	
	@Test
	public void testSubmitPay() throws Exception{
		String orderId = GeneratorUtil.getOrderId("EX");
		System.out.println(orderId);
		MessageResult mr = service.submitPay(orderId, 0.1, "闫其政", "6228480052180065113", "中国银行");
	}
	
	//[{"date":"2017-07-25","amount":"128.00"}]
	//@Test
	public void testSetPaymentPlan(){
		String agreeNo = "2017081129512560";
		String repaymentNo = "REPAY124";
		JSONArray list = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("date", "2017-08-30");
		item.put("amount","0.2");
		list.add(item);
		service.authApply(594, agreeNo, repaymentNo, list);
	}
	
	//@Test
	public void testRepay() throws Exception{
		String orderId = GeneratorUtil.getOrderId("EX");
		String agreeNo = "2017081129512560";
		//还款编号必须与提交计划时一致
		String repaymentNo = "REPAY124";
		service.autoRepayment(orderId, 594, agreeNo, repaymentNo, 0.2, "2017-08-30");
	}
	
	
	
	//@Test
	public void testSign(){
		String json = "{\"bank_code\":\"01040000\",\"dt_order\":\"20170822164518\",\"info_order\":\"商品分期还款\",\"money_order\":\"0.2\",\"no_order\":\"EX150339151870234\",\"oid_partner\":\"201707301001914515\",\"oid_paybill\":\"2017082254030370\",\"pay_type\":\"D\",\"result_pay\":\"SUCCESS\",\"settle_date\":\"20170822\",\"sign\":\"MzUblMo/9MJge79DfEYqMzd4bCG/rCkCfwYStVxM1uxhZTFNrqQ0IPk2/TgZExtH9qlI4l4wMoxIuQPO1aY5fgtqzt4C4Ly9W9zszNK0fN9QOq8vwwjCaJeZneOi2N73cGVi8O7D/z34Injv2NpJWYvntiBsX/Wzx9ZR2L3h6AQ=\",\"sign_type\":\"RSA\"}";
		BusinessNoticeBean businessNoticeBean = JSON.parseObject(json,BusinessNoticeBean.class);
		
		
		boolean signCheck = LLPayUtil.checkSign(json,config.YT_PUB_KEY,config.MD5_KEY);
		
		System.out.println(signCheck);
	}
	
	//@Test
	public void testRisk() throws Exception{
		System.out.println(service.getRiskItem(594));
	}
}
