package com.spark.p2p.controller.admin;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FormUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.DataException;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 内容管理，新闻，图片，通知，网站信息
 *
 * @author yanqizheng
 */
@Controller
@RequestMapping("/admin/cms")
public class CMSController extends BaseAdminController {
	private static Log log = LogFactory.getLog(CMSController.class);
	private String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
	private String saveImgPath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
	private long maxAllowedImgSize = 1024 * 5000;
	@Autowired
	private CMSService cmsService;
	@Autowired
	private SelectService selectService;

	/**
	 * 文章列表页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "article")
	public String articleIndex(HttpServletRequest request) throws Exception {
		Map<String, String> cates = selectService.getSelectMap("ARTICLE_CATE");
		request.setAttribute("cates", cates);
		return view("cms/article-index");
	}

	/**
	 * banner图列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "image")
	public String imageList(HttpServletRequest request) throws Exception {
		Map<String, String> cates = selectService.getSelectMap("IMG_CATE");
		request.setAttribute("cates", cates);
		return view("cms/image-index");
	}

	/**
	 * 平台意见
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "advise")
	public String adviseIndex() throws Exception {
		return view("cms/advise-index");
	}

	/**
	 * 文章列表页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dasd")
	public String asasdd(HttpServletRequest request) throws Exception {
		Map<String, String> cates = selectService.getSelectMap("ARTICLE_CATE");
		request.setAttribute("cates", cates);
		return view("cms/asd");
	}

	/**
	 * 文章查询列表
	 *
	 * @param title
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "article/list")
	public DataTable articlelist() {
		return dataTable((params) -> cmsService.queryByArticle(params));
	}

	/**
	 * 平台意见
	 *
	 * @param title
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "advise/list")
	public DataTable adviseList() {
		return dataTable((params) -> cmsService.queryAdvise(params));
	}

	/**
	 * 文章详情
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "article/{id}")
	public String articleDetail(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		try {
			Map<String, String> result = cmsService.findArticle(id);
			if (result != null) {
				request.setAttribute("resultMap", result);
			} else {
				request.setAttribute("resultMap", new HashMap<String, String>());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		Map<String, String> cates = selectService.getSelectMap("ARTICLE_CATE");
		request.setAttribute("cates", cates);
		return view("cms/article-detail");
	}

	/**
	 * 文章删除
	 *
	 * @return
	 */
	@RequestMapping(value = "article/delete/{id}")
	public @ResponseBody MessageResult articleDelete(@PathVariable Integer id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = cmsService.deleteByArticleId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}

	/**
	 * 意见删除
	 *
	 * @return
	 */
	@RequestMapping(value = "advise/delete/{id}")
	public @ResponseBody MessageResult adviseDelete(@PathVariable Integer id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = cmsService.deleteByAdviseId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}

