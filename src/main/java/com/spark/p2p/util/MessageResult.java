package com.spark.p2p.util;

import java.util.Map;

public class MessageResult {
	private int code;
	private String message;
	private Object data;
	public MessageResult(int code , String msg){
		this.code = code;
		this.message = msg;
	}
	public MessageResult(int code , String msg,Object object){
		this.code = code;
		this.message = msg;
		this.data = object;
	}
	
	public MessageResult() {
		
	}
	
	public static MessageResult success(){
		return new MessageResult(0,"");
	}
	
	public static MessageResult success(String msg){
		return new MessageResult(0,msg);
	}
	public static MessageResult success(Object object){
		return new MessageResult(0,"",object);
	}	
	public static MessageResult error(int code,String msg){
		return new MessageResult(code,msg);
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String toString(){
		return "{\"code\":"+code+",\"message\":\""+message+"\"}";
	}
}
