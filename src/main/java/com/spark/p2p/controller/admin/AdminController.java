package com.spark.p2p.controller.admin;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.Admin;

public class AdminController extends BaseController{
	public AdminController(){
		super.viewPath = "admin";
	}
	
	protected Admin getAdmin(){
		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

		return (Admin) attributes.getAttribute(Const.SESSION_ADMIN, RequestAttributes.SCOPE_SESSION);
	}
}
