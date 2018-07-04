package com.spark.p2p.controller.zmxy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.WebUtils;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

@Controller
public class ZmController extends BaseController {
	
	public static final Log log = LogFactory.getLog(ZmController.class);
	
	@Autowired
	private MemberService memberService;
	@RequestMapping("/zmxy/notify")
	public String pageNotify(HttpServletRequest request) throws Exception{
		int code=0;
		String sign = request("sign");
    	String params = request("params");
    	if(params.indexOf("%") != -1) {
            params = URLDecoder.decode(params, "utf-8");
        }
        if(sign.indexOf("%") != -1) {
            sign = URLDecoder.decode(sign, "utf-8");
        }
    	DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId, ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
    	try {
            String result = client.decryptAndVerifySign(params, sign);
            Map<String,String> map = WebUtils.splitUrlQuery(result);
            String openid = map.get("open_id");
            long uid = Convert.strToLong(map.get("state").split("%")[0], 0);
            log.info(map);
            log.info(map.get("open_id"));
            MessageResult mr = fetchZhimaCreditScore(openid);
            if(mr.getCode()==0){
            	
            int socre=Convert.strToInt(mr.getData().toString(), 0);
            Map<String,String> tmap =new HashMap<String,String>();
            tmap.put("openid",openid);
            tmap.put("uid",uid+"");
            if(mr.getCode()==0){
            	mr.setCode(0);
            	tmap.put("socre",mr.getData().toString());
            	tmap.put("msg","success");
            }else{
            	mr.setCode(500);
            	tmap.put("socre","0");
            	tmap.put("msg",mr.getMessage());
            }
            code=mr.getCode();
            mr.setData(tmap);
            if(mr.getCode()==0){
    			
    			
    			if(uid>0){
    				long ret=memberService.updateZhimaStatus(uid,openid,socre);
    				
    			}
    		}else{
    			
    		}
           }
            request.setAttribute("result", mr);
            session.setAttribute("uid", uid);
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
		
    	return "redirect:/notifys.html";
		
	}
	
	
	public MessageResult fetchZhimaCreditScore(String openid){
		ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        //String openid = "268808826806723969655125080";
        DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sss=YYYYMMDDMMHHSSSSS.format(new Date());
		sss=sss+System.currentTimeMillis();
        req.setTransactionId(sss);// 必要参数 
        req.setProductCode("w1010100100000000001");// 必要参数 
        req.setOpenId(openid);// 必要参数 
        DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId, ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
        try {
            ZhimaCreditScoreGetResponse response = client.execute(req);
            log.info(response.isSuccess());
            log.info(response.getErrorCode());
            log.info(response.getErrorMessage());
            log.info(response.getZmScore());
            if(response.isSuccess()){
            	session.setAttribute("flag", 1);
            	String score = response.getZmScore();
            	return success("SUCCESS",score);
            }
            else{
            	session.setAttribute("flag", 0);
            	return error(500,response.getErrorMessage());
            }
           
        } catch (ZhimaApiException e) {
            e.printStackTrace();
            return error(505,e.getErrMsg());
        }
       
	}
}
