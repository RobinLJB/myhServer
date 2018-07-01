package com.spark.p2p.controller.ucenter;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.event.MemberEvent;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.EmailUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DataException;

@Controller
@RequestMapping("/ucenter/safety")
public class SafetyController extends UCenterController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberEvent memberEvent;
	
	/**
	 * 安全中心首页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public String safety(HttpServletRequest request) throws Exception {
		// 账户安全信息
		Map<String, String> safety = memberService.findSafetyInfo(getUser().getId());
		request.setAttribute("safety", safety);
		return view("safety-index");
	}

	/**
	 * 绑定银行卡
	 */
/*
	@RequestMapping(value = "bindBank", method = RequestMethod.GET)
	public String bindBank() throws Exception {
		request.setAttribute("user", memberService.findMember(getUser().getId()));
		return view("safety-bindBank");
	}
*/
/*
	@RequestMapping(value = "bindBank", method = RequestMethod.POST)
	public @ResponseBody MessageResult doBindBank() throws Exception {
		MessageResult result = null;
		String sncode = request("sncode");
		if (!sncode.equals(session.getAttribute("BINDBACK_" + getUser().getMobilePhone()))) {
			return error("手机验证码不正确");
		}
		String bank_account_no = request("bank_account_no");
		String bankCode = request("bank_code");
		String province = request("province");
		String city = request("city");
		
		result = null;//sinaPayService.addBankCard(getUser().getId(),bank_account_no,bankCode,province,city, getRemoteIp());
		return result;
	}*/

	/**
	 * 修改登录密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "passwd", method = RequestMethod.GET)
	public String password() {
		return view("safety-passwd");
	}

	@RequestMapping(value = "passwd", method = RequestMethod.POST)
	public @ResponseBody MessageResult updatePwd() throws Exception {
		String originPasswd = request("originPasswd");
		String newPasswd = request("newPasswd");
		if (StringUtils.isEmpty(originPasswd) || StringUtils.isEmpty(newPasswd)) {
			return error("原始密码和新密码不能为空");
		}
		long uid = getUser().getId();
		if (!memberService.checkPasswd(uid, originPasswd)) {
			return error("原始密码不正确");
		}
		long ret = memberService.updatePasswd(uid, newPasswd);
		if (ret > 0)
			return success("修改成功");
		else
			return error("更改失败");
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "dealpwd", method = RequestMethod.GET)
	public String dealpasswd() {
		return view("safety-dealpwd");
	}

	@RequestMapping(value = "dealpwd", method = RequestMethod.POST)
	public @ResponseBody MessageResult updateDealpwd() throws SQLException {
		String dealpwd = request("originPasswd");
		String newPasswd = request("newPasswd");
		if (StringUtils.isEmpty(dealpwd) || StringUtils.isEmpty(newPasswd)) {
			return error("原始密码和新密码不能为空");
		}
		long uid = getUser().getId();
		if (!memberService.checkDealpwd(uid, dealpwd)) {
			return error("原始密码不正确");
		}
		long ret = memberService.updateDealpwd(uid, newPasswd);
		if (ret > 0)
			return success("修改成功");
		else
			return error("更改失败");
	}

	/**
	 * 实名认证 页面显示 guokui
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "realname", method = RequestMethod.GET)
	public String realName(HttpServletRequest request) throws Exception {
		Map<String, String> person = memberService.findRealName(getUser().getId());
		if (person == null || person.isEmpty()) {
			request.setAttribute("person", new HashMap<String, String>());
		} else {
			request.setAttribute("person", person);
		}
		return view("safety-realname");
	}

	/**
	 * 实名认证提交处理 guokui
	 * 
	 * @throws SQLException
	 * 
	 */
	@RequestMapping(value = "realname", method = RequestMethod.POST)
	public @ResponseBody MessageResult realNameAuth() throws Exception {
		long userid = getUser().getId();
		String realName = request("realName");
		String idNo = request("idNo");
		long status = -1;
		if (!ValidateUtil.isCard(idNo)) {
			return error("请输入正确的身份证格式");
		}
		MessageResult result =null;// sinaPayService.setRealName(userid, realName, idNo, this.getRemoteIp());
		if (result.getCode() == 0) {
			status = memberService.realNameAuth(userid, realName, idNo, 1);
		}
		return status > 0 ? success(result.getMessage()) : error(result.getMessage());
	}

	
	@RequestMapping("realNameAuthentication")
	public @ResponseBody MessageResult realNameAuthentication() throws Exception {
		long userid = getUser().getId();
		String realName = request("realName");
		String idNo = request("idNo");
		String cardFrontImg = request("cardFrontImg");
		String cardBackImg = request("cardBackImg");
		if (StringUtils.isEmpty(idNo) || StringUtils.isEmpty(realName)) {
			return error("真实姓名和身份证号不能为空！");
		}
		if (StringUtils.isEmpty(cardFrontImg) || StringUtils.isEmpty(cardBackImg)) {
			return error("请上传身份证正反面照片");
		}
		long ret = memberService.realNameAuth(userid, realName, idNo, 2);
		return success("修改成功");
	}

	/**
	 * 手机绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mobile")
	public String mobile(HttpServletRequest request) throws Exception {
		if (checkAuthority()) {
			return "redirect:";
		}
		String phone = memberService.getMobilePhone(getUser().getId());
		request.setAttribute("mobilePhone", phone);
		return view("safety-mobile");
	}

	/**
	 * 修改手机绑定
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@RequestMapping(value = "mobile", method = RequestMethod.POST)
	public @ResponseBody MessageResult mobileUpdate() throws SQLException, DataException {
		String code = (String) session.getAttribute("PHONE_CODE_UPDATEPHONE");
		String sncode = request("sncode");
		String phone = request("phone");
		if (sncode.equals(code)) {
			long ret = memberService.updateMobilePhone(getUser().getId(), phone, getUser().getUsername());
			if (ret > 0)
				return success("操作成功");
			else
				return error("操作失败");
		} else {
			return error("验证码错误");
		}
	}

	/**
	 * 邮箱绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "email", method = RequestMethod.GET)
	public String email(HttpServletRequest request) throws Exception {
		String email = memberService.getEmail(getUser().getId());
		request.setAttribute("email", email);
		return view("safety-email");
	}

	/**
	 * 修改&绑定邮箱
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "email", method = RequestMethod.POST)
	public @ResponseBody MessageResult updateEmail() throws Exception {
		String email = request("email");
		String code = (String) session.getAttribute("PHONE_CODE_EMAIL");
		String sncode = request("sncode");
		if (!sncode.equals(code)) {
			return error("验证码错误");
		}
		long ret = memberService.updateEmail(getUser().getId(), email);

		return ret > 0 ? success("修改成功") : error("系统错误");
	}

	/**
	 * 检查验证码是否正确
	 * 
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	public @ResponseBody MessageResult checkCode() {
		String verify = Convert.strToStr(request("verify"), "normal");
		String code = (String) session.getAttribute("PHONE_CODE_" + verify);
		String sncode = request("sncode");
		if (sncode.equals(code)) {
			session.setAttribute("PHONE_CODE_" + verify, verify);
			return success("验证成功");
		} else {
			return error("验证码错误");
		}
	}

	/**
	 * 验证安全修改权限
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "auth", method = RequestMethod.GET)
	public String auth(HttpServletRequest request) throws Exception {
		String mobilePhone = memberService.getMobilePhone(getUser().getId());
		// 查询用户手机号、email
		request.setAttribute("phone", mobilePhone);
		request.setAttribute("tool", "");
		request.setAttribute("redirect", request.getParameter("redirect"));
		return view("safety-auth");
	}

	@RequestMapping(value = "auth", method = RequestMethod.POST)
	public @ResponseBody MessageResult doAuth() {
		String mcode = request("code");
		String tool = request("tool");
		String sessionCode = (String) session.getAttribute("AUTH_PHONE_CODE_" + getUser().getMobilePhone());
		if (mcode.equals(sessionCode)) {
			JSONObject subject = new JSONObject();
			// expire-time以毫秒为单位,10分钟内有效
			subject.put("expireTime", new Date().getTime() + 10 * 60000);
			subject.put("token", "AUTH");
			session.setAttribute("AUTH_TOKEN", subject.toString());
			return success("success");
		} else
			return error("验证失败");
	}

	/**
	 * 检查当前用户是否有修改敏感资料的权限
	 * 
	 * @return
	 */
	private boolean checkAuthority() {
		String token = (String) session.getAttribute("AUTH_TOKEN");
		if (StringUtils.isBlank(token)) {
			return false;
		}
		JSONObject subject = new JSONObject(token);
		long ticks = new Date().getTime();
		long expireTime = subject.getLong("expireTime");
		return ticks < expireTime;
	}

}
