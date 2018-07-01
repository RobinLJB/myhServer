package com.spark.p2p.controller.admin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.Admin;

public class BaseAdminController extends BaseController{
	public BaseAdminController(){
		super.viewPath = "admin";
	}
	
	protected Admin getAdmin(){
		return (Admin) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(Const.SESSION_ADMIN);
	}
}
