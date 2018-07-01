package com.spark.p2p.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.util.MessageResult;

/**
 * 前台会员中心拦截器
 * @author yanqizheng
 *
 */

public class MemberInterceptor implements HandlerInterceptor{
	public static Log log = LogFactory.getLog(MemberInterceptor.class);
	private List<String> excludedUrls;
	
	public void setExcludedUrls(ArrayList<String> list){
		excludedUrls = list;
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		log.info("ucenterInterceptor:preHandle,excludedUrls:"+excludedUrls);
		
		HttpSession session = request.getSession();
		int index = request.getContextPath().length();
		String uri = request.getRequestURI().substring(index);
		//公共请求不拦截
		if(isPublic(uri)){
			return true;
		}
		Member user = (Member) session.getAttribute(Const.SESSION_MEMBER);
		if (user != null) {
			return true;
		}
		if (isAjaxRequest(request)) {
			response.setContentType("text/html");
			MessageResult mr = new MessageResult(401,"unauthorized");
			response.getWriter().print(mr.toString());
		}
		else{
		   uri = java.net.URLEncoder.encode(request.getContextPath()+uri,"utf-8");
		   response.sendRedirect(request.getContextPath()+"/mobile/login.html?redirect="+uri);
		}
		return false;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
	
	private boolean isPublic(String path){
		return excludedUrls.contains(path);
	}
}
