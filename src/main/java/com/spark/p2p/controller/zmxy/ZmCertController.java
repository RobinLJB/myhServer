package com.spark.p2p.controller.zmxy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

//芝麻认证（人脸认证）测试地址http://localhost:8080/exinbao/mobile/zmxy/certify.html
@Controller
public class ZmCertController extends WeixinBaseController {

	public static final Log log = LogFactory.getLog(ZmCertController.class);
	
	DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId,
			ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
	@Autowired
	private MemberService memberService;

	@ResponseBody
	@RequestMapping(value = "/zmxy/certify", method = RequestMethod.POST)
	// @RequestMapping(value = "/zmxy/certify", method =
	// {RequestMethod.POST,RequestMethod.GET}) //调试时使用
	public MessageResult pageCertify() throws Exception {
		ZhimaCustomerCertificationCertifyRequest request = new ZhimaCustomerCertificationCertifyRequest();
		String BizNo = fetchZhimaBizNo();
		request.setPlatform("zmop");
		request.setBizNo(BizNo);// 必要参数
		// 设置回调地址,必填. 如果需要直接在支付宝APP里面打开回调地址使用alipay协议
		// alipay://www.taobao.com 或者 alipays://www.taobao.com,分别对应http和https请求
		// request.setReturnUrl("http://t.yibaoxd.com/mobile/zmxy/notifys.html");// 必要参数
		request.setReturnUrl("http://localhost:8080/exinbao/mobile/borrow/attestation.html");// 此处回调地址还需要修改，必要参数
		log.info(request);
		try {
			String url = client.generatePageRedirectInvokeUrl(request);
			log.info("generateCertifyUrl url:" + url);// 生成的 URL 必须包含回调地址
			session.setAttribute("code", 0);
        	return success("SUCCESS",url);
			// return "redirect:/notifys.html";
		} catch (ZhimaApiException e) {
			session.setAttribute("code", 1);
			e.printStackTrace();
			return error(500,"信息错误");
		}
	}

	/*
	 * // 这一步是对芝麻认证的结果进行返回 public void ZhimaCustomerCertificationQuery() { String
	 * BizNo = fetchZhimaBizNo(); ZhimaCustomerCertificationQueryRequest req = new
	 * ZhimaCustomerCertificationQueryRequest(); req.setChannel("apppc");
	 * req.setPlatform("zmop"); req.setBizNo(BizNo);// 必要参数 try {
	 * ZhimaCustomerCertificationQueryResponse response = client.execute(req);
	 * log.info(response.isSuccess());
	 * log.info(response.getErrorCode());
	 * log.info(response.getErrorMessage()); } catch (ZhimaApiException e)
	 * { e.printStackTrace(); } }
	 */
	
	// 这一步仅仅是成功时获取一个BizNo
	public String fetchZhimaBizNo() {
		Member member = getUser();
		try {
			member = memberService.findMember(member.getId());
			String certName = member.getRealName();
			String certNo = member.getIdentNo();
			log.info(certNo);

			/*
			 * long uid = member.getId(); String realName = member.getRealName(); String
			 * certNo = member.getIdentNo();
			 */

			ZhimaCustomerCertificationInitializeRequest req = new ZhimaCustomerCertificationInitializeRequest();
			req.setPlatform("zmop");
			DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String sss = YYYYMMDDMMHHSSSSS.format(new Date());
			sss = "xh" + sss + System.currentTimeMillis();// 此处需要一个32位的字符串，前面通过时间生成了30位的字符串，加上"xh"（薪火）正好凑成32位
			req.setTransactionId(sss);// 必要参数
			req.setProductCode("w1010100000000002978");// 必要参数，目前直接使用即可2017.8.4
			req.setBizCode("FACE");// 必要参数
			req.setIdentityParam("{\"identity_type\":\"CERT_INFO\",\"cert_type\":\"IDENTITY_CARD\",\"cert_name\":\""
					+ certName + "\",\"cert_no\":\"" + certNo + "\"}");// 必要参数
			// req.setIdentityParam("{\"identity_type\":\"CERT_INFO\",\"cert_type\":\"IDENTITY_CARD\",\"cert_name\":\"夏超超\",\"cert_no\":\"34052119931014683X\"}");//
			// 必要参数
			req.setMerchantConfig("{\"need_user_authorization\":\"false\"}");//
			req.setExtBizParam("{}");// 必要参数
			log.info(req);
			try {
				ZhimaCustomerCertificationInitializeResponse response = (ZhimaCustomerCertificationInitializeResponse) client
						.execute(req);
				log.info(response.isSuccess());
				log.info(response.getErrorCode());
				log.info(response.getErrorMessage());
				log.info(response.getBizNo());// 获取最为重要的biz_no
				if (response.isSuccess()) {
					session.setAttribute("flag", 1);
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
