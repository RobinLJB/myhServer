package com.spark.p2p.controller.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.WebUtils;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationCertifyRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationQueryRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationQueryResponse;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.controller.wenxin.WeixinBaseController;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

/**
 * 获得认证的url链接
 * 
 * @return
 */
@Controller
@RequestMapping(value = "/app/uc/zmxy")
public class AppZmCertController extends AppBaseController {
	DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId,
			ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
	@Autowired
	private MemberService memberService;
	@RequestMapping(value = "/certify", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody MessageResult pageCertify() throws Exception {
		Member member = getMember();
		if (member == null) {
			return error("token失效");
		}
		/* member = memberService.findMember(member.getId()); */
		ZhimaCustomerCertificationCertifyRequest request = new ZhimaCustomerCertificationCertifyRequest();
		String BizNo = fetchZhimaBizNo();
		request.setPlatform("zmop");
		request.setBizNo(BizNo);// 必要参数
		// 设置回调地址,必填. 如果需要直接在支付宝APP里面打开回调地址使用alipay协议
		// alipay://www.taobao.com 或者 alipays://www.taobao.com,分别对应http和https请求
		// request.setReturnUrl("http://t.yibaoxd.com/mobile/zmxy/certify.html");// 必要参数
		request.setReturnUrl("YiXin://");// app里的回调地址是写APP的scheme URL（客服）
		
		System.out.println(request);
		try {
			String url = client.generatePageRedirectInvokeUrl(request);
			//System.out.println("generateCertifyUrl url:" + url);// 生成的 URL 必须包含回调地址
			// return url;
			return new MessageResult(0, "SUCCESS", url);
			// return "redirect:/notifys.html";
		} catch (ZhimaApiException e) {
			e.printStackTrace();
			return new MessageResult(1, "参数有误");
		}
	}

	/*
	 * 这一步是对芝麻认证的结果进行返回,暂时不用,只在回调失败时需要
	 * 
	 * @RequestMapping(value = "app/zmxy/query", method = RequestMethod.POST) public
	 * MessageResult ZhimaCustomerCertificationQuery() { MessageResult mr = new
	 * MessageResult(); String BizNo = fetchZhimaBizNo();
	 * ZhimaCustomerCertificationQueryRequest req = new
	 * ZhimaCustomerCertificationQueryRequest(); req.setChannel("apppc");
	 * req.setPlatform("zmop"); req.setBizNo(BizNo);// 必要参数 Map<String, String>
	 * resultMap = new HashMap<String, String>(); try {
	 * ZhimaCustomerCertificationQueryResponse response = client.execute(req);
	 * System.out.println(response.isSuccess()); if (response.isSuccess()) {
	 * mr.setCode(0); resultMap.put("success_msg", "认证成功"); } else { mr.setCode(1);
	 * resultMap.put("error_message", "认证失败");
	 * 
	 * } System.out.println(response.getErrorCode());
	 * System.out.println(response.getErrorMessage()); } catch (ZhimaApiException e)
	 * { e.printStackTrace(); } mr.setData(resultMap); return mr; }
	 */

	// 这一步仅仅是成功时获取一个BizNo
	public String fetchZhimaBizNo() {
		Member member = getMember();
		try {
			member = memberService.findMember(member.getId());
			String certName = member.getRealName();
			String certNo = member.getIdentNo();
			System.out.println(certNo);
			ZhimaCustomerCertificationInitializeRequest req = new ZhimaCustomerCertificationInitializeRequest();
			req.setPlatform("zmop");
			DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String sss = YYYYMMDDMMHHSSSSS.format(new Date());
			sss = "xh" + sss + System.currentTimeMillis();
			req.setTransactionId(sss);// 必要参数
			req.setProductCode("w1010100000000002978");// 必要参数，目前直接使用即可2017.8.4
			req.setBizCode("FACE");// 必要参数
			req.setIdentityParam("{\"identity_type\":\"CERT_INFO\",\"cert_type\":\"IDENTITY_CARD\",\"cert_name\":\""
					+ certName + "\",\"cert_no\":\"" + certNo + "\"}");// 必要参数
			// req.setIdentityParam("{\"identity_type\":\"CERT_INFO\",\"cert_type\":\"IDENTITY_CARD\",\"cert_name\":\"夏超超\",\"cert_no\":\"34052119931014683X\"}");//
			// 必要参数
			req.setMerchantConfig("{\"need_user_authorization\":\"false\"}");//
			req.setExtBizParam("{}");// 必要参数
			System.out.println(req);
			try {
				ZhimaCustomerCertificationInitializeResponse response = (ZhimaCustomerCertificationInitializeResponse) client
						.execute(req);
				System.out.println(response.isSuccess());
				System.out.println(response.getErrorCode());
				System.out.println(response.getErrorMessage());
				System.out.println(response.getBizNo());// 获取最为重要的biz_no
				if (response.isSuccess()) {
					String BizNo = response.getBizNo();
					return BizNo;
				} else {
					return response.getErrorMessage();
				}
			} catch (ZhimaApiException e) {
				e.printStackTrace();
				return e.getErrMsg();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

}
