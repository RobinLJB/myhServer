package com.spark.p2p.controller.admin;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.DataException;

@Controller
@RequestMapping("/admin/borrowSet")
public class BorrowSystemSetController extends BaseAdminController{
	
	public static final Log log = LogFactory.getLog(BorrowSystemSetController.class);
	
	@Autowired
	private BorrowAdminService borrowService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private RepayAdminService repayAdminService;
	
	
	
	/**
	 * 借款设置列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrow")
	public String articleIndex() throws Exception {
		return view("system/borrowProduct-index");
	}
	
	
	/**
	 * 借款设置列表
	 * @param title
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "borrow/list")
	public DataTable forFristAuditList() {
		return dataTable((params) -> borrowService.queryBorrowroduct(params));
	}
	
	
	/**
	 * 借款参数详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrow/{id}")
	public String articleDetail(HttpServletRequest request ,@PathVariable Integer id) throws Exception {
		if(id<0){
			request.setAttribute("resultMap", null);
		}else{
			request.setAttribute("resultMap", borrowService.findBorrowProductById(id));
			
		}
		return view("system/borrowProduct-detail");
	}
	
	
	/**
	 * 修改借款参数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrow/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult articleUpdate(
			@RequestParam(value = "id", required = false, defaultValue = "-1") Integer id,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "borrowdays") int borrowdays,
			@RequestParam(value = "xinfee") double xinfee, @RequestParam(value = "servicefee") double servicefee,
			@RequestParam(value = "shoufee") double shoufee, @RequestParam(value = "overduefee") double overduefee,
			@RequestParam(value = "status") int status )
					throws SQLException {
		Long result = 0l;
		if (id > 0l) {
			result = borrowService.updateBorrowProduct(id, amount, borrowdays, xinfee, servicefee, shoufee, overduefee, status);
		} else if (id == -1) {
			result = borrowService.insertBorrowProduct( amount, borrowdays, xinfee, servicefee, shoufee, overduefee, status);
		} else {
			log.error("参数异常");
			return error("参数异常!");
		}
		return result > 0l ? success("修改成功!") : error("修改失败!");
	}
	
	
	
	
	
}
