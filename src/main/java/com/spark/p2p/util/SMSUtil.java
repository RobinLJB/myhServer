package com.spark.p2p.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
//import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;

import com.spark.p2p.config.AppSetting;

/**
 * 短信接口，对短信接口地址进行拼接，提供公用
 * 
 * @author Administrator
 * 
 */
public class SMSUtil {
	
	public static final Log log = LogFactory.getLog(SMSUtil.class);
	/**   
	 * @MethodName: sendSMS  
	 * @Param: SMSUtil  
	 * @Author: yanqizheng
	 * @Return:    
	 * @Descb: 发送通知短信,使用美圣接口
	 * @Throws:
	*/
	//public static String sign = "永金贷";
	public static String sendSMS(String user,String phone,String content) {
		String tpl = "尊敬的会员%s,%s";
		String tmp = String.format(tpl,user,content);
		try {
			return sendCRSMS(phone, tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}
	
	/**
	 * 发送验证码短信
	 * @param mobile
	 * @param code
	 * @return
	 */
	public static String sendCheckCode(String mobile,String code){
		//code = "您注册的验证码"+code+"如非本人操作，请忽略。";
		try {
			return sendCRSMS(mobile,code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}
	
	public static String successBorrow(String borrowNo){
		return "您的编号" + borrowNo + "申请，已经成功还款，恭喜您成为平台的优质会员，再次申请，飞速审核！";
	}
	
	public static String overdueBorrow(String borrowNo){
		return  "您的编号" + borrowNo + "申请，已经成功还款，本次已逾期，联系客服，查看会员状态！";
	}
	
	/*public static String renewalBorrow(String borrowNo,int borrowDays,double deduceSum,String bankCardNo){
		return  "您的编号" + borrowNo + "申请，申请续期" + borrowDays
				+ "天成功，续期费用" + deduceSum + "元，扣款卡号" + bankCardNo + "，请到时正常还款！";
	}*/
	public static String renewalBorrow(String borrowNo,int borrowDays,double deduceSum){
		return  "您的编号" + borrowNo + "申请，申请续期" + borrowDays
				+ "天成功，续期费用" + deduceSum + "元，请到时正常还款！";
	}
	
	public static String autotrialBorrow(String borrowNo){
		return  "你的借款编号"+borrowNo+"已自动通过初审，请完成后续认证";
	}
	
	public static String subtrialBorrow(String borrowNo){
		return  "你的借款编号"+borrowNo+"已提交到后台初审，请耐心等待";
	}
	
	public static String limitoverBorrow(String borrowNo,int dayGap,double overdueFee){
		return  "你的借款（借款金额："+borrowNo+"元），已经逾期了"+dayGap+"天，逾期费用为："+overdueFee+"元";
	}
	
	public static String adoptBorrow(String borrowNo){
		return  "您的编号"+borrowNo+"申请，已通过后台的初审，请按照提示完成其他认证！";
	}
	
	public static String failBorrow(String borrowNo){
		return  "你的借款编号"+borrowNo+"初审失败，请30天后重试，谢谢！";
	}
	
	public static String reviewSuccessBorrow(String borrowNo){
		return  "您的编号"+borrowNo+"申请，复审成功，请耐心等待客服进行终审审核，谢谢！";
	}
	public static String reviewFailBorrow(String borrowNo){
		return  "您的编号"+borrowNo+"申请，复审失败，请30天后重试，谢谢！";
	}
	public static String finalFailBorrow(String borrowNo){
		return  "您的编号"+borrowNo+"申请，终审失败，请30天后重试，谢谢！";
	}
	public static String finalSuccessBorrow(String borrowNo){
		return  "您的编号"+borrowNo+"申请，终审成功，请耐心等待放款，谢谢！";
	}
	
	/**
	 * 发送验证码短信
	 * @param mobile
	 * @param code
	 * @return
	 */
	public static String sendContent(String mobile,String code){
		try {
			return sendCRSMS(mobile,code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}
	
	/**
	 * 美圣短信发送接口，单条发送
	 * @param mobile
	 * @param content
	 * @param tpl
	 * @return "success"表示成功，"fail"表示失败
	 */
	public static String sendMSMsg(String mobile,String content,String tpl){
		String username = AppSetting.SMS_USERNAME;
		String passwd = AppSetting.SMS_PASSWD;
		HttpClient client = new HttpClient(AppSetting.SMS_GATEWAY, "POST");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		params.put("scode", passwd);
		params.put("mobile", mobile);
		try {
			content = java.net.URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		params.put("tempid", tpl);
		params.put("content", content);
		client.addParams(params);
		String ret = "";
		try {
			ret = client.excute();
			log.info("[SMS RET]："+ret);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	public static String sendBatchSMS(String mobileList,String content){
		String username = AppSetting.SMS_USERNAME;
		String passwd = AppSetting.SMS_PASSWD;
		HttpClient client = new HttpClient(AppSetting.SMS_GATEWAY, "POST");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		params.put("scode", passwd);
		params.put("mobile", mobileList);
		try {
			content = java.net.URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		params.put("content", content);
		client.addParams(params);
		String ret = "";
		try {
			ret = client.excute();
			log.info("[SMS RET]："+ret);
			if(ret.startsWith("0"))return "success";
			else return "fail";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	public static String sendCRSMS(String mobile,String content) throws IOException{
		
/*		
		// 发送内容
		String sign = AppSetting.SMS_SIGN;
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(AppSetting.SMS_GATEWAY);
		// 向StringBuffer追加用户名
		sb.append("name="+AppSetting.SMS_USERNAME);
		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd="+AppSetting.SMS_PASSWD);
		// 向StringBuffer追加手机号码
		sb.append("&mobile="+mobile);

		// 向StringBuffer追加消息内容转URL标准码
	
		sb.append("&content=" + URLEncoder.encode(content+"【"+sign +"】","utf-8"));

		// 追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");
		
		// 加签名
		//sb.append("&sign=" + URLEncoder.encode(sign,"utf8"));
		log.info(sb.toString());
		// type为固定值pt extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno=");
		// 创建url对象
		URL url = new URL(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");
		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// 返回发送结果
		String ret = in.readLine();
		// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
		log.info(ret);
		if(ret.startsWith("0"))return "success";*/
		String templateId = null;
	    String url = "http://api.1cloudsp.com/api/v2/send";
	    if (content.length() == 6) {
	      url = "http://api.1cloudsp.com/api/v2/single_send";
	      templateId = "4076";
	    }
	    org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
	    PostMethod postMethod = new PostMethod(url);
	    postMethod.getParams().setContentCharset("UTF-8");
	    postMethod.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());

	    NameValuePair[] data = { 
	      new NameValuePair("accesskey", "MwwxxTIFT4YAecyc"), 
	      new NameValuePair("secret", "aUVGMNu6rh4kwmZAR5wjn0W4rlNTkqkF"), 
	      new NameValuePair("sign", "2566"), 
	      new NameValuePair("templateId", templateId), 
	      new NameValuePair("mobile", mobile), 
	      new NameValuePair("content", URLEncoder.encode(content, "utf-8")) };

	    postMethod.setRequestBody(data);

	    int statusCode = httpClient.executeMethod(postMethod);
	    log.info("statusCode: " + statusCode + ", body: " + 
	      postMethod.getResponseBodyAsString());

	    JSONObject jo = new JSONObject(postMethod.getResponseBodyAsString());
	    String code = jo.getString("code");
	    if (code.equals("0")) return "success";
		return "fail";
	}
	
	public static String sendCRSMSNew(String mobile,String content) throws IOException{
		// 发送内容
		String sign = AppSetting.SMS_SIGN;
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(AppSetting.SMS_GATEWAY);//new
		// 向StringBuffer追加用户名
		sb.append("name="+AppSetting.SMS_USERNAME);
		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd="+AppSetting.SMS_PASSWD);
		// 向StringBuffer追加手机号码
		sb.append("&mobile="+mobile);

		// 向StringBuffer追加消息内容转URL标准码
	
		sb.append("&content=" + URLEncoder.encode(content+"【"+sign +"】","utf-8"));

		// 追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");
		
		// 加签名
		//sb.append("&sign=" + URLEncoder.encode(sign,"utf8"));
		log.info(sb.toString());
		// type为固定值pt extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno=");
		// 创建url对象
		URL url = new URL(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");
		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// 返回发送结果
		String ret = in.readLine();
		// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
		log.info(ret);
		if(ret.startsWith("0"))return "success";
		else return "fail";
	}
	
/*    //普通短信
    private void sendsms() throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "xxxxxxxxxx"; //用户开发key
        String accessSecret = "yyyyyyyyyy"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "123"),
                new NameValuePair("templateId", "100"),
                new NameValuePair("mobile", "13900000001"),
                new NameValuePair("content", URLEncoder.encode("先生##9:40##快递公司##1234567", "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        log.info("statusCode: " + statusCode + ", body: "
                    + postMethod.getResponseBodyAsString());
    }*/
	
}

