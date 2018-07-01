package test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.WebUtils;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.DES;
import com.sparkframework.security.Encrypt;
import org.apache.commons.codec.digest.DigestUtils;
import edu.emory.mathcs.backport.java.util.Collections;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;   
import org.w3c.dom.Node;   
import org.w3c.dom.NodeList;   
import org.xml.sax.InputSource; 
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import java.util.Base64;

public class DesTest {
	private String desKey = "asfdweyeyrVgOV4P8Uf70REVpIw3iVNwNs";
	private String apiKey = "Tuac9Cl4RQFT3S8ijAfjxHATHk4BZabc";
	String appid="7a7984eaf31123dd639b5da67ad79d1b";
	String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	
	@Autowired
	private BorrowService borrowService;
	
	
	@Test
	public void sfsd(){
        GenerateImage("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", "/home/lyj/youysssou.jpg");
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
	 * 调用接口的工具-----获取token
	 * @throws Exception 
	 * 
	 */           
	String token="3e9d79ddc5d2ab46bc722d3cb531848c";
	String sid="SID60fababa1dd244ffab22661fab874577";
	String sms_code="";
	String captcha_code="hbqj";
	//String sms_code="273152";
	//第一步：获取token
	@Test
	public void dianHuaBangA() throws Exception{

        String time=System.currentTimeMillis()/1000+"";
        String sign=Encrypt.MD5(appid+appsecret+time);
        System.out.println(sign+"---"+time);
        String path="https://crs-api.dianhua.cn/token?appid="+appid+"&sign="+sign+"&time="+time;
        
        URL url = new URL(path.trim());
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
                System.out.print(s);
               // JSONObject myJsonObject = new JSONObject(s); 
              ///  JSONObject data = myJsonObject.getJSONObject("data");
              //  String phoneToken = data.getString("token");
              //  return phoneToken;
                //System.out.print(phoneToken);
                //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
            }
       //return null;

	}
	
	
	//第二步：获取sid，如果需要，会发送短信
	@Test
	public void dianHuaBangB() throws Exception{
		String tel="15156062323";
       String path="https://crs-api.dianhua.cn/calls/flow?token="+token;
       String param="tel="+URLEncoder.encode(tel,"UTF-8");
       URL url = new URL(path.trim());
       HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
       httpURLConnection.setRequestMethod("POST");// 提交模式
       httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
       httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
       httpURLConnection.setDoOutput(true);
       httpURLConnection.setDoInput(true);
       OutputStream os = httpURLConnection.getOutputStream();    
       os.write(param.getBytes());
       //开始获取数据
       BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       int len;
       byte[] arr = new byte[1024];
       while((len=bis.read(arr))!= -1){
               bos.write(arr,0,len);
               bos.flush();
            }
                bos.close();
                System.out.println(bos.toString("utf-8"));
                JSONObject myJsonObject = new JSONObject(bos.toString()); 
                JSONObject data = myJsonObject.getJSONObject("data");
               String captcha_image = data.getString("captcha_image");
               System.out.println(captcha_image);
               System.out.println(captcha_image);
               captcha_image=captcha_image+"==";
               // JSONObject myJsonObject = new JSONObject(bos.toString("utf-8")); 
              //  Object data = myJsonObject.getJSONObject("data");
               // System.out.println(data); 
                //JSONObject myJsonObjecta = new JSONObject(data.toString()); 
              //  System.out.println(bos.toString("utf-8"));  
               // return myJsonObjecta.getString("sid");  
                //{"status":0,"msg":"OK","data":{"sid":"SIDa4be478c4ead4988acf34afd62013bb2","need_full_name":0,"need_id_card":0,"need_pin_pwd":1,"sms_duration":"","captcha_image":""}}
               GenerateImage("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", "D:\\youyou.jpg");
   	        
   	        // 测试从图片文件转换为Base64编码
   	    // System.out.println(GetImageStr("d:\\wangyc.jpg"));

	}

