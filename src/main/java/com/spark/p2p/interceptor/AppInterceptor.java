package com.spark.p2p.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spark.p2p.config.Config;
import com.spark.p2p.util.AppSessionUtil;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.TokenObject;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;

public class AppInterceptor implements HandlerInterceptor {
	public static Log log = LogFactory.getLog(AppInterceptor.class);
	private List<String> excludedUrls;
	public void setExcludedUrls(ArrayList<String> list) {
		excludedUrls = list;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info(request.getRequestURL());
		int index = request.getContextPath().length();
		String url = request.getRequestURI().substring(index);///app/uc/login.do
		//验证签名，保证是客户端发出的请求
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		long timestampL = Convert.strToLong(timestamp, 0);
		if(StringUtils.isEmpty(signature) || timestampL == 0){
			ajaxReturn(response,4000,"parameter error");
			return false;
		}
		//保证请求时效性,超过10分钟失效，注意前后端保证为一个时区
		long nowTick = new Date().getTime();
		if(nowTick - timestampL > 120*60000){
			//ajaxReturn(response,5001,"timestamp expired");
			//return false;
		}
		//Config cfg = ApplicationUtil.getBean(Config.class);
	//	if(!checkSignature(url,cfg.getApiKey(),timestamp,signature)){
		//	ajaxReturn(response,5000,"signature error");
		//	return false;
	//	}
		//需要登录的验证TOKEN,公共请求不拦截
		if (isPublic(url)) {
			return true;
		}
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token)){
			token=request.getParameter("token");
		}
		if (StringUtils.isEmpty(token)) {
			ajaxReturn(response, 4101, "token required");
			return false;
		}
		log.info("access_token:" + token);
		TokenObject tokenObj = AppSessionUtil.getToken(token);
		if (tokenObj == null) {
			ajaxReturn(response,4102,"token invalid");
			return false;
		}
		
		/*
		if (tokenObj.isExpired()) {
			ajaxReturn(response,4103,"token expired,please login");
			return false;
		}*/
		log.info(tokenObj);
		request.setAttribute("token", tokenObj);
		return true;
	}

	private boolean checkSignature(String url,String key,String timestamp,String signature){
		//return Encrypt.MD5(url + key + timestamp).equals(signature);
		return true;
	}
	
	public void ajaxReturn(HttpServletResponse response, int code, String msg) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("message", msg);
		out.print(json.toString());
		out.flush();
		out.close();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private boolean isPublic(String path) {
		return excludedUrls.contains(path);
	}
}
