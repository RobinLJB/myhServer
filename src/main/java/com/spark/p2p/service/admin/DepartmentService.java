package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.dao.admin.DepartmentDao;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;

@Service
public class DepartmentService extends BaseService{
	
	@Autowired
	private  DepartmentDao departmentDao;
	/**
	 * 读取所有部门
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryAllDepartment(DataTableRequest params) throws SQLException{
		return page("admin_department","*","","",params.getStart(),params.getLength());
	}

	/**
	 * 通过id查询部门
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> findDepartment(int id) throws Exception {
		return departmentDao.findDepartmentById(id);
	}
	/**
	 * 删除部门
	 * @return
	 */
	@Transactional
	public long deleteDepartmentById(int id) {
		long ret = departmentDao.deleteDepartmentById(id);
		return ret;
	}
	/**
	 * 添加部门
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public long addDepartment(String name,String remark,String leader) throws Exception {
		return departmentDao.addDepartment(name, remark,leader);
	}
	
	/**
	 * 更新部门
	 * @param id
	 * @param name
	 * @param remark
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public long updateDepartmentById(int id, String name, String remark,String leader) throws Exception {
		return departmentDao.updateDepartmentById(id, name, remark,leader);
	}

	public  List<Map<String,String>>  queryAllDepartment() {
		try {
			return departmentDao.queryAllDepartment();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	/**
	 * 获取部门人员列表，或导出excel
	 * @param id
	 * @return
	 */
	public DataTable queryUserBydepartmentId(DataTableRequest params,int id) throws SQLException{
		return page("v_admin_user","*","  departmentId="+id,"",params.getStart(),params.getLength());
	}
}
