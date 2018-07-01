package com.spark.p2p.controller.zmxy;

import java.net.URLDecoder;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.controller.wenxin.WeixinBaseController;

//解密验签

public class ZmJieQian extends WeixinBaseController {
	static DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId,
			ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);

	public static void getResult() throws Exception {
		
		String params = "DYOn6o%2FYGv1YDY%2FLuCOi84Y60EM7JCl3P2gvy5GHipQbhrO097q9hheiUaecTVycPhR3GzptJ%2B5rq2A%2FasPKG8buAmXqekKDy97qbUaOh8nh0Nof9aXIXvSK3Zp%2BpC5gUH09KK2y5x5FXB6SgWogxXXCZOVbDKp1ulVuMKrVoQg%3D";
		// 从回调URL中获取params参数，此处为示例值
		String sign = "Y2xy60QWmSrgENO%2FVSlwMNIzS8hf4wJQSWFEfUXhf64ZZ6DD3mYpv4UrfJcH%2FNtzf98VU1tG%2BRddVq5i%2B3TTr8tmL5%2Fv03YSrenh8EKLCXmNhGSHvt8pHmf4ymPMomBCkX6Rcw2zlQb8N%2Fh6ubIyLf%2BEOsJQNieh%2Ftq8eF8Xyjg%3D";
		// 从回调URL中获取sign参数，此处为示例值
		// 判断串中是否有%，有则需要decode
		if (params.indexOf("%") != -1) {
			params = URLDecoder.decode(params, "utf-8");
		}
		if (sign.indexOf("%") != -1) {
			sign = URLDecoder.decode(sign, "utf-8");
		}
		try {
			String result = client.decryptAndVerifySign(params, sign);
			System.out.println(result);
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
	}

	

}