	@RequestMapping(value = "article/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult articleUpdate(
			@RequestParam(value = "id", required = false, defaultValue = "-1") Integer id,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "isShow") String isShow,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "detail") String content,
			@RequestParam(value = "link", required = false, defaultValue = "") String link)
			throws SQLException {
		Long result = 0l;
		if (id > 0l) {
			result = cmsService.updateArticle(id, title, null, null, type,
					isShow, sort, content, link);
		} else if (id == -1) {
			result = cmsService.addArticle(title, null, null, type, isShow,
					sort, content, link);
		} else {
			return error("参数异常!");
		}
		return result > 0l ? success("操作成功!") : error("操作失败!");
	}

	/**
	 * 图片列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "banner")
	public String bannerList(HttpServletRequest request) throws Exception {
		Map<String, String> bannerImgs = cmsService.getBannerImg();
		request.setAttribute("bannerImgs", bannerImgs);
		return view("cms/banner-index");
	}

	/**
	 * 会员相关图片
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberImg")
	public String memberImgList() throws Exception {
		return view("cms/memberImg-index");
	}

	@RequestMapping(value = "bannerImgA/upload")
	@ResponseBody
	public String bannerImgAUpload(HttpServletRequest request,
			HttpServletResponse response, MultipartFile myFile)
			throws Exception {
		log.info("上传中");
		String flag = "0";
		log.info(myFile);
		int type = 3;
		String originalFilename = myFile.getOriginalFilename();
		log.info(originalFilename);
		// 如果不行，换成这个String fileName = myFile.getName()+"."
		String fileName = myFile.getName() + ".";
		String name[] = originalFilename.split("\\.");
		// 文件后缀
		String suffix = name[1];
		if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
			return "-1";
		}
		InputStream is = myFile.getInputStream();
		// 相对工程路径
		String relativePath = PathFormat.parse(
				parseCatePath(saveImgPath, type), fileName) + '.' + suffix;
		// 磁盘路径
		String physicalPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ relativePath;
		BaseState storageState = (BaseState) StorageManager
				.saveFileByInputStream(is, physicalPath, maxAllowedImgSize);
		is.close();
		if (storageState.isSuccess()) {
			String relativeUrl = "/" + relativePath;
			String absPath = request.getContextPath() + relativeUrl;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// 将图片地址传入到数据库中
			long ret = cmsService.updateBannerA(absPath);
			if (ret > 0) {

				return "0";
			} else {
				return "-1";
			}
		}
		return "-1";
	}

	@RequestMapping(value = "bannerImgB/upload")
	@ResponseBody
	public String bannerImgBUpload(HttpServletRequest request,
			HttpServletResponse response, MultipartFile myFile)
			throws Exception {
		log.info("上传中");
		String flag = "0";
		log.info(myFile);
		int type = 3;
		String originalFilename = myFile.getOriginalFilename();
		log.info(originalFilename);
		// 如果不行，换成这个String fileName = myFile.getName()+"."
		String fileName = myFile.getName() + ".";
		String name[] = originalFilename.split("\\.");
		// 文件后缀
		String suffix = name[1];
		if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
			return "-1";
		}
		InputStream is = myFile.getInputStream();
		// 相对工程路径
		String relativePath = PathFormat.parse(
				parseCatePath(saveImgPath, type), fileName) + '.' + suffix;
		// 磁盘路径
		String physicalPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ relativePath;
		BaseState storageState = (BaseState) StorageManager
				.saveFileByInputStream(is, physicalPath, maxAllowedImgSize);
		is.close();
		if (storageState.isSuccess()) {
			String relativeUrl = "/" + relativePath;
			String absPath = request.getContextPath() + relativeUrl;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// 将图片地址传入到数据库中
			long ret = cmsService.updateBannerB(absPath);
			if (ret > 0) {

				return "0";
			} else {
				return "-1";
			}
		}
		return "-1";
	}

	@RequestMapping(value = "bannerImgC/upload")
	@ResponseBody
	public String bannerImgCUpload(HttpServletRequest request,
			HttpServletResponse response, MultipartFile myFile)
			throws Exception {
		log.info("上传中");
		String flag = "0";
		log.info(myFile);
		int type = 3;
		String originalFilename = myFile.getOriginalFilename();
		log.info(originalFilename);
		// 如果不行，换成这个String fileName = myFile.getName()+"."
		String fileName = myFile.getName() + ".";
		String name[] = originalFilename.split("\\.");
		// 文件后缀
		String suffix = name[1];
		if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
			return "-1";
		}
		InputStream is = myFile.getInputStream();
		// 相对工程路径
		String relativePath = PathFormat.parse(
				parseCatePath(saveImgPath, type), fileName) + '.' + suffix;
		// 磁盘路径
		String physicalPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ relativePath;
		BaseState storageState = (BaseState) StorageManager
				.saveFileByInputStream(is, physicalPath, maxAllowedImgSize);
		is.close();
		if (storageState.isSuccess()) {
			String relativeUrl = "/" + relativePath;
			String absPath = request.getContextPath() + relativeUrl;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// 将图片地址传入到数据库中
			long ret = cmsService.updateBannerC(absPath);
			if (ret > 0) {

				return "0";
			} else {
				return "-1";
			}
		}
		return "-1";
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

	/**
	 * 图片编辑
	 *
	 * @return
	 */
	@RequestMapping(value = "image/{id}")
	public String imageEditor(HttpServletRequest request,@PathVariable Integer id) {
		try {
			List<Map<String, String>> result = cmsService.queryByImageId(id);
			if (result != null) {
				request.setAttribute("imageMap", result.get(0));
			} else {
				request.setAttribute("imageMap", new HashMap<String, String>());
			}
			Map<String, String> cates = selectService.getSelectMap("IMG_CATE");
			request.setAttribute("cates", cates);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("cms/image-detail");
	}

	/**
	 * 会员图像查看
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberImg/{id}")
	public String memberImgEditor(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		Map<String, String> result = cmsService.queryByMemberImageId(id);
		request.setAttribute("result", result);

		return view("cms/memberImg-detail");
	}

	/**
	 * 图片编辑&新增
	 *
	 * @param id
	 * @param name
	 * @param path
	 * @param type
	 * @param sort
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "image/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult imageUpdate() throws SQLException {
		Integer id = requestInt("id");
		String title = requestString("title");
		String path = requestString("imgPath");
		String cate = requestString("cate");
		String sort = requestString("sort");
		String link = requestString("link");
		Long result = 0l;
		if (id > 0l) {
			result = cmsService.updateImage(id, title, path, cate, sort, link);
		} else {
			result = cmsService.addImage(title, path, cate, sort, link);
		}
		return result > 0l ? success("操作成功!") : error("操作失败!");
	}

	/**
	 * 图片查询列表
	 *
	 * @return
	 */
	@RequestMapping(value = "image/list")
	public DataTable imagesearch() {
		return dataTable((params) -> cmsService.queryByImage(params));
	}

	/**
	 * 图片查询列表
	 *
	 * @return
	 */
	@RequestMapping(value = "memberImg/list")
	public DataTable memberImg() {
		return dataTable((params) -> cmsService.queryByMemberImg(params));
	}

	/**
	 * 图片删除
	 *
	 * @return
	 */
	@RequestMapping("image/delete/{id}")
	public @ResponseBody MessageResult imagedelete(@PathVariable Integer id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = cmsService.deleteByImageId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}

	/**
	 * 信息管理列表
	 *
	 * @return
	 */
	@RequestMapping(value = "message/list")
	public DataTable messagelist() {
		return dataTable((params) -> cmsService.queryMessage(params));
	}

	/**
	 * 信息管理view
	 *
	 * @return
	 */
	@RequestMapping(value = "message")
	public String message() {
		return view("cms/message-index");
	}

	/**
	 * 信息管理详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "message/{id}")
	public String messageDetail(HttpServletRequest request,@PathVariable Integer id) {
		try {
			List<Map<String, String>> result = cmsService.queryByMessageId(id);
			request.setAttribute("messageMap", result.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("cms/message-detail");
	}

	/**
	 * 信息保存
	 *
	 * @param id
	 * @param columName
	 * @param content
	 * @param sort
	 * @param publishTime
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("message/update")
	public @ResponseBody MessageResult siteupdate(
			@RequestParam(value = "id", defaultValue = "-1") Integer id,
			@RequestParam(value = "columName") String columName,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "typeId", required = false, defaultValue = "-1") Integer typeId)
			throws SQLException {
		Long result = -1l;
		result = cmsService.updateMessage(id, columName, content, sort, typeId);
		return result > 0l ? success("操作成功!") : error("操作失败!");
	}

	/**
	 * 信息删除
	 *
	 * @return
	 */
	@RequestMapping("message/delete/{id}")
	public @ResponseBody MessageResult messagedelete(@PathVariable Integer id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = cmsService.deleteByMessageId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}

	@RequestMapping(value = "attach")
	public String attachIndex(HttpServletRequest request) throws Exception {
		request.setAttribute("SITE_URL", AppSetting.SITE_HOST);
		return view("cms/attach-index");
	}

	@RequestMapping(value = "attach/list")
	public DataTable attachList() {
		return dataTable((params) -> cmsService.queryAttach(params));
	}

	/**
	 * 附件编辑
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "attach/{id}")
	public String attachDetail(HttpServletRequest request,@PathVariable Integer id) {
		try {
			Map<String, String> result = cmsService.findAttach(id);
			if (result != null) {
				request.setAttribute("attachMap", result);
			} else {
				request.setAttribute("attachMap", new HashMap<String, String>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("cms/attach-detail");
	}

	@RequestMapping(value = "attach/update", method = RequestMethod.POST)
	public @ResponseBody MessageResult attachUpdate(
			@RequestParam(value = "id", required = false, defaultValue = "-1") Integer id,
			@RequestParam(value = "path") String path,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "filename") String filename)
			throws SQLException {
		Long result = 0l;
		if (id > 0l) {
			result = cmsService.updateAttach(id, title, path, filename, sort);
		} else if (id == -1) {
			result = cmsService.addAttach(title, path, filename, sort);
		} else {
			return error("参数异常!");
		}
		return result > 0l ? success("操作成功!") : error("操作失败!");
	}

	@RequestMapping("attach/delete/{id}")
	public @ResponseBody MessageResult attachDelete(@PathVariable int id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = cmsService.deleteByAttachId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}
}
