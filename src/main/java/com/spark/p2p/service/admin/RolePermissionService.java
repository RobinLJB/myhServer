package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.dao.admin.RolePermissionDao;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DataException;
import com.spark.p2p.entity.Menu;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;

@Service
public class RolePermissionService extends BaseService {
	@Autowired
	private RolePermissionDao permissionDao;

	/**
	 * 根据role_id 查询权限集合
	 * 
	 * @param role
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> queryByRoleId(long role) throws SQLException {
		List<Map<String, String>> permissions = null;
		try {
			// 超级管理员
			if (role == -1) {
				permissions = permissionDao.queryAllRules();
			} else {
				permissions = permissionDao.queryRulesByRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissions;
	}

	public List<Map<String, String>> queryAllRights() throws SQLException {
		List<Map<String, String>> permissions = null;
		try {
			permissions = permissionDao.queryAllRules();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissions;
	}

	public DataTable queryAllRights(DataTableRequest params) throws SQLException {
		Map<String, String> condiMap = new HashMap<String, String>();
		return pageEnableSearch(params, "admin_auth_rule", "*", condiMap, "");
	}

	/**
	 * 读取菜单列表
	 * 
	 * @param role
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> queryMenusByRoleId(long role) throws SQLException {
		List<Map<String, String>> permissions = null;
		try {
			// 超级管理员
			if (role == -1) {
				permissions = permissionDao.queryAllRules(1, 1);
			} else {
				permissions = permissionDao.queryRulesByRole(role, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissions;
	}

	/**
	 * 读取权限列表
	 * 
	 * @param role
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> queryRulesByRole(long roleId) throws SQLException {
		List<Map<String, String>> permissions = null;
		try {
			// 超级管理员
			if (roleId == -1) {
				permissions = permissionDao.queryAllRules(1);
			} else {
				permissions = permissionDao.queryRulesByRole(roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissions;
	}

	/**
	 * 根据权限集合获取用户菜单
	 * 
	 * @param permissions
	 * @return
	 */
	public List<Menu> getMenuTree(List<Map<String, String>> permissions, long parentId) {
		List<Menu> tree = new ArrayList<Menu>();
		for (Map<String, String> permission : permissions) {
			if (Convert.strToLong(permission.get("parentId"), 0) == parentId) {
				Menu m = new Menu();
				m.setId(Convert.strToInt(permission.get("id"), -1));
				m.setName(permission.get("name"));
				m.setTitle(permission.get("title"));
				m.setParentId(Convert.strToInt(permission.get("parentId"), 0));
				m.setSort(Convert.strToInt(permission.get("sort"), 0));
				m.setStatus(Convert.strToInt(permission.get("status"), 0));
				m.setType(Convert.strToInt(permission.get("type"), 0));
				m.setUrl(permission.get("url"));
				m.setIcon(permission.get("icon"));
				// 遍历子菜单
				m.setSubMenu(getMenuTree(permissions, m.getId()));
				tree.add(m);
			}
		}
		return tree;
	}

