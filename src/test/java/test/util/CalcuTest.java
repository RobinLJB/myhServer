package test.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.spark.p2p.entity.Repayment;
import com.spark.p2p.util.CalcuUtil;
import com.sparkframework.security.Encrypt;

public class CalcuTest {
	//@Test
	public void testAverage(){
		CalcuUtil util = new CalcuUtil();
		List<Repayment> list = util.repayAverage(10000, 13, 1);
		System.out.println(list);
	}
	@Test
	public void testDesDecrypt() throws Exception{
	        
	        String path="http://www-1.fuiou.com:18670/mobile_pay/checkCardDebit.pay?FM=";
	        //"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><ucAccount>" + "zkf48267" + "</ucAccount>"+ "<passWord>"+ "zkf48267"+ "</passWord></root>" 
	       String rqestXml= "<FM><MchntCD>0002900F0096235</MchntCD><Ono>6217973610006488319</Ono><Onm>吴涛</Onm><OCerTp>0</OCerTp><OCerNo>34262219900129295X</OCerNo><Mno>18555116208</Mno><Sign>c3f66ff5800f9817c80317ebe10e7bbe</Sign><Ver>1.30</Ver><OSsn>14932633310001</OSsn></FM>";
	        byte[] xmlbyte = rqestXml.getBytes("UTF-8");  
	        URL url = new URL(path);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5* 1000);  
	        conn.setDoOutput(true);//允许输出   
	        conn.setUseCaches(false);//不使用Cache   
	        conn.setRequestMethod("POST");            
	        conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接   
	        conn.setRequestProperty("Charset", "UTF-8");  
	        conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));  
	     //   conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");  
	        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());  
	        outStream.write(xmlbyte);//发送xml数据   
	        outStream.flush(); 
	        outStream.close();

	     //解析返回来的xml消息体
	        byte[] msgBody = null;
	           if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
	        InputStream is = conn.getInputStream();//获取返回数据 

	        byte[] temp = new byte[1024];
	        int n = 0;
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        while ((n = is.read(temp)) != -1)
	        {
	         bos.write(temp, 0, n);
	        }
	        msgBody = bos.toByteArray();
	        bos.close();
	        is.close();
	        String returnXml= new String(msgBody, "UTF-8").trim();
	        System.out.println(returnXml);
	        conn.disconnect();
	        
	        
	}
	
	//@Test
	public void testMonthly(){
		CalcuUtil util = new CalcuUtil();
		List<Repayment> list = util.repayMonthlyInterest(10000, 12, 3);
		System.out.println(list);
	}
	
	@Test
	public void testOnce(){
		CalcuUtil util = new CalcuUtil();
		List<Repayment> list = util.repayOnce(10000, 12, 3, 1);
		System.out.println(list);
	}
}