	//登陆时刷新刷新获取短信-----图形验证码
	@Test
	public void dianHuaBangAa() throws Exception{

        String time=System.currentTimeMillis()/1000+"";
        String sign=Encrypt.MD5(appid+appsecret+time);
        String path="https://crs-api.dianhua.cn/calls/verify/captcha?token="+token+"&sid="+sid;
        
        URL url = new URL(path.trim());
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
                System.out.print(s);
               JSONObject myJsonObject = new JSONObject(s); 
                JSONObject data = myJsonObject.getJSONObject("data");
               String captcha_image = data.getString("captcha_image");
               System.out.println(captcha_image);
              //  return phoneToken;
                //System.out.print(phoneToken);
                //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
            }
       //return null;

	}
	
	
	//刷新短信验证码----
	@Test
	public void dianHuaBangAxa() throws Exception{

        String time=System.currentTimeMillis()/1000+"";
        String sign=Encrypt.MD5(appid+appsecret+time);
        String path="https://crs-api.dianhua.cn/calls/verify/sms?token="+token+"&sid="+sid;
        
        URL url = new URL(path.trim());
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
                System.out.print(s);
               JSONObject myJsonObject = new JSONObject(s); 
                JSONObject data = myJsonObject.getJSONObject("data");
               String captcha_image = data.getString("captcha_image");
               System.out.println(captcha_image);
              //  return phoneToken;
                //System.out.print(phoneToken);
                //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
            }
       //return null;

	}
	
	
	//登陆，有时需要验证码
	@Test
	public void dianHuaBangC() throws Exception{
		String pinpwd="319926";
		String tel="15156062323";
		
        	String path="https://crs-api.dianhua.cn/calls/login?token="+token;
        	//String param="tel="+URLEncoder.encode(tel,"UTF-8")+"sid="+URLEncoder.encode(sid,"UTF-8")+"&pin_pwd="+pinpwd;
        	String param="tel="+URLEncoder.encode(tel,"UTF-8")+"sid="+URLEncoder.encode(sid,"UTF-8")+"&pin_pwd="+pinpwd+"&sms_code="+sms_code+"&captcha_code"+captcha_code;
            URL url = new URL(path.trim());
            System.out.println(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");// 提交模式
                httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
                httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);                
                OutputStream os = httpURLConnection.getOutputStream();    
                 // param = "tel="+tel+"&sid="+sid+"&pin_pwd="+pinpwd;    
                  param = "tel="+tel+"&sid="+sid+"&pin_pwd="+pinpwd+"&sms_code="+sms_code+"&captcha_code"+captcha_code;    
                  System.out.println(param);
                os.write(param.getBytes());
                
                
                //开始获取数据
                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len;
                byte[] arr = new byte[1024];
                while((len=bis.read(arr))!= -1){
                    bos.write(arr,0,len);
                    bos.flush();
                }
                bos.close();
                System.out.println(bos.toString("utf-8"));
             //   return bos.toString("utf-8");
                //{"status":0,"msg":"OK","data":{"sms_duration":"","captcha_image":""}}
                //{"status":0,"msg":"OK","data":{"sms_duration":120,"captcha_image":"data:image\/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKAAAABGCAYAAABL0p+yAAAWOElEQVR42u1dB3tVVdb2b3wIM4Ng\/RD9HGVGR2Qo0psCCiK9wyAgVQSUUVQQHEDKKE2kE1JIhTTSQwrppJECpJBeSCON9a13J+dmn8O9ufck90bQs57nPBLIPefcvd+91rvetfb2KTLMsN\/QnjKGwDADgIYZADTMMAOAhhkANMwwA4CGGQA0zDADgIYZADTMMAOAhtnFHj4kampupWa+ZMPPdQ3Nj\/y9AUDDOrXisgYKjS2m1KxKAax7pfV042YZ+QTn088XMmjL3njaui+eDp1Np3OeOeQRmEdhN4oojn\/nekIJHXHKpG387\/hdz6A8Co4pIt\/wAroSWkChN4rp1u1q8ZzW1ofU0traDuKHBgD\/6FZUWkdzN4XSC6Nd6X\/evki9Bl+k50e50F+GXqJnR7rQ\/45zo1cnudOA8ZdNP78x1ZNe5p\/\/9E8n8Zn+77rQP2Z40\/N8D+XvcL062Z1GL\/SjaauC6IPVQfTPWVdoxqfBtOd4CkUlltKDxhaqrW+morJ6Kilv6LbXNAD4hFhLSyvl5tXQ4fPpNG6JH\/2ZwaaAxtzVh0H10lg3emWiOw2a6kUTlvrTtE+CaMTcq9RvhLMA3XMMzmf4z0+\/c5GeHtwGZO19evGF38X9AORBDORJywJow\/exdOB0Gp12z6Iz7tkUEV9MNXVNBgB7yuAJWloemkISwpSjLOVWJe37NZXeXxlIAyfCi5kH36RlgXSWQ23A9XsUnVzGHquEAiML6dKV2+R0JZdiU8ooI7eabhfUCO8J4PUd7izAhT8\/x170r+950BtTPIUnfH6Uq+nevYc4CdD34f\/2fsfJ9HcA7ev8+6MX+NFJtywel2YDgI4h8A+p4UEz5dy9T2HMuzCpPzIoVu+IosVbw+mid65dn9fMHg9A+dUtmxZtCad\/TPcW3qozr\/cBh80YBh4WR1NTq8VFAb54+Fy62XsAXPCMANWYRX40b1OYCMFjF\/vROzN9aPn263TsUiZ9dSiRPl4fSm9N96IR867SyPm+NHNdCHkxlzQA6ADLza+hIxczBC8Cr9JO2otj3KiyutEuQMd9AKT9p1JpFHsWmaMp14SlAQzOLJq1PkTwNVxTOcT6cRLR2NRi9TngcS+Oce0U0Aq\/BJd8d54vzd4QShs59H59OFE8O5aTmQYGe2p2lUhi4AGRwGABGAC0o4Fs\/\/tggiD2liZqIHMtcKLuGsB33itHeBMtQBAmkRQcuZhJhSV1JgqAC96ukb1ew4MWmxIDeEF4K4B2+Jyr9H+TPSyGdu318oQOQG7YFUuHzqSTT0g+ZXF0wDvhHQwA2tEOnUl7hKADCJ\/tuUEXfXLJ2fcOnbh0i066ZHWZC5ZWNNAF7xwaz8lCL82Ev\/mhF81YG0wHGeCgADW1TeI53ZVEcA\/wQs9reQLU2w8k0Ip\/X6cPVwfTMAYlgKYHkLMYkIgSLTrGwCIAC4pq6SaT38rqB39o8GXzhM\/dGKoa7O+PJlMpe0UAAIMNvtZVq2deGchJw+ItEcKLKs\/pO\/wSDWGQr\/02mi5dvU1JGRVUzM8Et3OkYSHEp5ULzfAge7ZNu2\/QvM\/CmQP601\/f92Av7NQpGAd\/5MNhOL97AMTAlvGLrNgeSWu+iab41PJuDfKTnHgUldbTgAlupgF+hcMwNDB7WUNDMx1n79lPSjDwjI\/Y4x11yqTkzEqqb2j+zcYACwyLEIsE7wnhehEvFnBQJCq9NYD8y7BL9MnXUd33gCmZFeyKg0Tq\/Rqn5lDT7UGye8oQXiqqHtBtTh7gySGiYnXrlkB4HEbM9VWF3oT0cru9I95tyReRpgz3Tc52D55BqK2h6vuNHJZzRahfvDVCXIkWno3QiXl68wNvET6RJDgqet0trKXg6CL6hSnHtv3xNGahvxDBlTEas9CPEtPKuwdATBbIJbI75cajF\/gK+aHq\/uMPxMr7D5jM59KCz8MFoX\/\/X9fouyPJuu+TlF4hPquMwd8\/8KJzHtl2ecdi9qQ7+Z0GclhHJWPdd9F0r7iugwYV19Ln\/4kTTgCZ8JBZPuTmf8fsvVz97rTpcm+3icrvrQikWM6kHW2NnPGecL4l9ElljF6f4kFHLmR0D4CKHHDqcjaNmu9riv3gKTs4De+KN+nJ0Fle2SA8gTY86B5g5lwz14eqJJete+N036e88sEj3g+L+aWxrkLcXb8rRoR7+TtAzpiy8prq\/REOzYZyzjxluQbiskfgXYePM97xwJlUFYWAjogFYbcsOOvOfVrNXFAWQt\/iUOHie5tq65seOwBCCjjGqxJqvpYkh8cV677fqh1RYvKVe8z7LMwiEBRDWQoyx0Ym8cgQIeKGcNhSNDIPzjwHTfMU9wWnQoOAlmffK6mjScsDTBn4UF5QieyRLWW\/oExytr6LvaujHQUaFsBX+7dro3j+MqYUyBts0SN1yTBB0fdo+qfBonyDh6GQjcFDlpZfVPfYeD8sGPAlc1nanuM3dd\/zx1Nppu+Ma+wifwqILHxkIjA+d+\/Vip+RPKz6Olpwo6fbQbFkWwTFppSSX0Sh4JLQ3aZ+co2Cou6Z1c6wWFDDVZ47f3M4pedUWXxPNAxAK1R+H5l1enaVw8YaVOwYJ0qDP\/I2PfNv07zov+fTRaeO3XVArFC0\/mxmUvz8aHWdENWBiZwZXQ64+5sCEBPp5JMrwoCs5it\/nrspTPc9ATa5+oFB3s2TfcYjR0SGt2f4iPEYv9ifQmKKxGfg6byD80Vd1SRWM9eDt3iXKQ3eCY0B0ODMeQo0H6CqoWhxCK\/LvozstMpwNbRAhPMOzu5HUQmlDhlnCNkJnGis5IxXaYzo\/66z0AKRuDlUiEb5BeFlMIcVtVLvJFp5fqskBd4PGdrkFQEm4L3IHEsmyAiFeg0KPwZX2yHSb4SL8GIKwCGfAFAdSUYDff5DnLpLpb2ADzACoJYMDaKn3bNNn0OS8sOJzr13JnthmQeiuhEQUeiYJI8z7HNeOfTM8EumuX\/zQ29elNk2hd5uAVCxPA43mzlLkwkoVoM8CT0JPnRi7D150\/QuGJyt++Jo+prgDoI8ytWme6H8hgmfszHMpooAJBCUpsB9TBGDvURyRoWKPwqReZgz+QR3PkbQ\/sA1lc+AY+FenVl1TZMKgM8wbfCy81xgbPC9ILOAinTMuxOt3hGtuxL0VHdfBlIBxEl5gFHK0VOQxn2g2V0NKxDeplVniamtFsqTza6\/z5CO9xjHIbGSvfHH60JU4diW+4XdKG5rVRpiXvlHQyeAje9++nK2aHtCgoAiv2qyOJQu3hL+SPOCnPGaez4kGrRHmcIpRxZrngWTLwMQnvqy\/127A7Cev+On30arvtPYRX6Ce+u1p+zxQk08yHLhvNdgJ7oSkq\/rHtC3wLPQp4ZGSj1cDZ+v5awTrUHKO+A+KZwI4N+6AsBgTihU9EJTC569IUwAroCTL0uLTVSUKhtEFqwFMGqvnT3\/hHOm6vdRhbAqbD98+EjnjD0B2FZ6bBXvLnNi\/FlvG5bdAKgQ7k3fx6q++J95INBE2WDFE+JL1dQ20tRV11Rh\/PujKbavSA5XqASYQg9TglOXs0yhbIYUgjFYtiRbkfHFgvTjPdDx++xIZxWP2\/5jgtX3ysm7T0u\/iBC\/30\/ikADzJE7YzI0NPldd0yi0V7lNHpTAmqFJQQZgXw799gzBSDyikkrp1UmXVdrkziNJujpg7A5AhAasAG0rNxIAdFigfFRWab4sVFffRO6cOfdtJ7PwUNAYkUzYAr4qnqzDZ9NUvGftd9Gm30HbklzJwD4IWwBYUl5PmbnVQkfL5tDygpT1I+O1Juegf\/BfvCjwPuOW+ItymuyFsUcjJrnUzCS3kD8nDr2kxbR1b7xNnS\/QJmUAIinyt1MSgmZcvO+w2R3iPpIw8N7yyq6X\/OzWjhUZXyL2DJhC4Bg30d49gP\/u4\/UhtIs9iTZUYVCRykOa6Mjc3GkPk39rLT347H1e8Wfcc3gVtg36n9hzLt0WIci4YkgKwJ+U+89aH6r7u0Gnk0MOwHPSNcsiD8vIqaIPVl8Tn4EQDTCibUvmdLgH9DItsLLvVtO8zR3JBzqNEeptMYBN5qwI\/dd5Xrrv+VpEez9okbzQP1obIrp0umN2AyAE1onLAkwvOHlFIC3fHkkvtYOyH5P2rw8lqkJJBafy3\/w3yZRFQ09cw5lUTl6NDWG\/WYDgtfc6qh0rtl8Xky0bSkLwesrv7OxCPRjt689IQjQAje2LWkNV6JxntpB9AL4dh5NENQOLCe8hc0F4\/J0\/Jz8iWR11yjCBHf\/dzzSm0caEDpRH7k5ZsDmc0rK6L0RDEF+0NULVsIrO8Kth+d2+t90ACLI9YUlHWv4ae7IjFzJpLE+WUkdGVonyEPQxrPyzHjlir4PcaRKdVGoD+Fro5wvpoj9N+ezSLyIF59IaJln2zNfYm+k1VHv6SKEN0kxm+55ZufSG\/bWQbLCQjjvfUu0Sw64xdInIzaZf7E+gO4UdCyYjp1p4vA7RPNRqyU82RBpZdP+WF7etFQlLUQYARoSSpbahc66QX3ihXdrE7AZAZGCoBMg8EOEPKx\/AkkPPT+czhKd6RRKIMXDoXrE2IOCbF3xyVM+ZvDzQ4jvpzYDNhVRk5fLzNv8Q98h77TmWIu6PCowW5EKjZJIuZ+m4pjA3Vcg7fmfdrhjTv8Hb2Bp6zUkw3c2A8T7w3kNn+2gkpK4tYocDsKC4jtZI2tAbUz1EtwcIKvrbBraLuahVoqUJNdAOcdhFdIPcr+m8sQHa2EFOOJSqBDKw5ZypVlqovEQnlqo8ysLPw3V\/L2iTMv\/Du4N3KkJzXGqZWAAI0Qh58OAtmqYCTCa4krY+Dc8C7QxJD8R7Jcwj8fiSs2w9oi6SQJn\/QSTG9++SM0GrfnIZzZeE8LYGFC+R8OmpdPQYADEZ2MKnhACo\/SDFSlfxtn1xprKN9kLXdWdFdqXqAg+h1KAh1SCcd9advPeXm6oyXGelL0v2yVdRKu6D3kLs062taxZbMYd87COAs+9kqhDTLZbJcqtoJgNOBgl4FeqmuMZIVYUVvKhy8\/WJuriXHH4B4MLirjWIYBFNXxOkelfQGEhb9i6z2g2AqADIWRI4A8RcxQOgtxCt2lrw\/W2aZ6dVAQifYl\/GplBJBnERhNuaJoaKjPKZFzmM6jW8s9yQC4+L\/bDgoOiJRCKBaou1xSPkIp64cRJFQbKAZgHIRPM\/C1cJ6PlFtbreE8CXwy+qNN5B+V3wfK1iLrAVVJ4jLLDdx5PJEWY3AObzaoNwK3M9uTMGm2kOoK1JUxcVnbspZaKHz5zw6R2cJ3riFA43dPYVcuLQbk2mcfW9o0pwdh1NNguMzgzbEOTsF10sSBzQog\/asGlPrDguw5bSIQ4DmiNtbsKFvjlQCKXVC9s+kZjptb0nNdkvU420LrRhIWSj\/ixvPIIjgdDsqL5Cu27LfFuSO5DtIdFQeAxS9v4jnc3WVJHBop9Oy3ng8rEp2uTFxriK7AvlL3in+vYjxeS9sQBtHXvjeewxe0u9cRC7wUl\/5gQI8ggA0WRl\/yz4npy1wjMgy32WOagLA1zPRq28wlqxKOUwiT46+ecDp1NFB7Zek8cIF07C0rt7DmOJnW9yPyEu1LoxnvY+FcvuAESnhpxYjFrgSxHxJWIg3APuCM9lUugnuqs6TLCH4DB7m5KKBtMXDY0pUoUscD704aHnEJUNHEOBEtvMtSEi01Uu\/Dxy3lVV9zb4y1DOxFFhQdcKyLW1TTPYDASlX86gkeGu\/Oq62LKql9T7cjIjC+La5oRVzIPruiBroGVL9n4Yl4RUfZum8plfbz+QqC6lcqRCJ3hn9Oixy4IHSqCC9yjhBAEpu1w7hCANaaatTHXJlBlPYfCixR96IkCI0pUc\/ux5YRONtckeNttX5Z0QipDNo07bFW\/gGXSXXh7vZvZ9sMWxVFp8egyb1vV+N639wMma9rgR8GeUIx3l+ewOQAANnkz5AsiisDUQXb+mkDPTh35xuSVkCnRW44wRfFF4K2Rcw5lbofCP2jHOIIGUMHT2VXrrQ29R1sP5dsiCAQZ4ROV0Jm0NWv4ZGSwWBvawghOKjNsKP8I7yMefDWDgAHxdNYQ3nFo1YNyjAEQbU0wXd6+hvNdbylQRGbC3RN\/CyFPNm6KrhsYWUU+Y3QAIiUMu2KP8hRCmAAQZZOD1QnESgGy3C9q2HiolOzQwoFyWxgBFmAyMvCeSDrQA7T6WQl\/uj6f1O2OEB0WFApk36ru4UAnoq\/Ga676Lof+cuCnameB5wR8t6Wu1nDljM7jWG2AvR3lV1wvueN63PyWqasFiTBjkONHUXAJmi4FTyvf71e2W4Gu2Gryu9h5YwDc4KWyyo9bncACCjJ\/nFf70YPMhDwIm9omam3iQ7oT0CkF2BzHHU+4xnsMSyLStm6tx7zNSCzsuW3bo43OoT+NZOHhRe\/AjEit4mu4YNMxvGIByOQ+89ITLrS7fExxVfk\/wbz2NAYo+i6wbx\/jCe8ITQl5ydNh1iAfEpm9znA0eCftTO9u+CRCgmxnnkChCMzwmSDs2\/6ANyFomhmRnkGY13ymosSqexySVip49ec+HfK38Kqpb3g8G3XD2hhCxSw3SDpKzrvbPCU9d16Ta7IQrIFLfsWidjUlPml0ACGIOnU07eUhEMOC2lJTgRVE7hlameEF4DJBseDJs\/USHM1YtBloGI+6v9QjojLFkKCXFpfCzNoQJARc0ARt4cMKonD2DRlyww8GTesKiNUPlB2K4\/F1BR1qe0LN77OYB4UVk3Q0T6+p3m+7X6ivdoPUo\/Eax0Aafaz8iFsIoZBxwPOzCR+sXwia8CARpfOYdzQ49baIBD4y9umhvQrhSjp9Fo8HCLeFi9xgqE7IIC2Hd0TKEudDY+tDy0WtIhuTtD5OXB9DdwpoeDZuPrRB9p7BWHK64cXesIP7dOVELG7zBTwA6bTdKf\/ZS0Ai\/\/DGePK\/dpV9db6n+HUfKwsuhRJWRWyV2oCHjhtyhtMbDy46c5yuK+Oj2xX5eOflA1ozDd3rS4NnROY4GBQjzhSX1YpHBgya182SZ5kBrtfUQoD8EAO1pCKvI0nAKE7zrc5rMtLPrp4sZQqAFf3uLQ3gfTZvSKxMui8ZRmTMtFg2XTtKmo1BxoHdPWkxSmVgkOA8GOii6yNFUgC4beHg5QYJEhW7yJ9XzPfYAlPmaPxNscDocmo0NOvACvQbbBkYkM5AacBQt+vhOsrcEl5SlDxwr8oLUdIASGWrJPWnwcpCKrH0f7L\/FER2\/l4NDn4gjekGwcToovOH+U2k0Z1OYyAL7WgAiRG2EbfQComEAFRZ0rKAjxVyWh\/Z6tN1Dc7Rl95kjDJwVuia+j7mzmlG6ROPCtahCEZafdM\/3RAHQnKHXDU2cr7\/vYap+YPJQGUCrFlrmHX2crT0NWiHOV16yNZLGcXjFpveFDEiE4LMe2aJHD5TEkf8\/EgOAXfWQPCmQJ4LYQ6Lx4Umztv8HSYup2RP0oKKqUZzuip1\/vxdv97sFoGEGAA0zzACgYQYADTPMAKBhBgANM8wAoGEGAA0zzACgYQYADTPMAKBhBgANM8wAoGEGAA0zTNj\/Ayyg4zGWXpWlAAAAAElFTkSuQmCC"}}

               // {"status":9999,"msg":"操作超时,请稍后再试","data":{}}
	}
	
	//如果登陆后的sms不为空，会发送短信，验证
	@Test
	public void dianHuaBangCs() throws Exception{
		String pinpwd="319926";
		String tel="15156062323";
        	String path="https://crs-api.dianhua.cn/calls/verify?token="+token;
        	String param="tel="+URLEncoder.encode(tel,"UTF-8")+"sid="+URLEncoder.encode(sid,"UTF-8")+"&sms_code="+sms_code+"&captcha_code"+captcha_code;
            URL url = new URL(path.trim());
            
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");// 提交模式
                httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
                httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);                
                OutputStream os = httpURLConnection.getOutputStream();    
                param = "tel="+tel+"&sid="+sid+"&pin_pwd="+pinpwd+"&sms_code="+sms_code+"&captcha_code"+captcha_code;    
                os.write(param.getBytes());
                
                
                //开始获取数据
                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len;
                byte[] arr = new byte[1024];
                while((len=bis.read(arr))!= -1){
                    bos.write(arr,0,len);
                    bos.flush();
                }
                bos.close();
                System.out.println(bos.toString("utf-8"));
             //   return bos.toString("utf-8");
                //{"status":0,"msg":"OK","data":{"sms_duration":"","captcha_image":""}}
                //{"status":0,"msg":"OK","data":{"sms_duration":120,"captcha_image":"data:image\/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKAAAABGCAYAAABL0p+yAAAWrUlEQVR42u2diV9WZRbH+zemdRqbmmmmqanJppm0cqvcWsw02xezMsulMkutLMtpU2vay9K0DATFBRERWRQ3BBVBERVQEVBERNnRM+f7wMV77\/u+8ALvS9nc8\/ncD6mXuzzP7znn9zvnPLfzxDPPfkE7zxsCzzwAeuYB0DPPPAB65gHQM888AHrmAdAzzzwAeuYB0DPPPAB65gHwXLCa2gbJzjsuKeklsnp9kcTrkbixWA4cPiX1Dae92fYAGFqrPFUvmTllMm\/JXnlu2kYZOCpBrr93uVzZf4n8oU+UXHRzpJzfI0J+d1OEXN43WkZOTpPEDcVSW9fozbwHwM7b5z\/tlhHjk+X6Icvlwp6RBmitHYDx0t5R8vhr66X0WI2cOXPGQ4AHwI7bJbcu8gu0S26NlEt7LZILevgH4sW3LJJ7n18rlSfrugSEe\/JPyKbtRyUto1RKjlY7\/u306TMeAM9VI8yer4D69\/2xMu6dzfJDzF7J2XtcDpdWyQkFF6F2174KmTU3W\/50x2IXCCPN3zeGEQCHSk5JkT7L6Dc3yuCn1xiKcN\/YZHn+7U3y9ufb5cfl+yUj+5gUHDopGzJLJSq+QLbtOmae3QPgOWD3jU2SVz\/cKiuSDsq+A5WGE+LR7F7N+jNg6KFAtYPwb4NjpLSsOiResKq6QdZtLTWAw9PN\/D5bXv84U25\/crVc1i+6hYty\/F49Nwvisr5R5s8spN6PrpKeD6yU\/k8lyPBxSbJqXZFU1TT85mnCOQ3A9YQ0BVBjY9sKl4lcmXJIrrtnmQOEP6\/Yr+Cp79D9qxUgpxR40eq5Js\/KkHueS5Qb7lshf797mVyrB17WcM82uKm\/488K0PuV386emyNJm4rNAvqlw3VFZZ3x0EsSCn2oxDkPwNMKECY0nFavQCUc2id6knpQ0jTtMQC\/e3+FxKUekimzM6XXI6vkz\/0XO8SO9d8oceP1lJNec+dSo9Ddwqg1IMJzr1Mw363gnvhButKMfbJx2xEpPlLVZaml4qNV8vmPu+XRV9bJnc8mys0PrpSliQfObQDikeA6cLT41CL5YmGuyd2F+55zF+e1eCaOYcrJMnKOBeXtyitqjSf6cfk+BW663KHhFaFjv551\/FHDbg8NqX00tI55a5N8On+XzPwu27zngmX7ZZaG6Bemb2pJG11x+2IjjtryjITuWx6KM9Rjx+7yLpkrOOqL\/9nieI5XZ2ac2wCsq280CePHJq2TG4etkG7KgybM2BL2++buP6EKOaplIAnJazce9gtW4zXVyxwoPmU42cfzchQwy9QDxMnlt0f7gANvd1nfaLlywBJ5Vj3tVz\/nytadZVJ2vFaOHKs5C+baBvN3ADort1wuUgDj6eCGwYZrzn\/opRRDQ7jW6TByxRMn6+Uz9YD2+7MIzvkQHBFXoKHrbEga\/EyiY6LCYTU1jfKPIcsdg7k6rciRnLaEy8mqeolZc0Cmf7Fd+j0RbzzdBT39A+IvA5fIPWPWyhOT18s6BcXmHUeNMLHoRWvGAvxdB3giYEW4IHbWbS0xvKymtlEalCKEWrzE6wL8ywAnfWgvdfnVAXBLVplRstYL\/Wt4rJlwy46ph0BZfrsoz5TUGhs7P6jblUQ\/9FLqWSU8KEa+i86TumYAAjr4FZ6FsHPnM2tUVCz1y9VQr4Tau5QXvf9NluFFgABhEowwsmz6Fzvkwp4IjyUmJF9+W5N3Bex41cD5zEjDLfl3fg58eo18pGF+mT7H\/oOVPhmBztjOPcfl4ZdTHff\/dMGuzgOQULhbeRjJUga\/K638RJ3M+CrrbHqCQRyVIGOnb1buFG8m5PJ+i81Ew6Xq6ztHugEFKg6lag\/BMarq6nUcCotOGaC\/922WXHvPUuOdLRHhPqi8kMYZ\/cYGQyVYKAf0909Vt19IHTlWbcTQjC93GJ4YGZdvlC+q\/eMfcoxwIuS5vS\/A+5Nyx2t1gXRX4P6hd5QDnPeNXSt7C06EBIiE4c\/DEIbPY1Jfm7nVeIVJH201yVnyaigfkrSEEXhQqCkGYGeyR01Nk0t7L3IMaqBVT36tM6mIYvVOEH8mzbrmU3r\/9KyjpnFhtnK8QerxWAj+0idWua+b8rxrm1Mtj7+aapThMwrE6Z9vl+\/VmyKq4H4HlTsGW3fG6\/J8LASM32tocIbSQg15eDi8pN0jExrfVfBO+3Sb3DQiVi60LRre4ZGJqbJJKQEg6kx4Tt1SIje5cqlUeDoFwPITtfLhnJ0tFwQMeIV\/qpe49eE4mTo7wxT75y\/dJ8vXHpSj5dUGkG2lTPCsR8tr5LheH9AAOIvfFR+p1utmmvAFmQ6UiuDfSXHg+vEQsbow2hPaLD5HWPxRleeI8SlG7OBJ8BCErJ9j801KZe6SPOl+n38udoECD2HRfegKGT4+WZ54bb0B33VDmnKK3ZobH6zzOZewPPGDrerNCkxlJlQ5PMYdLz7u3c2OUiTP823kHkNbvovKk+uHLHO9Q4T01agyb\/FeyT940oCxvUA8VFIlU2ZlOK6LA+k0ByTZeVm\/qFYJL7XVa+5aKjfoJHTXl\/tk\/i6T0oiOL9QXqpTDuoIzc47Jlh1lsibtsCxYts9wm6df3yC9FMis2rd0hVZU1pobL1ld6FMes46\/630+0dCzMvmQ8XrwDwjv8RNt12535h03SeGnpqSZlc+1LHBYXvWim9XzTU6TVeqpdu4pNwtszLSNLdzL6fUiTDVjpIqLb3SCs\/V8FgTc8YIewYmFq\/TcZ9VDksDlHUJhRCYW5e97RTqeNTe\/QsHetEjJF9793Fqjsu3Pc+WAxTJZHQBpr4JDlcbTBmuMmTuXCTA7BcAsHdQZX+2QkTpp\/R6PN6mEYAaWUHV1c3L1rwNj5DZViv1HJpjV6O983HfEynxz4zzlJnAe+A05rYGj1rScd\/XgpUb2d8QoyY2fsdnRiuVO\/A7QZ\/xp+T5J0ZDy0Xc7ZegLa83zW\/9uhTAiwdh3Nss3Ebkm1JyqapA1Gw7Lgy+mtIRp+wGArUVFuHZ36MDRHp6YouLraEhASIR545NMxz0eeDHZ1MPtESBX+T30ikS4\/bmZZwBKXZroUl7R9uJA3FB3t9\/zFfX0IVHBTaWWcklNLzVEeMK7WzQExhkBAM\/oSEnJ4U3U89w\/PkkI+6eq6w1HgiQTNgjxiA3rXAaG2m5HbIkq6W59WvfoLBre65YH41omBdCixHs9tsqE\/oUr8hV4R6TseE1L08L23eWGnri9H9cYMmat8b6LVxfIwuX7ZeL76SZn6L736GkbTJ03FMZzXTXI6ZHmapit88M9q2sajeIngc4it4uavw5cIpNnZph3rW9oNFjAsx0sqTJ\/BshkIfgZm3RIebBzfJnTkKVhiHLwPG7Iz3x107EaDqmbQoLvHp0o9z6fZH4GCqNMpttLECLwEtepaiN\/BQ+ErySqV0H1DbWlZFits5QaQOYJw3Q6H6+sa1n5eDpWI2Kp4mSdoyxFmxU8CSqwVoXO6nVFErUqX976bLv0eSxeLvFTuSA5jQd\/87\/bDOhq6po4rB0oDH7KlmIZZntO6+B3\/6u0gZDGwoHzWrzvoNKHr3\/ONSknxosKyaJVBSETdIyhvYoyfFyyoUMBeWRtg2zIPGLGf5DyYGsOGQNSTveOSdRxyDSLCQrF9R55JVX5Y55SrKOyWaMBHT329+fcLskD1jWrNH5SXrrSlZy87YnVpqLx8ntb5HUND6+p679HvdmV\/Rcb\/mgnzoQ7PBVh3OpO8Vd6whv1H7naeBPCO9WIQaMSjBhgIJ5WIvyQ8rL7JySbeuVzGtZR9nSk0LQ68\/scGa+kHQoAHwokeq7SMMxiaGgWOv74JqAyKRHXNQaret6afdRHudp\/j5LWkoQCeXTSOg1jm1oS1Z32guoo\/jZ4qeN5ohTgwdSKaeZAKL2pc4VnhB8Haua1hOFTyocnfZjhUzq0HETYE9EMMN4Hom9\/CDqTWen2Tgm8AZ4EF01yl563LxbuNt7uYRUJrypAUVL\/HLpcbhwWa7pAOhriz29O4fBMF5mqRWSbhX\/C0IW2UHSDPsdPGj4DiR087rRPt\/tc5xFdAJD+YIwxSVfPviM3NLVcwuX73+x0vCcLriJIQKCsYxIOGEoSKO\/pLwnvHk8aM7qsEoKLv+ZO56q7f1ySCddtKVXLw7BC4YIAFENJU1a6+NZIR0LVzTfO79FxHvoPFRaILRK8i1WJo2y\/XbRHqYET+JEqlgJ5kNjkg6a1337+v4avMEr9l2iZ4jk\/mZ\/jiC6kgYL1sPA+AGsfZ5T7IxPXyfvfZsmTGmkudom6QHqA9E6XeMCFygevtrl9gDJN+VNnu3nxCjQoWNe94vZoMwCkdchhUZcdoaG2t4ojeCR1XSobtAhd7VJ59oNU0Pyle6VIQU4FBuHDs8LV8EiELKoJLfdVrjpHgenPtmaXmRSPoyqg949sVvhd3dRBRgHuSuOrxb\/Hq1ItDqJnDxGTml4iA5TSWLSCuYTaILjYaYiDoNIzXkUpnd1EiUAAnDIrM2jP2ykPiGx33\/ybiD0hqT1+F73Xcd2hLyQZz2JVBzByjwkqMqLiC034ILVBZ8newkqTaGXwqLTQ+gQXfFkVKeBubKUSgCd0CCmdCMSOv\/OZcPf7z56XHZYOZgCAIrXXwhmPfOWUG1UQ8I43DY91bLzCQQT7LJT87N6P8Eru1d\/vMwcAFodAys2dE+SgIkRFJ5j7d9gDkjaxiwZW3WcLdrWIlM4YnSSAzt6k0J4GSJ6PCeKw6qDW37U2KEU6yc+o6rMPJp6goOikz7nL1h404siZXtkoe9Qbhdqo07+jnv+hl1NkrIoX8pykeBB4JMgRdxc0A4\/EOzQGcAblQRVQzJsdSCTa6dNszQAiB+Ad9HSCYxzIJTOHYQMgmXbSInYlSOhD+YaqTky6x35t+uvCbQCU93Inl6n4uA3FB2+yn0sye+2m4pA\/Fw0OXy7MbS6VRhmhdomrdxAHgMr\/dMFuqWhHtYWUDOkVB11RMdIQROcRAINnzlnk\/H20QbBtdR0OweTZ2Ehjv\/G46ZvlUPGpkAx6XGqR9BixsuXafR+LN2E13HakrFruGp3oeC84aZ6Gdt9nPGR25NnPpS3LElWhtI3bjxgeGzDJ37OpsSJ5S4lZHG15e7u9p89M59FZMZksefnBe3LytL0edmIhaXNxeAG4TyeErxHYb0piOiOnLCQDzoSPsSU7yfbTqhRuI9ySQ7S\/V18NKStU+bqNJgPKiPZzSeySYgm1kRgnFFOh+u\/8XaZq8cSr6024o0plTznRiPDhnCyzQy+YjAQFBrt4YwFmZAf\/Dqj\/SR84x4FtCGEFINxhyuwMnzZxOmdDlVr48udcZ6pjWGxYwcdkoYhffi\/d572WJx30G7JpZnB7IrqLwmWoUlr5Adee\/ApZr+r0Mw27D0xINqHPXl4jqdyWMGpUOrVhW6lPWXHrzuDr1YA4IjbfJ08YVgBiNAxc7crAfzAndCEIgktyumVAdXDXpZcG3ZgKl2xPpwcKmZVPf599Imkq+FFV34kA6QUS6vYevNvUK9Hi1dUGLRo5Zb1PZ9PjSiHIDgTaGpCi4ZI+QkcI3VTSrk379A9QYrRfg3JfWAEI4b7r2TWuZHSypGUeCcmAunOCTQotxeSkgtnCSSoBmkAtc4GqdrxGICMnSI0TBXmNa1HRSoYQCeRJaGbtaRt8AEvqp66+67\/IhTLlCxHdXfteRk1JU2V73O\/v8FUG9jTbz\/9wTna7kuo5eyvkcaUE9mswBmEFYKHyJUppbgXU3oe3wp+\/\/Bf7DuylMnJztO3Pj9lndqotXXNAvvhpt+nycBu5s8m2Bkq+GUPCmpwgqhKhE7Om0BTzGSy6mt0bb1CXcNukVtQtYZt3tlcKaObdGgYuGCwI6dPs1ifa1gq2SF5SatHgp6EXGsEmePt7U1NvzxzCh9\/4ZJuPeAsrAKs0xEWvKvRpWyc\/VhBkHsriNf72uAJKQgeNCP5auwA7bp8GBZpG\/Xm1j3\/Y5bePkbZ88md4Ltqr\/CVUTelOCT17eE+18vUEnpOE9a02lUo1gSaJcG+2by15zUKzvwuVnp15vuNM7Z56vP3cHhqS29MOV+lnz0jPB+PCC0BWCGHtqoExPqSdHWV8d6W1SWOQaJmiqQEQBFrNtILd7OIX7oMcnG+Z6rTZIRaowyNw32JT3ZP67hz1jodL2+74hT9SkbGrSUAId\/wlDBHHDkN3Vzsq2p8DoCnVfi7fraG7PNjoBQ7c+0XYbB9WAFognOpSw9ZB9t7ybPW2NiUDvmPVRi0STpk0fwCyzmVyKauxWw4wsVsN70WekD0arHRaivz9blMF5LTkFVSYlvh39ZmoHvB5NuqatCD1fjS+qeXrqQS5Q73toFFrVExlm6pGe+qajAVh3P2pONrYu9p4b9Sye05e99OxwrlwZPe5ESsLgr7PC29v8mlRYxGHHYBNYfKED3ey+NOICSny2swMs6GJgxZyVGIwHsx+DyYXT1ReUWPa4qNXF0ikDlB6VpkUHj7ptwPYF4hnTKmQ0MzGKMI7XT0kb1HcNK9m29rZ2\/u1Ae5BqO73mDMpy4LhkxyHS6u77GtX3If9N+5xpobt71w4rvtcmg\/aTNyrI3nv6yyz3cC+29DqmOkSAFbVNMV\/Wnj87d63b\/a50LW3lQ4OvFhcEF4ilButwznxLEi8qf092V8CSd\/bXFHhPFJE1l5sGg2CCfXBh+BGWZpY6MN92QPtz3PT32lPPZ3fvIcnN0BFhOevrW00Io9WLRpV\/\/N1liuMR4cfgNYL0Fk79ePMgBuS3Aed0A+8mCLzYva2S7CcC4bSxJvSUOHvK67dhy6X0W9uMIuWfkS6R55\/a5PfBDZjW9\/OBg\/AQY8fX9Ny7jZcJhl+lHnT9xOr5E5XSg2PBsAONDdj8NkParxseqJzyGpfgy+SBSAb4eyRjO0aAFqDTgcF2ynveDLBAOyP\/aJt+42jjNB48MVU0zxJ6AxVS\/qv0RBPJGLd6Y1An3BjrNh\/4TbqunyIne6WYNIiZ5o\/c8e2V\/smL7wbWxgCXQMRAWd3f3mB3j84M5mAn5btN1+yoFvG2kZB6KUixty\/\/dl21w69lK4DoFXWwZvRmk5XxoQZmw3noIbLl6Vodaeh4P\/pE7RsakpIKzJZARoqrnDzpB5nFyibsPwleNm6OkBDOl9doGs8oBPQkM7WB38b0xF7reUycQbzYvbJjcNj\/X4gwO5M7GEa8PFVB746NmrqBiMMEYV0mwfzqZfzwrX6MQh5k3Colf9nYzzIE5ISYisAnwBBjD2rYZjmhSFjEs0XB+z7eVvKZOkljr5DAMLeC77oQKIbpU7Zj4OwT6d2tz6LfNIhcSqy2iphUinBu8EV29r2QHiNVpBVN0exsvLaDm3N9P5PSb+AESJR4XgIQqbFrfwJLJog3OkN9vCSQKdagXccNi5Z\/u3Hc1n8jG2mwYg3ALp5R5lp+jAdNj0ifEQFux75wgVl0lDsf\/EA+Cv3nGmZpYaD+dsMH+igNctwyqlpQXdGW8l06NG2nGNGfFDbJazSWgWFmKsUgPp6KKs7HgDPAeN7MnzB6+uIXKOeUas0gBIGSe9QkqQVn2\/38HcvKVjo7O7I\/4LC8pSkhQAvn21h01NHv1LhAfA3ZpQvaR6A77GTj493ss+a\/97eRd+M9gDo2W\/GPAB65gHQMw+AnnnmAdAzD4CeeeYB0DMPgJ555gHQMw+AnnnmAdAzD4CeeeYB0DMPgJ55FlL7H0mchjIiY56+AAAAAElFTkSuQmCC"}}


	}
	
	
