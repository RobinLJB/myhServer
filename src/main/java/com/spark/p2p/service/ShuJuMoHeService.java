package com.spark.p2p.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.PublicSuffixDomainFilter;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.shujumohe.HttpUtils;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.sql.model.Model;

import freemarker.cache.MruCacheStorage;

@Service
public class ShuJuMoHeService extends BaseService {
	
	public static final Log log = LogFactory.getLog(ShuJuMoHeService.class);
	
	@Autowired
	private MemberService memberService;

	static Map<String, String> headers = null;
	// 定义接口返回码
	public static Integer code;
	// 返回数据
	String data = null;
	// 最终结果
	public static String lastQueryResult = null;

	public String callReportReslut = null;
	// 请求body
	public static String body;
	// 初始化任务阶段
	public static String stage = "INIT";

	public static String taskStage = null;
	// 图片验证码
	String authCode;
	// 任务id
	public static String taskId;
	// 验证码存放路径
	String path = "/var/tmp";

	public static final String callBackUrl = "";// 回调地址

	// 定义共同参数
	public static final String commonUrl = "https://api.shujumohe.com/octopus";// 数据魔盒所有操作前相同的url

	public static String queryParam = "?partner_code=" + AppSetting.partner_code + "&partner_key="
			+ AppSetting.partner_key;

	// 创建任务的url
	public static String createTaskAddr = commonUrl + "/task.unify.create/v3";
	// 登录爬取地址的url
	public String crawlAddr = commonUrl + "/yys.unify.acquire/v3";
	// 查询任务结果的url
	public String queryTaskaddr = commonUrl + "/task.unify.query/v3";
	// 生成魔盒数据报告的url
	public String reportTaskaddr = commonUrl + "/report.task.query/v2";

	public static String getTaskId(String basicInfo) {
		// 发送创建任务请求
		String queryResult = HttpUtils.executeHttpPost(createTaskAddr, queryParam, headers, basicInfo);
		// 获取任务id
		return taskId = JSON.parseObject(queryResult).getString("task_id");
	}

	// 首次将手机号和服务密码提交
	public MessageResult doAuth(String taskId, String accountPwd, long memberId) throws Exception {
		log.info("#############手机登录首次输入参数###############");
		// 接口body内容
		body = accountPwd + "&task_id=" + taskId + "&task_stage=" + stage + "&request_type=submit";
		// 发送init阶段请求
		String queryResult = HttpUtils.executeHttpPost(crawlAddr, queryParam, headers, body);
		// 获取init阶段返回码
		code = JSON.parseObject(queryResult).getInteger("code");
		String message = JSON.parseObject(queryResult).getString("message");
		taskId = JSON.parseObject(queryResult).getString("task_id");
		Map<String, String> map = new HashMap<>();
		MessageResult mr = new MessageResult();
		map.put("taskId", taskId);
		mr.setCode(code);
		mr.setData(map);
		mr.setMessage(message);
		return mr;
	}

	// 轮询（只有在code==100时进行轮询）
	public MessageResult doQuery(String taskId, String accountPwd, long memberId) {
		// 接口body内容
		body = accountPwd + "&task_id=" + taskId + "&task_stage=" + stage + "&&request_type=query";
		// 发送请求
		String queryResult = HttpUtils.executeHttpPost(crawlAddr, queryParam, headers,body);
		// 获取返回code

		code = JSON.parseObject(queryResult).getInteger("code");
		String data = JSON.parseObject(queryResult).getString("data");
		log.info(data);
		if (!ValidateUtil.isnull(data)) {
			log.info(JSON.parseObject(data).get("next_stage"));
			taskStage = (String) JSON.parseObject(data).get("next_stage");
		}
		String message = JSON.parseObject(queryResult).getString("message");

		Map<String, String> map = new HashMap<>();
		MessageResult mr = new MessageResult();
		map.put("taskId", taskId);
		map.put("taskStage", taskStage);
		mr.setCode(code);
		mr.setData(map);
		mr.setMessage(message);
		return mr;
	}

