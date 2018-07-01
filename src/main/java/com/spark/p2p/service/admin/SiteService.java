package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.p2p.entity.AppRevision;
import com.spark.p2p.event.AppSettingEvent;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;
import com.sparkframework.util.SqlInfusion;

@Service
public class SiteService extends BaseService {
	@Autowired
	private AppSettingEvent kvEvent;

	public DataTable queryOption(DataTableRequest params) throws SQLException {
		DataTableRequest.DataColumn column = params.columns.get("group_key");
		String where = "";
		if (column != null && StringUtils.isNotBlank(column.getSearchValue())) {
			where = "group_key = '" + SqlInfusion.FilterSqlInfusion(column.getSearchValue()) + "'";
		}
		return page("sys_site_setting", "*", where, "", params.getStart(), params.getLength());
	}

	
	/**
	 * 读取分组内的设置
	 * 
	 * @param group
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryOptionByGroup(String group, DataTableRequest params) throws SQLException {
		return page("sys_site_setting", "*", "group_key = ?", "", params.getStart(), params.getLength(), group);
	}

	public long addSelectOption(String group,String key,String name,int sort) throws SQLException{
		return new Model("sys_select_option")
				.set("group", group)
				.set("name", name)
				.set("key",key)
				.set("sort", sort)
				.insert();
	}
	
	public long deleteSelectOption(long id){
		return new Model("sys_select_option")	
				.delete(id);
	}
	
	/**
	 * 获取select键值
	 * @param group
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public DataTable querySelectOptionByGroup(String group, DataTableRequest params) throws SQLException {
		return page("sys_select_option", "*", "`group` = ?", "", params.getStart(), params.getLength(), group);
	}
	
	public DataTable queryAppRevision(DataTableRequest params) throws SQLException {
		return page("sys_app_revision", "*", "", "id desc", params.getStart(), params.getLength());
	}

	
	/**
	 * 读取所有组
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryGroup() throws Exception {
		return new Model("sys_site_setting").field("group_name,group_key").group("group_key asc").select();
	}

	/**
	 * 更新键值
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public long updateKV(String key, String value) throws Exception {
		long ret = new Model("sys_site_setting").where("`key` = ?", key).setField("value", value);
		if (ret > 0) {
			kvEvent.onChange(key, value);
		}
		return ret;
	}

	/**
	 * 获取一组设置
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryGroupItem(String group) throws Exception {
		List<Map<String, String>> list = new Model("sys_site_setting").field("`key`,`value`")
				.where("`group_key` = ?", group).select();
		Map<String, String> kvMap = new HashMap<String, String>();
		for (Map<String, String> map : list) {
			kvMap.put(map.get("key"), map.get("value"));
		}
		return kvMap;
	}

	public Map<String, String> findItem(String key) throws Exception {
		return new Model("sys_site_setting").where("`key` = ?", key).find();
	}

	public double findItemAsDouble(String key) throws Exception {
		Map<String, String> map = findItem(key);
		if (map != null) {
			return Convert.strToDouble(map.get("value"), -1);
		}
		return -1;
	}

	/**
	 * 收费项目列表
	 * 
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public DataTable platformCostList(DataTableRequest params) throws SQLException {
		Map<String, String> condiMap = new HashMap<String, String>();
		return pageEnableSearch(params, "constant_variable", "*", condiMap, "");
	}

	public Map<String, String> findPlatformCost(Integer id) throws Exception {
		return new Model("constant_variable").find(id);
	}
	
	public Map<String, String> findAreaByID(Integer id) throws Exception {
		return new Model("db_nation").find(id);
	}

	// 获取merchant_config的detail字段
	public String findPlatformCostDetail(Integer id) throws Exception {
		Model m = new Model("merchant_config");
		return m.field("detail").find(id).get("detail");
	}

	public long platformCostUpdate(int id, String review, String value)
			throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("constant_variable");
		m.set("value", value);
		m.set("review", review);
		m.set("updateTime", sdf.format(now));
		return m.update(id);
	}

	
	/**
	 * 添加APP版本记录
	 * 
	 * @param platform
	 * @param revision
	 * @param url
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public long addAppRevision(int platform, String revision, String url, String remark) throws SQLException {
		Model m = new Model("sys_app_revision");
		m.set("platform", platform);
		m.set("revision", revision);
		m.set("update_remark", remark);
		m.set("download_url", url);
		m.set("publish_time", DateUtil.getDateTime());
		return m.insert();
	}
	
	/**
	 * 查询APP最新build记录
	 * @param platform
	 * @return
	 * @throws Exception
	 */
	public AppRevision findLateastRevision(int platform) throws Exception{
		Model m = new Model("sys_app_revision");
		return m.where("platform = ?",platform)
				.order("id desc")
				.find(AppRevision.class);
	}
	
	/**
	 * 查询省份
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryProvince() throws Exception{
		return new Model("db_nation_2")
		.field("id,code,name")
		.where("parent = 1")
		.order("id asc")
		.select();
	}
	
	/**
	 * 查询城市
	 * @param provinceId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryCity(long provinceId) throws Exception{
		return new Model("db_nation_2")
				.field("id,code,name")
				.order("id asc")
				.where("parent = ? or (id = ? and autonomous = 1)",provinceId,provinceId)
				.select();
	}


	public DataTable areaSetList(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("parent", "=");
		map.put("islimit", "=");
		params.addColumn("parent", "1");	
		map.put("province", "like");
		return pageEnableSearch(params, "db_nation", "*", map, "id desc");
	}


	public long areaSetUpdate(Integer id, int strToInt) throws SQLException {
		Model m = new Model("db_nation");
		m.set("islimit", strToInt);
		return m.update(id);
	}


	
}
