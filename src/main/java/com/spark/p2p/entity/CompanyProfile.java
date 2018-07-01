package com.spark.p2p.entity;

/**
 * 企业详细资料
 * 
 * @author yanqizheng
 *
 */
public class CompanyProfile {
	// 公司名称
	private String company_name;
	// 注册资金
	private String registered_fund;
	//公司地址
	private String address;
	//营业执照号
	private String license_no;
	//营业执照地址
	private String license_address;
	//营业执照过期日
	private String license_expire_date;
	//营业范围
	private String business_scope;
	//联系电话
	private String telephone;
	//联系邮箱
	private String email;
	//组织机构代码
	private String organization_no;
	//企业简介
	private String summary;
	//企业法人
	private String legal_person;
	//法人身份证号
	private String cert_no;
	//法人手机号
	private String legal_person_phone;
	//开户银行
	private String bank_code;
	//开户省份
	private String province;
	//开户城市
	private String city;
	//开户支行名称
	private String bank_branch;
	//银行卡号
	private String bank_account_no;
	
	
	
	
	//公司营业执照
	private String assetyyzz;
	//组织机构代码证
	private String assetzzjgz;
	//税务登记证
	private String assetswdjz;
	//银行开户许可证
	private String assetjsxkz;
	//法人身份证正面
	private String assetfrzjz;
	//法人身份证反面
	private String assetfrzjf;
	
	private String card_type = "DEBIT";
	private String card_attribute = "B";
	
	
	
	public String getRegistered_fund() {
		return registered_fund;
	}
	public void setRegistered_fund(String registered_fund) {
		this.registered_fund = registered_fund;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLicense_no() {
		return license_no;
	}
	public void setLicense_no(String license_no) {
		this.license_no = license_no;
	}
	public String getLicense_address() {
		return license_address;
	}
	public void setLicense_address(String license_address) {
		this.license_address = license_address;
	}
	public String getLicense_expire_date() {
		return license_expire_date;
	}
	public void setLicense_expire_date(String license_expire_date) {
		this.license_expire_date = license_expire_date;
	}
	public String getBusiness_scope() {
		return business_scope;
	}
	public void setBusiness_scope(String business_scope) {
		this.business_scope = business_scope;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrganization_no() {
		return organization_no;
	}
	public void setOrganization_no(String organization_no) {
		this.organization_no = organization_no;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLegal_person() {
		return legal_person;
	}
	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getLegal_person_phone() {
		return legal_person_phone;
	}
	public void setLegal_person_phone(String legal_person_phone) {
		this.legal_person_phone = legal_person_phone;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_account_no() {
		return bank_account_no;
	}
	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCard_attribute() {
		return card_attribute;
	}
	public void setCard_attribute(String card_attribute) {
		this.card_attribute = card_attribute;
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
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}
	public String getAssetyyzz() {
		return assetyyzz;
	}
	public void setAssetyyzz(String assetyyzz) {
		this.assetyyzz = assetyyzz;
	}
	public String getAssetzzjgz() {
		return assetzzjgz;
	}
	public void setAssetzzjgz(String assetzzjgz) {
		this.assetzzjgz = assetzzjgz;
	}
	public String getAssetswdjz() {
		return assetswdjz;
	}
	public void setAssetswdjz(String assetswdjz) {
		this.assetswdjz = assetswdjz;
	}
	public String getAssetjsxkz() {
		return assetjsxkz;
	}
	public void setAssetjsxkz(String assetjsxkz) {
		this.assetjsxkz = assetjsxkz;
	}
	public String getAssetfrzjz() {
		return assetfrzjz;
	}
	public void setAssetfrzjz(String assetfrzjz) {
		this.assetfrzjz = assetfrzjz;
	}
	public String getAssetfrzjf() {
		return assetfrzjf;
	}
	public void setAssetfrzjf(String assetfrzjf) {
		this.assetfrzjf = assetfrzjf;
	}
}
