package com.spark.p2p.controller.admin;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Menu;
import com.spark.p2p.service.admin.RolePermissionService;
import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

/**
 * 系统键值管理
 * 
 * @author yanqizheng
 *
 */
@Controller
@RequestMapping("/admin")
public class SiteController extends BaseAdminController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private RolePermissionService rpService;
	
	/**
	 * 不带group时默认取第一个
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("site")
	public String group(HttpServletRequest request) throws Exception {
		List<Map<String, String>> groups = siteService.queryGroup();
		request.setAttribute("groups", groups);
		String group = groups.get(0).get("group_key");
		request.setAttribute("currGroup", group);
		return view("system/site-index");
	}

	
	@RequestMapping("site/{group}")
	public String groupIndex(HttpServletRequest request,@PathVariable String group) throws Exception {
		List<Map<String, String>> groups = siteService.queryGroup();
		request.setAttribute("groups", groups);
		if (group == null) {
			group = groups.get(0).get("group_key");
		}
		request.setAttribute("currGroup", group);
		return view("system/site-index");
	}

	
	/**
	 * 读取项值
	 * 
	 * @return
	 */
	@RequestMapping("site/option/{group}")
	public DataTable optionList(@PathVariable String group) {
		return dataTable((params) -> siteService.queryOptionByGroup(group, params));
	}

	/**
	 * 更新键值
	 * 
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("site/option/update")
	public @ResponseBody MessageResult optionUpdate() throws Exception {
		String key = request("keyName");
		String value = request("keyValue");
		if (siteService.updateKV(key, value) > 0) {
			return success("更新成功");
		} else
			return error("更新失败");
	}
	
	
	/**
	 * 设置借款地区
	 * 
	 * @return
	 */
	@RequestMapping("areaSet")
	public String areaSet() {
		return view("system/areaSet-index");
	}
	
	/**
	 * 地区显示列表
	 * 
	 * @return
	 */
	@RequestMapping("areaSet/list")
	public DataTable areaSetList() {
		return dataTable((params) -> siteService.areaSetList(params));
	}
	
	/**
	 * 修改地区显示
	 * @return
	 */
	@RequestMapping("areaSet/{id}")
	public String areaSetDetail(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		Map<String, String> result = siteService.findAreaByID(id);
		request.setAttribute("result", result);
		
		return view("system/areaSet-detail");
	}
	
	
	/**
	 * 保存修改限制
	 * @return
	 */
	@RequestMapping("areaSet/update/{id}")
	public @ResponseBody MessageResult areaSetUpdate(@PathVariable Integer id) throws Exception {
		String status=request("status");
		
		long ret = siteService.areaSetUpdate(id, Convert.strToInt(status, 0));;
		if (ret > 0) {
			return success("更新成功");
		} else {
			return error("更新失败");
		}
	}
	
	
	/**
	 * 平台收费设置显示 guokui
	 * 
	 * @return
	 */
	@RequestMapping("platformCost")
	public String platformCost() {
		return view("system/platformCost-index");
	}
	
	

	/**
	 * 收费项目列表
	 * 
	 * @return
	 */
	@RequestMapping("platformCost/list")
	public DataTable platformCostList() {
		return dataTable((params) -> siteService.platformCostList(params));
	}

	@RequestMapping("platformCost/{id}")
	public String platformCost(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		Map<String, String> result = siteService.findPlatformCost(id);
		if (result != null) {
			request.setAttribute("costMap", result);
		} else {
			request.setAttribute("costMap", null);
		}
		return view("system/platformCost-detail");
	}

	@RequestMapping("platformCost/update/{id}")
	public @ResponseBody MessageResult platformCostUpdate(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		StringBuilder json = new StringBuilder();
		request.setCharacterEncoding("UTF-8");
		String day = request("day");
		String value = request("value");
		String review = request("review");
		String xinFee = request("xinFee");
		String serviceFee = request("serviceFee");
		String shouFee = request("shouFee");
		String values=null;
		if(day!=""){
			json.append("{");
			json.append("\"xinFee\":" + "\"" + xinFee + "\",");
			json.append("\"serviceFee\":" + "\"" + serviceFee + "\",");
			json.append("\"shouFee\":" + "\"" + shouFee + "\"");
			json.append("}");
			values = json.toString();
		}else{
			values=value;
		}
		
		long ret = siteService.platformCostUpdate(id, review, values);
		if (ret > 0) {
			return success("更新成功");
		} else {
			return error("更新失败");
		}
	}

	@RequestMapping("select")
	public String selectGroup(HttpServletRequest request) throws Exception {
		String currGroup = request("group");
		Map<String, String> selectGroup = siteService.queryGroupItem("CATEGORY");
		request.setAttribute("groups", selectGroup);
		if (currGroup == null) {
			currGroup = selectGroup.keySet().iterator().next();
		}
		request.setAttribute("currGroup", currGroup);
		return view("system/select");
	}

	/**
	 * 读取项值
	 * 
	 * @return
	 */
	@RequestMapping("select/option/{group}")
	public DataTable selectOptionList(@PathVariable String group) {
		return dataTable((params) -> siteService.querySelectOptionByGroup(group, params));
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("select/option/add")
	public @ResponseBody MessageResult addSelectOption() throws SQLException {
		String group = request("group");
		String name = request("name");
		String key = request("key");
		int sort = requestInt("sort");
		long ret = siteService.addSelectOption(group, key, name, sort);
		return ret > 0 ? success("添加成功") : error(500, "添加失败");
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("select/option/delete")
	public @ResponseBody MessageResult deleteSelectOption() {
		int id = requestInt("id");
		long ret = siteService.deleteSelectOption(id);
		return ret > 0 ? success("删除成功") : error(500, "删除失败");
	}

	/**
	 * 菜单管理
	 * 
	 * @return
	 */
	@RequestMapping("system/menu")
	public String queryMemu() {
		return view("system/menu");
	}

	/**
	 * 菜单列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("system/menu/list")
	public @ResponseBody List<Menu> queryMemuList() throws SQLException {
		List<Map<String, String>> permissions = rpService.queryAllRights();
		List<Menu> menus = rpService.getMenuTree(permissions, 0);
		return menus;
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 */
	@RequestMapping("system/menu/delete")
	public @ResponseBody MessageResult deleteMenu() {
		int id = requestInt("id");
		long ret = rpService.deleteRule(id);
		return ret > 0 ? success("删除成功") : error(500, "删除失败");
	}

	/**
	 * 添加、更新菜单
	 * 
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("system/menu/edit")
	public @ResponseBody MessageResult editMenu() throws SQLException {
		int id = requestInt("id");
		String title = request("title");
		String name = request("name");
		String url = request("url");
		String icon = request("icon");
		int sort = requestInt("sort");
		int status = requestInt("status");
		int type = requestInt("type");
		int pid = requestInt("parentId");
		long ret = -1;
		if (id != 0) {
			ret = rpService.updateRule(id, pid, title, name, url, icon, type, sort, status);
		} else {
			ret = rpService.addRule(pid, title, name, url, icon, type, sort, status);
		}
		return ret > 0 ? success("编辑成功") : new MessageResult(500, "编辑失败", ret);
	}
	
	//日志查询
	
}
