package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class Apple {
	@Field("id")
	private long id;
	
	@Field("apple_id")
	private String appleId;
	
	@Field("apple_pass")
	private String applePass;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppleId() {
		return appleId;
	}

	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}

	public String getApplePass() {
		return applePass;
	}

	public void setApplePass(String applePass) {
		this.applePass = applePass;
	}
	
	
}
