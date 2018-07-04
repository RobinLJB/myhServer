package com.spark.p2p.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

@SuppressWarnings("AlibabaClassMustHaveAuthor")
@Controller
public class UploadController extends BaseController{
	private static Log log = LogFactory.getLog(UploadController.class);
	private String saveImgPath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
	private String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
	private long maxAllowedImgSize = 1024 * 5000;
	private String saveAttachPath = "data/attach/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
	private String allowedAttachFormat = ".pdf,.apk,.doc,.docx,.xls,.xlsx,.jpg,.gif,.png,.zip";
	private long maxAllowedAttachSize = 20 * 1024 * 1000;
	
	/**
	 * 单文件上传
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "upload/image", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		log.info("--------------------------上传图片");
		int type = Convert.strToInt(request.getParameter("type"), 0);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//if (!ServletFileUpload.isMultipartContent(request)) {
		//	out.write(error(500, "表单格式不正确").toString());
		//	return ;
		//}
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		FileItemIterator iterator;
		FileItemStream fileStream = null;
		try {
			iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField())break;
				fileStream = null;
			}
			if (fileStream == null) {
				out.write(error(500, "未找到上传数据").toString());
				return ;
			}
			String fileName = fileStream.getName();
			// 获取文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
				out.write(MessageResult.error(401, "不允许的图片格式，请上传" + allowedImgFormat + "格式！").toString());
				return ;
			}
			InputStream is = fileStream.openStream();
			// 相对工程路径
			String relativePath =  PathFormat.parse(parseCatePath(saveImgPath,type),fileName) + suffix;
			//磁盘绝对路径
			String physicalPath = request.getSession().getServletContext().getRealPath("/") + relativePath;
			log.info("--------------------------上传图片----地址"+physicalPath);
			BaseState storageState = (BaseState) StorageManager.saveFileByInputStream(is,physicalPath, maxAllowedImgSize);
			is.close();
			if (storageState.isSuccess()) {
				String relativeUrl = "/" + relativePath;
				String absPath = request.getContextPath() + relativeUrl;
				log.info("--------------------------上传图片----地址absPath----"+absPath);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				out.write(success(relativeUrl, absPath).toString());
			}
			else {
				out.write(error(500, storageState.getInfo()).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fileStream = null;
		}
	}
	/**
	 * 处理ueditor 后台动作
	 */
	@RequestMapping("ueditor/dispatch")
	public void ueditor(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String cfgPath = rootPath + "WEB-INF/classes/ueditor";
			String result = new ActionEnter(request, cfgPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 附件上传
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "upload/attach", method = RequestMethod.POST)
	public void uploadAttach(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		log.info("附件上传");
		int type = Convert.strToInt(request.getParameter("type"), 0);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (!ServletFileUpload.isMultipartContent(request)) {
			out.write(error(500, "表单格式不正确").toString());
			return ;
		}
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		FileItemIterator iterator;
		FileItemStream fileStream = null;
		try {
			iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField())break;
				fileStream = null;
			}
			if (fileStream == null) {
				out.write(error(500, "未找到上传数据").toString());
				return ;
			}
			String fileName = fileStream.getName();
			// 获取文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if (!allowedAttachFormat.contains(suffix.trim().toLowerCase())) {
				out.write(MessageResult.error(401, "不允许的附件格式，请上传" + allowedAttachFormat + "格式！").toString());
				return ;
			}
			InputStream is = fileStream.openStream();
			// 相对工程路径
			String relativePath =  PathFormat.parse(parseCatePath(saveAttachPath,type),fileName) + suffix;
			//磁盘绝对路径
			String physicalPath = request.getSession().getServletContext().getRealPath("/") + relativePath;
			
			BaseState storageState = (BaseState) StorageManager.saveFileByInputStream(is,physicalPath, maxAllowedAttachSize);
			is.close();
			if (storageState.isSuccess()) {
				String relativeUrl = "/" + relativePath;
				String absPath = request.getContextPath() + relativeUrl;
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				out.write(success(relativeUrl, absPath).toString());
			}
			else{
				out.write(error(500,storageState.getInfo()).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fileStream = null;
		}
	}

	private JSONObject success(String relativePath, String absPath) {
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("relativePath", relativePath);
		obj.put("absPath", absPath);
		return obj;
	}

	private String parseCatePath(String origin,int type) {
		String replacement = "default";
		if (type == 1){
			replacement =  "member";
		}
		else if (type == 2){
			replacement =  "banner";
		}
		else if (type == 3){
			replacement =  "cms";
		}
		else if(type == 4){
			replacement = "company_info";
		}
		return origin.replaceFirst("\\{:cate\\}", replacement);
	}

}
