package com.spark.p2p.entity;

/**
 * 个人详细资料
 * 
 * @author yanqizheng
 *
 */
public class PersonProfile {
	// 真实姓名
	private String realname;
	// 身份号
	private String cardNo;
	// 性别
	private String sex;
	// 出生日期
	private String birthday;
	// 年收入
	private String income;
	// 是否婚配
	private String marry;
	// 教育程度
	private String education;
	// 工作城市
	private String workCity;
	// 工作城市
	private String province;
	// 工作城市
	private String city;
	// 有无社保
	private String socialSecurity;
	// 从事行业
	private String industry;
	// 工作岗位
	private String jobs;
	// 是否有房
	private String hasHourse;
	// 是否有车
	private String hasCar;
	
	
	
	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	

	public String getIndustry() {
		return industry;
	}
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getHasHourse() {
		return hasHourse;
	}

	public void setHasHourse(String hasHourse) {
		this.hasHourse = hasHourse;
	}

	public String getHasCar() {
		return hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	
}
