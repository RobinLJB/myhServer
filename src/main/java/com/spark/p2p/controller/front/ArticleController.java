package com.spark.p2p.controller.front;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

@Controller
public class ArticleController extends BaseController {
	@Autowired
	private CMSService cmsService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private SelectService selectService;
	@Autowired
	private IndexService indexService;
	public ArticleController(){
		super.viewPath = "front";
	}
	
	

	
	/*文章列表*/
	@RequestMapping("/noticeList")
	public String articleList(HttpServletRequest request,@PathVariable int cid) throws Exception{
		request.setAttribute("cateId", cid);
		Pagination page = getPage();	
		request.setAttribute("article", cmsService.queryAritcleByCate(page,cid));
		request.setAttribute("page", page);
		request.setAttribute("cate", selectService.querySelectOption("CATE_ARTICLE"));
		request.setAttribute("imgMap", indexService.findImgByCate("pc_frontarticle"));
		return view("article-list");
	}
	

	/*文章详情*/
	@RequestMapping("/article/{id}")
	public String articleDetail(HttpServletRequest request,@PathVariable int id) throws Exception{
		Integer type = requestInt("type");
		request.setAttribute("cateId", type);
		
		/*上一篇文章*/
		
			request.setAttribute("artPrevious",cmsService.artPrevious(id,type));
		
		
		/*下一篇文章*/
			request.setAttribute("artNext",cmsService.artNext(id,type));
		
		request.setAttribute("cate", selectService.querySelectOption("CATE_ARTICLE"));
		request.setAttribute("imgMap", indexService.findImgByCate("pc_frontarticle"));
		request.setAttribute("article", cmsService.findArticle(id));
		return view("article-detail");
	}
	

	// 公司统计
	@RequestMapping("/category/gstj")
	public String articleList() throws Exception{
		return view("gstj");
	}

	
	// app网页
	@RequestMapping("app-download")
	public String download(){
		return view("app-download");
	}	
	
	// 客户端下载
	@RequestMapping("download")
	public String ios_download(){
		return view("download");
	}
}
