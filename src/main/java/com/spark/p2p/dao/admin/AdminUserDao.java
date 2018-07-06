package com.spark.p2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.spark.p2p.entity.Admin;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Repository
public class AdminUserDao {
	/**
	 * 
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public Admin login(String username,String password) throws Exception{
		Model m = new Model("miaoyihua.admin");
		Admin admin = null;
		Map<String,String> map = m.where("username = ? and password = ?",username,password).find();
		if(map != null){
			admin = new Admin();
			admin.setId(Convert.strToLong(map.get("id"), -1));
			admin.setUserName(Convert.strToStr(map.get("username"), ""));
			admin.setEnable(Convert.strToInt(map.get("enable"), 0));
			admin.setRoleId(Convert.strToLong(map.get("roleId"), -1));
		}
		return admin;
	}
	
	public Map<String,String> findUser(long id) throws Exception{
		return new Model("admin").find(id);
	}
	
	public long deleteUser(long id){
		return new Model("admin").delete(id);
	}
	
	public long updateUser(long id,String username,int enable,int departmentId,long roleId,String realName,String mobilePhone,String remark) throws SQLException{
		Model m = new Model("admin");
		m.set("username", username);
		m.set("realName", realName);
		m.set("departmentId", departmentId);
		m.set("roleId", roleId);
		m.set("enable", enable);
		m.set("mobilePhone", mobilePhone);
		m.set("summary", remark);
		return m.update(id);
	}
	
	public long updateUserPassword(long id,String password) throws SQLException{
		Model m = new Model("admin");
		m.set("password", password);
		return m.update(id);
	}
	
	public long addUser(String username,String password,int enable,int departmentId,long roleId,String realName,String mobilePhone,String remark) throws SQLException{
		Model m = new Model("admin");
		m.set("username", username);
		m.set("realName", realName);
		m.set("departmentId", departmentId);
		m.set("roleId", roleId);
		m.set("enable", enable);
		m.set("mobilePhone", mobilePhone);
		m.set("password", password);
		m.set("summary", remark);
		return m.insert();
	}
	
	public long updateLoginLog(long id,String ip,String time) throws SQLException{
		Model m = new Model("admin");
		m.set("last_login_ip", ip);
		m.set("last_login_time", time);
		return m.update(id);
	}
	
	public String findUserPassword(long id) throws Exception {
		Model m = new Model("admin");
		Map<String,String> map = m.where("id = ?",id).field("password").find();
		return map == null?"":map.get("password");
	}
}
