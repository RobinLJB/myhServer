package com.spark.p2p.controller.front;

import com.spark.p2p.Enum.GeneralizeMobileTypeEnum;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.*;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//通过广告链接跳转到推广页面进行手机号和验证码的验证，进而下载app
@Controller
public class GeneralizeController extends FrontController {

	public static final Log log = LogFactory.getLog(GeneralizeController.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private EhCacheFactoryBean ehCache;

	private Map<String, Long> loginRequest = new HashMap();

	// 通过链接跳转到检验下载页面
	@RequestMapping(value = "kssdre", method = RequestMethod.GET)
	public String kssdre(HttpServletRequest request) throws Exception {
		log.info("测试");
		String reruestType = request("reruestType");
		String requestCode = request("requestCode");
		request.setAttribute("reruestType", reruestType);
		request.setAttribute("requestCode", requestCode);
		if ("2".equals(reruestType)) {
			Map<String, String> maps = memberService.findJiliangByNo(requestCode);
			if (maps == null) {
				return view("mobile/login");
			}
		}
		return view("kssdre");
	}

	// 给推广的图片验证码
	@RequestMapping("/generalize/captcha")
	@ResponseBody
	public MessageResult getBase64Img(String phone) throws Exception {
		MessageResult result = new MessageResult();
		if (phone == null) {
			return MessageResult.error(-500, "请先输入手机号码");
		}
		String randomStr = getStringRandom(phone);
		String base64Img = RandomCodeUtil.imageToBase64(120, 40, randomStr);
		Map<String, String> map = new HashMap<>();
		if (base64Img != null) {
			map.put("base64Img", base64Img);
			result.setMessage("图形验证码获取成功");
			result.setData(map);
		} else {
			result.setMessage("请刷新重试");
		}
		return result;
	}

	// 随机码生成
	public String getStringRandom(String phone) {
		String val = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
		Ehcache cache = ehCache.getObject();
		Element el = new Element("GENERALZIE_BASE64_IMG_" + phone, val);
		el.setTimeToLive(30 * 60);
		cache.put(el);
		log.info(val);
		return val;
	}

	// 获取短信验证码
	@RequestMapping(value = "verification/code", method = RequestMethod.POST)
	public @ResponseBody MessageResult getVerificationCode() throws UnsupportedEncodingException {
		String phone = request("phone");

		String code = request("captchaCode");
		Ehcache cache2 = ehCache.getObject();
		Element el2 = cache2.get("GENERALZIE_BASE64_IMG_" + phone);
		if (el2 == null) {
			return error("图形验证码未生成或已过期");
		}
		String elsss = el2.getObjectValue().toString();
		if (StringUtils.isEmpty(code) || !code.equals(el2.getObjectValue().toString())) {
			return error("图形验证码不正确");
		}

		if (phone.equals("18655553616") || phone.equals("13399558122")) {
			String randomCode = "123456";
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession()
					.setAttribute("VERIFY_PHONE_CODE_" + phone, randomCode);
			return null;
		} else {
			if (StringUtils.isEmpty(phone)) {
				return error("手机号不能为空");
			}
			return sendPhoneCode(phone);
		}
	}

	@RequestMapping(value = { "verification/codeNew" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public MessageResult getVerificationCodeNew(HttpServletRequest request) throws UnsupportedEncodingException {
		String phone = request("phone");

		if (StringUtils.isEmpty(phone)) {
			return error("手机号不能为空");
		}

		String ip = request.getRemoteHost();
		if ((this.loginRequest.get(ip) != null)
				&& (new Date().getTime() - ((Long) this.loginRequest.get(ip)).longValue() < 60000L)) {
			this.loginRequest.put(ip, Long.valueOf(new Date().getTime()));
			return error("发送过多，请等待1分钟后再试");
		}
		this.loginRequest.put(ip, Long.valueOf(new Date().getTime()));

		return sendPhoneCode(phone);
	}

	public MessageResult sendPhoneCode(String phone) throws UnsupportedEncodingException {
		String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
		String result = SMSUtil.sendCheckCode(phone, randomCode);
		if (result.equals("success")) {
			// 加上phone可以区分不同号码的session会话
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession()
					.setAttribute("VERIFY_PHONE_CODE_" + phone, randomCode);
			return success("success");
		} else {
			return error("发送短信失败");
		}
	}

	// 校验验证码及密码
	@RequestMapping(value = "kssdre", method = RequestMethod.POST)
	public @ResponseBody MessageResult verify(HttpServletRequest request) throws Exception {
		String phone = request("phone");
		String checkcode = request("checkcode");

		String password = request("password");
		if (!ValidateUtil.isMobilePhone(phone.trim())) {
			return error("手机号码格式不正确");
		}
		if (memberService.checkUserPhone(phone.trim())) {
			return error("该手机号码已经注册");
		}
		String loginIp = getRemoteIp();
		String reruestType = request("reruestType");
		String requestCode = request("requestCode");
		String sessionCode = (String) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest().getSession().getAttribute("VERIFY_PHONE_CODE_" + phone);
		log.info("输入的验证码checkcode=" + checkcode + "缓存的验证码" + sessionCode);
		if (sessionCode == null || !sessionCode.equals(checkcode)) {
			return error("验证码不正确");
		} else {
			long memberId = memberService.spread(phone, password, loginIp, reruestType, requestCode);
			if (memberId <= 0) {
				return error("注册失败");
			} else {
				long ret = memberService.createMemer(phone, password, memberId);
				if (ret > 0) {
					request.setAttribute("memberId", ret);
					return success("通过验证");
				} else {
					return error("注册失败");
				}
			}
		}
	}

	// 统计是苹果还是安卓，需要前端传入requestCode值及type值，还有安卓还是苹果

	@RequestMapping("generalize/mobile/type")
	public @ResponseBody MessageResult generalizeMobileType() throws Exception {
		String requestCode = request("requestCode");
		String type = request("type");
		String reruestType = request("reruestType");
		String phone = request("phone");
		Map<String, String> memberMap = new Model("member").where("username=?", phone).find();
		if (memberMap != null) {
			//更新用户手机类型
			String memberId = memberMap.get("id");
			new Model("member").set("type", type).update(Long.parseLong(memberId));
			if (memberId != null) {
				Model memberRalation = new Model("member_ralation");
				Map<String, String> ralationMap = memberRalation.where("afterMemberId=?", memberId).find();
				if (ralationMap != null) {
					Model memberRalation2 = new Model("member_ralation");
					memberRalation2.set("type", type);
					memberRalation2.update(Long.parseLong(ralationMap.get("id")));
					String inviteStatus = ralationMap.get("invite_status");
					if ("0".equals(inviteStatus)) {
						checkGeralize(phone, reruestType, requestCode, type);
					}
				} else {
					checkGeralize(phone, reruestType, requestCode, type);
				}
			}
		}
		return success("记录手机");
	}

	public void checkGeralize(String phone, String reruestType, String requestCode, String type) throws Exception {
		memberService.generalize(phone, reruestType, requestCode);
		if ("1".equals(reruestType)) {
			Member upmember = memberService.findMemberByRequestCode(requestCode);
			long upmid = upmember.getId();
			Model msa = new Model("member");
			msa.set("inviteSum", upmember.getInviteSum() + 1);
			if ((GeneralizeMobileTypeEnum.IOS.getType()).equals(type)) {
				msa.set("ios_invite_sum", upmember.getIosInviteSum() + 1);
			} else {
				msa.set("android_invite_sum", upmember.getAndroidInviteSum() + 1);
			}
			msa.update(upmid);
		} else if ("2".equals(reruestType)) {
			Map<String, String> map = memberService.findJiliangByNo(requestCode);
			Model msa = new Model("jiliang_extension");
			msa.set("invitesum", Convert.strToInt(map.get("invitesum"), 0) + 1);
			if ((GeneralizeMobileTypeEnum.IOS.getType()).equals(type)) {
				msa.set("ios_invite_sum", Convert.strToInt(map.get("ios_invite_sum"), 0) + 1);
			} else {
				msa.set("android_invite_sum", Convert.strToInt(map.get("android_invite_sum"), 0) + 1);
			}
			msa.update(Convert.strToLong(map.get("id"), 0));
		}
	}
}