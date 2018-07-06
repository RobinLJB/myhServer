package com.spark.p2p.config;

import java.util.List;
import java.util.Map;

import com.spark.p2p.annotation.SiteSetting;

/**
 * 系统常量
 * @author yanqizheng
 *
 */
public class AppSetting {
	@SiteSetting(key="EMAIL_GATEWAY")
	public static  String MAIL_HOST;
	@SiteSetting(key="EMAIL_USERNAME")
	public static  String MAIL_USERNAME;
	@SiteSetting(key="EMAIL_PASSWORD")
	public static  String MAIL_PASSWORD;
	public static boolean DEBUG_MODE = true;
	
	public static String MD5_KEY;
	/**网址**/
	public static String SITE_URL;
	public static String DES_KEY;
	
	public static String HOST_NAME;
	public static String WAP_HOST_NAME;
	
	/** SMS CONFIG **/
	@SiteSetting(key="SMS_USERNAME")
	public static String SMS_USERNAME;
	
	@SiteSetting(key="SMS_PASSWORD")
	public static String SMS_PASSWD;
	
	@SiteSetting(key="SMS_GATEWAY")
	public static String SMS_GATEWAY;
	
	@SiteSetting(key="SMS_SIGN")
	public static String SMS_SIGN;
	
	/** SEO **/
	@SiteSetting(key="SEO_TITLE")
	public static String SEO_TITLE;
	
	@SiteSetting(key="SEO_KEY")
	public static String SEO_KEY;
	
	@SiteSetting(key="SEO_DESC")
	public static String SEO_DESC;
	public static String SITE_HOST;
	
	public static String PREFIX = "GDR";
	
	public static String WEIXIN_APPID = "wxd2b26a838e51fae9";
	public static String WEIXIN_KEY = "438f100f01f65176ab4900578aa7a094";

	public static String IGNORE_PHONE_LIST[] = 
		        {"18609658355", "15156062323", "17602131013", "13027163405", "18375312099",
				"18656003579", "13164606327","18655553618","13399558122"};

	//阿里云的Access Key （LTAIWdbI3LdtOk2q是秒易花的）
	public static String ACCESS_KEY_ID = "LTAIWdbI3LdtOk2q";
	public static String ACCESS_KEY_SECRET = "DsMR1173YgGwpbX4ugRCuamspFJeN4";
	
	//阿里云oss存储 endPoint
	public static String ALIYUN_OSS_END_POINT = "oss-cn-beijing.aliyuncs.com";
	public static String ALIYUN_BUCKET_NAME = "image-miaoyihua";
	public static String ALIYUN_BUCKET_URL = "https://image-miaoyihua.oss.beijing.aliyuncs.com/";
	
	//同盾数据魔盒partner_code和partner_key(已更新成秒一花)
	public static final String partner_code = "miaoyihua_mohe";
	public static final String partner_key = "0e2df09a563444ca937c0e1bbb6ce1e0";
	public static final String BOX_TOKEN="85786D0E92B147B9B088414FDC6B3EBB";
	
	public static String APP_NAME = "秒易花";
	//实名认证（联卓）（4a4d752321e844a0949fdd84e1c7384e是秒易花的）
    public static String LianZhuoAppCode="4a4d752321e844a0949fdd84e1c7384e";

    //可以借款的手机型号列表
    public static String ALLOW_IPHONE_LIST[]={"iPhone6","iPhoneSE","iPhone6Plus","iPhone6s","iPhone6sPlus",
			               "iPhone7","iPhone7Plus","iPhone8","iPhone8Plus","iPhoneX"};
	public static int ALLOW_AMOUNT_LIST[]={800,1000,1200,1600,2000,
			               2200,2400,2600,2800,3000,3200,3500,3800};
	//服务器ip
	public  static final String HOST_IP="http://39.105.50.129:8080";
}
