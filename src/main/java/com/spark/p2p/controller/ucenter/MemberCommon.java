package com.spark.p2p.controller.ucenter;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.EmailUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.lang.Convert;
@Controller
public class MemberCommon  extends UCenterController{
	
	@Autowired
	private MemberService memberService;





	
	
	
	/**
	 * 获取邮箱验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="sendEmail",method=RequestMethod.POST)
	public @ResponseBody MessageResult getEmailCode() throws Exception{
		String email = request("email");
		String verify = Convert.strToStr(request("verify"), "normal");
		String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
		String msg = "尊敬的"+getUser().getUsername()+",您好！<br/>感谢您使用功德融网络借贷平台，我们将竭诚为您服务！本次验证码为:"+randomCode;
		EmailUtil emailUtil = new EmailUtil();
		emailUtil.sendEmail("【功德融】邮箱绑定验证!",email,msg);		 
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute("PHONE_CODE_"+verify, randomCode);
		return success("success");
	}
}
