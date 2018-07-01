package com.spark.p2p.controller.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.MemberAdminService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FormUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

/**
 * 实名认证管理，资料认证管理
 * 
 * @author guokui
 *
 */
@Controller
@RequestMapping("/admin/constant")
public class ConstantController extends BaseAdminController{
	private static Log log = LogFactory.getLog(ConstantController.class);
	
	/**
	 * 实名认证页面初始化
	 * @return
	 */
	@Autowired
	private MemberAdminService memberService;
	@Autowired
	private MemberService memberService2;
	@RequestMapping(value = "identity")
	public  String realnameInit() {
		return view("audit/identity-index");
	}
	
	
	/**
	 * 实名认证列表
	 * @param title
	 * @param type
	 * @return 
	 */
 	@RequestMapping(value = "identity/list")
 	public DataTable realnamelist() {
 		return dataTable((params) -> memberService.queryRealnamelist(params));
 	}
 	
 	
 	@RequestMapping(value = "identity/{id}")
	public String personDetail(HttpServletRequest request,@PathVariable Integer id) throws Exception {
 		
		Map<String, String> result = memberService.queryIdentity(id);
			
		request.setAttribute("result", result);
		
		return view("audit/identity-detail");
	}
 	
 	
 	@RequestMapping(value = "identity/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult personUpdate(HttpServletRequest request,@RequestParam(value = "id") Integer id)
					throws SQLException {
		int status=Convert.strToInt(request.getParameter("status"), 0);
		String check_view=request.getParameter("check_view");
		Long result = 0l;
		result = memberService.personUpdate(id, status,check_view);
		return result > 0l ? success("操作成功!") : error("操作失败!");
	}
 	
 	
 	
 	//个人信息审核
 		@RequestMapping(value = "person")
 		public String personInit() {
 			return view("audit/person-index");
 		}
 		
 		
 		/**
 		 * 实名认证资料详情 guokui
 		 * 
 		 * @param id
 		 * @return
 		 * @throws Exception
 		 */
 		@RequestMapping(value = "company/{id}")
 		public String companyDetail(HttpServletRequest request,@PathVariable Integer id) throws Exception {
 			Map<String, String> result = memberService.queryMemberCompany(id);
 				request.setAttribute("companyMaps", result);
 			return view("audit/company-detail");
 		}
 		@RequestMapping(value = "company/update", method = RequestMethod.POST)
 		public @ResponseBody MessageResult companyUpdate(HttpServletRequest request,@RequestParam(value = "id") Integer id)
 						throws SQLException {
 			int status=Convert.strToInt(request.getParameter("status"), 0);
 			Long result = 0l;
 			result = memberService.companyUpdate(id, status);
 			return result > 0l ? success("操作成功!") : error("操作失败!");
 		}
 		//企业信息审核
 		@RequestMapping(value = "company")
 		public String companyInit() {
 			return view("audit/company-index");
 		}
 	
	@RequestMapping(value = "person/list")
	public DataTable personList() {
		return dataTable((params) -> memberService.queryMemberInfo(params));
	}
	@RequestMapping(value = "company/list")
	public DataTable companyList() {
		return dataTable((params) -> memberService.queryMemberConmpany(params));
	}
 	/**
 	 * 实名认证资料详情
 	 * guokui
 	 * @param id
 	 * @return
 	 * @throws Exception 
 	 */
 	@RequestMapping(value = "realname/{uid}")
 	public String realnameDetail(HttpServletRequest request,@PathVariable Integer uid) throws Exception {
 		Map<String,String> result = memberService.queryRealnameDetail(uid);
 		if (result != null) {
			request.setAttribute("userMap", result);
		} else {
			request.setAttribute("userMap", new HashMap<String,String>());
		}		
 		return view("audit/realname-detail");
 	}	
 	/**
 	 * 实名认证操作
 	 * guokui
 	 * @param id
 	 * @param checkOpinion
 	 * @param status
 	 * @return
 	 * @throws SQLException 
 	 */
 	@RequestMapping(value = "realname/update",method = RequestMethod.POST)
 	public @ResponseBody MessageResult realnameUpdate(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "checkOpinion") String checkOpinion,
			@RequestParam(value = "status") Integer status) throws SQLException {
 		//FormUtil.createToken("REALNAME_UPDATE");
		Long result = 0l;
		
		result = memberService.realnameUpdate(id, status, checkOpinion);
		
		return result > 0l ? success("操作成功!") : error("操作失败!");
 	}	
}
