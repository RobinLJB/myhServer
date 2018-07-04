package com.spark.p2p.controller.app;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xml.sax.InputSource;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.util.AppSessionUtil;
import com.spark.p2p.util.TokenObject;
import com.sparkframework.security.DES;
import com.sparkframework.sql.model.Model;

/**
 * APP控制器基类
 * @author yanqizheng
 *
 */ 
public class AppBaseController extends BaseController{
	
	public static final Log log = LogFactory.getLog(AppBaseController.class);
	
	public TokenObject getTokenObject(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		log.info("token=" + AppSessionUtil.getToken(request("token")));
		String token = request("token");
		//return (TokenObject)((Object)token);
		Object obj = request.getAttribute("token");
		log.info("obj"+obj);
		return (TokenObject)request.getAttribute("token");
	}
	
	public Member getMember(){
		
		TokenObject token = getTokenObject();
		Member member =  (Member)token.get("member");
		if(token != null){
			try {
				member = new Model("member").where("id=?", member.getId()).find(Member.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return member;
	}
	
	/**
	 * 获取加密字段信息
	 * @return
	 * @throws  
	 */
	public JSONObject decryptRequest(){
		String raw = request("data");
		try {
			String data = DES.decrypt(raw, AppSetting.DES_KEY);
			return new JSONObject(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//接口的post提交
		public Map<String,String> httpclientPost(String path,String param ) throws Exception{
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
			        log.info(bos.toString());
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
				return map;
		}
}
