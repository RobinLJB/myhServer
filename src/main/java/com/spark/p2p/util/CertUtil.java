/**
 * 
 */
package com.spark.p2p.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.sparkframework.security.Base64;
import com.sparkframework.security.DES;
import com.sparkframework.security.Encrypt;

/**
 * @author yanqizheng
 * 
 */
public class CertUtil {
	public static MessageResult checkCardName(String name, String cardId) {
		String appid = "10001";
		String nonce = GeneratorUtil.getNonceString(6);
		String appkey = "12apUjJHGapIkTjmVjEbYSPmu8HagM3C";
		String gateway = "http://cert.xinhuokj.com/cert/idcard.do";
		String data = name+":"+cardId;
		String secData = Base64.encode(DES.encrypt(data, appkey));
		String signature = Encrypt.MD5(appid+nonce+appkey+secData);
		// 发送内容
		String urlStr = String.format("%s?appid=%s&nonce=%s&signature=%s&data=%s",gateway,appid,nonce,signature,secData);
		MessageResult result = new MessageResult();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			// 返回发送结果
			String ret = in.readLine();
			JSONObject json = new JSONObject(ret);
			result.setCode(json.getInt("code"));
			result.setMessage(json.getString("message"));
		} catch (Exception e) {
			result.setCode(500);
			result.setMessage("系统验证错误");
			e.printStackTrace();
		}
		return result;
	}
}
