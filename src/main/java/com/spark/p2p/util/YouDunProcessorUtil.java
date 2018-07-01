package com.spark.p2p.util;
/**
 * 接收云慧眼认证结果
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.util.MD5Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



/**
* 有盾调用工具类
*
* @author geosmart
* @date 2017-01-06
*/
public class YouDunProcessorUtil extends BaseController{
   /**
    * TODO 获取商户开户的pub_key
    */
   static final String PUB_KEY = "a2f6da6e-a4e4-4026-895d-d70955f1f542";
   /**
    * TODO 获取商户开户的security_key
    */
   static final String SECURITY_KEY = "dcf03232-4fb2-46db-b4e5-96c5f978b1c4";

   static final String CHARSET_UTF_8 = "UTF-8";
   static final boolean IS_DEBUG = true;
 //订单查询接口地址
   static final String Order_Query = "https://idsafe-auth.udcredit.com/frontserver/4.2/api/order_query/pub_key/" + PUB_KEY;
   
// 有盾银行卡四要素接口地址
   static final String BANK_CARD4_URL="https://api4.udcredit.com";
   /**
    * 生成MD5签名
    *
    * @param pub_key          商户公钥
    * @param partner_order_id 商户订单号
    * @param sign_time        签名时间
    * @param security_key     商户私钥
    */
   public static String getMD5Sign(String pub_key, String partner_order_id, String sign_time, String security_key) throws UnsupportedEncodingException {
       String signStr = String.format("pub_key=%s|partner_order_id=%s|sign_time=%s|security_key=%s", pub_key, partner_order_id, sign_time, security_key);
       System.out.println("signField：" + signStr);
       return MD5Utils.MD5Encrpytion(signStr);
   }

   /**
    * 接收实名认证异步通知
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
       JSONObject reqObject = getRequestJson(request);
       JSONObject respJson = new JSONObject();
       //验签
       String sign = reqObject.getString("sign");
       String sign_time = reqObject.getString("sign_time");
       String partner_order_id = reqObject.getString("partner_order_id");
       System.out.println("sign：" + sign);
       String signMD5 = getMD5Sign(PUB_KEY, partner_order_id, sign_time, SECURITY_KEY);
       System.out.println("signMD5：" + signMD5);
       if (!sign.equals(signMD5)) {
           System.err.println("异步通知签名错误");
           respJson.put("code", "0");
           respJson.put("message", "签名错误");
       } else {
           System.out.print("收到商户异步通知");
           respJson.put("code", "1");
           respJson.put("message", "收到通知");
           //TODO 异步执行商户自己的业务逻辑(以免阻塞返回导致通知多次)
           Thread asyncThread = new Thread("asyncDataHandlerThread") {
               public void run() {
                   System.out.println("异步执行业务逻辑...");
                   try {
                       String id_name = reqObject.getString("id_name");
                       String id_number = reqObject.getString("id_number");
                       System.out.println(id_name + "：" + id_number);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           };
           asyncThread.start();
       }
       System.out.println("返回结果：" + respJson.toJSONString());
       //返回结果
       response.setCharacterEncoding(CHARSET_UTF_8);
       response.setContentType("application/json; charset=utf-8");
       response.getOutputStream().write(respJson.toJSONString().getBytes(CHARSET_UTF_8));
   }
   
   /**
    * 获取请求json对象
    */
   private JSONObject getRequestJson(HttpServletRequest request) throws IOException {
       InputStream in = request.getInputStream();
       byte[] b = new byte[10240];
       int len;
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       while ((len = in.read(b)) > 0) {
           baos.write(b, 0, len);
       }
       String bodyText = new String(baos.toByteArray(), CHARSET_UTF_8);
       JSONObject json = (JSONObject) JSONObject.parse(bodyText);
       if (IS_DEBUG) {
           System.out.println("received notify message:");
           System.out.println(JSON.toJSONString(json, true));
       }
       return json;
   }
   
   public static JSONObject OrderQueryTest(String partner_order_id) throws Exception {
       JSONObject renJson = new JSONObject();
       String sign_time = DateUtil.YYYYMMDDHHMMSS.format(new Date());
       String sign = getMD5Sign(PUB_KEY, partner_order_id, sign_time, SECURITY_KEY);
       System.out.println(sign);
       renJson.put("partner_order_id", partner_order_id);
       renJson.put("sign", sign);
       renJson.put("sign_time", sign_time);

       System.out.println("查询接口参数：" + JSON.toJSONString(renJson, true));

       JSONObject order_query = doHttpRequest(Order_Query, renJson);
       System.out.println("查询接口查询结果：" + JSON.toJSONString(order_query, true));
       return order_query;
   }
   /**
    * Http请求
    */
   public static JSONObject doHttpRequest(String url, JSONObject reqJson) throws IOException {
       CloseableHttpClient client = HttpClients.createDefault();
       //设置传入参数
       StringEntity entity = new StringEntity(reqJson.toJSONString(), CHARSET_UTF_8);
       entity.setContentEncoding(CHARSET_UTF_8);
       entity.setContentType("application/json");
       System.out.println(url);
       HttpPost httpPost = new HttpPost(url);
       httpPost.setEntity(entity);

       HttpResponse resp = client.execute(httpPost);
       if (resp.getStatusLine().getStatusCode() == 200) {
           HttpEntity he = resp.getEntity();
           String respContent = EntityUtils.toString(he, CHARSET_UTF_8);
           return (JSONObject) JSONObject.parse(respContent);
       }
       return null;
   }
   public static void main(String[] args) throws Exception {
	   OrderQueryTest("222");
   }	
   public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
       if (imgStr == null) // 图像数据为空
           return false;
       Base64.Decoder decoder = Base64.getDecoder();
       try {
           // Base64解码
           byte[] bytes = decoder.decode(imgStr);
           for (int i = 0; i < bytes.length; ++i) {
               if (bytes[i] < 0) {// 调整异常数据
                   bytes[i] += 256;
               }
           }
           // 生成jpeg图片
           OutputStream out = new FileOutputStream(imgFilePath);
           out.write(bytes);
           out.flush();
           out.close();
           return true;
       } catch (Exception e) {
       	e.printStackTrace();
           return false;
       }
}
   

  
   
   public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
       byte[] data = null;
       
       // 读取图片字节数组
       try {
           InputStream in = new FileInputStream(imgFilePath);
           data = new byte[in.available()];
           in.read(data);
           in.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       // 对字节数组Base64编码
       Base64.Encoder encoder = Base64.getEncoder();
       return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
   }

   
   /**
    * 有盾银行卡四要素验证接口
    * @param params
    * @return
    * @throws Exception 
    */
   public static  JSONObject bankCard4Validate(Map<String, String> parameter) throws Exception {
   	String respContent =YouDunBank4Util.apiCall(BANK_CARD4_URL, PUB_KEY, SECURITY_KEY,"O1001S0401", String.valueOf(System.currentTimeMillis()),  parameter);
   	return (JSONObject) JSONObject.parse(respContent);
   }
}