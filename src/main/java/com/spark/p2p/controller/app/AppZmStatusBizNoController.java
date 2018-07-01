package com.spark.p2p.controller.app;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.jcraft.jsch.Signature;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.controller.wenxin.WeixinBaseController;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.MessageResult;

import freemarker.cache.MruCacheStorage;

/**
 * 
 * @result params和sign 通过app传回的url参数进行解密验签,解析之后的值为返回的认证结果值如
 *         passed=true&biz_no=ZM201708083000000929200543596168
 */
@Controller
@RequestMapping("/app/uc/zmxy")
public class AppZmStatusBizNoController extends AppBaseController {
	static DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId,
			ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
	@Autowired
	private MemberService memberService;// 此处需要修改

	@RequestMapping(value = "analysis", method = RequestMethod.POST)
	public @ResponseBody MessageResult getResult(HttpServletRequest request) throws Exception {
		Member member = getMember();
		int id = (int) member.getId();
		if (member == null) {
			return error("token失效");
		}
		try {
			request.setCharacterEncoding("utf-8");
			// String urlParamsAndSigns = request("urlPams");// 从ios端获得的认证返回结果，
			// System.out.println(urlParamsAndSigns);
			// String urlParamsAndSigns1 = urlParamsAndSigns.replaceAll("%25","%");
			String urlParamsAndSigns = request("urlPams").replaceAll("%25", "%");// 从ios端获得的认证返回结果，
			System.out.println(urlParamsAndSigns);
			System.out.println("----------------------");
			String signature = request("signature");
			System.out.println(signature);
			String[] ParamsAndSignsDelHead = urlParamsAndSigns.split("\\?");// 先通过？去掉app的头端信息，只剩下params和signs
			String[] ParamsAndSignInclEqual = ParamsAndSignsDelHead[1].split("&");// 通过&分开params=...和signs=.....
			String[] urlParams = ParamsAndSignInclEqual[0].split("=");// 通过=分开params
																		// 和他的具体值
			String params = urlParams[1];
			System.out.println(params);
			String[] urlSigns = ParamsAndSignInclEqual[1].split("=");// 通过=分开signs
																		// 和他的具体值
			String sign = urlSigns[1];
			System.out.println(sign);
			// 判断串中是否有%，有则需要decode
			if (params.indexOf("%") != -1) {
				params = URLDecoder.decode(params, "utf-8");
				System.out.println(params);
			}
			if (sign.indexOf("%") != -1) {
				sign = URLDecoder.decode(sign, "utf-8");
			}
			try {
				String result = client.decryptAndVerifySign(params, sign);
				System.out.println(result);// 返回的认证结果值
											// passed=true&biz_no=ZM201708083000000929200543596168
				String[] array = result.split("&");
				String status = array[0];// 认证状态值 形如passed=true
				String[] statusValue = status.split("=");
				String statusValueR = statusValue[1];// 获得认证结果，结果只能是true或者false
				System.out.println(statusValueR);

				String bizNo = array[1];// 流水号：biz_no=ZM201708083000000929200543596168
				String[] bizNoValue = bizNo.split("=");
				String BizNo = bizNoValue[1];// 获得biz_no具体值
				System.out.println(BizNo);

				if ("true".equals(statusValueR)) {
					int faceStatus = 1;// 人脸认证状态为1
					long ret = memberService.updateZmFaceStatus(faceStatus, BizNo, id);
					System.out.println();
					return success("认证成功");
				} else {
					return error("认证失败");
				}

			} catch (ZhimaApiException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
