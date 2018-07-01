package com.spark.p2p.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.service.admin.DepartmentService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FormUtil;
import com.spark.p2p.util.MessageResult;
/**
 * 后台部门管理类
 * @author guokui
 *
 */
@Controller
@RequestMapping("/admin")
public class DepartmentController  extends BaseAdminController{
	private static Log log = LogFactory.getLog(RoleRightsController.class);
	@Autowired
	private DepartmentService departmentService ;
	/**
	 * 获取部门
	 * @return
	 */	
	@RequestMapping(value="department")
	public String departmentIndex(){
		return view("system/department-index");
	}
	/**
	 * 获取部门列表，或导出excel
	 * @return
	 */	
	@RequestMapping("department/list")
	public DataTable roleList(){
		return dataTable((params)->departmentService.queryAllDepartment(params));
	}
	
	
	/**
	 * 获取部门详情
	 * @param id ,<=-1 时为新建
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="department/{id}")
	public String roleDetail(HttpServletRequest request,@PathVariable int id) throws Exception{
		FormUtil.createToken("DEPARTMENT_DETAIL");
		Map<String,String> department = new HashMap<String,String>();
		if(id != -1){
			department = departmentService.findDepartment(id);
		}
		else {
			department.put("id", "-1");
		}
		request.setAttribute("department", department);
		return view("system/department-detail");
	}
	
	
	/**
	 * 获取部门人员列表，或导出excel
	 * @param id
	 * @return
	 */
	@RequestMapping(value="department/queryUserBydepartmentId/{id}")
	public DataTable queryUserBydepartmentId(@PathVariable int id){
		
		return dataTable((params)->departmentService.queryUserBydepartmentId(params,id));
	}
	
	/**
	 * 修改部门详情
	 * @param id 为-1时表示新建
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="department/update/{id}",method = RequestMethod.POST)
	public @ResponseBody MessageResult departmentSave(@PathVariable int id) throws Exception{
		long ret = -1;
		if(FormUtil.validateToken("DEPARTMENT_DETAIL")){
			if(id == -1){
				ret = departmentService.addDepartment(request("name"), request("remark"),request("leader"));
			}
			else if (id > 0){
				ret = departmentService.updateDepartmentById(id, request("name"), request("remark"),request("leader"));
			}
		}
		else return error("表单验证失败！");
		if(ret > 0)return success("操作成功");
		else return error("操作失败");
	}
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@RequestMapping(value="department/delete/{id}")
	public @ResponseBody MessageResult departmentDelete(@PathVariable int id){
		if(id<=0){
			return error("参数错误");
		}
		long ret = departmentService.deleteDepartmentById(id);
		if(ret > 0) return success("删除成功");
		else return error("操作失败");
	}	
	
}
