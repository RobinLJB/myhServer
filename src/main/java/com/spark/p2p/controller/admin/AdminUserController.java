package com.spark.p2p.controller.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.Menu;
import com.spark.p2p.service.admin.AdminUserService;
import com.spark.p2p.service.admin.RolePermissionService;
import com.spark.p2p.util.CaptchaUtil;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FormUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;
import com.spark.p2p.service.admin.DepartmentService; 

/**
 * 后台用户登录、登出
 * @author yanqizheng
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController extends BaseAdminController{
	private static Log log = LogFactory.getLog(AdminUserController.class);
	@Autowired
	private AdminUserService userService;
	@Autowired
	private RolePermissionService rolePermission;
	@Autowired
	private RolePermissionService roleService;
	@Autowired
	private DepartmentService departmentService;	
	/**
	 * 后台登录
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/login");
		String redirect = "";
		if(WebUtils.getSavedRequest(request)!=null){
			redirect = WebUtils.getSavedRequest(request).getRequestUrl();
			if(StringUtils.isNotBlank(redirect)){
				mv.addObject("redirect", redirect);
			}
		}
		return mv;
	}
	
	/**
	 * 提交登录用户信息
	 * @return
	 * @throws DataException 
	 */
	@RequestMapping(value="signon")
	public @ResponseBody MessageResult doLogin() throws DataException{
		MessageResult mr =new MessageResult();
		String username = Convert.strToStr(request("username"), "");
		String password = Convert.strToStr(request("password"),"");
		String captcha = Convert.strToStr(request("captcha"),"");
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			log.error("用户名或密码不能为空");
			return error("用户名或密码不能为空");
		}
		if(StringUtils.isBlank(captcha)){
			log.error("验证码不能为空");
			return error("验证码不能为空");
		}
		if(!CaptchaUtil.validate(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(),"ADMIN_LOGIN",captcha)){
			log.error("验证码不正确");
			return error("验证码不正确");
		}
		try {
			password = Encrypt.MD5(password+AppSetting.MD5_KEY);
			log.debug("password="+password);
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setHost(getRemoteIp());
			SecurityUtils.getSubject().login(token);
			Admin admin = (Admin)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(Const.SESSION_ADMIN);
			//获取当前用户的菜单权限
			List<Map<String,String>> permissions = rolePermission.queryMenusByRoleId(admin.getRoleId());
			List<Menu> menus = rolePermission.getMenuTree(permissions, 0);
			//获取当前角色的访问地址
			long roleID=admin.getRoleId();
			String url="";
			List<Map<String,String>> lists=DB.query("select * from admin_role_permission where roleId="+roleID+" order by id asc");	
			if(lists.size()>0){
				int ruleId=Convert.strToInt(lists.get(0).get("ruleId"), 0);
				url=DB.query("select * from admin_auth_rule where id="+ruleId).get(0).get("url")+".html";
				
			}
			if(admin.getId()==1){
				url="/admin/dashboard/index.html"; 
			}
			mr.setCode(0);
			mr.setData(url);
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute("ADMIN_MENU", menus);
			log.info(menus);
		}catch (AuthenticationException e) {
			e.printStackTrace();
			return error(e.getMessage());
		}
		catch(SQLException e){
			log.error(e);
			return error("系统错误");
		}
		return mr;
	}
	
	@RequestMapping(value="logout")
	public String logout(){  
        SecurityUtils.getSubject().logout();
        return "redirect:/admin/login.html";
    }
	
	@RequestMapping(value="unauthor")
	public String unauthor(){
        return "admin/unauthor";
    }
	
	@RequestMapping(value="user")
	public String userIndex(){
		return view("system/user-index");
	}
	
	/**
	 * 获取role列表，或导出excel
	 * @return
	 */
	@RequestMapping("user/list")
	public DataTable userList(){
		return dataTable((params)->userService.queryUser(params));
	}
	
	@RequestMapping(value="user/{id}")
	public String userDetail(HttpServletRequest request,@PathVariable int id) throws Exception{
		FormUtil.createToken("USER_DETAIL");
		Map<String,String> user = new HashMap<String,String>();
		if(id != -1){
			user = userService.findUser(id);
		}
		else {
			user.put("id", "-1");
		}
		request.setAttribute("roles",roleService.queryAllRole());
		request.setAttribute("departments",departmentService.queryAllDepartment());
		request.setAttribute("user", user);
		return view("system/user-detail");
	}
	
	@RequestMapping(value="user/update/{id}",method = RequestMethod.POST)
	public @ResponseBody MessageResult userUpdate(@PathVariable int id) throws SQLException{
		long ret = -1;
		String username = request("username");
		String password = request("password");
		int enable = Convert.strToInt(request("enable"), 2);
		int departmentId = Convert.strToInt(request("departmentId"), 2);
		long roleId = Convert.strToLong(request("roleId"), 0);
		String realName = request("realName");
		String mobilePhone = request("mobilePhone");
		String remark = request("remark");
		if(roleId == 0){
			return error("请选择用户角色");
		}
		if(FormUtil.validateToken("USER_DETAIL")){
			if(id == -1){
				password = Encrypt.MD5(password + AppSetting.MD5_KEY);
				ret = userService.addUser(username,password,enable,departmentId,roleId,realName,mobilePhone,remark);
			}
			else if (id > 0){
				ret = userService.updateUser(id,username,enable,departmentId,roleId,realName,mobilePhone,remark);
				//密码不为空则修改密码
				if(StringUtils.isNotBlank(password)){
					password = Encrypt.MD5(password + AppSetting.MD5_KEY);
					ret = userService.updateUserPassword(id, password);
				}
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
	@RequestMapping(value="user/delete/{id}")
	public @ResponseBody MessageResult roleDelete(@PathVariable int id){
		if(id<=0){
			return error("参数错误");
		}
		long ret = userService.deleteUser(id);
		if(ret > 0) return success("删除成功");
		else return error("操作失败");
	}
	
	@RequestMapping(value = "profile/password")
	public String profile(HttpServletRequest request) {
		FormUtil.createToken("USER_PROFILE");
		Admin admin = (Admin) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(Const.SESSION_ADMIN);
		request.setAttribute("roles", roleService.queryAllRole());
		request.setAttribute("user", admin);
		return view("system/admin-profile");
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "profile/password/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult profileUpdate() throws Exception {
		long ret = -1;
		Admin admin = (Admin) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(Const.SESSION_ADMIN);
		long id = admin.getId();
		String oldPassword = request("oldpassword");
		String password = request("password");
		//if (FormUtil.validateToken("USER_PROFILE")) {
			if (userService.checkUserPassword(id, Encrypt.MD5(oldPassword + AppSetting.MD5_KEY))) {
				password = Encrypt.MD5(password + AppSetting.MD5_KEY);
				ret = userService.updateUserPassword(id, password);
			} else {
				log.error("原密码错误");
				return error("原密码错误");
			}
		//} else {
		//	return error("表单验证失败！");
		//}
		if (ret > 0) {
			return success("操作成功");
		} else {
			return error("操作失败");
		}
	}
}