//查询	
	@Test
	public void dianHuaBange() throws Exception{
		
        	String path="https://crs-api.dianhua.cn/calls/record?token="+token+"&sid="+sid;
            URL url = new URL(path.trim());
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
                System.out.println(s);
              //  return s;
            }
          //  return null;
		}
	
	
	
	//富有签约--短信验证
		@Test
		public void qianyuemsg() throws Exception{
			
			StringBuilder json = new StringBuilder();
			String uuid="132";
			json.append("{");
			json.append("\"uuid\":" + "\"" + uuid + "\",");
			json.append("\"tempTypeId\":\"6\",");
			json.append("\"tempStatus\":\"0\",");
		
			json.append("\"remark\":\"\"");
			json.append("}");
			String jsonstr = json.toString();
			System.out.println(jsonstr);
		}
	
	
	
	//富有扣款
	@Test
	public void koukuan() throws IOException, DocumentException{
		

	//System.out.printf(DateUtil.getDateYMD());
	String path="http://www-1.fuiou.com:8992/fuMer/req.do";
	String merid="0002900F0345178";
	String pwd="123456";
	String reqtype="sincomeforreq";//收款
	String bankno="0403";
	String cityno="3610";
	String bankname="中国邮政储蓄银行股份有限公司 滨湖支行";
	String bankCardNo="6217973610006488319";
	String realname="吴涛";
	String remarks="借款下放";//备注，借款下放，还款，扣除手续费
	String amountFen="1";//单位分
	String companyOrderNo="2342342342323";//该支付订单企业的编号
	String mobile="18555116208";
	String idcardNo="34262219900129295X";
	
	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
     		+ "<incomeforreq><ver>2.00</ver>"
     		+ "<merdt>"+DateUtil.getDateYMD()+"</merdt>"
     		+ "<orderno>"+System.currentTimeMillis()+"</orderno>"
     		+ "<bankno>"+bankno+"</bankno>"
     		+ "<accntno>"+bankCardNo+"</accntno>"
     		+ "<accntnm>"+realname+"</accntnm>"
     		+ "<amt>"+1+"</amt>"
     		+ "<entseq>test</entseq>"
     		+ "<memo>备注</memo>"
     		+ "<mobile>"+mobile+"</mobile>"
     		+ "<certtp>0</certtp>"
     		+ "<certno>"+idcardNo+"</certno>"
     		+ "</incomeforreq>";
	
	String macSource = "0002900F0345178|123456|"+"sincomeforreq"+"|"+xml;
	String mac=Encrypt.MD5(macSource).toUpperCase();
	String param="merid="+merid+"&reqtype="+reqtype+"&xml="+xml+"&mac="+mac;
    URL url = new URL(path.trim());
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("POST");// 提交模式
    httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
    httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
    httpURLConnection.setDoOutput(true);
    httpURLConnection.setDoInput(true);
    OutputStream os = httpURLConnection.getOutputStream();    
    os.write(param.getBytes());
    if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
        
        //开始获取数据
        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while((len=bis.read(arr))!= -1){
            bos.write(arr,0,len);
            bos.flush();
        }
        bos.close();
        System.out.println(bos.toString());
        
        Map<String,String> map=new HashMap<String,String>();
		InputSource in = new InputSource(new StringReader(bos.toString()));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for(Iterator<Element> it = elements.iterator();it.hasNext();){
		   Element element = it.next();
		   map.put(element.getName(),element.getTextTrim());
		} 
		
		 System.out.println(map.toString());
        
}	
	 
	
	//富有下放借款
		@Test
		public void fuyouBinBank() throws IOException, DocumentException{
			
	    	
		//System.out.printf(DateUtil.getDateYMD());
    	String path="http://www-1.fuiou.com:8992/fuMer/req.do";
    	String merid="0002900F0345178";
    	String pwd="123456";
    	String reqtype="payforreq";
    	String bankno="0403";
    	String cityno="3610";
    	String bankname="中国邮政储蓄银行股份有限公司 滨湖支行";
    	String bankCardNo="6217973610006488319";
    	String realname="吴涛";
    	String remarks="借款下放";//备注，借款下放，还款，扣除手续费
    	String amountFen="1";//单位分
    	String companyOrderNo="2342342342323";//该支付订单企业的编号
    	String mobile="18555116208";
    	
    	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
    			"<payforreq>"+
    			"<ver>2.00</ver>"+
    			"<merdt>"+DateUtil.getDateYMD()+"</merdt>"+
    			"<orderno>"+System.currentTimeMillis()+"</orderno>"+
    			"<bankno>"+bankno+"</bankno>"+
    			"<cityno>"+cityno+"</cityno>"+
    			"<accntno>"+bankCardNo+"</accntno>"+
    			"<accntnm>"+realname+"</accntnm>"+
    			"<amt>"+1+"</amt>"+
    			"</payforreq>";
    	
    	String macSource = "0002900F0345178|123456|"+"payforreq"+"|"+xml;
    	String mac=Encrypt.MD5(macSource).toUpperCase();
    	String param="merid="+merid+"&reqtype="+reqtype+"&xml="+xml+"&mac="+mac;
        URL url = new URL(path.trim());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");// 提交模式
        httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
        httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream os = httpURLConnection.getOutputStream();    
        os.write(param.getBytes());
        if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
            
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            System.out.println(bos.toString());
            Map<String,String> map=new HashMap<String,String>();
			InputSource in = new InputSource(new StringReader(bos.toString()));
			in.setEncoding("UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Iterator<Element> it = elements.iterator();it.hasNext();){
			   Element element = it.next();
			   map.put(element.getName(),element.getTextTrim());
			} 
			
			System.out.println(map.toString());
            
	}	
	
		
		
		
	
	
	
			
	
	
	
	@Test
	public void testPost() throws Exception{
	        try {
	        	String appid="7a7984eaf31123dd639b5da67ad79d1b";
	        	String time=System.currentTimeMillis()/1000+"";
	        	String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	        	String sign=Encrypt.MD5(appid+appsecret+time);
	        	String token="40fb8e9082c8bbd80bf98287253f7875";
	        	String path="https://crs-api.dianhua.cn/calls/flow?token="+token;
	        	String param="tel="+URLEncoder.encode("18555116208","UTF-8")+"user_name="+URLEncoder.encode("dasd","UTF-8");
	            URL url = new URL(path.trim());
	            
	                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	                httpURLConnection.setRequestMethod("POST");// 提交模式
	                httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	                httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	                httpURLConnection.setDoOutput(true);
	                httpURLConnection.setDoInput(true);
	                
	                OutputStream os = httpURLConnection.getOutputStream();    
	                String params = new String();    
	                param = "tel=18555116208";    
	                os.write("tel=18555116208".getBytes());
	                
	                //开始获取数据
	                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                int len;
	                byte[] arr = new byte[1024];
	                while((len=bis.read(arr))!= -1){
	                    bos.write(arr,0,len);
	                    bos.flush();
	                }
	                bos.close();
	                System.out.println(bos.toString("utf-8"));
	                //{"status":0,"msg":"OK","data":{"sid":"SIDa4be478c4ead4988acf34afd62013bb2","need_full_name":0,"need_id_card":0,"need_pin_pwd":1,"sms_duration":"","captcha_image":""}}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	}
	

	
	
	
	
	
	@Test
	public void testPodasdst() throws Exception{
	        try {
	        	String appid="7a7984eaf31123dd639b5da67ad79d1b";
	        	String time=System.currentTimeMillis()/1000+"";
	        	String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	        	String sign=Encrypt.MD5(appid+appsecret+time);
	        	String token="40fb8e9082c8bbd80bf98287253f7875";
	        	String path="https://crs-api.dianhua.cn/calls/login?token="+token;
	        	String param="tel="+URLEncoder.encode("18555116208","UTF-8")+"sid="+URLEncoder.encode("SIDf7df5d5ee1b540faaa1f51601bc893e9","UTF-8");
	            URL url = new URL(path.trim());
	            
	                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	                httpURLConnection.setRequestMethod("POST");// 提交模式
	                httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	                httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	                httpURLConnection.setDoOutput(true);
	                httpURLConnection.setDoInput(true);
	               
	                OutputStream os = httpURLConnection.getOutputStream();    
	                String params = new String();    
	                param = "tel=18555116208";    
	                os.write("tel=18555116208&sid=SID3a37eab38f8c4766a26810cedd855d26&pin_pwd=012919".getBytes());
	                
	                //开始获取数据
	                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                int len;
	                byte[] arr = new byte[1024];
	                while((len=bis.read(arr))!= -1){
	                    bos.write(arr,0,len);
	                    bos.flush();
	                }
	                bos.close();
	                System.out.println(bos.toString("utf-8"));
	                //{"status":0,"msg":"OK","data":{"sid":"SIDa4be478c4ead4988acf34afd62013bb2","need_full_name":0,"need_id_card":0,"need_pin_pwd":1,"sms_duration":"","captcha_image":""}}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	}
	
	
	@Test
	public void testDesDesdsdcrypt() throws Exception{
	        try {
	        	String appid="7a7984eaf31123dd639b5da67ad79d1b";
	        	String time=System.currentTimeMillis()/1000+"";
	        	String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	        	String sign=Encrypt.MD5(appid+appsecret+time);
	        	String token="5b7f1e6bda5d07f78fa1c1cba2e93b4c";
	        	System.out.println(sign+"---"+time);
	        	String path="https://crs-api.dianhua.cn/calls/record?token="+token+"&sid=SID3b751a1b30a943ddab92d393974b1e0c";
	            URL url = new URL(path.trim());
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
	                System.out.println(s);
	              //将str转换成JSONArray  
	                JSONObject myJsonObject = new JSONObject(s); 
	                String data = myJsonObject.getString("data");
	                System.out.println(data); 
	                JSONObject myJsonObjecta = new JSONObject(data); 
	                System.out.println(myJsonObjecta.getString("token"));  
	                    
	                //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
	            }
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	

	//@Test
	public void testRecord() throws Exception{
	        try {
	        	String appid="7a7984eaf31123dd639b5da67ad79d1b";
	        	String time=System.currentTimeMillis()/1000+"";
	        	String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	        	String sign=Encrypt.MD5(appid+appsecret+time);
	        	String token="5b7f1e6bda5d07f78fa1c1cba2e93b4c";
	        	String path="https://crs-api.dianhua.cn/calls/record?token="+token;
	        	String param="sid=SID3a37eab38f8c4766a26810cedd855d26";
	            URL url = new URL(path.trim());
	            
	                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	                httpURLConnection.setRequestMethod("POST");// 提交模式
	                httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	                httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	                // 发送POST请求必须设置如下两行
	                httpURLConnection.setDoOutput(true);
	                httpURLConnection.setDoInput(true);
	               
	                OutputStream os = httpURLConnection.getOutputStream();    
	                os.write(param.getBytes());
	                
	                //开始获取数据
	                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                int len;
	                byte[] arr = new byte[1024];
	                while((len=bis.read(arr))!= -1){
	                    bos.write(arr,0,len);
	                    bos.flush();
	                }
	                bos.close();
	                System.out.println(bos.toString("utf-8"));
	                 
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	}
	
	
	
	
	@Test
	public void recharge() throws Exception{

		String appid="7a7984eaf31123dd639b5da67ad79d1b";
		String appsecret="a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
        String time=System.currentTimeMillis()/1000+"";
        String sign=Encrypt.MD5(appid+appsecret+time);
        System.out.println(sign+"---"+time);
        String path="https://crs-api.dianhua.cn/token?appid="+appid+"&sign="+sign+"&time="+time;
        URL url = new URL(path.trim());
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
                System.out.print(phoneToken);
                //{"status":0,"msg":"OK","data":{"token":"0f313f19e1e6991f56956ec48a9b497e","expires":"2017-04-24 12:04:56"}}
            }

	
	}
	
	
	
	@Test
	public void currentTimeMillis(){
		String url = "app/uc/login.do";//"app/uc/phoneIdentityA.do";
		//String identity = "18919693267";
		//String password = "123456";
		//String device = "IOS:A1024";
		JSONObject json = new JSONObject();
		//String json = "{\"device\":\"ANDROID:abasdf\",\"identity\":\"18919693267\",\"password\":\"654321\"}";
		//json.put("identity", identity);
		//json.put("password", password);
		//json.put("device", device);
		//System.out.println("json:"+json.toString());
		System.out.print(sign(url,json));
		//System.out.print(System.currentTimeMillis());
		//1494553537537
		  
	}
	
	
	
	
	private String sign(String url,JSONObject json){
		String timestamp = String.valueOf(new Date().getTime());
		String signature = Encrypt.MD5(url+apiKey+timestamp);
		String request = json==null?"":DES.encrypt(json.toString(), desKey);
		return String.format("{timestamp=%s,signature=%s,data=%s}", timestamp,signature,request);
	}
	
	private String sign(String url,String json){
		String timestamp = String.valueOf(new Date().getTime());
		String signature = Encrypt.MD5(url+apiKey+timestamp);
		String request = StringUtils.isEmpty(json)?"":DES.encrypt(json.toString(), desKey);
		return String.format("{timestamp=%s,signature=%s,data=%s}", timestamp,signature,request);
	}
	
	
	//银行卡签约第一步
	@Test
	public void currentTimeasdadMillis() throws ZhimaApiException, IOException, DocumentException{
		String isCallback ="0";
        String busiCd ="AC01" ;
        String credtTp = "0";
        String bankCardNo ="6217973610006488319";
        String bankNo ="0403";
        String realName = "吴涛";
        String idCardNo ="34262219900129295X";
        String srcChnl ="APP";
        String acntTp = "01";
        String mobileNo ="18555116208";
       // String mchntCd ="0002900F0345178";测试
        String mchntCd ="0003720F0395576";//正式
        String reserved1 ="备注";
        
        
        ArrayList<String> list=new ArrayList<String>();
		list.add(isCallback);
		list.add(busiCd);
		list.add(credtTp);
		list.add(bankCardNo);
		list.add(bankNo);
		list.add(realName);
		list.add(idCardNo);
		list.add(srcChnl);
		list.add(acntTp);
		list.add(mobileNo);
		list.add(mchntCd);
		list.add(reserved1);
		
		
		String[] strs = new String[list.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = list.get(i);
		}
		Arrays.sort(strs);//集合自动排序
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		System.out.println(bigstr);
		String signature = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+"iyqw910fydjn3is3w2k8b22ej5dohacg");
		
		
		/**
		Collections.sort(paramList);
		StringBuffer str=new StringBuffer();
		for(String st:paramList){
			str.append(st);
			str.append("|");
		}
			
		String bstr=str.toString().substring(0,str.toString().length()-1);
		
		
		signature= DigestUtils.shaHex(DigestUtils.shaHex(bstr)+"|"+"123456");
		*/
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<custmrBusi>"
				+ "<srcChnl>" + srcChnl + "</srcChnl>"
				+ "<busiCd>" + busiCd + "</busiCd>"
				+ "<bankCd>" + bankNo + "</bankCd>"
				+ "<userNm>" + realName + "</userNm>"
				+ "<mobileNo>" + mobileNo + "</mobileNo>"
				+ "<credtTp>" + credtTp + "</credtTp>"
				+ "<credtNo>" + idCardNo + "</credtNo>"
				+ "<acntTp>" + acntTp + "</acntTp>"
				+ "<acntNo>" + bankCardNo + "</acntNo>"
				+ "<mchntCd>" + mchntCd + "</mchntCd>"
				+ "<isCallback>" + isCallback + "</isCallback>"
        		+ "<reserved1>" + reserved1 + "</reserved1>"
        		+ "<signature>" + signature + "</signature>"
        		+ "</custmrBusi>";
		
		System.out.println(xml);
		
		
		//https://fht.fuiou.com/api_contract5.do   生产
		//String path="https://fht-test.fuiou.com/fuMer/api_contract5.do";
		String path="https://fht.fuiou.com/api_contract5.do";
		String param="xml="+xml;
	    URL url = new URL(path.trim());
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod("POST");// 提交模式
	    httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	    httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	    httpURLConnection.setDoOutput(true);
	    httpURLConnection.setDoInput(true);
	    OutputStream os = httpURLConnection.getOutputStream();    
	    os.write(param.getBytes());
	    if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
	        
	        //开始获取数据
	        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        int len;
	        byte[] arr = new byte[1024];
	        while((len=bis.read(arr))!= -1){
	            bos.write(arr,0,len);
	            bos.flush();
	        }
	        bos.close();
	        System.out.println(bos.toString());
	        
	        Map<String,String> map=new HashMap<String,String>();
			InputSource in = new InputSource(new StringReader(bos.toString()));
			in.setEncoding("UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Iterator<Element> it = elements.iterator();it.hasNext();){
			   Element element = it.next();
			   map.put(element.getName(),element.getTextTrim());
			} 
			
	}
	
	//银行卡签约第二步
	@Test
	public void currentTimeasdadMisssllis() throws ZhimaApiException, IOException, DocumentException{
		   String bankCardNo ="6217973610006488319";
        String mchntCd ="0003720F0395576";
        String verifyCode = "559729";
    
        ArrayList<String> list=new ArrayList<String>();
		list.add(bankCardNo);
		list.add(mchntCd);
		list.add(verifyCode);
		
		String[] strs = new String[list.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = list.get(i);
		}
		Arrays.sort(strs);//集合自动排序
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		System.out.println(bigstr);
		String signature = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+"iyqw910fydjn3is3w2k8b22ej5dohacg");
		
	
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<custmrBusi>"
				+ "<acntNo>" + bankCardNo + "</acntNo>"
				+ "<mchntCd>" + mchntCd + "</mchntCd>"
				+ "<verifyCode>" + verifyCode + "</verifyCode>"
				+ "<signature>" + signature + "</signature>"
        		+ "</custmrBusi>";
		
		
		
		
		//生产   https://fht.fuiou.com/api_verifyMsg.do
		//String path="https://fht-test.fuiou.com/fuMer/api_verifyMsg.do";
		String path="https://fht.fuiou.com/api_verifyMsg.do";
		String param="xml="+xml;
	    URL url = new URL(path.trim());
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod("POST");// 提交模式
	    httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	    httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	    httpURLConnection.setDoOutput(true);
	    httpURLConnection.setDoInput(true);
	    OutputStream os = httpURLConnection.getOutputStream();    
	    os.write(param.getBytes());
	    if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
	        
	        //开始获取数据
	        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        int len;
	        byte[] arr = new byte[1024];
	        while((len=bis.read(arr))!= -1){
	            bos.write(arr,0,len);
	            bos.flush();
	        }
	        bos.close();
	        System.out.println(bos.toString());
	        
	        Map<String,String> map=new HashMap<String,String>();
			InputSource in = new InputSource(new StringReader(bos.toString()));
			in.setEncoding("UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Iterator<Element> it = elements.iterator();it.hasNext();){
			   Element element = it.next();
			   map.put(element.getName(),element.getTextTrim());
			} 
			
	}
	

	//付款和收款
	@Test
	public void fukuan() throws Exception{
		String path="https://fht.fuiou.com/req.do";
		String merid="0003720F0395576";
		String reqtype="sincomeforreq";//收款
		//String reqtype="payforreq";//收款
		//
		
		//备注，借款下放，还款，扣除手续费
		//https://.../req.do?merid=...&reqtype=...&xml=...&mac=...
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
	     		+ "<incomeforreq>"
	     		//+ "<payforreq>"
	     		+ "<ver>2.00</ver>"
	     		+ "<merdt>"+DateUtil.getDateYMD()+"</merdt>"
	     		+ "<orderno>"+System.currentTimeMillis()+"911</orderno>"
	     		+ "<bankno>"+"0403"+"</bankno>"
	     		+ "<cityno>"+"3610"+"</cityno>"//付款
	     		+ "<accntno>"+"6217973610006488319"+"</accntno>"
	     		+ "<accntnm>"+"吴涛"+"</accntnm>"
	     		+ "<amt>"+"100"+"</amt>"
	     		+ "<entseq>test</entseq>"
	     		+ "<memo>备注</memo>"
	     		+ "<mobile>"+"18555116208"+"</mobile>"
	     		+ "<certtp>0</certtp>"
	     		+ "<certno>"+"34262219900129295X"+"</certno>"
	     		+"<txncd>"+"06"+"</txncd>"//9.5 付款业务定义说明01 借款下发   02 逾期还款03 债权转让04 其他
	     		//9.6 代收业务定义说明06 贷款还款07 逾期还款08 债权转让09 其他
	     		+"<projectid>"+"0003720F0395576_20170603_0001"+"</projectid>"
	     		+ "</incomeforreq>";
	     		//+ "</payforreq>";
		//String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+"payforreq"+"|"+xml;//付款
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+"sincomeforreq"+"|"+xml;//付款
		String mac=Encrypt.MD5(macSource).toUpperCase();
		String param="merid="+merid+"&reqtype="+reqtype+"&xml="+xml+"&mac="+mac;
	    URL url = new URL(path.trim());
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod("POST");// 提交模式
	    httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
	    httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
	    httpURLConnection.setDoOutput(true);
	    httpURLConnection.setDoInput(true);
	    OutputStream os = httpURLConnection.getOutputStream();    
	    os.write(param.getBytes());
	    if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
	        
	        //开始获取数据
	        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        int len;
	        byte[] arr = new byte[1024];
	        while((len=bis.read(arr))!= -1){
	            bos.write(arr,0,len);
	            bos.flush();
	        }
	        bos.close();
	        System.out.println(bos.toString());
	        Map<String,String> map=new HashMap<String,String>();
			InputSource in = new InputSource(new StringReader(bos.toString()));
			in.setEncoding("UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Iterator<Element> it = elements.iterator();it.hasNext();){
			   Element element = it.next();
			   map.put(element.getName(),element.getTextTrim());
			} 
			
	        
	}

	
	@Test
	public void ssdsdf(){
		String result = SMSUtil.sendContent("18555116208", "你的本次借款：借款金额为10元，借款日期10，借款天数10天，打款银行卡号为10，请注意查收");
	}

}
