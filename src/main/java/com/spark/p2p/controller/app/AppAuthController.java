package com.spark.p2p.controller.app;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.StorageManager;
import com.lianpay.util.LLPayUtil;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.config.PaymentConfig;
import com.spark.p2p.controller.lundroid.LundriodSample;
import com.spark.p2p.entity.BorrowMain;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.ShuJuMoHeService;
import com.spark.p2p.util.AESDecode;
import com.spark.p2p.util.AliyunOssUtil;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.HttpUtil;
import com.spark.p2p.util.IdCard;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;

@Controller
@RequestMapping("/app/uc")
public class AppAuthController extends AppBaseController {

	@Autowired
	private MemberService memberService;

	private PaymentConfig config = new PaymentConfig();

	Logger log = LoggerFactory.getLogger(AppAuthController.class);
	@Autowired
	private ShuJuMoHeService shuJuMoHeService;

	/* -------同盾数据魔盒接口 start -------- */
	@RequestMapping(value = "mobileRec", method = RequestMethod.POST)
	private @ResponseBody MessageResult TongdunPhoneAudit() throws Exception {
		Member member = getMember();
		String name = member.getRealName();
		String mobile = member.getMobilePhone();
		String identityNo = member.getIdentNo();
		if (name.equals(null) || "".equals(name) || "".equals(identityNo) || identityNo.equals(null)) {
			return error("资料输入错误");
		}
		// 手动更改编码
		String xmString = new String(name.toString().getBytes("UTF-8"));
		String xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
		String url = "https://open.shujumohe.com/box/yys?box_token=" + AppSetting.BOX_TOKEN;// 秒一花的h5手机认证
		String cb = "http://www.91chuangnuo.cn/mobile/login.html";
		String finalUrl = url + "&cb=" + cb + "&real_name=" + xmlUTF8 + "&identity_code=" + identityNo + "&user_mobile="
				+ mobile;
		return success(finalUrl);
	}

	// 连连支付绑定银行卡
	@RequestMapping("llpay/auth")
	public void webAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = this.getMember().getId();
		String cardNo = request("bankCard");
		String bankCode = request("bankCode");

		Member member = memberService.findMember(userId);
		String identNo = member.getIdentNo();
		String realName = member.getRealName();
		JSONObject json = new JSONObject();
		json.put("version", config.VERSION);
		json.put("oid_partner", config.OID_PARTNER);
		json.put("user_id", String.valueOf(userId));
		json.put("timestamp", DateUtil.YYYYMMDDHHMMSS.format(new Date()));
		json.put("sign_type", "MD5");
		json.put("app_request", "3");
		json.put("url_return", config.AUTH_URL_RETURN);
		json.put("risk_item", createRiskItem(realName));
		json.put("id_type", "0");
		json.put("id_no", identNo);
		json.put("acct_name", realName);
		json.put("card_no", cardNo);
		// 加签名
		String sign = LLPayUtil.addSign(JSON.parseObject(json.toString()), config.TRADER_PRI_KEY, config.MD5_KEY);
		json.put("sign", sign);
		log.info(json.toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put("req_data", json.toString());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 添加绑卡记录，成功时更新状态值（member_bank）
		this.memberService.addMemberBank(userId, cardNo, bankCode);
		sendHtml(response, createForwardHtml(config.AUTH_GATEWAY, "post", map));
	}

	private String createRiskItem(String realName) {
		JSONObject riskItemObj = new JSONObject();
		riskItemObj.put("user_info_full_name", realName);
		riskItemObj.put("frms_ware_category", "1999");
		return riskItemObj.toString();
	}