	/*
	public List<Menu> getMenuTree(List<Map<String, String>> permissions, long parentId) {
		List<Menu> tree = new ArrayList<Menu>();
		for(int i=0;i<permissions.size();i++){
			if("0".equals(permissions.get(i).get("parentId"))){
				//一级菜单
				Menu fm=new Menu();
				fm.setId(Convert.strToInt(permissions.get(i).get("id"), -1));
				fm.setName(permissions.get(i).get("name"));
				fm.setTitle(permissions.get(i).get("title"));
				fm.setParentId(Convert.strToInt(permissions.get(i).get("parentId"), 0));
				fm.setSort(Convert.strToInt(permissions.get(i).get("sort"), 0));
				fm.setStatus(Convert.strToInt(permissions.get(i).get("status"), 0));
				fm.setType(Convert.strToInt(permissions.get(i).get("type"), 0));
				fm.setUrl(permissions.get(i).get("url"));
				fm.setIcon(permissions.get(i).get("icon"));
				List<Menu> ftree = new ArrayList<Menu>();
				for(int j=0;j<permissions.size();j++){
					if((fm.getId()+"").equals(permissions.get(j).get("parentId"))){
						//一级菜单
						Menu sm=new Menu();
						sm.setId(Convert.strToInt(permissions.get(j).get("id"), -1));
						sm.setName(permissions.get(j).get("name"));
						sm.setTitle(permissions.get(j).get("title"));
						sm.setParentId(Convert.strToInt(permissions.get(j).get("parentId"), 0));
						sm.setSort(Convert.strToInt(permissions.get(j).get("sort"), 0));
						sm.setStatus(Convert.strToInt(permissions.get(j).get("status"), 0));
						sm.setType(Convert.strToInt(permissions.get(j).get("type"), 0));
						sm.setUrl(permissions.get(j).get("url"));
						sm.setIcon(permissions.get(j).get("icon"));
						List<Menu> stree = new ArrayList<Menu>();
						for(int k=0;k<permissions.size();k++){
							if((sm.getId()+"").equals(permissions.get(k).get("parentId"))){
								//一级菜单
								Menu tm=new Menu();
								tm.setId(Convert.strToInt(permissions.get(k).get("id"), -1));
								tm.setName(permissions.get(k).get("name"));
								tm.setTitle(permissions.get(k).get("title"));
								tm.setParentId(Convert.strToInt(permissions.get(k).get("parentId"), 0));
								tm.setSort(Convert.strToInt(permissions.get(k).get("sort"), 0));
								tm.setStatus(Convert.strToInt(permissions.get(k).get("status"), 0));
								tm.setType(Convert.strToInt(permissions.get(k).get("type"), 0));
								tm.setUrl(permissions.get(k).get("url"));
								tm.setIcon(permissions.get(k).get("icon"));
								stree.add(tm);
							}
						}
						sm.setSubMenu(stree);
						ftree.add(sm);
					}
				}
				fm.setSubMenu(ftree);
				tree.add(fm);
			}
		}
		return tree;
	}
	*/
	
	
	/**
	 * 读取所有角色
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryAllRole(DataTableRequest params) throws SQLException {
		return page("admin_role", "*", "", "", params.getStart(), params.getLength());
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> findRole(long id) throws Exception {
		return permissionDao.findRoleById(id);
	}

	@Transactional
	public long deleteRoleById(long id) {
		long ret = permissionDao.deleteRoleById(id);
		return ret;
	}

	@Transactional(rollbackFor = Exception.class)
	public long updateRoleById(long id, String name, String remark) throws SQLException {
		return permissionDao.updateRole(id, name, remark);
	}

	@Transactional(rollbackFor = Exception.class)
	public long addRole(String name, String remark) throws SQLException {
		return permissionDao.addRole(name, remark);
	}

	@Transactional
	public void updateRoleRights(long roleId, Long[] rights) throws SQLException {
		List<Map<String, String>> oldRights = queryByRoleId(roleId);
		List<Long> rightsList = new ArrayList<Long>();
		for (Map<String, String> map : oldRights) {
			rightsList.add(Convert.strToLong(map.get("id"), -1));
		}
		List<Long> rights4Add = new ArrayList<Long>();
		for (Long item : rights) {
			if (rightsList.contains(item)) {
				rightsList.remove(item);
			} else {
				rights4Add.add(item);
			}
		}
		// 删除
		for (Long item : rightsList) {
			permissionDao.deleteRoleRule(roleId, item);
		}
		// 增加
		for (Long item : rights4Add) {
			permissionDao.addRoleRule(roleId, item);
		}
	}

	public List<Map<String, String>> queryAllRole() {
		try {
			return permissionDao.queryAllRole();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public long addRule(long pid, String title, String name, String url, String icon, int type, int sort, int status)
			throws SQLException {
		return permissionDao.addRule(pid, title, name, url, icon, type, sort, status);
	}

	public long updateRule(long id, long pid, String title, String name, String url, String icon, int type, int sort,
			int status) throws SQLException {
		return permissionDao.updateRule(id, pid, title, name, url, icon, type, sort, status);
	}

	public long deleteRule(long id) {
		long ret = permissionDao.deleteRule(id);
		permissionDao.deleteSubRules(id);
		return ret;
	}

}
