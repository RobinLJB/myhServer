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
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.shujumohe.HttpUtils;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.model.Model;

@Service
public class DaiQianShenHeService extends BaseService {
	
	public static final Log log = LogFactory.getLog(DaiQianShenHeService.class);
	
	@Autowired
	private MemberService memberService;

	static Map<String, String> headers = null;
	// 定义接口返回码
	public static Integer code;
	// 返回数据
	String data = null;
	// 请求body
	public static String body;
	// 生成reportId
	public static String reportId;

	// 定义共同参数
	public static final String commonUrl = "https://apitest.tongdun.cn";// 贷前审核所有操作前相同的url

	public static final String partner_code = "niuwaqd";
	public static final String partner_key = "dfb904941a3f4bf99182fb5df90d9d2f";
	public static final String app_name="";//app应用名称,需写入
	public static String queryParam = "?partner_code=" + partner_code + "&partner_key=" + partner_key;

	// submit接口，获取reportId
	public static String createReportIdAddr = commonUrl + "/preloan/v5";
	// query接口，获取相关信息
	public static String queryReportAddr = commonUrl + "/preloan/v6";
	


	// 调用同盾个人贷前审核服务的submit接口，获取审核报告编号
	public MessageResult doSubmit(String name, String cardNumber,String mobile, long memberId) throws Exception {
		log.info("#############审核服务的submit接口###############");
		// 接口body内容
		body = "name=" + name + "&id_number=" + cardNumber + "mobile"+mobile;
		String queryBeforeLoanParam=queryParam+"app_name"+"";//此处app_name还未给参数
		// 发送init阶段请求
		String queryResult = HttpUtils.executeHttpPost(createReportIdAddr, queryBeforeLoanParam, headers, body);
		// 获取init阶段返回码
		//code = JSON.parseObject(queryResult).getInteger("code");
		String success=JSON.parseObject(queryResult).getString("success");
		String reportId=JSON.parseObject(queryResult).getString("report_id");
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", success);
		map.put("reportId",reportId);
		return MessageResult.success(map);
	}
	
	// 调用同盾个人贷前审核服务的query接口，获取报告结果
		public MessageResult doQuery(String reportId, long memberId) throws Exception {
			log.info("#############审核服务的query接口###############");
			// 接口body内容
			String BeforeLoanParam=queryParam+"app_name"+""+"report_id"+reportId;//此处app_name还未给参数
			// 发送init阶段请求
			String queryResult = HttpUtils.executeHttpPost(queryReportAddr, queryParam, headers, body);
			// 获取init阶段返回码
			
			String success=JSON.parseObject(queryResult).getString("success");
			
			if(success.equals(true)){
			/*	return MessageResult.success();
				try {
					Model m = new Model("member");
					
					}
				catch (Exception e) {
					// TODO: handle exception
				}*/
				return null;
			}
			else{
				Map<String, String> map = new HashMap<String, String>();
				String code2 = JSON.parseObject(queryResult).getString("reason_code");
				String reasonDesc=JSON.parseObject(queryResult).getString("reason_desc");
				map.put("success", success);
				map.put("reason_code",code2);
				map.put("reason_desc",reasonDesc);
				return MessageResult.success(map);
			}
			
		}
	/**
	 * @param 获取魔盒报告并保存到数据库
	 * @throws Exception
	 * @throws @Description:
	 */
	public long getData(long memberId, String taskId) throws Exception {
		String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 此段代码是为了获取手机通话记录并保存到数据库中去
		/*lastQueryResult = HttpUtils.executeHttpPost(queryTaskaddr, queryParam, headers, "task_id=" + taskId);
		// 获取返回code
		code = JSON.parseObject(lastQueryResult).getInteger("code");
		data = JSON.parseObject(JSON.parseObject(lastQueryResult).getString("data")).getString("task_data");
		log.info("结果集" + data);
		com.alibaba.fastjson.JSONObject object = JSON.parseObject(data);
		com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("call_info");

		if (code == 0) {
			Model m = new Model("mobile_talk_detail");
			for (Object object2 : jsonArray) {
				log.info(object2);
				Map<String, Object> map = (Map) object2;
				// log.info(map.get("total_call_count"));
				// log.info(map.get("call_record"));
				com.alibaba.fastjson.JSONArray jsonArray2 = (com.alibaba.fastjson.JSONArray) map.get("call_record");// 获取call_record通话详单里的数据
				for (Object object3 : jsonArray2) {
					// log.info(object3);
					Map<String, Object> map2 = (Map) object3;
					// log.info(map2.get("call_cost"));
					m.set("member_id", memberId);
					m.set("callAddress", map2.get("call_address"));
					log.info(map2.get("call_address"));
					m.set("callDateTime", map2.get("call_start_time"));
					m.set("callTimeLength", map2.get("call_time"));
					m.set("callType", map2.get("call_type_name"));
					m.set("mobileNo", map2.get("call_other_number"));
					m.set("createTime", curTime);
					try {
						long ret = m.insert();
						log.info("插入数据");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// Map<K, V> map2=(Map)(map.get("call_record"));
			}
		}
*/
		
		return 0;
	}
}
