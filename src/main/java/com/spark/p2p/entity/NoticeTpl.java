package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class NoticeTpl {
	private String group;
	private String name;
	private String title;
	@Field("site_tpl")
	private String siteTpl;
	@Field("sms_tpl")
	private String smsTpl;
	@Field("email_tpl")
	private String emailTpl;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSiteTpl() {
		return siteTpl;
	}
	public void setSiteTpl(String siteTpl) {
		this.siteTpl = siteTpl;
	}
	public String getSmsTpl() {
		return smsTpl;
	}
	public void setSmsTpl(String smsTpl) {
		this.smsTpl = smsTpl;
	}
	public String getEmailTpl() {
		return emailTpl;
	}
	public void setEmailTpl(String emailTpl) {
		this.emailTpl = emailTpl;
	}
}
