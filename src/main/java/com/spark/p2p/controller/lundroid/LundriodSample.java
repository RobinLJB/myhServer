package com.spark.p2p.controller.lundroid;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.spark.p2p.config.AppSetting;

/**
 * 使用联卓认证
 * 
 * @return
 * @throws Exception
 */
public class LundriodSample {
	public  String lundriodIdentity(String realname , String cardno) throws Exception {
		
		String host = "http://idcard.market.alicloudapi.com";
		String path = "/lianzhuo/idcard";
		String method = "GET";
		String appcode = AppSetting.LianZhuoAppCode;
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("cardno", cardno);
		querys.put("name", realname);
		//目前写一个固定的值来进行测试，在AppCode审核通过之后就放开下面被注释的段落
		/*String str="{ \"resp\": {\"code\": 0,\"desc\": \"匹配\"},\"data\": {\"sex\": \"男\",\"address\": \"广东省清远市清新县\",\"birthday\": \"1989-05-25\"}}";
		return str;*/
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			System.out.println(response.toString());
			String str= EntityUtils.toString(response.getEntity());
			// 获取response的body
			 return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
