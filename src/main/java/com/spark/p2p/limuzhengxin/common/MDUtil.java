/**
 * @Author: zy
 * @Company: 
 * @Create Time: 2016�?8�?13�? 下午3:13:19
 */
package com.spark.p2p.limuzhengxin.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Project: trust-common
 * @Author zy
 * @Company:
 * @Create Time: 2016�?8�?13�? 下午3:13:19
 */
public class MDUtil {

	public static String SHA1(String decript) {
		String ret = "";
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes("UTF-8"));
			byte messageDigest[] = digest.digest();
			ret = StringUtils.bytesToHexString(messageDigest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}