package com.spark.p2p.service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianlianpay.security.utils.LianLianPaySecurity;
import com.lianpay.util.LLPayUtil;
import com.lianpay.util.SignUtil;
import com.lianpay.util.YTHttpHandler;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.config.PaymentConfig;
import com.spark.p2p.entity.llpay.BankCardAgreeBean;
import com.spark.p2p.entity.llpay.BankCardPayBean;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.RSAUtil;
import com.spark.p2p.util.TraderRSAUtil;
import com.sparkframework.lang.Convert;

/**
 * 连连支付接口处理类
 * 
 * @author yanqizheng
 *
 */
@Service
public class LLPayService {
	
	public static final Log log = LogFactory.getLog(LLPayService.class);
	
	@Autowired
	private MemberService memberService;
	private PaymentConfig config = new PaymentConfig();

	/**
	 * 授权申请，提交还款计划
	 * 
	 * @param userId
	 *            用户唯一编号
	 * @param agreeNo
	 *            用户授权号
	 * @param repaymentNo
	 *            还款编号
	 * @param plans
	 * 还款计划明细，json数组格式,如
	 *            [{"date":"2017-07-25","amount":"128.00"},{"date":"2017-08-25","amount":"128.00"}]
	 * @return code = 0表示成功
	 * 
	 */
	public MessageResult authApply(long userId, String agreeNo, String repaymentNo, JSONArray plans) {
		BankCardAgreeBean bankCardAgreeBean = new BankCardAgreeBean();
		bankCardAgreeBean.setOid_partner(config.OID_PARTNER);
		// 商户用户id
		bankCardAgreeBean.setUser_id(String.valueOf(userId));
		bankCardAgreeBean.setSign_type("RSA");
		bankCardAgreeBean.setApi_version(config.VERSION);
		bankCardAgreeBean.setRepayment_no(repaymentNo);
		// 分期计划时间需要传当天或者之后的日期
		bankCardAgreeBean.setRepayment_plan("{\"repaymentPlan\":" + plans.toJSONString() + "}");
		// 短信参数字段, contract_type 商户名称 , contact_way 商户联系电话
		//bankCardAgreeBean.setSms_param("{\"contract_type\":\"商品分期\",\"contact_way\":\"" + config.contact_way + "\"}");
		bankCardAgreeBean.setSms_param("{\"contract_type\":\""+AppSetting.APP_NAME+"\",\"contact_way\":\"" + config.contact_way + "\"}");
		bankCardAgreeBean.setPay_type("D");
		// 调用签约接口后返回的协议号,和 用户id 对应
		bankCardAgreeBean.setNo_agree(agreeNo);
		bankCardAgreeBean.setSign(genSign(JSON.parseObject(JSON.toJSONString(bankCardAgreeBean))));
		String reqJson = JSON.toJSONString(bankCardAgreeBean);
		log.info("请求报文:"+reqJson);
		String resJson = YTHttpHandler.getInstance().doRequestPostString(reqJson, config.APPLY_GATEWAY);
		log.info("结果报文为:" + resJson);
		return parseResult(resJson);
	}

	/**
	 * 重置还款计划，当需要提前还款或有逾期利息时需要
	 * 
	 * @param userId
	 *            用户编号
	 * @param agreeNo
	 *            用户授权号
	 * @param repaymentNo
	 *            还款编号
	 * @param plans
	 *            新的还款计划，[{"date":"2017-07-25","amount":"128.00"},{"date":
	 *            "2017-08-25","amount":"128.00"}]， 注意：已经还完的计划不需要加入
	 * @return
	 */
	public MessageResult resetRepaymentPlan(long userId, String agreeNo, String repaymentNo, JSONArray plans) {
		MessageResult result = new MessageResult(0, "SUCCESS");
		JSONObject json = new JSONObject();
		json.put("oid_partner", config.OID_PARTNER);
		json.put("sign_type", "RSA");
		json.put("user_id", String.valueOf(userId));
		json.put("repayment_no", repaymentNo);
		json.put("repayment_plan", "{\"repaymentPlan\":" + plans.toJSONString() + "}");
		json.put("sms_param", "{\"contract_type\":\"测试\",\"contact_way\":\"" + config.contact_way + "\"}");
		json.put("sign", genSign(json));
		log.info(json.toJSONString());
		log.info(json.toString());

		String resJson = YTHttpHandler.getInstance().doRequestPostString(json.toJSONString(),
				config.PLAN_CHANGE_GATEWAY);
		log.info(resJson);
		result = parseResult(resJson);
		return result;
	}

