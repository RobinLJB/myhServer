package com.spark.p2p.controller.admin;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;

@Controller
@RequestMapping("/admin")
public class AppRevisionController extends BaseAdminController {
	@Autowired
	private SiteService siteService;

	@RequestMapping("app")
	public String index() {
		return view("system/app-index");
	}

	/**
	 * 历史版本列表
	 * 
	 * @return
	 */
	@RequestMapping("app/revision")
	public DataTable optionList() {
		return dataTable((params) -> siteService.queryAppRevision(params));
	}

	@RequestMapping(value="app/publish",method = RequestMethod.GET)
	public String publish() {
		return view("system/app-publish");
	}

	/**
	 * 发布新版本
	 * 
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value="app/publish",method = RequestMethod.POST)
	public @ResponseBody MessageResult publishRevision() throws SQLException {
		String revision = request("revision");
		int platform = requestInt("platform");
		String remark = request("remark");
		String url = request("url");
		if (siteService.addAppRevision(platform, revision, url, remark) > 0) {
			return MessageResult.success("发布成功！");
		} else {
			return MessageResult.error(500, "发布失败！");
		}
	}
}
