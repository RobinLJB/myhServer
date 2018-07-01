package com.spark.p2p.util;

import com.spark.p2p.config.AppSetting;
import com.sparkframework.security.DES;
import com.sparkframework.security.Encrypt;

public class SecurityUtil {
	public static String md5(String raw){
		return Encrypt.MD5(raw + AppSetting.MD5_KEY);
	}
	
	public static String encryptDES(String plainData){
		return DES.encrypt(plainData, AppSetting.DES_KEY);
	}
	
	public static String decryptDES(String secretData) throws Exception{
		return DES.decrypt(secretData, AppSetting.DES_KEY);
	}
}
