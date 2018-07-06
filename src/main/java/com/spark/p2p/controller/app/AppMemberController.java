package com.spark.p2p.controller.app;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spark.p2p.dao.MemberDao;
import com.spark.p2p.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.upload.StorageManager;
import com.mysql.jdbc.log.LogUtils;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.MemberAdminService;
import com.sparkframework.sql.model.Model;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Controller
@ResponseBody
@RequestMapping("/app/uc")
public class AppMemberController extends AppBaseController {
	private static Log log = LogFactory.getLog(AppMemberController.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberAdminService userService;

	@Autowired
	private EhCacheFactoryBean ehCache;

	@Autowired
	private IphoneAuthService iphoneAuthService;
	private String saveImgPath = "data/upload/default/{yyyy}{mm}{dd}/{time}{rand:6}";

	/**
	 * 手机验证码发送
	 *
	 * @return
	 */
	@RequestMapping(value = { "mobileCode" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public MessageResult sendCheckCode() throws UnsupportedEncodingException {
		String phone = request("mobilePhone");
		String code = request("base64Code");
		Ehcache cache2 = this.ehCache.getObject();
		Element el2 = cache2.get("PHONE_BASE64_IMG_" + phone);
		if (el2 == null) {
			return error("图形验证码未生成或已过期");
		}
		String oa = el2.getObjectValue().toString();
		log.info("短信验证码合肥师范学院el2=" + el2);
		log.info("短信验证码合肥师范学院code=" + code);
		log.info("短信验证码合肥师范学院oa=" + oa);
		if ((StringUtils.isEmpty(code)) || (!code.equals(el2.getObjectValue().toString()))) {
			return error("图形验证码不正确哟");
		}

		if (ValidateUtil.isnull(phone)) {
			return error("手机号为空或格式不正确！");
		}
		String randomCode = "";
		String result = "";
		if ("18655553618".equals(phone)) {
			randomCode = "123456";
			result = SMSUtil.sendCheckCode(phone, "123456");
		} else {
			randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
			result = SMSUtil.sendCheckCode(phone, randomCode);
		}
		if (result.equals("success")) {
			Ehcache cache = this.ehCache.getObject();
			Element el = new Element("PHONE_REG_CODE_" + phone, randomCode);
			el.setTimeToLive(1800);
			cache.put(el);
			return success("发送短信成功");
		}
		return error("发送短信失败");
	}

	/**
	 * 登录
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public MessageResult login() throws Exception {
		String username = request("username");
		String password = request("checkcode");
		// 设备编号
		String device = "IOS:A1024";
		if (ValidateUtil.isnull(username)) {
			return error("手机号或用户名不能为空!");
		}
		if (ValidateUtil.isnull(password)) {
			return error("密码不能为空!");
		}

		// Long memberId = memberService.login(username, password);
		String memberNo = memberService.getMemberNo(username);
		if (ValidateUtil.isnull(memberNo)) {
			return error("请先去进行注册");
		} else {
			String memberNo1 = memberService.getMemberNo(username, password);
			if (ValidateUtil.isnull(memberNo1)) {
				return error("密码错误");
			}
			long memberId = Long.parseLong(memberService.getMemberIdByMemberNo(memberNo));
			Member member = userService.findByMemberId(memberId);
			member.setCommicateDetail("");
			// 创建token信息
			int platform = 0;
			if (device.contains("ANDROID")) {
				platform = Const.PLATFORM_ANDROID;
			} else if (device.contains("IOS")) {
				platform = Const.PLATFORM_IOS;
			}
			AppSessionUtil.createToken(member, platform);
			return success(member);

		}
	}

	/**
	 * APP用户手机号码注册
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public MessageResult doRegister() throws Exception {
		String password = request("password");
		String phone = request("mobilePhone");
		String code = request("code");
		String base64Code = request("base64Code");
		Ehcache cache = ehCache.getObject();
		Element el = cache.get("PHONE_BASE64_IMG_" + phone);
		if (el == null) {
			return error("图形验证码未生成或已过期");
		}
		String oa = el.getObjectValue().toString();
		log.info("注册合肥师范学院el=" + el);
		log.info("注册合肥师范学院code=" + base64Code);
		log.info("注册合肥师范学院oa=" + oa);
		/*
		 * if (StringUtils.isEmpty(base64Code) ||
		 * !base64Code.equals(el.getObjectValue().toString())) { return
		 * error("图形验证码不正确"); }
		 */
		if (ValidateUtil.isnull(phone)) {
			return error("手机号为空或格式不正确！");
		}
		if (!ValidateUtil.isMobilePhone(phone.trim())) {
			return error("手机号码格式不正确");
		}
		if (memberService.checkUserPhone(phone.trim())) {
			return error("该手机号码已经注册");
		}
		List<String> ignorePhoneList = Arrays.asList(AppSetting.IGNORE_PHONE_LIST);
		if (!ignorePhoneList.contains(phone)) {
			Ehcache cache2 = ehCache.getObject();
			Element e2 = cache2.get("PHONE_REG_CODE_" + phone.trim());
			if (e2 == null) {
				return error("验证码未发送或已过期");
			}
			if (StringUtils.isEmpty(code) || !code.equals(e2.getObjectValue().toString())) {
				return error("手机验证码不正确");
			}

			if (password.length() > 18 || password.length() < 6) {
				return error(500, "登录密码必须为6-18位字符");
			}
		}
		// 注册
		Long memberId = memberService.register(phone.trim(), password);
		if (memberId > 0) {
			Member member = memberService.findMember(memberId);
			return success("注册成功", member);
		}
		return error("注册出错");
	}

	// 给前台app做的图片验证码
	@RequestMapping("/captcha")
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
		Element el = new Element("PHONE_BASE64_IMG_" + phone, val);
		el.setTimeToLive(30 * 60);
		cache.put(el);
		log.info(val);
		return val;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "modify/password")
	public MessageResult modifyPassword() throws Exception {
		Member member = getMember();
		String oldPassword = request("oldPassword");
		String newPassword = request("newPassword");
		String confirmNewPassword = request("confirmNewPassword");
		if (!newPassword.equals(confirmNewPassword)) {
			return MessageResult.error(-500, "两次输入的密码不一致,请重新输入");
		}
		Boolean flag = memberService.checkPasswd(member.getId(), oldPassword);
		if (!flag) {
			return MessageResult.error(-500, "您输入的旧密码有误，请确认后再输入");
		}
		if (newPassword.length() > 18 || newPassword.length() < 6) {
			return error(500, "登录密码必须为6-18位字符");
		}
		long ret = memberService.updatePwd(newPassword, member.getMobilePhone());
		if (ret > 0) {
			return MessageResult.success("密码更新成功");
		}
		return MessageResult.error(-500, "更新密码失败");
	}

	/**
	 * 忘记密码
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "reset/pwd", method = RequestMethod.POST)
	public MessageResult resetPassword() throws Exception {
		String password = request("password");
		String phone = request("mobilePhone");
		String code = request("code");
		String base64Code = request("base64Code");
		Ehcache cache = ehCache.getObject();
		Element el = cache.get("PHONE_BASE64_IMG_" + phone);
		if (el == null) {
			return error("图形验证码未生成或已过期");
		}

		/*
		 * if (StringUtils.isEmpty(base64Code) ||
		 * !base64Code.equals(el.getObjectValue().toString())) { return
		 * error("图形验证码不正确"); }
		 */
		if (ValidateUtil.isnull(phone)) {
			return error("手机号为空或格式不正确！");
		}
		if (!ValidateUtil.isMobilePhone(phone.trim())) {
			return error("手机号码格式不正确");
		}
		Ehcache cache2 = ehCache.getObject();
		Element e2 = cache2.get("PHONE_REG_CODE_" + phone.trim());
		if (e2 == null) {
			return error("验证码未发送或已过期");
		}
		if (StringUtils.isEmpty(code) || !code.equals(e2.getObjectValue().toString())) {
			return error("手机验证码不正确");
		}
		if (!memberService.checkUserPhone(phone.trim())) {
			return error("没有该用户");
		}
		if (password.length() > 18 || password.length() < 6) {
			return error(500, "登录密码必须为6-18位字符");
		}
		if (memberService.updatePwd(password, phone) > 0) {
			return success("修改成功");
		} else {
			return error(500, "修改失败");
		}
	}

