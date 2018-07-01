package com.spark.p2p.controller.ucenter;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spark.p2p.entity.Pagination;
import com.spark.p2p.service.NoticeService;
import com.spark.p2p.util.DataTable;

@Controller("messageUcenterController")
public class MessageController extends UCenterController{
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 系统消息首页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("notice")
	public String siteNotice(HttpServletRequest request) throws Exception{
		//手机端获取数据
		noticeService.updatSiteNotice(getUser().getId());
		List<Map<String,String>> notice = noticeService.querySiteNotice2(getUser().getId());
		request.setAttribute("notice", notice);
		return view("myaccount-notice");
	}
	@RequestMapping("notice/list")
	public DataTable siteNoticeList() throws SQLException{
		return dataTable((params) -> noticeService.querySiteNotice(params, getUser().getId()));
	}	
	/**
	 * 系统消息首页
	 * @return
	 */
	@RequestMapping("notice-set")
	public String messageset(){
		return view("myaccount-notice-set");
	}
}
