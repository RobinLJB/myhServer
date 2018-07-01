/**
 * 
 */
package com.spark.p2p.controller.ucenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;
import com.jcraft.jsch.ChannelSftp;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.CompanyProfile;
import com.spark.p2p.entity.Coupon;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.entity.PersonProfile;
import com.spark.p2p.service.CouponService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.MerchantService;
import com.spark.p2p.service.ProfileService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ZipUtil;
import com.sparkframework.lang.Convert;

/**
 * @author yanqizheng
 */
@Controller
public class ProfileController extends UCenterController{
	
	public static final Log log = LogFactory.getLog(ProfileController.class);
	
	private String saveImgPath = "data/company/U{:uid}/{:cate}";
	private String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
	private long maxAllowedImgSize = 1024 * 1000;
	@Autowired
	private CouponService couponService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private MemberService mbService;
	@Autowired
	private MerchantService merchantService;
	
	/**
	 * 会员推荐人列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("friend")
	public String friend(HttpServletRequest request) throws Exception{
		Member user = getUser();
		request.setAttribute("userId", user.getId());
		return view("myaccount-firend");
	}
	@RequestMapping("friend/list")
	public DataTable friendList(){
		return dataTable((params)->profileService.queryFriends(params,getUser().getId()));
	}	
	
	/**
	 * 个人、企业详细信息
	 * profile-info
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(HttpServletRequest request) throws Exception {
		Member member=memberService.findMember(getUser().getId());
		String profile = memberService.findMemberProfile(getUser().getId());
		if(profile!="" && profile!=null){
			JSONObject json = new JSONObject(profile);
			request.setAttribute("profile", json);
		}
		//是否通过实名验证
		Map<String,String> identno1 =  mbService.findMemberInfo(getUser().getId());
		return "info";
	}

	
	/**
	 * 用户详细资料提交
	 * @param loaner
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "person/profile", method = RequestMethod.POST)
	public @ResponseBody MessageResult doInfo(@ModelAttribute("personProfile") PersonProfile profile) throws Exception {
		String workCity=profile.getProvince()+"/"+profile.getCity();
		profile.setWorkCity(workCity);
		JSONObject json = new JSONObject(profile);
		Map<String,String> map=memberService.findMemberInfoAudit(getUser().getId());
		log.info(json.toString());
		long ret = memberService.updateMemberInfo(getUser().getId(), json.toString());
		if(ret>0){
			if(map==null){
				//新增
				ret=memberService.insertMemberInfoCheck(profile,getUser().getId());
			}else{ 
				int ids=Convert.strToInt(map.get("id"), 0);
				//修改
				ret=memberService.updateMemberInfoCheck(profile,ids,getUser().getId());
			}
		}
		return ret > 0 ? success("提交成功") : error("提交失败");
	}
	
	

	/**
	 * 个人信用审核 
	 * yuweisong
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("creditre")
	public String creditre(HttpServletRequest request) throws Exception{
		request.setAttribute("profile2",memberService.creditreview(getUser().getId()));
		return view("profile-creditre");
	}
	
	@RequestMapping(value="creditreview",method=RequestMethod.POST)
	public @ResponseBody MessageResult addLoanApplication() throws Exception{	
		String idcard = request("idcard");
		String booklet = request("booklet");
		String earning = request("earning");
		String credit = request("credit");
		String house = request("house");
		String work = request("work");
		Map<String, String> map = memberService.creditreview(getUser().getId());
			long ret = 0;
			if(map==null){
				//新增
				ret=memberService.insertcreditreview(getUser().getId(),idcard,booklet,earning,credit,house,work);
			}else{ 
				//修改
				ret=memberService.updatecreditreview(getUser().getId(),idcard,booklet,earning,credit,house,work);
			}
			log.info("====================="+ret+"-----------------------------");
			return ret > 0 ? success("提交成功") : error("提交失败");
		
	}
	
	
	/*我的卡券*/
	@RequestMapping("card")
	public String card(HttpServletRequest request) throws Exception{
		long uid = getUser().getId();
		//红包
		List<Coupon> hongbaoList = couponService.queryMemberCoupon(uid,1);
		request.setAttribute("hongbaoList",hongbaoList);
		return view("card");
	}
	
	public boolean checkCompanyFile(String path,CompanyProfile profile){
		return new File(path).exists();
	}
	
	/**
	 * 企业资料上传
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "upload/file", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String type = request("type");
		log.info(type);
		Member membmer = getUser();
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
			if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
				out.write(MessageResult.error(401, "不允许的图片格式，请上传" + allowedImgFormat + "格式！").toString());
				return ;
			}
			InputStream is = fileStream.openStream();
			// 相对工程路径
			String relativePath =  PathFormat.parse(parseCatePath(saveImgPath,type,membmer.getId()),fileName) + suffix;
			//磁盘绝对路径
			String physicalPath = request.getSession().getServletContext().getRealPath("/") + relativePath;
			log.info(physicalPath);
			File targetFile = new File(physicalPath);
			if( targetFile.exists()){
				targetFile.delete();
			}
			BaseState storageState = (BaseState) StorageManager.saveFileByInputStream(is,physicalPath, maxAllowedImgSize);
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
	/**
	 * 解析路径
	 * @param origin
	 * @param type
	 * @param uid
	 * @return
	 */
	private String parseCatePath(String origin,String type,long uid) {
		String result = origin.replaceFirst("\\{:cate\\}", type);
		result = result.replaceFirst("\\{:uid\\}", String.valueOf(uid));
		return result;
	}
	 
}