	public MessageResult checkCode(String taskId, long memberId, String smsCode, String taskStage) throws Exception {
		MessageResult mr = new MessageResult();
		if (smsCode != null && smsCode.length() != 0 && !"".equals(smsCode)) {
			body = "task_id=" + taskId + "&task_stage=" + taskStage + "&sms_code=" + smsCode + "&request_type=submit";
			// 发送请求
			String queryResult = HttpUtils.executeHttpPost(crawlAddr, queryParam, headers, body);

			// 获取返回code
			int code = JSON.parseObject(queryResult).getInteger("code");
			String data = JSON.parseObject(queryResult).getString("data");
			log.info(data);
			if (!ValidateUtil.isnull(data)) {
				log.info(JSON.parseObject(data).get("next_stage"));
				taskStage = (String) JSON.parseObject(data).get("next_stage");
			}
			String message = JSON.parseObject(queryResult).getString("message");
			Map<String, String> map = new HashMap<>();
			map.put("taskId", taskId);
			map.put("taskStage", taskStage);
			mr.setCode(code);
			mr.setData(map);
			mr.setMessage(message);
			return mr;
		} else {
			mr.setCode(-500);
			mr.setMessage("手机密码为空，请重新输入");
			return mr;
		}
	}

	// 查询提交完短信验证码后的结果
	public MessageResult queryCode(String taskId, long memberId, String smsCode, String taskStage) throws Exception {
		MessageResult mr = new MessageResult();
		if (smsCode != null && smsCode.length() != 0 && !"".equals(smsCode)) {
			body = "task_id=" + taskId + "&task_stage=" + taskStage + "&sms_code=" + smsCode + "&request_type=query";
			// 发送请求
			String queryResult = HttpUtils.executeHttpPost(crawlAddr, queryParam, headers, body);

			// 获取返回code
			int code = JSON.parseObject(queryResult).getInteger("code");
			String data = JSON.parseObject(queryResult).getString("data");
			log.info(data);
			if (!ValidateUtil.isnull(data)) {
				log.info(JSON.parseObject(data).get("next_stage"));
				taskStage = (String) JSON.parseObject(data).get("next_stage");
			}
			String message = JSON.parseObject(queryResult).getString("message");
			Map<String, String> map = new HashMap<>();
			map.put("taskId", taskId);
			map.put("taskStage", taskStage);
			mr.setCode(code);
			mr.setData(map);
			mr.setMessage(message);
			return mr;
		} else {
			mr.setCode(-500);
			mr.setMessage("手机密码为空，请重新输入");
			return mr;
		}
	}

	

	/**
	 * @paramc查询报告结果，只有在返回值为0的时候说明认证成功
	 * @throws Exception
	 * @throws @Description:
	 */
	public MessageResult queryData(long memberId, String taskId) throws Exception {
		lastQueryResult = HttpUtils.executeHttpPost(queryTaskaddr, queryParam, headers, "task_id=" + taskId);
		Integer queryCode = JSON.parseObject(lastQueryResult).getInteger("code");
		String message=JSON.parseObject(lastQueryResult).getString("message");
		MessageResult mr=new MessageResult();
		mr.setCode(queryCode);
		mr.setMessage(message);
		if(queryCode==0) {
			updatePhoneStatus(memberId);
			saveTaskId(memberId,taskId);
			//getData(memberId, taskId);
			mr.setMessage("认证成功");
		}
		return mr;
	}

