package com.spark.p2p.controller.wenxin;

import java.text.DecimalFormat; 
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.lianpay.util.LLPayUtil;
import com.spark.p2p.config.PaymentConfig;
import com.spark.p2p.config.ZmxyAppConfig;
import com.spark.p2p.controller.lundroid.LundriodSample;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.LLPayService;
import com.spark.p2p.service.LimuAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.CardIdCheck;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.IdCard;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.model.Model;

@Controller
@RequestMapping("/mobile/borrow")
public class WeixinBorrowController extends WeixinBaseController {

	
	public static final Log log = LogFactory.getLog(WeixinBorrowController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private BorrowAdminService borrowAdminService;

	@Autowired
	private RepayAdminService repayAdminService;

	@Autowired
	private LimuAuthService limuAuthService;
	
	@Autowired
	private LLPayService llpayService;
	
	String appid = "7a7984eaf31123dd639b5da67ad79d1b";
	String appsecret = "a8338b8fe73cd57d84b0d548deb731077f9bb93c2c3942bac97c9aafa58ffb8f";
	
	private PaymentConfig payConfig = new PaymentConfig();
	/*
	 * 个人认证中心首页 判断各个认证，是否成功认证
	 */
	@RequestMapping(value = "attestation", method = RequestMethod.GET)
	public String attestation(HttpServletRequest request) throws Exception {
		log.info("attestion----------------------");
		String mid = request("mid");// 回调地址返回的参数
		log.info("attestion----------------------mid" + mid);
		Member member = getUser();
		
		Map<String, String> checkMap = memberService.checkIdentityStatus(member.getId());
		request.setAttribute("aplipayUrl", requestZhiMaPages());
		if (checkMap == null) {
			request.setAttribute("self_info_status", "0");
			request.setAttribute("identity_status", "0");
			request.setAttribute("phone_status", "0");
			request.setAttribute("alipay_status", "0");
		} else {
			String phone_audit_time = checkMap.get("phone_audit_time");
			String alipay_audit_time = checkMap.get("alipay_audit_time");
			if (ValidateUtil.isnull(phone_audit_time)) {
				request.setAttribute("phone_status", "0");
			} else {
				long phoneDay = DateUtil.diffDays(DateUtil.strToDate(phone_audit_time), new Date());
				if (phoneDay > 30) {
					request.setAttribute("phone_status", "0");
				} else {
					request.setAttribute("phone_status", checkMap.get("phone_status"));
				}
			}
			if (ValidateUtil.isnull(alipay_audit_time)) {
				request.setAttribute("alipay_status", "0");

			} else {
				long alipayDay = DateUtil.diffDays(DateUtil.strToDate(alipay_audit_time), new Date());
				if (alipayDay > 30) {
					request.setAttribute("alipay_status", "0");
				} else {
					request.setAttribute("alipay_status", checkMap.get("alipay_status"));
				}
			}

			request.setAttribute("self_info_status", checkMap.get("self_info_status"));
			request.setAttribute("identity_status", checkMap.get("identity_status"));
		}

		return view("ucenter/attestation");
	}

	/*
	 * 个人信息填写页面
	 */
	@RequestMapping(value = "personInfo", method = RequestMethod.GET)
	public String personInfo() throws Exception {
		return view("ucenter/personal-info");
	}

	/*
	 * 保存个人信息
	 */
	@RequestMapping(value = "savePersonInfo", method = RequestMethod.POST)
	public @ResponseBody MessageResult cardIdCheckedPost() throws Exception {
		String qqno = request("qqno");
		String weino = request("weino");
		String xueli = request("xueli");
		String name = request("name");
		String phone = request("phone");
		String relation = request("relation");
		Member user = getUser();
		if (user == null) {
			return error("token失效");
		} 
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

	/*
	 * 身份认证页面
	 */
	@RequestMapping(value = "identityPage", method = RequestMethod.GET)
	public String identityPage() throws Exception {

		return view("ucenter/identity");
	}

	/* 人脸识别 */
	@RequestMapping(value = "faceRec", method = RequestMethod.GET)
	public String faceRec() throws Exception {
		return view("ucenter/face-rec");
	}

	// 手机认证
	@RequestMapping(value = "mobileRec", method = RequestMethod.GET)
	public String mobileRec(HttpServletRequest request) throws Exception {
		Member member = this.getUser();
		String mobilePhone = member.getMobilePhone();
		request.setAttribute("mobilePhone", mobilePhone);
		return view("ucenter/mobile-rec");
	}

	/*//【获取通过详单】提交手机查询密码
	@RequestMapping(value = "mobileRec", method = RequestMethod.POST)
	public @ResponseBody MessageResult doMobileRec() throws Exception {
		String password = request("password");
		
		Member member = this.getUser();
		String mobilePhone = member.getMobilePhone();
		
		MessageResult mr = this.limuAuthService.doAuth(mobilePhone, password);
		
		
		return mr;
	}
	
	//【获取通过详单】获取状态或者结果集（循环每5秒查询一次）
	@RequestMapping(value = "mobileAuthGet", method = RequestMethod.POST)
	public @ResponseBody MessageResult mobileAuthGet() throws Exception {
		String m_token = request("m_token");
		Member member = this.getUser();
		MessageResult mr = limuAuthService.roundRobin(m_token, member.getId());
		log.info(mr);
		
		return mr;
	}
	
	//【获取通过详单】发送手机验证码
	@RequestMapping(value = "mobileAuthSend", method = RequestMethod.POST)
	public @ResponseBody MessageResult mobileAuthSend() throws Exception {
		String m_token = request("m_token");
		String code = request("mobileCode");
		String json = limuAuthService.sendLimuMobileCode(m_token, code);
		log.info(json);
		
		return success("已发送");
	}*/

	/**
	 * 实名认证
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * 使用天机系统
	 * 
	 * @RequestMapping(value = "saveIdentityCard", method = RequestMethod.POST)
	 * public @ResponseBody MessageResult identityCard() throws Exception {
	 * 
	 * Member user = getUser(); if(user==null){ return error("token失效"); }
	 * user=memberService.findMember(user.getId()); Map<String,String>
	 * identityMap=memberService.findMemberIdentyByMemId(user.getId());
	 * if(identityMap!=null){ MessageResult mr=new MessageResult();
	 * mr.setData(identityMap); mr.setCode(0); //return mr; }
	 * 
	 * String realname = request("realname"); String cardno = request("cardno");
	 * String cardimg = request("cardimg");
	 * 
	 * if(ValidateUtil.isnull(cardno)){ return error("请输入身份证"); }
	 * if(ValidateUtil.isnull(cardimg)){ return error("请选择图片"); } String
	 * flag=IdCard.validate(cardno); if(flag!="SUCCESS"){ return error("身份证格式出错"); }
	 * 
	 * Identity identitya=memberService.findMemberIdentyByMid(user.getId());
	 * if(identitya==null){
	 * if(memberService.findMemberIdentyByCardNo(cardno)!=null){ return
	 * error("该身份证已经验证了"); }
	 * 
	 * }
	 * 
	 * TianjiSample tianjiSample=new TianjiSample(); String
	 * result=tianjiSample.tianjiPzczzxhoneLogin(realname,cardno); JSONObject js=new
	 * JSONObject(result.toString()); log.info(js.toString()); int
	 * error=js.getInt("error"); if(error==200){ JSONArray
	 * jsarr=js.getJSONArray("tianji_api_rong360_idindentify_response"); JSONObject
	 * jsarrJSa=(JSONObject) jsarr.get(0); String
	 * status=jsarrJSa.getString("checkStatus"); if("S".equals(status)){ Identity
	 * identity=new Identity(); identity.setMemberId(user.getId());
	 * identity.setRealName(realname); identity.setCardNo(cardno);
	 * identity.setCardImgurl(cardimg); long
	 * ret=memberService.updateXqdIdentityCheck(identity,user.getId());
	 * 
	 * if(ret>0){ return success("保存成功"); }else{ return error("保存失败"); } }else{
	 * return error("身份不匹配"); } }else{ return error("身份不匹配"); }
	 * 
	 * 
	 * 
	 * }
	 */
	/*
	 * 调用联卓接口验证身份信息
	 * 
	 */
	@RequestMapping(value = "saveIdentityCard", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityCard() throws Exception {

		Member user = getUser();
		if (user == null) {
			return error("token失效");
		} 
		Map<String, String> identityMap = memberService.findMemberIdentyByMemId(user.getId());
		if (identityMap != null) {
			MessageResult mr = new MessageResult();
			mr.setData(identityMap);
			mr.setCode(0);
			// return mr;
		}

		String realname = request("realname");
		String cardno = request("cardno");
		String cardimg = request("cardimg");

		if (ValidateUtil.isnull(cardno)) {
			return error("请输入身份证");
		}
		if (ValidateUtil.isnull(cardimg)) {
			return error("请选择图片");
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

		LundriodSample lundriodSample = new LundriodSample();
		String result = lundriodSample.lundriodIdentity(realname, cardno);
		log.info(result);

		JSONObject obj = new JSONObject(result);
		// 获取调用身份验证之后的返回code值
		int code = (int) obj.getJSONObject("resp").getInt("code");
		// code+"" 将int类型转换为String类型
		if ("0".equals(code + "")) {
			Identity identity = new Identity();
			identity.setMemberId(user.getId());
			identity.setRealName(realname);
			identity.setCardNo(cardno);
			long ret = memberService.updateXqdIdentityCheck(identity, user.getId());
			if (ret > 0) {
				return success("保存成功");
			} else {
				return error("保存失败");
			}
		//错误码值为5
		} else if ("5".equals(code + "")) {
			return error("身份不匹配");
		//错误码值为14
		} else if ("14".equals(code + "")) {
			return error("无此身份证号码");
		//错误码值为96
		} else {
			return error("交易失败，请稍后重试");
		}
	}

	/**
	 * 访问芝麻页面
	 * 
	 * @return
	 */
	public String requestZhiMaPages() throws Exception {
		Member member = getUser();

		if (member == null) {
			return null;
		} 
		if (memberService.findMemberIdentyByMemId(member.getId()) == null) {
			return "100";
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
			return "200";
		} else {
			return url;
		}
	}

	//查询借款状态
	@RequestMapping(value = "getBorrowStatus")
	public @ResponseBody MessageResult getBorrowStatus() throws Exception {
		long borrowId = Convert.strToLong(request("id"), -1);
		if(borrowId <= 0){
			return error("参数borrowId不正确");
		}
		long borrowStatus = this.borrowService.getBorrowStatusById(borrowId);
		
		return success(borrowStatus);
	}
	
	/**
	 * 点击我要借款（底部按钮），判断进入那个页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrowPath")
	public String borrowPath(HttpServletRequest request) throws Exception {
		Member member = getUser();

		String bankCardNo = null;
		/*Map<String, String> bank = this.memberService.findBankCard(member.getId());*/
		/*if(bank != null) {
			bankCardNo = bank.get("cardNo");
		}*/
		request.setAttribute("bankCardNo", bankCardNo);
		if (!ValidateUtil.isnull(bankCardNo)) { 
			request.setAttribute("bankcardstatus", 1);
		} else {
			request.setAttribute("bankcardstatus", 0); 
		}
		
		long memberId = member.getId();
		// 判断是否有借款
		// 判断条件：借款状态是未还款的
		Map<String, String> map = borrowService.findHasNotGiveBorrow( memberId );
		if (map == null) {
			// 查询是否有通过初审的借款
			Map<String, String> notRepayBorrowMap = this.borrowService.getMyNotRepayBorrow(memberId);
			Map<String, String> memberMap = memberService.findMemberIdentyByMemId(memberId);
			if (memberMap != null) {
				if (!ValidateUtil.isnull(memberMap.get("people_imgurl"))) {
					request.setAttribute("idcardstatus", 1);
				} else {
					request.setAttribute("idcardstatus", 0);
				}
			} else {
				request.setAttribute("idcardstatus", 0);
			}

			log.info("notRepayBorrowMap = " + notRepayBorrowMap);
			if (notRepayBorrowMap != null) { 
				int borrowStatus = Integer.valueOf(notRepayBorrowMap.get("borrowStatus"));
				request.setAttribute("borrowId", notRepayBorrowMap.get("id"));
				request.setAttribute("borrowStatus", borrowStatus);
				request.setAttribute("msg", "");
				return view("ucenter/borrow-path");
			} else {
				return view("ucenter/borrow-index-list");
			}
		} else {
			// 没有超过还款期限
			// 显示剩余天数和要还的本金

			Map<String, String> normalBorrowMap = borrowService.findBorrowByMemberAndStatus(member.getId(), 8);
			if (normalBorrowMap != null) {
				
				Map<String, String> remainMap = repayAdminService
						.findLastPay(Convert.strToInt(normalBorrowMap.get("id"), 0));
				String managerFee = null;
				String benJin = null;
				String total = null;
				if (remainMap != null) {
					managerFee = remainMap.get("remainFee");
					benJin = remainMap.get("remainBenjin");
					total = ((int) Convert.strToDouble(remainMap.get("remainFee"), 0)
							+ (int) Convert.strToDouble(remainMap.get("remainBenjin"), 0)) + "";
					request.setAttribute("managerFee", remainMap.get("remainFee"));
					request.setAttribute("benJin", remainMap.get("remainBenjin"));
					request.setAttribute("total", ((int) Convert.strToDouble(remainMap.get("remainFee"), 0)
							+ (int) Convert.strToDouble(remainMap.get("remainBenjin"), 0)) + "");
				} else {

					int amount = Convert.strToInt(normalBorrowMap.get("benJin"), 0);
					int borrowDays = Convert.strToInt(normalBorrowMap.get("borrowDate"), 0);
					//计算相关费用
					DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
					double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
					double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
					double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;
					double otherFee = 0;  
					xinFee = Double.valueOf(df.format(xinFee));
					shouFee = Double.valueOf(df.format(shouFee));
					serviceFee = Double.valueOf(df.format(serviceFee));
					
					managerFee = "" + (xinFee + shouFee + serviceFee );
					total = "" + ( amount + xinFee + shouFee + serviceFee);
					benJin = "" + amount;
					
					
					request.setAttribute("total", (amount + xinFee + shouFee + serviceFee) );
					request.setAttribute("benJin", "" + amount);
					request.setAttribute("managerFee",
							"" + (xinFee + shouFee + serviceFee) );
				}

				request.setAttribute("borrowStatus", 8);
				int borrowDays = Convert.strToInt(normalBorrowMap.get("borrowDate"), 0);
				int addBorrowDay = Convert.strToInt(normalBorrowMap.get("addBorrowDay"), 0);
				String secondAuditTime = normalBorrowMap.get("secondAuditTime").substring(0, 10) + " 00:00:00";
				Date startDay = DateUtil.strToDate(secondAuditTime);
				int days = (int) DateUtil.diffDays(startDay, new Date());///////////////

				request.setAttribute("remainday", (borrowDays + addBorrowDay - days) + "");
				request.setAttribute("needRepayDate", normalBorrowMap.get("appointmentTime"));
				request.setAttribute("borrowId", normalBorrowMap.get("id"));

				request.setAttribute("otherFee", "0");
				request.setAttribute("feetotal", managerFee);

				request.setAttribute("normalBorrowMap", normalBorrowMap);
				// 到还款的页面
				return view("ucenter/repay");

			}

			// 逾期中
			Map<String, String> overdueBorrowMap = borrowService.findBorrowByMemberAndStatus(member.getId(), 9);
			if (overdueBorrowMap != null) {
				request.setAttribute("borrowId", overdueBorrowMap.get("id"));
				int benjin = 0;
				double totalfee = 0;
				double overdueFee = 0;
				request.setAttribute("borrowStatus", 9);
				int borrowDate = Convert.strToInt(overdueBorrowMap.get("borrowDate"), 0);
				int addBorrowDay = Convert.strToInt(overdueBorrowMap.get("addBorrowDay"), 0);
				String realLoanTime = overdueBorrowMap.get("realLoanTime");
				realLoanTime = realLoanTime.substring(0, 10) + " 00:00:00";
				Date startDay = DateUtil.strToDate(realLoanTime);
				int days = (int) DateUtil.diffDays(startDay, new Date());
				// Date appiontDate=DateUtil.dateAddDay(startDay, (addBorrowDay+borrowDate));
				request.setAttribute("appiontDate", overdueBorrowMap.get("appointmentTime"));
				request.setAttribute("startDayDate", startDay);
				request.setAttribute("planSumDay", (addBorrowDay + borrowDate) + "");
				request.setAttribute("realSumDays", days);
				request.setAttribute("overduedays", days - addBorrowDay - borrowDate);
				//request.setAttribute("bankCardNo", overdueBorrowMap.get("bankCardNo"));

				Map<String, String> feeMap = borrowService
						.findAppayOverdueLog(Convert.strToInt(overdueBorrowMap.get("id"), 0));
				if (feeMap != null) {
					overdueFee = Convert.strToDouble(feeMap.get("overdueFee"), 0);
					request.setAttribute("overdueFee", feeMap.get("overdueFee"));
					benjin = (int) Convert.strToDouble(feeMap.get("benjin"), 0);
					totalfee = Convert.strToDouble(feeMap.get("remainFee"), 0);
				}

				request.setAttribute("benJin", benjin + "");
				request.setAttribute("overdueBorrowMap", overdueBorrowMap);
				request.setAttribute("feetotal", "" + totalfee);
				request.setAttribute("total", "" + (totalfee + benjin + overdueFee));

			}
			return view("ucenter/repay");

		}
		

	}

	/*
	 * 借款之前的审核
	 */
	@RequestMapping(value = "beforeBorrowCheck", method = RequestMethod.POST)
	public @ResponseBody MessageResult beforeBorrowCheck() throws Exception {
		
		return borrowCheck();
	}
	
	public MessageResult borrowCheck() throws Exception{
		MessageResult mr = new MessageResult();
		Member member = getUser();
		int flag = 0;
		if (member.getMemberStatus() == 3) {
			flag = 1;
			mr.setCode(-1);
			mr.setMessage("你是黑名单，请联系客服");
			return mr;
		}
		Map<String, String> checkMap = memberService.checkIdentityStatus(member.getId());
		if (checkMap == null) {
			flag = 1;
			mr.setCode(-2);
			mr.setMessage("请先到个人中心认证");
			return mr;
		}

		String self_info_status = checkMap.get("self_info_status");
		String identity_status = checkMap.get("identity_status");
		String phone_status = checkMap.get("phone_status"); 

		if ("0".equals(self_info_status)) {
			flag = 1;
			mr.setCode(-3);
			mr.setMessage("个人信息没有认证");
			return mr;
		}
		if ("0".equals(identity_status)) {
			flag = 1;
			mr.setCode(-4);
			mr.setMessage("身份信息没有认证");
			return mr;
		}
		if ("0".equals(phone_status)) {
			flag = 1;
			mr.setCode(-5);
			mr.setMessage("手机信息没有认证");
			return mr;
		}
		/*if(this.memberService.findBankCard(member.getId()) == null){
			return error("请先进行银行卡认证");
		}*/
		
		// 判断地区是否限制
		String identyNo = member.getIdentNo();
		String area = CardIdCheck.getCardInfo(identyNo).get("area");
		List<Map<String, String>> list = memberService.queryAreaProviceList();
		for (int i = 0; i < list.size(); i++) {
			String province = list.get(i).get("province");
			if (province.contains(area)) {
				flag = 1;
				mr.setCode(-14);
				mr.setMessage("本地区暂时未开通借款业务");
				return mr;
			}
		}

		// 年龄判断
		int age = Convert.strToInt(CardIdCheck.getCardInfo(identyNo).get("age"), 0);
		int allowMaxAge = Convert
				.strToInt(new Model("constant_variable").where("name= ?", "ALLOWMAXAGE").find().get("value"), 0);
		int allowMinAge = Convert
				.strToInt(new Model("constant_variable").where("name= ?", "ALLOWMINAGE").find().get("value"), 0);
		if (!(age >= allowMinAge && age <= allowMaxAge)) {
			flag = 1;
			mr.setCode(-15);
			mr.setMessage("年龄不在系统允许范围内");
			return mr;
		}

		// 如果复审失败，三十天内不能再申请
		Map<String, String> failMap = borrowService.findBorrowByMemberAndStatus(member.getId(), 7);
		Map<String, String> failsMap = borrowService.findBorrowByMemberAndStatus(member.getId(), 3);
		String reapply = new Model("constant_variable").where("name= ?", "REAPPLYDAY").find().get("value");
		if (failMap != null) {
			String secondAuditTime = failMap.get("secondAuditTime");
			if (!ValidateUtil.isnull(secondAuditTime)) {
				Date oldDate = DateUtil.strToDate(secondAuditTime);
				long days = DateUtil.diffDays(oldDate, new Date());
				if (days < Convert.strToInt(reapply, 0)) {
					flag = 1;
					mr.setCode(-13);
					mr.setMessage("距离上次复审失败没有超过30天，请等待");
					return mr;
				}
			}
		}
		if (failsMap != null) {
			String secondAuditTime = failsMap.get("fristAuditTime");
			if (!ValidateUtil.isnull(secondAuditTime)) {
				Date oldDate = DateUtil.strToDate(secondAuditTime);
				long days = DateUtil.diffDays(oldDate, new Date());
				if (days < Convert.strToInt(reapply, 0)) {
					flag = 1;
					mr.setCode(-13);
					mr.setMessage("距离上次初审失败没有超过30天，请等待");
					return mr;
				}
			}
		}
		Map<String, String> auditMap = borrowService.findBorrowByMemberAndStatus(member.getId(), 1);
		Map<String, String> auditMapa = borrowService.findBorrowByMemberAndStatus(member.getId(), 2);
		Map<String, String> auditMapb = borrowService.findBorrowByMemberAndStatus(member.getId(), 4);
		Map<String, String> auditMapc = borrowService.findBorrowByMemberAndStatus(member.getId(), 5);
		Map<String, String> auditMapd = borrowService.findBorrowByMemberAndStatus(member.getId(), 6);
		if (auditMap != null) {
			flag = 1;
			mr.setCode(1);
			mr.setData(auditMap.get("id"));
			mr.setMessage("已经有提交过的借款，等待审核");
			return mr;
		}
		if (auditMapd != null) {
			flag = 1;
			mr.setCode(5);
			mr.setData(auditMapd.get("id"));
			mr.setMessage("等待放款");
			return mr;
		}
		if (auditMapa != null) {
			flag = 1;
			mr.setCode(2);
			mr.setData(auditMapa.get("id"));
			mr.setMessage("已经有提交过的借款，等待审核");
			return mr;
		}
		if (auditMapb != null) {
			flag = 1;
			mr.setCode(3);
			mr.setData(auditMapb.get("id"));
			mr.setMessage("已有通过初审的借款");
			return mr;
		}
		if (auditMapc != null) {
			flag = 1;
			mr.setCode(4);
			mr.setData(auditMapc.get("id"));
			mr.setMessage("有等待复审的借款");
			return mr;
		}
		if (flag == 1) {
			return mr;
		} else {
			return success("ok" );
		}
	}

	/*
	 * 进入到借款的选择页面
	 */
	@RequestMapping(value = "borrowApply", method = RequestMethod.GET)
	public String borrowApply(HttpServletRequest request) throws Exception {
		String benjin = request("benjin");
		request.setAttribute("benjin", benjin);
		return view("ucenter/borrow-apply");
	}

	/**
	 * 计算借款的相关费用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "calculateFee", method = RequestMethod.POST)
	public @ResponseBody MessageResult calculateFee() throws Exception {
		MessageResult mr = new MessageResult();
		int amount = (int) Convert.strToDouble(request("benJin"), 0);
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		if (amount == 0) {
			mr.setCode(-1);
			mr.setMessage("请选择借款金额");
			return mr;
		}
		if (borrowDays == 0) {
			mr.setCode(-2);
			mr.setMessage("请选择借款天数");
			return mr;
		}
		
		DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
		// 计算相关费用
		double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
		double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
		double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;
		double otherFee = 0;  
		
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));

		Map<String, String> map = new HashMap<String, String>();
		map.put("xinFee", "" + xinFee);
		map.put("shouFee", "" + shouFee);
		map.put("serviceFee", "" + serviceFee);
		map.put("otherFee", "" + otherFee);
		map.put("total", "" + (otherFee + xinFee + shouFee + serviceFee + amount));
		mr.setCode(0);
		mr.setData(map);
		log.info("calculateFee| map = " + map);
		return mr;
	}

	/**
	 * 提交借款的申请
	 * 
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "submitBorrow", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public @ResponseBody MessageResult submitBorrow() throws Exception {
		// 判断是否有等待审核的借款
		Member member = getUser();
		if(member.getMemberStatus()==3){
			return error("你是黑名单用户，请联系客服");
		}
		
		MessageResult mr = new MessageResult();
		
		mr = borrowCheck();
		if(mr.getCode() != 0){
			return mr;
		}
		
		int amount = (int) Convert.strToDouble(request("benJin"), 0);
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);

		if (amount == 0) {
			return error("请输入借款金额");
		}
		if (borrowDays == 0) {
			return error("请输入借款周期");
		}
		
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
		double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
		double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;
		double otherFee = 0;  
		
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
		
		String borrowNo = "XEXD" + DateUtil.getDateYMD() + (System.currentTimeMillis() + "").substring(6, 12);
		*//*long ret = borrowService.insertBorrowApply(member.getId(), borrowNo, xinFee, shouFee, serviceFee, otherFee,
				amount, borrowDays, member.getMemberStatus());
		if (ret > 0) {
			if ("4".equals(borrowService.findBorrowById(ret).get("borrowStatus"))) {
				// SMSUtil.sendContent(member.getMobilePhone(),
				// "您的编号"+borrowNo+"申请，已通过后台的初审，请按照提示完成其他认证！");
			} else {
				// SMSUtil.sendContent(member.getMobilePhone(),
				// "您的编号"+borrowNo+"申请，已提交到后台初审，请耐心等待，谢谢！");
			}
			mr.setCode(0);
			mr.setData(ret);
			mr.setMessage("提交借款申请成功");
		} else {
			mr.setCode(-1);
			mr.setMessage("提交借款申请失败");
		}

		return mr;*//*
	*/

	/*
	 * 进入到审核列表页面
	 */
//	@RequestMapping(value = "borrowStatus")
//	public String auditList() throws Exception {
//
//		log.info("------------------------enter auto  request ");
//		Member member = getUser(); 
//		long borrowIds = Convert.strToLong(request("borrowId"), 0);
//		Map<String, String> statusMap = borrowService.findBorrowById(borrowIds);
//		int borrowStatus = Convert.strToInt(statusMap.get("borrowStatus"), 0);
//		request.setAttribute("borrowStatus", borrowStatus);
//
//		if (!ValidateUtil.isnull(statusMap.get("bankCardNo"))) {
//			request.setAttribute("bankCardNo", statusMap.get("bankCardNo"));
//			request.setAttribute("bankcardstatus", 1);
//		} else {
//			request.setAttribute("bankcardstatus", 0);
//			request.setAttribute("bankCardNo", "");
//		}
//
//		Map<String, String> memberMap = memberService.findMemberIdentyByMemId(member.getId());
//		if (statusMap.get("bankCardNo") != null) {
//			if (!ValidateUtil.isnull(memberMap.get("people_imgurl"))) {
//				request.setAttribute("idcardstatus", 1);
//			} else {
//				request.setAttribute("idcardstatus", 0);
//			}
//		} else {
//			request.setAttribute("idcardstatus", 0);
//		}
//
//		request.setAttribute("loanStatus", Convert.strToInt(statusMap.get("loanStatus"), 0));
//		request.setAttribute("borrowId", borrowIds);
//		return view("ucenter/borrow-status");
//
//	}

	/**
	 * 点击申请借款后，查询借款的审核状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryBorrowStatus", method = RequestMethod.POST)
	public @ResponseBody MessageResult queryBorrowStatus() throws Exception {
		Member member = getUser();
		if (member == null) {
			return error("token失效");
		} 
		String borrowIds = request("borrowId");
		if (ValidateUtil.isnull(borrowIds)) {
			return error("请输入借款编号");
		}
		int borrowId = Convert.strToInt(borrowIds, 0);
		Map<String, String> statusMap = borrowService.findBorrowById(borrowId);
		int borrowStatus = Convert.strToInt(statusMap.get("borrowStatus"), 0);
		MessageResult mr = new MessageResult();
		if (borrowStatus == 1) {
			mr.setCode(1);
			mr.setMessage("已提交");
		}
		if (borrowStatus == 2) {
			mr.setCode(2);
			mr.setMessage("正在审核");
		}
		if (borrowStatus == 3) {
			mr.setCode(3);
			mr.setMessage("初审失败");
		}
		if (borrowStatus == 4) {
			mr.setCode(4);
			mr.setMessage("初审成功");
		}
		if (!ValidateUtil.isnull(statusMap.get("bankCardNo"))) {
			mr.setCode(5);
			mr.setMessage("银行卡已绑定");
		}
		Map<String, String> memberMap = memberService.findMemberIdentyByMemId(member.getId());
		if (statusMap.get("bankCardNo") != null) {
			if (!ValidateUtil.isnull(memberMap.get("people_imgurl"))) {
				mr.setCode(6);
				mr.setMessage("身份已认证");
			}
		}

		if (borrowStatus == 6) {
			mr.setCode(7);
			mr.setMessage("复审成功");
		}
		if (borrowStatus == 7) {
			mr.setCode(8);
			mr.setMessage("复审失败");
		}
		if (borrowStatus == 8) {
			mr.setCode(9);
			mr.setMessage("放款成功");
		}
		return mr;
	}

	/**
	 * 在操作审核列表的时候，检查状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkBorrowStatus", method = RequestMethod.POST)
	public @ResponseBody MessageResult checkBorrowStatus() throws Exception {
		// 判断是否有等待审核的借款
		Member member = getUser();
		if (member == null) {
			return error("token失效");
		} 
		long borrowId = Convert.strToLong(request("borrowId"), 0);
		int compareId = Convert.strToInt(request("compareId"), 0);
		Map<String, String> map = borrowService.findBorrowById(borrowId);
		if (map != null) {
			int status = Convert.strToInt(map.get("borrowStatus"), 0);
			if (compareId > status) {
				if (compareId == 4) {
					return error("等待初审通过");
				} else if (compareId == 5) {
					if (ValidateUtil.isnull(map.get("bankCardNo"))) {
						return error("请选择银行卡");
					}

				} else {
					return error("请完成上一步认证");
				}

			}
		}
		return success();

	}

	/*
	 * 进入到已绑定的银行卡列表页面
	 */
//	@RequestMapping(value = "boundedBankCardList", method = RequestMethod.GET)
//	public String boundedBankCardList() throws Exception {
//
//		Member member = getUser();
//
//		List<Map<String, String>> cardList = new ArrayList<Map<String, String>>();
//		cardList = memberService.queryBankCardList(member.getId());
//		request.setAttribute("cardList", cardList);
//		return view("ucenter/bounded-bankcard");
//	}

	/*
	 * 进入到新增银行卡页面
	 */
	@RequestMapping(value = "bindBankCard", method = RequestMethod.GET)
	public String bindBankCard(HttpServletRequest request) throws Exception {
		Member member = this.getUser();
		String realName = member.getRealName();
		request.setAttribute("realName", realName );
		
		return view("ucenter/bind-bankcard");
	}
	
	@RequestMapping(value = "bindBankCard", method = RequestMethod.POST)
	public void doBindBankCard(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long userId = this.getUser().getId();
		String cardNo = request("bankCard");
		String bankName=request("bankName");
		Member member = memberService.findMember(userId);
		String identNo = member.getIdentNo();
		String realName = member.getRealName();
		JSONObject json = new JSONObject();
		json.put("version", payConfig.VERSION);
		json.put("oid_partner", payConfig.OID_PARTNER);
		json.put("user_id", String.valueOf(userId));
		json.put("timestamp", DateUtil.YYYYMMDDHHMMSS.format(new Date()));
		json.put("sign_type", "MD5");
		json.put("app_request", "3");
		json.put("url_return", payConfig.AUTH_URL_RETURN);
		json.put("risk_item", createRiskItem(realName));
		json.put("id_type", "0");
		json.put("id_no", identNo);
		json.put("acct_name", realName);
		json.put("card_no", cardNo);
		// 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(json.toString()), payConfig.TRADER_PRI_KEY,payConfig.MD5_KEY);
        json.put("sign", sign);
        log.info(json.toString());
        Map<String,String> map = new HashMap<String,String>();
        map.put("req_data", json.toString());
        response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String html = createForwardHtml(payConfig.AUTH_GATEWAY,map);
		request.getSession().setAttribute("weixinBindBankCard", 1); //标记微信绑卡（区别与APP绑卡）
		this.memberService.addMemberBank(userId, cardNo,bankName);//添加绑卡记录，成功时更新状态值（member_bank）
		response.getWriter().print(html);
		//this.response.getOutputStream().write(html);
		
	}

	private String createRiskItem(String realName) {
		JSONObject riskItemObj = new JSONObject();
		riskItemObj.put("user_info_full_name", realName);
		riskItemObj.put("frms_ware_category", "1999");
		return riskItemObj.toString();
	}
	
	public String createForwardHtml(String gateway,Map<String,String> map) {
		StringBuffer sb = new StringBuffer(
				"<html><head <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><script type='text/javascript'>window.onload=function(){document.getElementById('submitForm').submit();}</script></head><body>");
		// 表单内容  URLEncoder.encode(value, "utf-8")
		sb.append("<form action='" + gateway + "'  id='submitForm' method='post'>");
		map.forEach((key,value) -> {
			try {
				sb.append("<input type='hidden' name='"+key+"'  value='" + value + "'  />");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		sb.append("</form></body></html>");
		return sb.toString();
	}
	/**
	 * 选择银行
	 * 
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "listConstantBank", method = RequestMethod.POST)
//	public @ResponseBody MessageResult listConstantBank() throws Exception {
//		List<Map<String, String>> list = memberService.listBank();
//		MessageResult mr = new MessageResult();
//		mr.setCode(0);
//		mr.setData(list);
//		mr.setMessage("保存银行的code");
//		return mr;
//
//	}
//
//	/**
//	 * 选择省
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "provinceList", method = RequestMethod.POST)
//	public @ResponseBody MessageResult provinceList() throws Exception {
//		MessageResult mr = new MessageResult();
//		List<Map<String, String>> list = memberService.queryPlace(1);
//		mr.setCode(0);
//		mr.setData(list);
//		mr.setMessage("返回对应的ID");
//		return mr;
//	}
//
//	/**
//	 * 选择市
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "cityList", method = RequestMethod.POST)
//	public @ResponseBody MessageResult cityList() throws Exception {
//		int provinceId = Convert.strToInt(request("provinceId"), 0);
//		if (provinceId == 0) {
//			return error("请选择省");
//		}
//		MessageResult mr = new MessageResult();
//		List<Map<String, String>> list = memberService.queryPlace(provinceId);
//		mr.setCode(0);
//		mr.setData(list);
//		mr.setMessage("返回城市对应的ID");
//		return mr;
//	}

	/**
	 * 初审通过后，绑定银行卡，调用富有接口
	 * 
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "binding", method = RequestMethod.POST)
//	public @ResponseBody MessageResult binding() throws Exception {
//		Member member = getUser();
//		if (member == null) {
//			return error("token失效");
//		} 
//		String province = request("provinceName");
//		String city = request("citName");
//
//		String bankCardNo = request("bankCardNo");
//		String standPhone = request("standPhone");
//
//		String bankNo = request("bankCode");
//		String cityNo = request("cityno");
//		String branchBankName = request("branchbank");
//		if (ValidateUtil.isnull(bankCardNo)) {
//			return error("请输入卡号");
//		}
//		if (ValidateUtil.isnull(bankNo)) {
//			return error("请输入银行编号");
//		}
//		if (ValidateUtil.isnull(cityNo)) {
//			return error("请输入城市编号");
//		}
//		Map<String, String> cardMap = borrowService.findBanCardByNo(bankCardNo);
//
//		if (ValidateUtil.isnull(bankNo)) {
//			return error("请输入银行编号");
//		}
//		if (ValidateUtil.isnull(cityNo)) {
//			return error("请输入城市编号");
//		}
//
//		String bankName = borrowService.findBankByCode(bankNo).get("name");
//		long ret = 0;
//		if (cardMap == null) {
//			ret = memberService.insertMemberBankCard(bankCardNo, bankName, member.getMobilePhone(), member.getId(),
//					city, province, bankNo, cityNo, branchBankName, standPhone);
//		} else {
//			ret = memberService.updateMemberBankCard(Convert.strToInt(cardMap.get("id"), 0), bankCardNo, bankName,
//					member.getMobilePhone(), member.getId(), city, province, bankNo, cityNo, branchBankName);
//		}
//
//		if (ret > 0) {
//			MessageResult mr = new MessageResult();
//			Map<String, String> resultMap = new HashMap<String, String>();
//			if (cardMap == null) {
//				resultMap.put("bankCardId", "" + ret);
//			} else {
//				resultMap.put("bankCardId", cardMap.get("id"));
//			}
//			resultMap.put("bankCardNo", bankCardNo);
//			resultMap.put("bankNo", bankNo);
//			resultMap.put("standPhone", standPhone);
//			mr.setData(resultMap);
//			mr.setCode(0);
//			mr.setMessage("绑定银行卡成功");
//			return mr;
//		} else {
//			return error("富有银行卡验证成功，绑定成功，保存失败");
//		}
//
//	}

	/**
	 * 进入到银行卡签约页面中
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "qianyeFrist")
	public String qianyeFrist(HttpServletRequest request) throws Exception {
		String standPhone = request("standPhone");
		String bankCardId = request("bankCardId");
		request.setAttribute("bankCardId", bankCardId);
		request.setAttribute("standPhone", standPhone);

		return view("ucenter/qianyeFrist");
	}


	/**
	 * 身份认证，显示身份信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "identity")
	public String identity(HttpServletRequest request) throws Exception {
		long borrowId = Convert.strToLong(request("borrowId"), 0);
		Member member = getUser();
		Map<String, String> identityMap = memberService.findMemberIdentyByMemId(member.getId());
		request.setAttribute("realname", identityMap.get("real_name"));
		request.setAttribute("cardNo", identityMap.get("card_no"));
		request.setAttribute("borrowId", borrowId);
		return view("ucenter/identitySecond");

	}

	/**
	 * 身份认证，上传图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "identityForImage", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityForImage() throws Exception {
		long borrowId = Convert.strToLong(request("borrowId"), 0);
		Member member = getUser();
		if (member == null) {
			return error("token失效");
		} 
		Map<String, String> identityMap = memberService.findMemberIdentyByMemId(member.getId());
		String imgPath = request("imgPath");
		if (imgPath == null) {
			return success("请上传图片");
		}
		long ret = borrowService.updateIndetityForImage(Convert.strToInt(identityMap.get("id"), 0), imgPath, borrowId);

		if (ret > 0) {
			// SMSUtil.sendContent(member.getMobilePhone(),"您的编号"+borrowMap.get("borrowNo")+"申请，已提交到后台复审，请耐心等待！");
			return success("提交成功，等待审核");
		} else {
			return error("身份图片保存失败，请重试");
		}

	}

	/**
	 * 计算续期的费用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "calculateRenewalFee", method = RequestMethod.POST)
	public @ResponseBody MessageResult calculateRenewalFee() throws Exception {
		MessageResult mr = new MessageResult();
		Member member = getUser(); 
		long memberId = member.getId();
		
		Map<String, String> borrowMap = this.borrowService.findBorrowByMemberAndStatus8_9(memberId); //borrowService.findBorrowByMemberAndStatus(member.getId(), 8);
		
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		int borrowId = Convert.strToInt(request("borrowId"), 0);
		if (borrowId <= 0) {
			mr.setCode(-1);
			mr.setMessage("borrowId参数不正确");
			return mr;
		}
		if (borrowDays == 0) {
			mr.setCode(-2);
			mr.setMessage("请选择借款天数");
			return mr;
		}
		int amount = (int) Convert.strToDouble(borrowMap.get("benJin"), 0);
		//计算相关费用
		DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
		double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
		double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
		double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;
		double otherFee = 0;  
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
				
		Map<String, String> map = new HashMap<String, String>();
		map.put("xinFee", df.format(xinFee));
		map.put("shouFee", df.format(shouFee));
		map.put("serviceFee", df.format(serviceFee));
		map.put("otherFee", df.format(otherFee));
		map.put("total", df.format(otherFee + xinFee + shouFee + serviceFee));
		map.put("benjintotal", df.format(otherFee + xinFee + shouFee + serviceFee + amount));
		map.put("amount", "" + (amount));
		int borrowDate = Convert.strToInt(borrowMap.get("borrowDate"), 0);
		int addBorrowDay = Convert.strToInt(borrowMap.get("addBorrowDay"), 0);
		String secondAuditTime = borrowMap.get("secondAuditTime");
		Date startDay = DateUtil.strToDate(secondAuditTime);
		int days = (int) DateUtil.diffDays(startDay, new Date());
		Date needRepayDate = DateUtil.dateAddDay(new Date(), borrowDate + addBorrowDay + borrowDays - days - 1);
		// map.put("newRepayDay",""+DateUtil.dateToStringDate(needRepayDate));
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.strToDate(borrowMap.get("secondAuditTime"))); // 当天
		c.add(Calendar.DAY_OF_YEAR, borrowDate + addBorrowDay + borrowDays); // 下一天
		String dateBegins = DateUtil.YYYY_MM_DD.format(c.getTime());
		map.put("newRepayDay", dateBegins);
		mr.setCode(0);
		mr.setData(map);
		return mr;
	}

	/**
	 * 正常还款
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping({ "normalRepay" })
	public @ResponseBody MessageResult normalRepay() throws Exception {
		
		Member member = getUser();
		
		long memberId = member.getId();
		// 获取扣款的银行卡，扣款金额
		/*Map<String, String> bankCardMap = this.memberService.findBankCard(memberId);*/
		/*if (bankCardMap == null) {
			return error("您还未认证银行卡");
		}
		String bankCardNo = bankCardMap.get("cardNo");*/
		log.info("memberId = " + memberId);
		Map<String, String> borrowMap = borrowService.findBorrowByMemberAndStatus8_9(memberId ); 
		if(borrowMap == null){
			return error("您没有待还款的借款");
		}
		log.info(borrowMap);
		// 正常还款或者逾期还款
		int status = Integer.valueOf(borrowMap.get("borrowStatus"));
		long bid = Long.valueOf(borrowMap.get("id")); 

		double deduceSum = this.borrowService.getRepaySum(bid);

		
		//调用连连支付来还款
		String orderId = memberId + "" +DateUtil.YYYYMMDDMMHHSSSSS(new Date()) ;
		String agreeNo = member.getAgreeNo();
		String repaymentNo = "borrow_id_" + bid;
		double amount = deduceSum / 1000;
		String appointmentTime = borrowMap.get("appointmentTime");//DateUtil.dateToString(new Date()).substring(0,10);
		
		this.borrowService.initBorrowRepayOrderNo(bid, orderId);
		MessageResult mr = this.llpayService.autoRepayment(orderId, memberId, agreeNo,
				repaymentNo, amount, appointmentTime);
		if(mr.getCode() == 0){//扣款成功
			this.borrowService.initBorrowRepayStatus(bid);
			//long ret = borrowService.updateBorrowStatusRelation(bid, 10, member.getId(), 
			//		bankCardNo, borrowMap, deduceSum * 100, 0, getRemoteIp(), status);
			String content = "";
			String borrowNo = borrowMap.get("borrowNo");
			if (status == 8) {
				//content = "您的编号" + borrowMap.get("borrowNo") + "申请，已经成功还款，恭喜您成为平台的优质会员，再次申请，飞速审核！";
				   content = SMSUtil.successBorrow(borrowNo);
			} else {
				//content = "您的编号" + borrowMap.get("borrowNo") + "申请，已经成功还款，本次已逾期，联系客服，查看会员状态！";
				 content = SMSUtil.overdueBorrow(borrowNo);
			}
			SMSUtil.sendContent(member.getMobilePhone(), content);
		}
		return mr;
		

	}

	
	
	/**
	 * 确认逾期---扣除逾期费用
	 * 
	 * @return
	 */
	@RequestMapping({ "confirmRenewalRepay" })
	public @ResponseBody MessageResult confirmRenewalRepay() throws Exception {
		Member member = getUser();
		
		long memberId = member.getId();
		log.info("memberId = " + memberId);
		// 获取扣款的银行卡，扣款金额
		/*Map<String, String> bankCardMap = this.memberService.findBankCard(memberId);*/
		/*if(bankCardMap == null){
			return error("您还未认证银行卡");
		}
		String bankCardNo = bankCardMap.get("cardNo");*/


		double deduceSum = 0;
		int borrowDays = Integer.valueOf( request("borrowDays") );
		
		
		Map<String, String> borrowMap = borrowService.findBorrowByMemberAndStatus8_9(memberId ); 
		if(borrowMap == null){
			return error("您没有待还款的结款");
		}  
		if(borrowMap.get("borrowStatus").equals("9")){
			return error("您的借款已经逾期，不能再申请续期");
		}
		
		long bid = Long.valueOf(borrowMap.get("id"));
		int benJin = Integer.valueOf(borrowMap.get("benJin"));
		DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
		double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (benJin/100) * borrowDays;
		double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (benJin/100) * borrowDays;
		double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (benJin/100) * borrowDays;
		double otherFee = 0;  
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
		//扣款金额
		deduceSum  =  xinFee + shouFee + serviceFee  ;
		//测试除以1000
		//deduceSum = 1.0 * deduceSum / 1000;
		
		//调用连连支付来还款
		String orderId = memberId + "" +DateUtil.YYYYMMDDMMHHSSSSS(new Date()) ;
		String agreeNo = member.getAgreeNo();
		String repaymentNo = "borrow_id_" + bid;
		String appointmentTime = borrowMap.get("appointmentTime").substring(0, 10);//DateUtil.dateToString(new Date()).substring(0,10);
		MessageResult mr = null ;
		
		
		
		//调用连连支付扣款
		mr = this.llpayService.autoRepayment(orderId, memberId, agreeNo,
				repaymentNo, deduceSum*1.0/1000, appointmentTime);
		if(mr.getCode() == 0){ //连连支付扣款成功
			
			//重新设置还款计划 还款金额不变、还款时间改变
	    	String appointmentTime1 = this.borrowService.getNDaysBehindStr(appointmentTime, borrowDays) ;
	    	double amountToPay = benJin + Double.valueOf(borrowMap.get("totalFee")) ; 
	    	////////////测试数据！
	    	amountToPay = 1.0 * amountToPay / 1000;
	    	
	    	Map<String, String> pl = new HashMap<String, String>();
			pl.put("date", appointmentTime1 );
			pl.put("amount", amountToPay + "" ); 
			com.alibaba.fastjson.JSONArray plans = new com.alibaba.fastjson.JSONArray();
			plans.add(pl);
			mr = this.llpayService.authApply(memberId, agreeNo, repaymentNo, plans);
			//mr = this.llpayService.resetRepaymentPlan(memberId, agreeNo, repaymentNo, plans);
			if(mr.getCode() != 0){
				DB.rollback();
				return mr;
			}
			// 保存扣款记录
			borrowService.insertFundRecord(member.getId(), Convert.strToInt(borrowMap.get("id"), 0)/*, bankCardNo*/,
					deduceSum + "", "扣除续期费用", getRemoteIp(), 3);
			// 发送短信
			//SMSUtil.sendContent(member.getMobilePhone(), "您的编号" + borrowMap.get("borrowNo") + "申请，申请续期" + borrowDays
			//		+ "天成功，续期费用" + deduceSum + "元，扣款卡号" + bankCardNo + "，请到时正常还款后继续续期！");
			
			String borrowNo = borrowMap.get("borrowNo");
			String content = SMSUtil.renewalBorrow(borrowNo,borrowDays,deduceSum/*,bankCardNo*/);
			SMSUtil.sendContent(member.getMobilePhone(), content);
			
			// 改变借款状态,记录申请逾期记录
			long ret = borrowService.borrowOverdueHandle(borrowMap, deduceSum, borrowDays, getRemoteIp());
		}else{
			DB.rollback();
		}
		return mr;

	}


	// 放款
	public Map<String, String> borrowMoney(String bankno, String cityno, String bankname, String bankCardNo,
			String realname, int amountFen, String mobile, String idcardNo, String orderno) throws Exception {

		String path = "https://fht.fuiou.com/req.do";
		String merid = "0003720F0395576";
		String reqtype = "payforreq";// 收款

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" + "<payforreq>" + "<ver>2.00</ver>"
				+ "<merdt>" + DateUtil.getDateYMD() + "</merdt>" + "<orderno>" + orderno + "</orderno>" + "<bankno>"
				+ bankno + "</bankno>" + "<cityno>" + cityno + "</cityno>"// 付款
				+ "<accntno>" + bankCardNo + "</accntno>" + "<accntnm>" + realname + "</accntnm>" + "<amt>" + amountFen
				+ "</amt>" + "<entseq>test</entseq>" + "<memo>备注</memo>" + "<mobile>" + mobile + "</mobile>"
				+ "<certtp>0</certtp>" + "<certno>" + idcardNo + "</certno>" + "<txncd>" + "06" + "</txncd>"// 9.5
																											// 付款业务定义说明01
																											// 借款下发 02
																											// 逾期还款03
																											// 债权转让04 其他
				// 9.6 代收业务定义说明06 贷款还款07 逾期还款08 债权转让09 其他
				// +"<projectid>"+"0003720F0395576_20170602_0001"+"</projectid>"
				+ "</payforreq>";
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + "payforreq" + "|" + xml;// 付款
		String mac = Encrypt.MD5(macSource).toUpperCase();
		String param = "merid=" + merid + "&reqtype=" + reqtype + "&xml=" + xml + "&mac=" + mac;
		return httpclientPost(path, param);
	}

	// 查询放款结果
	public Map<String, String> fuyouchecked(String orderno, String startdt) throws Exception {

		String path = "https://fht.fuiou.com/req.do";
		String merid = "0003720F0395576";
		String reqtype = "qrytransreq";// 查看

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" + "<qrytransreq>"
				+ "<ver>2.00</ver>" + "<busicd>AP01</busicd>" + "<orderno>" + orderno + "</orderno>" + "<startdt>"
				+ startdt + "</startdt>" + "<enddt>" + startdt + "</enddt>" + "</qrytransreq>";
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + "qrytransreq" + "|" + xml;// 付款
		String mac = Encrypt.MD5(macSource).toUpperCase();
		String param = "merid=" + merid + "&reqtype=" + reqtype + "&xml=" + xml + "&mac=" + mac;
		return httpclientPost(path, param);

	}

}
