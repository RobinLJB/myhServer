package com.spark.p2p.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.DB;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spark.p2p.entity.Admin;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.ExcelService;
import com.spark.p2p.service.admin.IphoneAuthAdminService;
import com.spark.p2p.util.AliyunOssUtil;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.POIUtil;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

/**
 *
 *
 */
@Controller
@RequestMapping("/admin/apple/excel")
public class AppleController extends BaseAdminController {

	public static final Log log = LogFactory.getLog(AppleController.class);

	@Autowired
	private IphoneAuthAdminService iphoneAuthAdiminService;
	@Autowired
	private ExcelService excelService;

	private String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
	private String saveImgPath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
	private long maxAllowedImgSize = 1024 * 5000;

	/**
	 * 全部的iphone账号信息
	 *
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "show")
	public String appleShowIndex() throws Exception {
		return view("system/apple-index");
	}

	// 通过苹果id进行删除
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable String id) throws SQLException {
		long ret = iphoneAuthAdiminService.deleteInfoById(id);
		if (ret < 0) {
			DB.rollback();
		}
		return view("system/apple-index");
	}

	@RequestMapping(value = "list")
	public DataTable forIphoneAuditList() {
		Admin admin = getAdmin();
		long roleid = admin.getRoleId();
		return dataTable((params) -> iphoneAuthAdiminService.queryAppleList(params, roleid));
	}

	// icloud图片二次上传
	@RequestMapping(value = "icloudImg/upload")
	@ResponseBody
	public String icloudImgUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile myFile)
			throws Exception {
		log.info("上传中");
		String memberId = request("memberId");
		String iphoneId = request("iphoneId");
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
		String relativePath = PathFormat.parse(parseCatePath(saveImgPath, type), fileName) + '.' + suffix;
		// //磁盘路径
		// String physicalPath =
		// request.getSession().getServletContext().getRealPath("/") + relativePath;
		// BaseState storageState = (BaseState) StorageManager.saveFileByInputStream(is,
		// physicalPath, maxAllowedImgSize);
		AliyunOssUtil.put(relativePath, is);
		is.close();
		// if (storageState.isSuccess()) {
//		String relativeUrl = "/" + relativePath;
//		String absPath = request.getContextPath() + relativeUrl;
		String absPath = AliyunOssUtil.downloadFile(relativePath);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 将图片地址传入到数据库中
		long ret = iphoneAuthAdiminService.updateIcloudAdress(absPath, iphoneId);
		if (ret > 0) {

			return "0";
		} else {
			return "-1";
		}
		// }
		// return "-1";
	}

	@RequestMapping(value = "banner")
	public String bannerImg() {
		return view("system/banner-index");
	}

	@RequestMapping(value = "upload")
	@ResponseBody
	public String appleUpload(MultipartFile myFile) throws Exception {
		log.info("上传中");
		String flag = "0";
		try {
			// 这里得到的是一个集合，里面的每一个元素是String[]数组
			List<String[]> list = POIUtil.readExcel(myFile);
			excelService.saveBath(list); // service实现方法
		} catch (Exception e) {
			flag = "1";
		}
		return flag;
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