	public String createForwardHtml(String gateway, String method, Map<String, String> map) {
		StringBuffer sb = new StringBuffer(
				"<html><head <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><script type='text/javascript'>window.onload=function(){document.getElementById('submitForm').submit();}</script></head><body>");
		// 表单内容 URLEncoder.encode(value, "utf-8")
		sb.append("<form action='" + gateway + "'  id='submitForm' method='" + method + "'>");
		map.forEach((key, value) -> {
			try {
				sb.append("<input type='hidden' name='" + key + "'  value='" + value + "'  />");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		sb.append("</form></body></html>");
		return sb.toString();
	}

	// 阿里云人脸识别认证
	// 文档
	// https://data.aliyun.com/product/face?spm=5176.8142029.388261.292.23896dfaCqirlt
	@RequestMapping("aliyun/faceRec")
	public @ResponseBody MessageResult faceRec(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		// 先上传保存图片
		int type = Convert.strToInt(request.getParameter("type"), 0);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String allowedImgFormat = ".jpg,.gif,.png,.jpeg,.bmp";
		String saveImgPath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
		long maxAllowedImgSize = 1024 * 5000;

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		FileItemIterator iterator;
		FileItemStream fileStream = null;

		// 获取当前url
		String curUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath(); // 项目名称

		// 人脸比对的图片1地址
		String image_url_1 = "";
		log.info("image_url_1执行了" + image_url_1);
		try {
			iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField()) {
					break;
				}
				fileStream = null;
			}
			if (fileStream == null) {
				return error("未找到上传数据");
			}
			String fileName = fileStream.getName();
			// 获取文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if (!allowedImgFormat.contains(suffix.trim().toLowerCase())) {
				return error("不允许的图片格式");
			}
			InputStream is = fileStream.openStream();
			// 相对工程路径
			String relativePath = PathFormat.parse(parseCatePath(saveImgPath, type), fileName) + suffix;
			// 磁盘绝对路径
			// String physicalPath =
			// request.getSession().getServletContext().getRealPath("/") + relativePath;
			if (AliyunOssUtil.put(relativePath, is)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				image_url_1 = AliyunOssUtil.downloadFile(relativePath);
				log.info(image_url_1);
			} else {
				return error("图片上传失败");
			}
			is.close();

			// BaseState storageState = (BaseState) StorageManager.saveFileByInputStream(is,
			// physicalPath,
			// maxAllowedImgSize);

			// if (storageState.isSuccess()) {
			// String relativeUrl = "/" + relativePath;
			// String absPath = request.getContextPath() + relativeUrl;
			// response.setCharacterEncoding("UTF-8");
			// response.setContentType("text/html; charset=UTF-8");
			//
			// image_url_1 = curUrl + relativeUrl;
			// log.info(image_url_1);
			// if (storageState.isSuccess()) {

			// } else {
			// return error(storageState.getInfo());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			fileStream = null;
			return error("数据异常");
		}

		long memberId = this.getMember().getId();
		// image_url_1 = "http://www.xinhuokj.com/5.jpg";
		// 调用阿里云人脸比对API
		// 文档地址https://help.aliyun.com/knowledge_detail/53535.html?spm=5176.7753399.6.554.oYAOyP
		String faceApi = "https://dtplus-cn-shanghai.data.aliyuncs.com/face/verify";
		// 从数据库中获取身份证照片

		String image_url_2 = this.memberService.findMemberIdentyByMid(memberId).getCardImgA();
		if (!image_url_2.startsWith("http")) {
			image_url_2 = curUrl + image_url_2;
		}

		// 发送POST请求示例
		String body = "{\"type\": \"0\", \"image_url_1\":\"" + image_url_1 + "\", \"image_url_2\":\"" + image_url_2
				+ "\"}";
		String result = AESDecode.sendPost(faceApi, body, AppSetting.ACCESS_KEY_ID, AppSetting.ACCESS_KEY_SECRET);
		log.info("body = " + body + ", \n response body = " + result);
		JSONObject json = JSONObject.parseObject(result);
		if (json != null && json.getFloat("confidence") >= 50) {// 相似度大于50，则识别通过
			log.info("confidence=" + json.getFloat("confidence"));
			this.memberService.updateZmFaceStatus(1, "", memberId);
			return success("识别通过");
		}

		return error("识别不通过");
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
	 * 查询支持的银行列表
	 *
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping("bank")
	 * 
	 * @ResponseBody
	 */
	/*
	 * 遇到傻逼客户，你懂得 public MessageResult bankList() throws Exception {
	 * List<Map<String, String>> banks = memberService.queryBanks(); MessageResult
	 * mr = MessageResult.success("查询成功"); mr.setData(banks); return mr; }
	 */

	/**
	 * 实名认证
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "identityCard", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityCard(HttpServletRequest request) throws Exception {

		Member user = getMember();
		Map<String, String> identityMap = memberService.findMemberIdentyByMemId(user.getId());
		if (identityMap != null) {
			MessageResult mr = new MessageResult();
			mr.setData(identityMap);
			mr.setCode(0);
			// return mr;
		}
		String realname = request("realname");
		String cardno = request("cardno");
		String qqMail = request("qqMail");
		String trueAddress = request("trueAddress");// 手机传入的ip抓取地址
		String nowAddressA = request("nowAddressA");// 省市区
		String nowAddressB = request("nowAddressB");// 详细地址
		String nowAddress = nowAddressA + nowAddressB;
		String cardImgA = request("cardImgA");
		String cardImgB = request("cardImgB");
		String handleImg = request("handleImg");
		log.info("realname=" + realname + ", cardno=" + cardno + ", cardImgA=" + cardImgA + ", cardImgB=" + cardImgB
				+ ", handleImg=" + handleImg + ",qqMail" + qqMail + ",nowAddress" + nowAddress);

		if (ValidateUtil.isnull(cardno)) {
			return error("请输入身份证");
		}
		String flag = IdCard.validate(cardno);
		if (flag != "SUCCESS") {
			return error("身份证格式出错");
		}
		Identity identitya = memberService.findMemberIdentyByMid(user.getId());
		if (identitya == null) {
			if (memberService.findMemberIdentyByCardNo(cardno) != null) {
				return error("该身份证已经验证了");
			}
		}
		if (ValidateUtil.isnull(qqMail)) {
			return error("请输入qq邮箱");
		}
		if (ValidateUtil.isnull(nowAddressA) || ValidateUtil.isnull(nowAddressB)) {
			return error("请输入完整的现住地址");
		}

		if (ValidateUtil.isnull(cardImgA)) {
			return error("请选择身份证正面图片");
		}
		if (ValidateUtil.isnull(cardImgB)) {
			return error("请选择身份证反面图片");
		}
		if (ValidateUtil.isnull(handleImg)) {
			return error("请选择手持身份证图片");
		}

		// 获取当前url
		String imgUrl = cardImgA;

		if (!cardImgA.startsWith("http")) {
			String curUrl = "http://" + request.getServerName() // 服务器地址
					+ ":" + request.getServerPort() // 端口号
					+ request.getContextPath(); // 项目名称
			imgUrl = curUrl + cardImgA;
		}

		if (!checkCardImg(imgUrl)) {// curUrl + cardimg
			return error("身份证照片不合格，无法检测出身份证人脸，请重新拍摄身份证正面照片");
		}
		LundriodSample lundriodSample = new LundriodSample();
		String result = lundriodSample.lundriodIdentity(realname, cardno);
		Map<String, String> map = new HashMap<>();
		log.info(result);
		if ("".equals(result)) {
			return error("平台实名认证余额不足，请帮忙联系客服");
		}
		org.json.JSONObject obj = new org.json.JSONObject(result);
		// 获取调用身份验证之后的返回code值
		int code = (int) obj.getJSONObject("resp").getInt("code");
		String message = (String) obj.getJSONObject("resp").getString("desc");
		MessageResult mr = new MessageResult();
		mr.setCode(code);
		mr.setMessage(message);
		if ("0".equals(code + "")) {
			Identity identity = new Identity();
			identity.setMemberId(user.getId());
			identity.setRealName(realname);
			identity.setCardNo(cardno);
			identity.setCardImgA(cardImgA);
			identity.setCardImgB(cardImgB);
			identity.setHandleImg(handleImg);
			identity.setQqMail(qqMail);
			identity.setNowAddress(nowAddress);
			identity.setTrueAddress(trueAddress);
			long ret = memberService.updateXqdIdentityCheck(identity, user.getId());
			if (ret > 0) {
				return success("保存成功");
			} else {
				return error("保存失败");
			}
		} else {
			return mr;
		}
	}

	// 检查身份证照片是否有人脸（调用阿里云的人脸识别接口）
	private boolean checkCardImg(String imgPath) {
		// 阿里云人脸识别文档地址：https://help.aliyun.com/knowledge_detail/53399.html?spm=5176.doc57935.6.552.wRYEH4
		String faceApi = "https://dtplus-cn-shanghai.data.aliyuncs.com/face/detect";
		String image_url_1 = imgPath;
		String body = "{\"type\": \"0\", \"image_url\":\"" + image_url_1 + "\"}";
		String result;
		try {
			result = AESDecode.sendPost(faceApi, body, AppSetting.ACCESS_KEY_ID, AppSetting.ACCESS_KEY_SECRET);
			log.info("body = " + body + ", \n response body = " + result + " \n -----result end----");
			org.json.JSONObject json = new org.json.JSONObject(result);
			if (json.getInt("errno") == 0 && json.getInt("face_num") > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