	/**
	 * @param 获取魔盒报告并保存到数据库
	 * @throws Exception
	 * @throws @Description:
	 */
	public long getData(long memberId, String taskId) throws Exception {

		// 此段代码是为了获取手机通话记录并保存到数据库中去，暂时不用
		/*
		 * lastQueryResult = HttpUtils.executeHttpPost(queryTaskaddr, queryParam,
		 * headers, "task_id=" + taskId); // 获取返回code code =
		 * JSON.parseObject(lastQueryResult).getInteger("code"); data =
		 * JSON.parseObject(JSON.parseObject(lastQueryResult).getString("data")).
		 * getString("task_data"); log.info("结果集" + data);
		 * com.alibaba.fastjson.JSONObject object = JSON.parseObject(data);
		 * com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("call_info");
		 * 
		 * if (code == 0) { Model m = new Model("mobile_talk_detail"); for (Object
		 * object2 : jsonArray) { log.info(object2); Map<String, Object> map =
		 * (Map) object2; // log.info(map.get("total_call_count")); //
		 * log.info(map.get("call_record")); com.alibaba.fastjson.JSONArray
		 * jsonArray2 = (com.alibaba.fastjson.JSONArray) map.get("call_record");//
		 * 获取call_record通话详单里的数据 for (Object object3 : jsonArray2) { //
		 * log.info(object3); Map<String, Object> map2 = (Map) object3; //
		 * log.info(map2.get("call_cost")); m.set("member_id", memberId);
		 * m.set("callAddress", map2.get("call_address"));
		 * log.info(map2.get("call_address")); m.set("callDateTime",
		 * map2.get("call_start_time")); m.set("callTimeLength", map2.get("call_time"));
		 * m.set("callType", map2.get("call_type_name")); m.set("mobileNo",
		 * map2.get("call_other_number")); m.set("createTime", curTime); try { long ret
		 * = m.insert(); log.info("插入数据"); } catch (SQLException e) {
		 * e.printStackTrace(); } } // Map<K, V> map2=(Map)(map.get("call_record")); } }
		 */
		callReportReslut = HttpUtils.executeHttpPost(reportTaskaddr, queryParam, headers, "task_id=" + taskId);
		int code1 = JSON.parseObject(callReportReslut).getInteger("code");
		log.info("***********");
		// data = JSON.parseObject(callReportReslut).getString("data");
		if (code1 != 0 && code1 != 4001) {
			return -1;
		} else if (code1 == 4001) {
			Thread.sleep(15000);// 数据魔盒生成报告需要10秒左右
			callReportReslut = HttpUtils.executeHttpPost(reportTaskaddr, queryParam, headers, "task_id=" + taskId);// 等待10s后重新查询
			if (code1 == 0) {// 生成魔盒报告成功
				String data = JSON.parseObject(callReportReslut).getString("data");
				log.info(data);
				String callReport = gunzip(data);
				log.info(callReport);
				try {
					Model m = new Model("member");
					callReport = FileUtil.filterEmoji(callReport);
					m.set("callDetail", callReport);
					m.set("callStatus",1);
					return m.update(memberId);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} else if (code1 == 0) {
			String data = JSON.parseObject(callReportReslut).getString("data");
			data = JSON.parseObject(callReportReslut).getString("data");
			log.info(data);
			String callReport = gunzip(data);
			log.info(callReport);
			try {
				Model m = new Model("member");
				callReport = FileUtil.filterEmoji(callReport);
				m.set("callDetail", callReport);
				m.set("callStatus",1);
				return m.update(memberId);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return 0;

	}

	// 将魔盒报告得到的值解压缩成json格式
	@SuppressWarnings("restriction")
	public String gunzip(String compressedStr) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			// 对返回数据BASE64解码
			compressed = Base64.decodeBase64(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			// 解码后对数据gzip解压缩
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			// 最后对数据进行utf-8转码
			decompressed = out.toString("utf-8");
		} catch (IOException e) {
		}
		return decompressed;
	}
	// 生成魔盒报告之后更新数据库的手机认证状态
		public  void updatePhoneStatus(long memberId) {
			// 首先更新手机认证状态
			Model m = new Model("audit_chain");
			try {
				String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				m.set("phone_status", 1);
				m.set("phone_audit_time", curTime);
				if (m.where("member_id=?", memberId).find() != null) {
					log.info(memberId);
					m.where("member_id=?", memberId).update();
				} else {
					m.set("member_id", memberId);
					m.insert();
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		
		// 将taskId存入数据库中，后台可根据taskId生成魔盒报告
		public void saveTaskId(long memberId, String taskId) {
			// 首先更新手机认证状态
			Model m = new Model("member");
			try {
				m.set("taskId", taskId);
				if (m.where("id=?", memberId).find() != null) {
					log.info(memberId);
					m.where("id=?", memberId).update();
				} else {
					m.set("id", memberId);
					m.insert();
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
}