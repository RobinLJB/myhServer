package com.spark.p2p.service;

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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.p2p.limuzhengxin.common.HttpClient;
import com.spark.p2p.limuzhengxin.common.JsonUtils;
import com.spark.p2p.limuzhengxin.common.MDUtil;
import com.spark.p2p.limuzhengxin.common.StringUtils;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.model.Model;

@Service
public class LimuAuthService extends BaseService {

	public static final Log log = LogFactory.getLog(LimuAuthService.class);
	
    public static HttpClient httpClient = new HttpClient();

    //业务参数
    public static final String method = "api.mobile.get";//请求接口
    public static final String bizType = "mobile";//业务类型
    public static final String callBackUrl = "";//回调地址

    public static final String contentType = "all";//内容类型---需客户指定
    
    
    //定义共同参数
    
    public static final String apiUrl = "https://api.limuzhengxin.com/api/gateway";//"https://t.limuzhengxin.cn/api/gateway";//服务地址---需客户指定---需联系立木开启IP白名单
    //public static final String apiKey = "1283825495214605";						//"4201866855797822";//API授权 key ---需客户指定
    //public static final String apiSecret = "NMx40GohmCMusSqU72NQ4OSVy2HVmlkj";	//"TgwCUuWUxpLg9cT3C3QP69KO4uEUXooE";//API授权 secret---需客户指定
    public static final String apiKey = "8649071153002755";						//"4201866855797822";//API授权 key ---需客户指定
    public static final String apiSecret = "lVVRO9s8b3aeacHCOgQurGfpN1GdYwRe";	//"TgwCUuWUxpLg9cT3C3QP69KO4uEUXooE";//API授权 secret---需客户指定
    /*
    public static final String apiUrl = "https://t.limuzhengxin.cn/api/gateway";//服务地址---需客户指定---需联系立木开启IP白名单
    public static final String apiKey = "4201866855797822";//API授权 key ---需客户指定
    public static final String apiSecret = "TgwCUuWUxpLg9cT3C3QP69KO4uEUXooE";//API授权 secret---需客户指定
*/
    
    public final String version = "1.2.0";//API版本号
    public final long timeInterval = 5000;//轮训时间 默认5秒


    
    public MessageResult doAuth(String mobile, String password){
    	log.info("doAuth|start");

        String token = null;
        try {

            //提交受理请求对象
            List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();

            reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
            reqParam.add(new BasicNameValuePair("version", version));//调用的接口版本
            reqParam.add(new BasicNameValuePair("callBackUrl", callBackUrl));//callBackUrl
            reqParam.add(new BasicNameValuePair("method", method));//接口名称

            reqParam.add(new BasicNameValuePair("username", mobile));//账号
            reqParam.add(new BasicNameValuePair("password",  
            		new String(Base64.encodeBase64(password.getBytes("UTF-8")))));//密码
            
            reqParam.add(new BasicNameValuePair("contentType", contentType));//内容类型
            reqParam.add(new BasicNameValuePair("otherInfo", ""));//其他信息
            reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名

          //提交受理请求
            String json = httpClient.doPost(apiUrl, reqParam);
            log.info("json=" + json);
            if(StringUtils.isBlank(json)) {
                log.info("查询失败");
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readValue(json, JsonNode.class);
                String code = rootNode.get("code").textValue();
                String msg = rootNode.get("msg").textValue();

                if("0010".equals(code)) {//受理成功
                    token = rootNode.get("token").textValue();
                    //timer();//每5秒查询一次数据请求
                } else {
                    return MessageResult.error(Integer.valueOf(code), msg);
                }
            }
            
        }catch (Exception ex){
        	ex.printStackTrace();
        	return MessageResult.error(-2, "获取运营商信息异常");
        }
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", "受理成功, 准备获取运营商信息");
        map.put("m_token", token);

        return MessageResult.success( map );
    }
 
    /**
     * @param @param  token
     * @param @return
     * @throws Exception true 停止循环(发送输入信息失败或信息查询成功)，false:未取到结果集或成功取结果集
     * @throws
     * @Description:循环取的状态，查询结果
     */
    public MessageResult roundRobin(String _token, long memberId) throws Exception {

        //状态查询
        String json = httpClient.doPost(apiUrl, getReqParam(_token));
        JsonNode rootNode = JsonUtils.toJsonNode(json);
        String token = JsonUtils.getJsonValue(rootNode, "token");
        String code =JsonUtils.getJsonValue(rootNode, "code");
        String msg = JsonUtils.getJsonValue(rootNode, "msg");
        String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
        log.info(curTime + ", 循环取的信息:" + json);

        if (StringUtils.isBlank(code)) {
            //未取到结果集
            return MessageResult.error(2, "未获取到结果集");
        }

        //0开头标处理成功相关
        if (code.startsWith("0")) {
            if ("0100".startsWith(code)) {//登陆成功
                log.info("对象结果查询 >>>>>" + msg);
                getTalkDetails(token, memberId); // 登录成功后由后台获取通话详单
                return MessageResult.success("登录成功");
            } else if ("0006".equals(code)) {//等待输入信息
            	return MessageResult.error(1, "请提交收到的短信验证码");
            } else if ("0000".startsWith(code)) {//成功
                log.info("对象结果查询 >>>>>");
                //发送对象结果查询
                getData(token, memberId);
                return MessageResult.success(msg);
            }
            //其他情况继续轮训
            else {
                //return false;
            }
        }

        //其他异常停止循环
        return MessageResult.error(Integer.valueOf(code), msg);
    }

    private int loopTimes_ = 0;
    
