package com.spark.p2p.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.spark.p2p.core.RequestHolder;
import com.sparkframework.security.Encrypt;

/**
 * 表单token生成与验证
 * @author yanqizheng
 *
 */
public class FormUtil {
	
	public static final Log log = LogFactory.getLog(FormUtil.class);
	
	public static final String TOKEN_NAME_PRIFIX = "FORM_TOKEN_";
	
	/**
	 * 生成表单TOKEN
	 * @param formId 表单ID
	 * @return
	 */
	public static String createToken(String formId){
		HttpServletRequest request = RequestHolder.getRequest();
		String token = String.valueOf(System.currentTimeMillis()) + String.valueOf(GeneratorUtil.getRandomNumber(1, 1000));
		token = Encrypt.MD5(token).toUpperCase();
		log.info("generate token:"+token);
		request.getSession().setAttribute(TOKEN_NAME_PRIFIX+formId, token);
		return token;
	}
	
	/**
	 * 验证表单token
	 * @param formId 表单ID
	 * @return
	 */
	public static boolean validateToken(String formId){
		HttpServletRequest request = RequestHolder.getRequest();
		String token = (String)request.getSession().getAttribute(TOKEN_NAME_PRIFIX+formId);
		String tokenSubmit = request.getParameter(TOKEN_NAME_PRIFIX+formId);
		boolean ret = token.equals(tokenSubmit);
		//验证成功后重新生成token
		if(ret)createToken(formId);
		return ret;
	}
}
