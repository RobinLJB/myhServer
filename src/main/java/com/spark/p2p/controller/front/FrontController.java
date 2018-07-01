package com.spark.p2p.controller.front;

import org.springframework.stereotype.Controller;

import com.spark.p2p.constant.Const;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;

@Controller("frontController")
public class FrontController extends BaseController{
	public FrontController() {
		super.viewPath = "front";
		
	}
	
	
	
}
