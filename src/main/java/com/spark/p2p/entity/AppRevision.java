package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class AppRevision {
	@Field("id")
	private int build;
	private int platform;
	private String revision;
	@Field("update_remark")
	private String remark;
	@Field("download_url")
	private String url;
	@Field("publish_time")
	private String publishTime;
	public int getBuild() {
		return build;
	}
	public void setBuild(int build) {
		this.build = build;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
}
