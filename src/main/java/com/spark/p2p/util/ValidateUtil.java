package com.spark.p2p.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 常用验证工具类
 * @author yanqizheng
 *
 */
public class ValidateUtil {
	
	public static final Log log = LogFactory.getLog(ValidateUtil.class);
	/**
	 * 验证手机号格式是否正确
	 * @param phone
	 * @return
	 */
	public static boolean isMobilePhone(String phone){
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		Pattern p = Pattern.compile("^(1[34578])\\d{9}$");
		Matcher m = p.matcher(phone);
		if(!m.matches()){
			return false;
		}
		return true;
	}
	/**
	 * 验证身份证号码格式
	 * @param s_aStr
	 * @return
	 */
	public static boolean isCard(String idNo){ 
		Pattern p2 = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
		log.info(p2.matcher(idNo).matches());
		return p2.matcher(idNo).matches();
	}
	
	public static boolean isnull(String str){ 
		if(str==null){
			return true;
		}
		if(str=="" || str.length()==0){
			return true;
		}
		return false;
	}
}
