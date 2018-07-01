package com.spark.p2p.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.AppRevision;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.service.admin.SiteService;

@Controller
@RequestMapping("/page")
public class SinglePageController extends FrontController {
	@Autowired 
	private SiteService siteService;
	@Autowired
	private CMSService cmsService;
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("static/{page}")
	public String singlePage(@PathVariable String page) {
		return view("singlepage/" + page);
	}
	
	/**
	 * APP下载链接页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("app-download")
	public String appDownload(HttpServletRequest request) throws Exception{
		AppRevision ios = siteService.findLateastRevision(Const.PLATFORM_IOS);
		AppRevision android = siteService.findLateastRevision(Const.PLATFORM_ANDROID);
		request.setAttribute("android", android);
		request.setAttribute("ios", ios);
		System.out.println("app-download");
		return viewMobile("app-download");
	}
}