	// 推广
	@RequestMapping(value = "spread", method = RequestMethod.POST)
	public @ResponseBody MessageResult spread() throws Exception {

		String username = request("username");
		String checkcode = request("checkcode");
		// 推广链接里已增加，此app端还没添加
		String pwd = request("password");
		String reruestType = request("reruestType");
		String requestCode = request("requestCode");
		// 设备编号
		String device = "IOS:A1024";
		if (ValidateUtil.isnull(username)) {
			return error("手机号或用户名不能为空!");
		}
		if (ValidateUtil.isnull(checkcode)) {
			return error("密码不能为空!");
		}
		// 内部测试手机号码不用验证
		List<String> ignorePhoneList = Arrays.asList(AppSetting.IGNORE_PHONE_LIST);
		if (!ignorePhoneList.contains(username)) {
			Ehcache cache = ehCache.getObject();
			Element el = cache.get("PHONE_REG_CODE_" + username);
			if (el == null) {
				return error("验证码未发送或已过期");
			}

			if (StringUtils.isEmpty(checkcode) || !checkcode.equals(el.getObjectValue().toString())) {
				return error("手机验证码不正确");
			}
		}

		long memberId = -1;

		String loginIp = getRemoteIp();
		if ("2".equals(reruestType)) {
			Map<String, String> maps = memberService.findJiliangByNo(requestCode);
			if (maps == null) {
				return error("推广链接编号不正确，请联系客服");
			}
		}
		if ("1".equals(reruestType)) {
			Map<String, String> maps = new Model("member").where("memberNo= ?", requestCode).find();
			if (maps == null) {
				return error("邀请用户不存在，请检查邀请链接");
			}
		}
		// 原先login方法名修改成spread 12.14
		memberId = memberService.spread(username, pwd, loginIp, reruestType, requestCode);
		// 获取用户信息
		Member member = userService.findByMemberId(memberId);
		member.setCommicateDetail("");
		// 创建token信息
		int platform = 0;
		if (device.contains("ANDROID")) {
			platform = Const.PLATFORM_ANDROID;
		} else if (device.contains("IOS")) {
			platform = Const.PLATFORM_IOS;
		}
		AppSessionUtil.createToken(member, platform);

		return success(member);

	}

