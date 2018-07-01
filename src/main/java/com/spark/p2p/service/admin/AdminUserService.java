package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.dao.admin.AdminUserDao;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.sql.model.Model;

@Service
public class AdminUserService extends BaseService {
	@Autowired
	private AdminUserDao userDao;

	public DataTable queryUser(DataTableRequest params) throws SQLException {
		return page("v_admin_user", "*", "", "", params.getStart(), params.getLength());
	}

	public Map<String, String> findUser(long id) throws Exception {
		return userDao.findUser(id);
	}

	public long deleteUser(long id) {
		return userDao.deleteUser(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public long updateUser(long id, String username, int enable, int departmentId, long roleId, String realName,
			String mobilePhone, String remark) throws SQLException {
		return userDao.updateUser(id, username, enable, departmentId, roleId, realName, mobilePhone, remark);
	}

	@Transactional(rollbackFor = Exception.class)
	public long addUser(String username, String password, int enable, int departmentId, long roleId, String realName,
			String mobilePhone, String remark) throws SQLException {
		return userDao.addUser(username, password, enable, departmentId, roleId, realName, mobilePhone, remark);
	}

	@Transactional(rollbackFor = Exception.class)
	public long updateUserPassword(long id, String password) throws SQLException {
		return userDao.updateUserPassword(id, password);
	}

	public boolean checkUserPassword(long id, String password) throws Exception {
		String pwd = userDao.findUserPassword(id);
		return pwd.equals(password);
	}
	
	public List<Map<String, String>> queryAdmin(int roleid) throws Exception {
		return new Model("admin").where("roleId=? ",roleid).select();
	}
	
	public Map<String, String> findAdminById(int id) throws Exception {
		return new Model("admin").where("id=?",id).find();
	}
}