    //后台获取通话详单
    private void getTalkDetails(String token, long memberId){
    	
    	//首先更新手机认证状态
    	Model m = new Model("audit_chain");
    	try {
            String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    		m.set("phone_status", 1);
    		m.set("phone_audit_time", curTime);
    		if(m.where("member_id=?", memberId).find() != null){
    			m.where("member_id=?", memberId).update();
    		}else{
    			m.set("member_id", memberId);
    			m.insert();
    		}
			
		} catch (Exception e1) { 
			e1.printStackTrace();
		}
    	
    	log.info("轮循 start");
        final Timer timer = new Timer();
        
        timer.schedule(new TimerTask() {

            public void run() {
            	loopTimes_ ++;
                try {
                    //循环取的状态，查询结果
                    //停止循环(发送短信失败或信息查询成功)
                    if (roundRobin(token, memberId).getCode() == 0) {
                        log.info("轮循 end，获取信息结束，token = " + token); 
                        timer.cancel();// 停止定时器
                    }
                    if(loopTimes_ >= 360){//超过30分钟则超时
                    	timer.cancel();
                    	log.info("轮循超时, token = " + token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //异常
                }
            }
        }, 0, timeInterval);
    }
    
    /**
     * 签名转化
     *
     * @param reqParam
     * @return
     */
    public String getSign(List<BasicNameValuePair> reqParam) {

        StringBuffer sbb = new StringBuffer();
        int index = 1;
        for (BasicNameValuePair nameValuePair : reqParam) {
            sbb.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
            if (reqParam.size() != index) {
                sbb.append("&");
            }
            index++;
        }
        String sign = sbb.toString();

        Map<String, String> paramMap = new HashMap<String, String>();
        String ret = "";
        if (!StringUtils.isEmpty(sign)) {
            String[] arr = sign.split("&");
            for (int i = 0; i < arr.length; i++) {
                String tmp = arr[i];
                if (-1 != tmp.indexOf("=")) {
                    paramMap.put(tmp.substring(0, tmp.indexOf("=")), tmp.substring(tmp.indexOf("=") + 1, tmp.length()));
                }

            }
        }
        List<Map.Entry<String, String>> list = new ArrayList<>(paramMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                int ret = 0;
                ret = o1.getKey().compareTo(o2.getKey());
                if (ret > 0) {
                    ret = 1;
                } else {
                    ret = -1;
                }
                return ret;
            }
        });

        StringBuilder sbTmp = new StringBuilder();
        for (Map.Entry<String, String> mapping : list) {
            if (!"sign".equals(mapping.getKey())) {
                sbTmp.append(mapping.getKey()).append("=").append(mapping.getValue()).append("&");
            }
        }
        sbTmp.setLength(sbTmp.lastIndexOf("&"));
        sbTmp.append(apiSecret);
        ret = MDUtil.SHA1(sbTmp.toString());

        //log.info(sb.toString());
        return ret;
    }

    /**
     * 获取共同提交参数
     *
     * @return
     */
    public List<BasicNameValuePair> getReqParam(String token) {
    	
        List<BasicNameValuePair> reqParam = new ArrayList<>();
        reqParam.add(new BasicNameValuePair("method", "api.common.getStatus"));//接口名称
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("version", version));
        reqParam.add(new BasicNameValuePair("token", token));
        reqParam.add(new BasicNameValuePair("bizType", getBizType()));
        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        return reqParam;
    }


    /**
     * @param @return
     * @throws Exception
     * @throws
     * @Description:信息输入
     */
    
    public String sendLimuMobileCode(String token, String mobileCode) throws Exception { 
        List<BasicNameValuePair> reqParam = new ArrayList<>();
        reqParam.add(new BasicNameValuePair("method", "api.common.input"));//接口名称
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("version", version));//调用的接口版本

        reqParam.add(new BasicNameValuePair("token", token));//请求标识
        reqParam.add(new BasicNameValuePair("input", mobileCode ));//短信验证码/图片验证码/独立密码

        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        String json = httpClient.doPost(apiUrl, reqParam);
        return json;
        //return JsonUtils.getJsonValue(json, "code");
    }


    /**
     * @param
     * @throws
     * @Description:查询的结果集(结果查询)
     */
    public void getData(String token, long memberId) {
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
        reqParam.add(new BasicNameValuePair("method", "api.common.getResult"));//接口名称	是	api.common.getResult
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("version", version));//调用的接口版本，可选值：1.0.0


        reqParam.add(new BasicNameValuePair("token", token));//请求标识
        reqParam.add(new BasicNameValuePair("bizType", getBizType()));//手机短信验证码

        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        String json = httpClient.doPost(apiUrl, reqParam);
        log.info("查询结果集:" + json);
        JSONObject obj = new JSONObject(json);
        if(!obj.optString("code").equals("0000")){
        	log.info("获取数据失败");
        	return;
        }
        JSONArray list = obj.optJSONObject("data").optJSONArray("callRecordInfo");
        String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
        //解析数据，保存到数据库中
        for(int i = 0; i < list.length(); i++){
        	Model m = new Model("mobile_talk_detail");
        	JSONObject js = list.getJSONObject(i);
        	//log.info(js);
            m.set("member_id", memberId);
            m.set("callAddress", js.opt("callAddress"));
            m.set("callDateTime", js.opt("callDateTime"));
            m.set("callTimeLength", js.opt("callTimeLength"));
            m.set("callType", js.opt("callType"));
            m.set("mobileNo", js.opt("mobileNo")); 
            m.set("createTime", curTime);
            try {
				m.insert();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * 获取业务类型
     *
     * @return
     */
    public String getBizType() {
    	return "mobile";
        //throw new RuntimeException("请重写该方法");
    }
}
