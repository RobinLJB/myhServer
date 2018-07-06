package com.spark.p2p.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.spark.p2p.config.AppSetting;

/**
 * 阿里云 oss 存贮 Util
 * 
 * @author robinluo
 *
 */
public class AliyunOssUtil {

	/**
	 * 文件流上传
	 * 
	 * @param key
	 * @param is
	 * @return 返回文件上传结果
	 */
	public static boolean put(String key, InputStream is) {
		OSS ossClient = new OSSClient(AppSetting.ALIYUN_OSS_END_POINT, AppSetting.ACCESS_KEY_ID,
				AppSetting.ACCESS_KEY_SECRET);
		PutObjectRequest putObjectRequest = new PutObjectRequest(AppSetting.ALIYUN_BUCKET_NAME, key, is);
		ossClient.putObject(putObjectRequest);
		return true;
	}
	
	/**
	 * 二进制上传
	 * @param key
	 * @param data
	 * @return
	 */
	public static boolean put(String key, byte[] data) {
		
		OSS ossClient = new OSSClient(AppSetting.ALIYUN_OSS_END_POINT, AppSetting.ACCESS_KEY_ID,
				AppSetting.ACCESS_KEY_SECRET);
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		PutObjectRequest putObjectRequest = new PutObjectRequest(AppSetting.ALIYUN_BUCKET_NAME, key, is);
		ossClient.putObject(putObjectRequest);
		return true;
	}

	/**
	 * 返回oss文件访问路径url
	 * 
	 * @param key
	 * @return
	 */
	public static String downloadFile(String key) {
		return AppSetting.ALIYUN_BUCKET_URL + key;
	}

}
