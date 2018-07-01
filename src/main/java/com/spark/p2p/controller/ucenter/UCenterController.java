package com.spark.p2p.controller.ucenter;
 

import org.springframework.web.bind.annotation.RequestMapping;

import com.jcraft.jsch.Session;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.ApplicationUtil;
import com.sparkframework.sql.model.Model;
@RequestMapping("/ucenter")
public class UCenterController extends BaseController{
	public UCenterController(){
		super.viewPath = "ucenter";
	}
	
	protected Member getUser(){
		Member member = (Member)session.getAttribute(Const.SESSION_MEMBER);
		try {
			member = new Model("member").where("id=?", member.getId()).find(Member.class);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return member;
	}
}