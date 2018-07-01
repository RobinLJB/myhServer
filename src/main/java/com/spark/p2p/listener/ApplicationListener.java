package com.spark.p2p.listener;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ContextLoaderListener;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.config.Config;
import com.spark.p2p.event.AppSettingEvent;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.ApplicationUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;

/**
 * 应用启动初始化
 * @author yanqizheng
 *
 */
@WebListener
public class ApplicationListener extends ContextLoaderListener implements
ServletContextListener,ServletRequestListener {
	private static Log log = LogFactory.getLog(ApplicationListener.class);
	
	/**
	 * 服务器启动时加载,初始化一些信息
	 */
	public void contextInitialized(ServletContextEvent sce){
		log.info("initialize application ...");
		//初始化应用设置
		try {
			initAppConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化系统配置
	 * @throws Exception
	 */
	private void initAppConfig() throws Exception{
		SiteService service = ApplicationUtil.getBean(SiteService.class);
		Map<String,String> seoMap = service.queryGroupItem("SEO");
		AppSetting.SEO_TITLE = seoMap.get("SEO_TITLE");
		AppSetting.SEO_KEY = seoMap.get("SEO_KEY");
		AppSetting.SEO_DESC = seoMap.get("SEO_DESC");
		//短信配置
		Map<String,String> smsMap = service.queryGroupItem("SMS");
		AppSetting.SMS_GATEWAY = smsMap.get("SMS_GATEWAY");
		System.out.println(AppSetting.SMS_GATEWAY+"oooooooooooooo");
		AppSetting.SMS_USERNAME = smsMap.get("SMS_USERNAME");
		AppSetting.SMS_PASSWD = smsMap.get("SMS_PASSWORD");
		AppSetting.SMS_SIGN = smsMap.get("SMS_SIGN");
		//邮件系统配置
		Map<String,String> emailMap = service.queryGroupItem("EMAIL");
		AppSetting.MAIL_HOST = emailMap.get("EMAIL_GATEWAY");
		AppSetting.MAIL_USERNAME = emailMap.get("EMAIL_USERNAME");
		AppSetting.MAIL_PASSWORD = emailMap.get("EMAIL_PASSWORD");
		//设置系统密钥
		Config config = ApplicationUtil.getBean(Config.class);
		AppSetting.MD5_KEY = config.getMd5Key();
		AppSetting.DES_KEY = config.getDesKey();
		AppSetting.DEBUG_MODE = config.isDebugMode();
		AppSetting.HOST_NAME = config.getHost();
		AppSetting.SITE_HOST = config.getHost();
	}
	
	
	public void requestInitialized(ServletRequestEvent sre) {
		//String host = sre.getServletRequest().getServerName();
		ServletRequest request = sre.getServletRequest();
		request.setAttribute("SEO_TITLE", AppSetting.SEO_TITLE);
		request.setAttribute("SEO_KEY", AppSetting.SEO_KEY);
		request.setAttribute("SEO_DESC", AppSetting.SEO_DESC);
		request.setAttribute("SITE_HOST", AppSetting.SITE_HOST);
	}

	
	public void requestDestroyed(ServletRequestEvent arg0) {
		
	}

 
 
 
}
