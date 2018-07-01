package com.spark.p2p.controller.admin;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Menu;
import com.spark.p2p.service.admin.RolePermissionService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FormUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;


/**
 * 后台角色、权限管理类
 * @author yanqizheng
 *
 */
@Controller
@RequestMapping("/admin")
public class RoleRightsController extends BaseAdminController{
	private static Log log = LogFactory.getLog(RoleRightsController.class);
	@Autowired
	private RolePermissionService roleService;
	
	/**
	 * 获取角色列表
	 * @return
	 */
	@RequestMapping(value="role")
	public String roleIndex(){
		return view("system/role-index");
	}
	
	
	/**
	 * 获取role列表，或导出excel
	 * @return
	 */
	@RequestMapping("role/list")
	public DataTable roleList(){
		return dataTable((params)->roleService.queryAllRole(params));
	}
	
	/**
	 * 获取角色详情
	 * @param id ,<=-1 时为新建
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="role/{id}")
	public String roleDetail(HttpServletRequest request,@PathVariable int id) throws Exception{
		FormUtil.createToken("ROLE_DETAIL");
		Map<String,String> role = new HashMap<String,String>();
		if(id != -1){
			 role = roleService.findRole(id);
		}
		else {
			role.put("id", "-1");
		}
		request.setAttribute("role", role);
		return view("system/role-detail");
	}
	
	
	/**
	 * 修改角色信息
	 * @param id 为-1时表示新建
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value="role/update/{id}",method = RequestMethod.POST)
	public @ResponseBody MessageResult roleSave(@PathVariable int id) throws SQLException{
		long ret = -1;
		if(FormUtil.validateToken("ROLE_DETAIL")){
			if(id == -1){
				ret = roleService.addRole(request("name"), request("remark"));
			}
			else if (id > 0){
				ret = roleService.updateRoleById(id, request("name"), request("remark"));
			}
		}
		else return error("表单验证失败！");
		if(ret > 0)return success("操作成功");
		else return error("操作失败");
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping(value="role/delete/{id}")
	public @ResponseBody MessageResult roleDelete(@PathVariable int id){
		if(id<=0){
			return error("参数错误");
		}
		long ret = roleService.deleteRoleById(id);
		if(ret > 0) return success("删除成功");
		else return error("操作失败");
	}
	
	@RequestMapping(value="role/rights/{id}",method = RequestMethod.GET)
	public String  roleRights(HttpServletRequest request,@PathVariable int id) throws SQLException{
		List<Map<String,String>> roleRights = roleService.queryMenusByRoleId(id);
		List<Map<String,String>> allRights = roleService.queryMenusByRoleId(-1);
		List<Menu> menus = roleService.getMenuTree(allRights, 0);
		JSONArray json = new JSONArray(menus);
		JSONArray rightsJson = new JSONArray(roleRights);
		request.setAttribute("rights", rightsJson.toString());
		request.setAttribute("menus", json.toString());
		request.setAttribute("id", id);
		return view("system/role-rights");
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value="role/rights/{id}",method = RequestMethod.POST)
	public @ResponseBody  MessageResult updateRoleRights(@PathVariable int id) throws SQLException{
		String rightsStr = request("rights");
		String[] rightsArray = rightsStr.split(",");
		List<Long> rights = new ArrayList<Long>();
		for(int i = 0;i<rightsArray.length;i++){
			Long tmp = Convert.strToLong(rightsArray[i], -1);
			rights.add(tmp);
		}
		roleService.updateRoleRights(id,rights.toArray(new Long[rights.size()]));
		return success("设置成功");
	}
}