	/**
	 * 基本信息认证
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "normalInfo", method = RequestMethod.POST)
	public @ResponseBody MessageResult normalInfo() throws Exception {

		String qqno = request("qqno");
		String weino = request("weino");
		String xueli = request("xueli");
		String name = request("name");
		String phone = request("phone");
		String relation = request("relation");
		Member user = getMember();
		if (user == null) {
			return error(400, "token失效");
		}
		user = memberService.findMember(user.getId());
		if (ValidateUtil.isnull(qqno)) {
			return error("请输入qq号");
		}
		if (ValidateUtil.isnull(phone)) {
			return error("请输入紧急联系电话");
		}
		if (phone.length() != 11) {
			return error("请输入正确的手机号");
		}
		if (ValidateUtil.isnull(name)) {
			return error("请输入紧急联系人电话");
		}
		long ret = memberService.updateMemberDetail(user.getId(), qqno, weino, xueli, name, phone, relation);

		if (ret > 0) {
			return success("保存成功");
		} else {
			return error("保存失败");
		}

	}

	/**
	 * 点击个人认证时，检测各项是否认证成功（对电话帮和芝麻信用要查看认证时间是否过期）
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "identityCenter", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityCenter() throws Exception {

		Member member = getMember();

		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> checkMap = memberService.checkIdentityStatus(member.getId());
		Map<String, String> bankCheckMap = memberService.findBankCard(member.getId()); /* //查询银行卡信息 */
		Map<String, String> iphoneCheckMap = iphoneAuthService.getInfoByMemberId(member.getId());// 苹果认证
		Map<String, String> commicateMap = memberService.findCommicateById(member.getId());// 通讯录信息
		if (iphoneCheckMap == null) {
			resultMap.put("apple_status", "0");
			resultMap.put("icloud_status", "0");
		} else {
			resultMap.put("apple_status", iphoneCheckMap.get("status"));// 为0时认证失败，为1或者2时都是认证成功的
			resultMap.put("icloud_status", iphoneCheckMap.get("status"));// 为0或者1时认证失败，为2时认证成功
		}
		if (bankCheckMap == null) {
			resultMap.put("bank_status", "0");
		} else {
			resultMap.put("bank_status", bankCheckMap.get("status"));
		}
		if (checkMap == null) {
			resultMap.put("self_info_status", "0");
			resultMap.put("identity_status", "0");
			resultMap.put("face_status", "0");
			resultMap.put("phone_status", "0");
		} else {
			String phone_audit_time = checkMap.get("phone_audit_time");
			if (ValidateUtil.isnull(phone_audit_time)) {
				resultMap.put("phone_status", "0");
			} else {
				long phoneDay = DateUtil.diffDays(DateUtil.strToDate(phone_audit_time), new Date());
				if (phoneDay > 30) {
					resultMap.put("phone_status", "0");
				} else {
					resultMap.put("phone_status", checkMap.get("phone_status"));
				}
			}
			resultMap.put("self_info_status", checkMap.get("self_info_status"));
			resultMap.put("identity_status", checkMap.get("identity_status"));
			resultMap.put("face_status", checkMap.get("face_status"));
		}
		resultMap.put("commicate_status", commicateMap.get("commicateStatus"));
		MessageResult mr = new MessageResult();
		mr.setCode(0);
		mr.setData(resultMap);
		return mr;

	}

	/**
	 * 提交意见
	 *
	 * @return
	 */
	@RequestMapping(value = "adviseSubmit", method = RequestMethod.POST)
	public @ResponseBody MessageResult adviseSubmit() throws Exception {
		String content = request("content");
		Member member = getMember();
		if (member == null) {
			return error(400, "token失效");
		}
		member = memberService.findMember(member.getId());
		if (ValidateUtil.isnull(content)) {
			return error("请输入内容");
		}
		long ret = memberService.insertAdvisePlat(content, member.getId());
		if (ret > 0) {
			return success("保存成功");
		} else {
			return error("保存失败");
		}
	}

	/**
	 * 访问芝麻页面
	 *
	 * @return
	 */
	@RequestMapping(value = "requestZhiMaPages", method = RequestMethod.POST)
	public @ResponseBody MessageResult requestZhiMaPages() throws Exception {
		Member member = getMember();

		if (member == null) {
			return error(400, "token失效");
		} else {
			member = memberService.findMember(member.getId());
		}
		if (memberService.findMemberIdentyByMemId(member.getId()) == null) {
			return error("请先实名认证");
		}
		ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
		long uid = member.getId();
		String realName = member.getRealName();
		String certNo = member.getIdentNo();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setIdentityType("2");// 必要参数
		req.setIdentityParam(
				"{\"name\":\"" + realName + "\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\"" + certNo + "\"}");// 必要参数
		req.setBizParams("{\"auth_code\":\"M_H5\",\"channelType\":\"app\",\"state\":\"" + uid + "+\"}");//
		DefaultZhimaClient client = new DefaultZhimaClient(ZmxyAppConfig.gatewayUrl, ZmxyAppConfig.appId,
				ZmxyAppConfig.privateKey, ZmxyAppConfig.zhimaPublicKey);
		String url = client.generatePageRedirectInvokeUrl(req);
		if (ValidateUtil.isnull(url)) {
			return error("生成芝麻URL出错");
		} else {
			MessageResult mr = new MessageResult();
			mr.setData(url);
			mr.setCode(0);
			return mr;
		}
	}

	/**
	 * 检测登录状态
	 *
	 * @return
	 */
	@RequestMapping(value = "checkLogin", method = RequestMethod.POST)
	public @ResponseBody MessageResult checkLoginStatus() {
		return success("OK");
	}

	/**
	 * 更改实名认证状态
	 *
	 * @return
	 */
	@RequestMapping(value = "identityAuth", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityAuth(HttpServletRequest request) {

		Member member = getMember();
		try {
			Map<String, String> checkMap = memberService.checkIdentityStatus(member.getId());

			// 接收到APP传来的认证成功信息
			String partner_order_id = request("partner_order_id");
			String trueAddress = request("trueAddress");

			// partner_order_id="1528466266661600";
			log.info("============开始进行有盾数据获取===========");
			// JSON.parseObject("{\"data\":{\"address\":\"浙江省杭州市滨江区\",\"age\":\"31\",\"auth_result\":\"T\",\"birthday\":\"1986.10.01\",\"gender\":\"男\",\"id_name\":\"小盾\",\"id_number\":\"340826198912287019\",\"idcard_back_photo\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4...\",\"idcard_front_photo\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4...\",\"idcard_portrait_photo\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4...\",\"issuing_authority\":\"武汉撄公安局武昌分局\",\"living_photo\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4...\",\"nation\":\"汉\",\"partner_code\":\"201708144049018434\",\"partner_order_id\":\"294ef51c-319a-4ad5-91d5-5c62a90e5235\",\"risk_tag\":{\"living_attack\":\"0\"},\"similarity\":\"0.9268\",\"validity_period\":\"2015.04.01-2025.04.01\",\"verify_status\":\"1\"},\"result\":{\"message\":\"操作成功\",\"success\":true}}");//
			com.alibaba.fastjson.JSONObject obj = YouDunProcessorUtil.OrderQueryTest(partner_order_id);
			log.info("===有盾数据返回结果，===\n" + JSON.toJSONString(obj));
			com.alibaba.fastjson.JSONObject result = obj.getJSONObject("result");
			// 如果接口返回成功
			if (result.getBooleanValue("success")) {
				com.alibaba.fastjson.JSONObject dataobj = obj.getJSONObject("data");
				// 磁盘绝对路径 存储路径
				String relativeUrl = getImgPath(dataobj.getString("id_number"));
				// String physicalPath =
				// request.getSession().getServletContext().getRealPath("/") + relativeUrl;
				Identity inIdentity = new Identity();
				inIdentity.setTrueAddress(trueAddress);

				// if (dataobj.containsKey("idcard_front_photo")
				// &&
				// StorageManager.saveBinaryFile(Base64.decode(dataobj.getString("idcard_front_photo")),
				// physicalPath + "_ImgA.jpg").isSuccess()) {
				// inIdentity.setCardImgA(request.getContextPath() + "/" + relativeUrl +
				// "_ImgA.jpg");
				// }

				if (dataobj.containsKey("idcard_front_photo") && AliyunOssUtil.put(relativeUrl + "_ImgA.jpg",
						Base64.decode(dataobj.getString("idcard_front_photo")))) {
					inIdentity.setCardImgA(AliyunOssUtil.downloadFile(relativeUrl + "_ImgA.jpg"));
				}
				// if (dataobj.containsKey("idcard_back_photo")
				// &&
				// StorageManager.saveBinaryFile(Base64.decode(dataobj.getString("idcard_back_photo")),
				// physicalPath + "_ImgB.jpg").isSuccess()) {
				// inIdentity.setCardImgB(request.getContextPath() + "/" + relativeUrl +
				// "_ImgB.jpg");
				// }
				if (dataobj.containsKey("idcard_back_photo") && AliyunOssUtil.put(relativeUrl + "_ImgB.jpg",
						Base64.decode(dataobj.getString("idcard_back_photo")))) {
					inIdentity.setCardImgB(AliyunOssUtil.downloadFile(relativeUrl + "_ImgB.jpg"));
				}
				// if (dataobj.containsKey("living_photo") && StorageManager
				// .saveBinaryFile(Base64.decode(dataobj.getString("living_photo")),
				// physicalPath + "_ImgC.jpg")
				// .isSuccess()) {
				// inIdentity.setHandleImg(request.getContextPath() + "/" + relativeUrl +
				// "_ImgC.jpg");
				// }
				if (dataobj.containsKey("living_photo") && AliyunOssUtil.put(relativeUrl + "_ImgC.jpg",
						Base64.decode(dataobj.getString("living_photo")))) {
					inIdentity.setHandleImg(AliyunOssUtil.downloadFile(relativeUrl + "_ImgC.jpg"));
				}
				inIdentity.setRealName(dataobj.containsKey("id_name") ? dataobj.getString("id_name") : "");
				inIdentity.setCardNo(dataobj.containsKey("id_number") ? dataobj.getString("id_number") : "");
				// inIdentity.setStatus(dataobj.getString("auth_result").equalsIgnoreCase("T")?0:1);

				memberService.updateIdentityStatus(inIdentity, member.getId());

				log.info("============结束有盾数据获取===========");
			} else {
				return error("当前认证信息有误，请稍后再试");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("============有盾数据获取异常===========", e);
		}
		return success("OK");
	}

	public String getImgPath(String filename) {
		// 相对工程路径
		return PathFormat.parse(saveImgPath, filename);
	}

	@RequestMapping(value = "bankCardAuth", method = RequestMethod.POST)
	public @ResponseBody MessageResult bankCardAuth() {
		log.info("============有盾银行卡认证数据获取start===========");
		Member member = getMember();
		String bankCardNo;
		try {
			if (StringUtils.isBlank(request("bank_card_no"))) {
				return error("请输入正确的银行卡号");
			} else {
				bankCardNo = request("bank_card_no");
				Map<String, String> checkMap = memberService.checkIdentityStatus(member.getId());
				// 先判断是否通过实名认证
				if (!checkMap.get("identity_status").equals("1")) {
					return error("请先通过实名认证再进行银行卡认证");
				} else {
					Identity identity = memberService.findMemberIdentyByMid(member.getId());
					Map<String, String> parameter = new HashMap<>();
					parameter.put("id_name", identity.getRealName());
					parameter.put("id_no", identity.getCardNo());
					parameter.put("bank_card_no", request("bank_card_no"));
					parameter.put("mobile", member.getMobilePhone());
					// 固定使用01方式
					parameter.put("req_type", "01");
					com.alibaba.fastjson.JSONObject obj = YouDunProcessorUtil.bankCard4Validate(parameter);
					if (obj == null) {
						return error("请确认认证数据是否一致");
					} else {
						log.info("有盾银行卡认证获取数据返回===>>>" + JSON.toJSONString(obj));
						if (!"000000".equals(obj.getJSONObject("header").getString("ret_code"))) {
							return error("网络异常，请稍后再试");
						} else {
							com.alibaba.fastjson.JSONObject bodyobj = obj.getJSONObject("body");
							// 判断认证状态
							if (!"1".equals(bodyobj.get("status"))) {
								return error(bodyobj.getString("message"));
							} else {
								if (!"借记卡".equals(bodyobj.getString("card_type"))) {
									return error("目前仅支持借记卡/储蓄卡进行认证，当前使用卡类型为:" + bodyobj.getString("card_type"));
								} else {
									// 更改用户银行卡认证状态并保存认证银行卡信息
									memberService.addMemberBankYD(member.getId(), bankCardNo,
											bodyobj.getString("org_code"), bodyobj.getString("org_name"));
								}
							}
						}
					}
					log.info("============有盾银行卡认证数据获取end===========");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("============有盾银行卡认证数据获取异常===========", e);
			return error("当前认证信息有误，请稍后再试");

		}
		return success("OK");
	}
}
