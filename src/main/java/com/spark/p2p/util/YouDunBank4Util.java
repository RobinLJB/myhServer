package com.spark.p2p.util;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.spark.p2p.controller.app.AppMemberController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class YouDunBank4Util {

	private static Log log = LogFactory.getLog(AppMemberController.class);
    // 银行卡四要素接口 format
    private static String fformatStr = "/dsp-front/4.1/dsp-front/default/pubkey/%s/" +
            "product_code/%s/out_order_id/%s/signature/%s";


    public static String apiCall(String url,String pubkey,String secretkey,String
            serviceCode, String outOrderId,Map<String, String> parameter) throws Exception{
    	log.info("======有盾银行卡四要素start=====");
        if (parameter == null || parameter.isEmpty())
            throw new Exception("error ! the parameter Map can't be null.");

        StringBuffer bodySb = new StringBuffer("{");
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
            bodySb.append("'").append(entry.getKey()).append("':'").append(entry.getValue()).append("',");
        }
        String bodyStr = bodySb.substring(0, bodySb.length() - 1) + "}";
        String signature = md5(bodyStr + "|" + secretkey);
        url += String.format(fformatStr, pubkey, serviceCode, System.currentTimeMillis(), signature);
        log.info("requestUrl=>" + url);
        log.info("request parameter body=>" + bodyStr);
        HttpResponse r = makePostRequest(url, bodyStr);
        log.info("======有盾银行卡四要素end=====");
        return EntityUtils.toString(r.getEntity());
    }
    private static final CloseableHttpClient client = HttpClientBuilder.create().build();
    private static HttpResponse makePostRequest(String uri, String jsonData)
            throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(URIUtil.encodeQuery(uri, "utf-8"));
        httpPost.setHeader("Connection","close");
        httpPost.setEntity(new StringEntity(jsonData, "UTF-8"));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        return client.execute(httpPost);
    }
    private static String md5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.toString().getBytes("utf-8"));
        return bytesToHex(md.digest());
    }
    private static String bytesToHex(byte[] ch) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; i < ch.length; i++)
            ret.append(byteToHex(ch[i]));
        return ret.toString();
    }

    /**
     *字节转换为16进制字符串
     */
    private static String byteToHex(byte ch) {
        String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
        return str[ch >> 4 & 0xF] + str[ch & 0xF];
    }
    public static void main(String[] args) {
        testDemo3();
    }
    public static void testDemo3(){
        try {
            Map<String,String> body = new HashMap<>();
            body.put("id_no", "410381199412227837");
            body.put("id_name","刘严基");
            body.put("bank_card_no","6212261001046423965");
            body.put("mobile","17721447525");
            body.put("req_type","01");
            String result = apiCall("https://api4.udcredit.com","a2f6da6e-a4e4-4026-895d-d70955f1f542",
                    "dcf03232-4fb2-46db-b4e5-96c5f978b1c4", "O1001S0401", String.valueOf(System.currentTimeMillis()), body);
            System.err.println(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
    
    
}