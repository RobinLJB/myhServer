package com.spark.p2p.controller.ucenter;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.member.Bank;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.event.MemberEvent;
import com.spark.p2p.exception.AuthenticationException;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.MemberAdminService;
import com.spark.p2p.util.AppSessionUtil;
import com.spark.p2p.util.EmailUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.IdCard;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;

@Controller
public class MemberController extends UCenterController {
	private static Log log = LogFactory.getLog(MemberController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberAdminService userService;
	@Autowired
	private IndexService indexService;
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute(Const.SESSION_MEMBER);
		return "redirect:/ucenter/login.html";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		if (session.getAttribute(Const.SESSION_MEMBER) != null) {
			return "redirect:/mobile/home.html";
		}
		String reruestType =  request("reruestType");
		String requestCode =  request("requestCode");
		request.setAttribute("redirect", request.getParameter("redirect"));
		request.setAttribute("reruestType",reruestType);
		request.setAttribute("requestCode",requestCode);
		return view("ucenter/login");
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) throws Exception {
		Member member=(Member) session.getAttribute(Const.SESSION_MEMBER);
		request.setAttribute("member",member);
		return view("ucenter/home");
	}

	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody MessageResult doLogin() {
		
		
		String username = request("username");
		String checkcode = request("checkcode");
		//12.28添加
		String pwd=request("password");
		String reruestType =  request("reruestType");
		String requestCode =  request("requestCode");
		if (!StringUtils.isNotBlank(username)) {
			return error("用户名不能为空!");
		}
		String sessionCode = (String) session.getAttribute("LOGIN_PHONE_CODE_" + username);
		if(sessionCode == null || !sessionCode.equals(checkcode)){
			//return error("验证码不正确");
		}
		long memberId = -1;
		try {
			String loginIp = getRemoteIp();
			memberId = memberService.spread(username, pwd,loginIp,reruestType,requestCode);
			// 获取用户信息
			Member member = userService.findByMemberId(memberId);
			session.setAttribute(Const.SESSION_MEMBER, member);

		} catch (AuthenticationException e) {
			return error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return error("系统错误");
		}
		return success("success");
	}
	

	/**
	 * 发送短信
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "sendPhoneCode2", method = RequestMethod.POST)
	public @ResponseBody MessageResult sendPhoneCodeDo() throws UnsupportedEncodingException{
		String phone=request("phone");
		if(StringUtils.isEmpty(phone)){
			return error("手机号不能为空");
		}
	
		return sendPhoneCode(phone);
	}
	
	public MessageResult sendPhoneCode(String phone) throws UnsupportedEncodingException {
		String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
		String result = SMSUtil.sendCheckCode(phone, randomCode);
		if (result.equals("success")) {
			session.setAttribute("LOGIN_PHONE_CODE_"+phone, randomCode);
			return success("success");
		} else {
			return error("发送短信失败");
		}
	}

	
	/**
	 * 发送邮件
	 * @param email
	 * @return
	 * @throws Exception
	 
	public MessageResult sendEmailCode(String email) throws Exception {
		int intCount = 0;
		intCount = (new Random()).nextInt(999999);// 最大值位9999
		if (intCount < 100000) {
			intCount += 100000; // 最小值位100001
		}
		String code = String.valueOf(intCount);
		String msg = "您好！<br/>感谢您使用优选理财网络借贷平台，我们将竭诚为您服务！本次验证码为:" + code;
		EmailUtil emailUtil = new EmailUtil();
		log.info("sendTo:"+email+";Content:"+msg);
		emailUtil.sendEmail("邮箱绑定验证!", email, msg);
		session.setAttribute("email", code);
		return success("success");
	}
*/
	
	
	

	
	/**
	 * 用户证件图片上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "infoimg", method = RequestMethod.GET)
	public String infoimg() {
		return view("home-img");
	}
	
	@RequestMapping(value="updateHeadImg",method=RequestMethod.POST)
	public @ResponseBody MessageResult updateHeadImg() throws Exception{
		String headImg = request("headImg");
		long ret = memberService.updateMemberHeadImg(getUser().getId(),headImg);
		if(ret>0){
			Member member = memberService.findMember(getUser().getId());
			session.setAttribute(Const.SESSION_MEMBER, member);
			return success("修改成功");
		}
		return error("修改失败");
	}
	
	
	/**
	 * 绑定微信,获取头像
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bindWeixin/{wid}", method = RequestMethod.GET)
	public String bindWeixin(HttpServletRequest request,@PathVariable String wid) throws Exception {
		MessageResult mr = new MessageResult(500,"绑定失败");
		if(StringUtils.isBlank(wid)){
			mr.setCode(400);
			mr.setMessage("缺少参数");
		}
		else{
			Member member = this.getUser();
			ApiConfig cfg = new ApiConfig(AppSetting.WEIXIN_APPID,AppSetting.WEIXIN_KEY);
			UserAPI userApi = new UserAPI(cfg);
			GetUserInfoResponse  info = userApi.getUserInfo(wid);
			memberService.updateMemberWechatId(member.getId(), wid,info.getHeadimgurl());
			if(StringUtils.isBlank(member.getAvatar())){
				memberService.updateMemberHeadImg(member.getId(),info.getHeadimgurl());
				member.setAvatar(info.getHeadimgurl());
			}
			mr.setCode(0);
			mr.setMessage("绑定成功");
		}
		request.setAttribute("result", mr);
		return viewMobile("bindWeixin");
	}
	
	
	
	
	/**
	 * 平台建议
	 * @throws SQLException 
	 */
	@RequestMapping(value="adviseSubmit",method=RequestMethod.POST)
	public @ResponseBody MessageResult adviseSubmitPost(
			@RequestParam("content") String content) throws UnsupportedEncodingException, SQLException {
		Member member =(Member) session.getAttribute(Const.SESSION_MEMBER);
		if(content==null || content.length()==0){
			return error("请输入内容");
		}
		long ret=memberService.insertAdvisePlat(content,member.getId());
		if(ret>0){
			return success("保存成功");
		}else{
			return error("保存失败");
		}
		
	}
	
	
	@RequestMapping({ "adviseSubmit" })
	public String adviseSubmitGet() throws Exception {
		return view("advise-plat");
	}
	

	
	
}
