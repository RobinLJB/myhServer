package com.spark.p2p.controller.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.AliyunOssUtil;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;

import freemarker.template.utility.StringUtil;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Controller
@ResponseBody
@RequestMapping("/app")
public class AppIndexController extends AppBaseController {

	@Autowired
	private IndexService indexService;

	@Autowired
	private CMSService cmsService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private SelectService selectService;
	@Autowired
	private EhCacheFactoryBean ehCache;
	private String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
	private String saveImgPath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
	private String allowedAttachFormat = ".pdf,.apk,.doc,.docx,.xls,.xlsx,.jpg,.gif,.png,.zip";
	private long maxAllowedImgSize = 1024 * 5000;
	private long maxAllowedAttachSize = 20 * 1024 * 1000;

	@RequestMapping("index/banner")
	public @ResponseBody MessageResult indexBanner() throws Exception {
		/* 轮播图列表 */
		String type = requestString("type");
		if (StringUtils.isEmpty(type)) {
			type = "WAP_BANNER";
		}
		return success(indexService.bannerlist(type, 0));
	}

	// app的轮播图
	@RequestMapping("iphone/banner")
	public @ResponseBody MessageResult bannerList() throws Exception {
		Map<String, String> map = new HashMap<>();
		map = indexService.bannerList();
		List<String> list = new ArrayList();
		list.add(map.get("path1"));
		list.add(map.get("path2"));
		list.add(map.get("path3"));
		return success(list);
	}

	/**
	 * 文章列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("article")
	public @ResponseBody MessageResult articleList() throws Exception {
		long cateId = requestInt("cateId");
		int limit = requestInt("limit");
		return success(cmsService.queryLastestArticle(cateId, limit));
	}

	/**
	 * 文章详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("article/detail/{id}")
	public @ResponseBody MessageResult articleDetail(@PathVariable long id) throws Exception {
		return success(cmsService.findArticle(id));
	}

	@RequestMapping(value = "upload/file", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int type = Convert.strToInt(request.getParameter("type"), 0);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (!ServletFileUpload.isMultipartContent(request)) {
			out.write(error(500, "表单格式不正确").toString());
			return;
		}
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		FileItemIterator iterator;
		FileItemStream fileStream = null;
		try {
			iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}
			if (fileStream == null) {
				out.write(error(500, "未找到上传数据").toString());
				return;
			}
			String fileName = fileStream.getName();
			// 获取文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
				out.write(MessageResult.error(401, "不允许的图片格式，请上传" + allowedImgFormat + "格式！").toString());
				return;
			}
			InputStream is = fileStream.openStream();
			// 相对工程路径
			String relativePath = PathFormat.parse(parseCatePath(saveImgPath, type), fileName) + suffix;
			// 磁盘绝对路径
			// String physicalPath =
			// request.getSession().getServletContext().getRealPath("/") + relativePath;

			AliyunOssUtil.put(relativePath, is);
			// BaseState storageState = (BaseState)
			// StorageManager.saveFileByInputStream(is,physicalPath, maxAllowedImgSize);
			is.close();
			// if (storageState.isSuccess()) {
			// String relativeUrl = "/" + relativePath;
			// String absPath = request.getContextPath() + relativeUrl;
			String absPath = AliyunOssUtil.downloadFile(relativePath);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out.write(success(absPath, absPath).toString());
			// }
			// else{
			// out.write(error(500,storageState.getInfo()).toString());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			fileStream = null;
		}
	}

	private String parseCatePath(String origin, int type) {
		String replacement = "default";
		if (type == 1) {
			replacement = "member";
		} else if (type == 2) {
			replacement = "banner";
		} else if (type == 3) {
			replacement = "cms";
		} else if (type == 4) {
			replacement = "company_info";
		}
		return origin.replaceFirst("\\{:cate\\}", replacement);
	}

}
