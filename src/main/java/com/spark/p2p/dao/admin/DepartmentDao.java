package com.spark.p2p.dao.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sparkframework.sql.DataException;
import com.sparkframework.sql.model.Model;

@Repository
public class DepartmentDao {
	/**
	 * 通过id查找部门
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> findDepartmentById(int id) throws Exception {
		return new Model("admin_department").where("id = ?",id).find();
	}
	
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	public long deleteDepartmentById(int id) {
		return new Model("admin_department").delete(id);
	}
	
	
	/**
	 * 添加部门
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException 
	 */
	public long addDepartment(String name, String remark,String leader) throws SQLException {
		Model m = new Model("admin_department");
		m.set("name", name);
		m.set("remark", remark);
		m.set("leader",leader);
		return m.insert();
	}

	/**
	 * 更新部门
	 * @param id
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException 
	 */
	public long updateDepartmentById(int id, String name, String remark,String leader) throws SQLException {
		Model m = new Model("admin_department");
		m.set("name", name);
		m.set("remark", remark);
		m.set("leader",leader);
		return m.update(id);
	}


	public List<Map<String, String>> queryAllDepartment() throws Exception{
		return new Model("admin_department").order("id asc").select();
	}

}