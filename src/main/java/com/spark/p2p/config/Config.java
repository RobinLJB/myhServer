package com.spark.p2p.config;

public class Config {
	private String md5Key;
	private String desKey;
	private boolean debugMode;
	private String host;
	private String apiKey = "Tuac9Cl4RQFT3S8ijAfjxHATHk4BZabc";
	private String androidUrl;
	private String iosUrl;
	private String wapDomain;
	private String sftpHost;
	private int sftpPort;
	private String sftpUsername;
	private String sftpPassword;
	private String sftpPrivateKey;
	
	public String getMd5Key() {
		return md5Key;
	}
	
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getDesKey() {
		return desKey;
	}
	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}
	
	public boolean isDebugMode() {
		return debugMode;
	}
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAndroidUrl() {
		return androidUrl;
	}
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	public String getIosUrl() {
		return iosUrl;
	}
	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}

	public String getWapDomain() {
		return wapDomain;
	}

	public void setWapDomain(String wapDomain) {
		this.wapDomain = wapDomain;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

	public String getSftpUsername() {
		return sftpUsername;
	}

	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpPrivateKey() {
		return sftpPrivateKey;
	}

	public void setSftpPrivateKey(String sftpPrivateKey) {
		this.sftpPrivateKey = sftpPrivateKey;
	}
}
