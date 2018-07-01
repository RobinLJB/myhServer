package com.spark.p2p.controller.wenxin;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.exception.AuthenticationException;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.ShuJuMoHeService;
import com.spark.p2p.service.admin.MemberAdminService;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Controller
public class WeixinMemberController extends WeixinBaseController{
	
	Logger logger= LoggerFactory.getLogger(WeixinMemberController.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberAdminService userService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private ShuJuMoHeService shuJuMoHeSerrvice;
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute(Const.SESSION_MEMBER);
		return "redirect:/mobile/login.html";
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
		member=memberService.findMember(member.getId());
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		member.setCommisionSum(Convert.strToDouble(df.format(member.getCommisionSum()), 0));; 
		request.setAttribute("member",member);
		return view("ucenter/home");
	}






	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody MessageResult doLogin() {
		
		String username = request("username");
		String checkcode = request("checkcode");
		String reruestType =  request("reruestType");
		String requestCode =  request("requestCode");
		if (!StringUtils.isNotBlank(username)) {
			return error("用户名不能为空!");
		}
		List<String> ignorePhoneList = Arrays.asList(AppSetting.IGNORE_PHONE_LIST);
		if(!ignorePhoneList.contains(username)){
			String sessionCode = (String) session.getAttribute("LOGIN_PHONE_CODE_" + username);
			if(sessionCode == null || !sessionCode.equals(checkcode)){
				return error("验证码不正确");
			}
		}
		long memberId = -1;
		try {
			String loginIp = getRemoteIp();
			if("2".equals(reruestType)){
				Map<String, String> maps=memberService.findJiliangByNo(requestCode);
				if(maps==null){
					return error("推广链接编号不正确，请联系客服");
				}
			}
			if("1".equals(reruestType)){
				Map<String, String> maps=new Model("member").where("memberNo= ?",requestCode).find();
				if(maps==null){
					return error("邀请用户不存在，请检查邀请链接");
				}
			}
			memberId = memberService.spread(username, "password",loginIp,reruestType,requestCode);
			// 获取用户信息
			Member member = userService.findByMemberId(memberId);
			member.setCommicateDetail("");
			session.setAttribute(Const.SESSION_MEMBER, member);

		} catch (AuthenticationException e) {
			return error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return error("系统错误");
		}
		return success("success");
	}
	
	@RequestMapping(value = "/tongdun/notify", method = RequestMethod.POST)
	public @ResponseBody MessageResult pageNotify() throws Exception {
		logger.info("-------------回调开始start----------------------");
		// String notifyEvent=request("notify_event");
		String notifyData = request("notify_data");
		logger.info("notifyData" + notifyData);
		logger.info("notify:"+notifyData);
		org.json.JSONObject obj = new org.json.JSONObject(notifyData);
		int code = (int) obj.getInt("code");
		logger.info("运营商报告数据data"+obj.getJSONObject("data"));
		String phone = obj.getJSONObject("data").getString("user_mobile");
		logger.info("手机号"+phone);
		Member member=memberService.findMemberByPhonea(phone);
		long memberId=member.getId();
		logger.info(""+memberId);
		logger.info("code" + code);
		String taskId = obj.getString("task_id");
		logger.info("taskId" + taskId);
		if (code == 0) {
			shuJuMoHeSerrvice.updatePhoneStatus(memberId);
			shuJuMoHeSerrvice.saveTaskId(memberId, taskId);
			return success("认证成功");
		} else {
			return error("认证失败");
		}
		}

	//同步跳转页面
	@RequestMapping("callback-interact/{uid}")
	public synchronized String callbackInteract(HttpServletRequest request,@PathVariable("uid") Long uid) throws Exception{
		Map<String,String> params = getParameters();
		request.setAttribute("result", success("授权成功"));
		request.setAttribute("uid",uid);
		request.setAttribute("params",params);
		return view("mobile/front/result-interact");
	}

	/**
	 *
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
