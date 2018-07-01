package com.spark.p2p.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.spark.p2p.entity.GatewayLog;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.sql.model.Model;

@Service
public class MerchantService {
	
	public long addGatewayRequest(String orderId,long uid,String bizType,String request) throws SQLException{
		Model m = new Model("merchant_fundgw_log");
		m.set("merchant_bill_no", orderId);
		m.set("uid", uid);
		m.set("biz_type", bizType);
		m.set("req_time", DateUtil.getDateTime());
		m.set("request", request);
		return m.insert();
	}
	
	public GatewayLog findGatewayLog(String orderId) throws Exception{
		return new Model("merchant_fundgw_log")
				.where("merchant_bill_no = ?",orderId)
				.find(GatewayLog.class);
	}
	
	public long updateGatewayLog(String orderId,String outBizNo,String response) throws SQLException{
		return new Model("merchant_fundgw_log")
				.where("merchant_bill_no = ?",orderId)
				.set("out_biz_no", outBizNo)
				.set("response", response)
				.update();
	}
}
