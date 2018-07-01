package com.spark.p2p.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3894615832097625678L;
	private Map<String,Object> objects;
	private Date expireTime;
	public TokenObject(){
		objects = new HashMap<String,Object>();
	}
	
	public Object get(String key){
		return objects.get(key);
	}
	
	public void set(String key,Object obj){
		objects.put(key, obj);
	}
	
	public void setExpireTime(Date time){
		expireTime = time;
	}
	
	public boolean isExpired(){
		return expireTime.getTime() <= new Date().getTime();
	}
}