	/**
	 * 系统发起银行卡扣款
	 * 
	 * @param orderId
	 *            订单号
	 * @param userId
	 *            用户编号
	 * @param agreeNo
	 *            授权号
	 * @param repaymentNo
	 *            还款编号
	 * @param amount
	 *            还款金额
	 * @param dueRepaymentDate
	 *            本次还款计划日期
	 * @return 
	 * @throws Exception 
	 */
	public MessageResult autoRepayment(String orderId, long userId, String agreeNo, String repaymentNo, double amount,
			String dueRepaymentDate) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		BankCardPayBean bankCardPayBean = new BankCardPayBean();
		bankCardPayBean.setOid_partner(config.OID_PARTNER);
		bankCardPayBean.setBusi_partner("101001");
		bankCardPayBean.setUser_id(String.valueOf(userId));
		bankCardPayBean.setSign_type("RSA");
		bankCardPayBean.setApi_version(config.VERSION);
		bankCardPayBean.setNo_order(orderId);
		bankCardPayBean.setDt_order(DateUtil.YYYYMMDDHHMMSS.format(new Date()));
		bankCardPayBean.setRepayment_no(repaymentNo);
		bankCardPayBean.setName_goods("现金贷款");
		bankCardPayBean.setInfo_order("现金贷款还款计划");
		bankCardPayBean.setMoney_order(df.format(amount));
		bankCardPayBean.setSchedule_repayment_date(dueRepaymentDate);
		bankCardPayBean.setRisk_item(getRiskItem(userId));
		bankCardPayBean.setNotify_url(config.REPAYMENT_NOTIFY_URL);
		bankCardPayBean.setPay_type("D");
		bankCardPayBean.setNo_agree(agreeNo);
		bankCardPayBean.setSign(genSign(JSON.parseObject(JSON.toJSONString(bankCardPayBean))));
		String reqJson = JSON.toJSONString(bankCardPayBean);
		log.info("请求报文为:" + reqJson);
		String resJson = YTHttpHandler.getInstance().doRequestPostString(reqJson, config.REPAYMENT_GATEWAY);
		log.info("结果报文为:" + resJson);
		return parseResult(resJson);
	}

	/**
	 * 生成连连支付风控参数
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public String getRiskItem(long uid) throws Exception{
		Member member = memberService.findMember(uid);
		JSONObject risk = new JSONObject();
		risk.put("frms_ware_category", "2010");
		risk.put("user_info_mercht_userno", String.valueOf(uid));
		Date regTime = DateUtil.YYYY_MM_DD_MM_HH_SS.parse(member.getCreateTime());
		risk.put("user_info_dt_register", DateUtil.YYYYMMDDHHMMSS.format(regTime));
		risk.put("user_info_bind_phone", member.getMobilePhone());
		risk.put("user_info_identify_state", "1");
		risk.put("user_info_identify_type", "3");
		risk.put("user_info_full_name", member.getRealName());
		risk.put("user_info_id_no", member.getIdentNo());
		return risk.toJSONString();
	}
	
	/**
	 * 解绑签约
	 * 
	 * @param userId
	 *            用户编号
	 * @param agreeNo
	 *            签约授权号
	 * @return
	 */
	public MessageResult unbindAuth(long userId, String agreeNo) {
		MessageResult result = new MessageResult(0, "SUCCESS");
		JSONObject json = new JSONObject();
		json.put("oid_partner", config.OID_PARTNER);
		json.put("sign_type", "RSA");
		json.put("user_id", String.valueOf(userId));
		json.put("pay_type", "D");
		json.put("no_agree", agreeNo);
		json.put("sign", genSign(json));
		log.info(json.toJSONString());
		log.info(json.toString());

		String resJson = YTHttpHandler.getInstance().doRequestPostString(json.toJSONString(), config.UNBIND_GATEWAY);
		log.info(resJson);
		result = parseResult(resJson);
		return result;
	}

	/**
	 * 提交实时付款
	 * @param orderNo
	 * @param amount
	 * @param realName
	 * @param cardNo
	 * @param bankName
	 * @return
	 * @throws Exception 
	 */
	public MessageResult submitPay(String orderNo,double amount,String realName,String cardNo,String bankName) throws Exception{
		DecimalFormat df = new DecimalFormat("0.00");
		MessageResult result = new MessageResult(0, "SUCCESS");
		JSONObject json = new JSONObject();
		json.put("oid_partner", config.OID_PARTNER);
		json.put("sign_type", "RSA");
		json.put("api_version", config.VERSION);
		json.put("no_order", orderNo);
		json.put("dt_order", DateUtil.YYYYMMDDHHMMSS.format(new Date()));
		json.put("money_order", df.format(amount));
		json.put("card_no", cardNo);
		json.put("acct_name", realName);
		json.put("flag_card", "0");
		json.put("notify_url", config.PAY_NOTIFY_URL);
		json.put("sign",LLPayUtil.addSign(JSON.parseObject(json.toString()), config.TRADER_PRI_KEY,config.MD5_KEY));
		log.info(json.toString());
		String encryptStr = LianLianPaySecurity.encrypt(json.toJSONString(), config.YT_PUB_KEY);
		if (StringUtils.isEmpty(encryptStr)) {
			return new MessageResult(500,"加密异常");
		}
		JSONObject encJson = new JSONObject();
		encJson.put("oid_partner", config.OID_PARTNER);
		encJson.put("pay_load", encryptStr);
		log.info(encJson);
		String resJson = YTHttpHandler.getInstance().doRequestPostString(encJson.toString(), config.PAY_GATEWAY);
		result = parseResult(resJson);
		return result;
	}
	
	
	/**
	 * 确认支付
	 * @param orderNo 订单编号
	 * @param confirmCode 确认码，提交付款申请的时候返回
	 * @return
	 */
	public MessageResult confirmPay(String orderNo,String confirmCode){
		MessageResult result = new MessageResult(0, "SUCCESS");
		JSONObject json = new JSONObject();
		json.put("oid_partner", config.OID_PARTNER);
		json.put("sign_type", "RSA");
		json.put("api_version", config.VERSION);
		json.put("no_order", orderNo);
		json.put("confirm_code", confirmCode);
		json.put("notify_url", config.PAY_NOTIFY_URL);
		json.put("flag_card", "0");
		json.put("info_order", "付款");
		json.put("sign", SignUtil.genRSASign(json,config.TRADER_PRI_KEY));
		log.info(json.toJSONString());
		log.info(json.toString());

		String resJson = YTHttpHandler.getInstance().doRequestPostString(json.toJSONString(), config.CONFIRM_PAY_GATEWAY);
		log.info(resJson);
		result = parseResult(resJson);
		return result;
	}
	
	
	public MessageResult queryPayOrder(String orderNo){
		MessageResult result = new MessageResult(0, "SUCCESS");
		JSONObject json = new JSONObject();
		json.put("oid_partner", config.OID_PARTNER);
		json.put("sign_type", "RSA");
		json.put("api_version", config.VERSION);
		json.put("no_order", orderNo);
		json.put("sign", genSign(json));
		log.info(json.toJSONString());
		log.info(json.toString());

		String resJson = YTHttpHandler.getInstance().doRequestPostString(json.toJSONString(), config.QUERY_PAY_GATEWAY);
		log.info(resJson);
		result = parseResult(resJson);
		return result;
	}
	
	private MessageResult parseResult(String resJson) {
		log.info("result:"+resJson);
		JSONObject json = JSON.parseObject(resJson);
		MessageResult result = new MessageResult();
		if (json.getString("ret_code").equals("0000")) {
			result.setCode(0);
			result.setMessage(json.getString("ret_msg"));
		} else {
			result.setCode(Convert.strToInt(json.getString("ret_code"), 500));
			result.setMessage(json.getString("ret_msg"));
		}
		result.setData(resJson);
		return result;
	}

	private String genSign(JSONObject reqObj) {
		String sign = reqObj.getString("sign");
		String sign_type = reqObj.getString("sign_type");
		// // 生成待签名串
		String sign_src = genSignData(reqObj);
		log.info("商户[" + reqObj.getString("oid_partner") + "]待签名原串" + sign_src);
		log.info("商户[" + reqObj.getString("oid_partner") + "]签名串" + sign);

		if ("MD5".equals(sign_type)) {
			sign_src += "&key=" + config.MD5_KEY;
			return signMD5(sign_src);
		}
		if ("RSA".equals(sign_type)) {
			return getSignRSA(reqObj);
		}
		return null;
	}

	private static String signMD5(String signSrc) {
		try {
			return new String(DigestUtils.md5(signSrc.getBytes("utf-8")));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * RSA签名验证
	 * 
	 * @param reqObj
	 * @return
	 */
	public String getSignRSA(JSONObject json) {
		return SignUtil.genRSASign(json,config.TRADER_PRI_KEY);
	}

	/**
	 * 生成待签名串
	 * 
	 * @param paramMap
	 * @return
	 */
	public String genSignData(JSONObject jsonObject) {
		StringBuffer content = new StringBuffer();

		// 按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign 和ip_client 不参与签名
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) jsonObject.getString(key);
			// 空串不参与签名
			if (null == value) {
				continue;
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);

		}
		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}
		return signSrc;
	}

	public void setConfig(PaymentConfig cfg) {
		this.config = cfg;
	}
}
