package com.spark.p2p.constant;

import com.spark.p2p.annotation.SiteSetting;

/**
 * 系统常量
 * @author yanqizheng
 *
 */
public class AppConstant {
	public static final String MAIL_HOST = "smtp.exmail.qq.com";
	public static final String MAIL_USERNAME = "kefu@hongchuangjr.com";
	public static final String MAIL_PASSWORD = "Hcjr1234";
	public static boolean IS_DEBUG = true;
	public static String SESSION_USER = "user";
	
	public static String SESSION_ADMIN = "admin_user";
	
	public static String MD5_KEY;
	
	public static String DES_KEY;
	
	public static String HOST_NAME;
	public static String WAP_HOST_NAME;
	
	/** SMS CONFIG **/
	@SiteSetting(key="SMS_USERNAME")
	public static String SMS_USERNAME = "cdg";
	
	@SiteSetting(key="SMS_PASSWORD")
	public static String SMS_PASSWD = "B9BE536523DC8BF4289F86B3F03C";
	
	@SiteSetting(key="SMS_GATEWAY")
	public static String SMS_URL = "http://web.cr6868.com/asmx/smsservice.aspx?";
	
	@SiteSetting(key="SMS_SIGN")
	public static String SMS_SIGN = "福币";
	
	public void setMD5_KEY(String mD5_KEY) {
		MD5_KEY = mD5_KEY;
	}
	
	public void setDES_KEY(String dES_KEY) {
		DES_KEY = dES_KEY;
	}
	
	/** SEO **/
	@SiteSetting(key="SEO_TITLE")
	public static String SEO_TITLE;
	
	@SiteSetting(key="SEO_KEY")
	public static String SEO_KEY;
	
	@SiteSetting(key="SEO_DESC")
	public static String SEO_DESC;
}
