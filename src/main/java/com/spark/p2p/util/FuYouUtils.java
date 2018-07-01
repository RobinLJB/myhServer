package com.spark.p2p.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.sparkframework.security.Encrypt;

/**
 *富有接口的相关工具 
 * @author Administrator
 *
 */
public class FuYouUtils {
	public static final String APPID = "7a7984eaf31123dd639b5da67ad79d1b";
	public static final String APPSECRET ="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	
	private static String requestStringGet(String urlpath) throws Exception{
		 String time=System.currentTimeMillis()/1000+"";
	     String sign=Encrypt.MD5(APPID+APPSECRET+time);
	     String path=urlpath+"?appid="+APPID+"&sign="+sign+"&time="+time;
	     URL url = new URL(urlpath.trim());
	     //打开连接
	     HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	            
	     if(200 == urlConnection.getResponseCode()){
	    	 //得到输入流
	         InputStream is =urlConnection.getInputStream();
	         ByteArrayOutputStream baos = new ByteArrayOutputStream();
	         byte[] buffer = new byte[1024];
	         int len = 0;
	         while(-1 != (len = is.read(buffer))){
	        	 baos.write(buffer,0,len);
	         	 baos.flush();
	          }
	          String s= baos.toString("utf-8");
	          JSONObject myJsonObject = new JSONObject(s); 
	          JSONObject data = myJsonObject.getJSONObject("data");
	          String phoneToken = data.getString("token");
	          return phoneToken;
	          //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
	       }
	       return null;
	}
	
}
