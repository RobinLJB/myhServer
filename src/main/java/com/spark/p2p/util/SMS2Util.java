package com.spark.p2p.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 亿美短信接口
 * 
 * @author guokui
 *
 */

public class SMS2Util {
	private static Logger logger = Logger.getLogger(SMS2Util.class);

	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @param content
	 *            result返回0表示成功
	 * @throws UnsupportedEncodingException
	 */
	public static String sendCheckCode(String phone, String content) throws UnsupportedEncodingException {
		String url = "http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
		String message = "您好,本次验证码为" + content + ",10分钟内有效";
		message = new String(message.getBytes(), "UTF-8");
		String param = "cdkey=8SDK-EMY-6699-RGZMO&password=034761&phone=" + phone + "&message=" + message;
		String result = sendSMS(url, param);
		logger.info("手机号:" + phone + "-----------返回结果:" + result + "----------验证码:" + content);
		return result;
	}

	public static String sendSMS(String url, String param) throws UnsupportedEncodingException {
		String ret = "";
		url = url + "?" + param;

		logger.info("短信发送->" + url);
		String responseString = HttpUtil.sendGet(url);
		// String responseString =
		// HttpClientUtil.getInstance().doGetRequest(url);
		responseString = responseString.trim();
		if (null != responseString && !"".equals(responseString)) {
			ret = xmlMt(responseString);
		}
		return ret;
	}

	// 解析下发response
	public static String xmlMt(String response) {
		String result = "0";
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			e.printStackTrace();
			result = "-250";
		}
		Element root = document.getRootElement();
		result = root.elementText("error");
		if (null == result || "".equals(result)) {
			result = "-250";
		}
		return result;
	}

}
