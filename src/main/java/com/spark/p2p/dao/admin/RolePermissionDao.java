package com.spark.p2p.dao.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.spark.p2p.entity.Menu;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;
import com.sparkframework.sql.model.Model;

@Repository
public class RolePermissionDao {
	public List<Map<String,String>> queryRulesByRole(long roleId) throws SQLException, DataException{
		return DB.query("select * from v_role_permission where roleId = ?", roleId);
	}
	
	public List<Map<String,String>> queryRulesByRole(long roleId,int type) throws SQLException, DataException{
		return DB.query("select * from v_role_permission where roleId = ? and type = ?", roleId,type);
	}
	
	public List<Map<String,String>> queryAllRules() throws SQLException, DataException{
		return DB.query("select * from admin_auth_rule  order by sort asc");	
	}
	
	public List<Menu> queryRules() throws Exception{
		return new Model("admin_auth_rule")
				.order("sort asc")
				.select(Menu.class);
	}
	
	public List<Map<String,String>> queryAllRules(int status) throws SQLException, DataException{
		return DB.query("select * from admin_auth_rule where `status` = ? order by sort asc",status);	
	}
	
	public List<Map<String,String>> queryAllRules(int status,int type) throws SQLException, DataException{
		return DB.query("select * from admin_auth_rule where `status` = ? and type = ? order by sort asc",status,type);	
	}
	
	public List<Map<String,String>> queryAllRole() throws Exception{
		return new Model("admin_role").order("id asc").select();
	}
	
	public long deleteRoleById(long id){
		return new Model("admin_role").delete(id);
	}
	
	public Map<String,String> findRoleById(long id) throws Exception{
		return new Model("admin_role").where("id = ?",id).find();
	}
	
	/**
	 * 更新角色
	 * @param id
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException 
	 */
	public long updateRole(long id,String name,String remark) throws SQLException{
		Model m = new Model("admin_role");
		m.set("name", name);
		m.set("remark", remark);
		return m.update(id);
	}
	
	/**
	 * 添加角色
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException 
	 */
	public long addRole(String name,String remark) throws SQLException{
		Model m = new Model("admin_role");
		m.set("name", name);
		m.set("remark", remark);
		return m.insert();
	}
	
	/**
	 * 增加角色权限
	 * @param roleId
	 * @param ruleId
	 * @return
	 * @throws SQLException 
	 */
	public long addRoleRule(long roleId,long ruleId) throws SQLException{
		Model m = new Model("admin_role_permission");
		m.set("roleId", roleId);
		m.set("ruleId", ruleId);
		return m.insert();
	}
	
	/**
	 * 删除角色权限
	 * @param roleId
	 * @param ruleId
	 * @return
	 */
	public long deleteRoleRule(long roleId,long ruleId){
		return new Model("admin_role_permission").where("roleId = ? and ruleId = ?",roleId,ruleId).delete();
	}
	
	/**
	 * 添加菜单权限
	 * @param pid
	 * @param title
	 * @param name
	 * @param type
	 * @param sort
	 * @param url
	 * @param icon
	 * @return
	 * @throws SQLException 
	 */
	public long addRule(long pid,String title,String name,String url,String icon,int type,int sort,int status) throws SQLException{
		Model model = new Model("admin_auth_rule");
		model.set("title", title);
		model.set("url", url);
		model.set("name", name);
		model.set("parentId", pid);
		model.set("sort", sort);
		model.set("type", type);
		model.set("status", status);
		model.set("icon", icon);
		return model.insert();
	}
	
	
	public long updateRule(long id,long pid,String title,String name,String url,String icon,int type,int sort,int status) throws SQLException{
		Model model = new Model("admin_auth_rule");
		model.set("title", title);
		model.set("url", url);
		model.set("name", name);
		model.set("parentId", pid);
		model.set("sort", sort);
		model.set("type", type);
		model.set("status", status);
		model.set("icon", icon);
		return model.where("id = ?",id).update();
	}
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @return
	 */
	public long deleteRule(long ruleId){
		return new Model("admin_auth_rule").where("id = ?",ruleId).delete();
	}
	
	public long deleteSubRules(long parentId){
		return new Model("admin_auth_rule").where("parentId = ?",parentId).delete();
	}
}