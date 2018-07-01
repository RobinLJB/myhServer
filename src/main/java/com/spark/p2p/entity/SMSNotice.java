package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class SMSNotice {
	private long id;
	private int uid;
	@Field("sms_content")
	private String content;
	@Field("sms_status")
	private int status;
	@Field("add_time")
	private String addTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
}
