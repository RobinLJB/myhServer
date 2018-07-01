package com.spark.p2p.controller.wechat;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.handle.MessageHandle;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import com.github.sd4324530.fastweixin.util.SignUtil;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.FinanceService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;

@RestController
@RequestMapping("/weixin")
public class WeixinController extends WeixinControllerSupport {
	private static final Logger log = LoggerFactory.getLogger(WeixinController.class);
	private String token = "abcasfweros";
	private String appId = "wxd2b26a838e51fae9";
	private String AESKey;
	private String scretKey = "438f100f01f65176ab4900578aa7a094";
	@Autowired
	private MemberService memberService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private FinanceService financeService;
	// 设置TOKEN，用于绑定微信服务器
	@Override
	protected String getToken() {
		return token;
	}

	// 使用安全模式时设置：APPID
	// 不再强制重写，有加密需要时自行重写该方法
	@Override
	protected String getAppId() {
		return appId;
	}
	
	
	// 使用安全模式时设置：密钥
	// 不再强制重写，有加密需要时自行重写该方法
	@Override
	protected String getAESKey() {
		return AESKey;
	}

	// 重写父类方法，处理对应的微信消息
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		String content = msg.getContent();
		String openId = msg.getFromUserName();
		log.debug("用户发送到服务器的内容:{},openId:{}", content,openId);
		return processKeyword(content,openId);
	}

	
	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		return processKeyword(event.getEventKey(),event.getFromUserName());
	}

	public TextMsg processKeyword(String keyword,String openId){
		Member member = null;
		Map<String,Double> finance = new HashMap<String,Double>();
		try {
			member = memberService.findMemberByWechatId(openId);
			finance = financeService.findMemberFinance(member.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(member == null){
			
			//提示绑定
			return new TextMsg("欢迎使用功德融微信公众号管理平台,若您还未注册账号,请<a href=\"http://m.gdrgdr.cn/ucenter/register.html\">点击这里</a>注册账号;"
					+"\n----------\n"
					+ "若您已有账号,<a href=\"http://www.gdrgdr.cn/ucenter/bindWeixin/"+openId+".html\">请先点击绑定账号</a>");
		}
		if(keyword.equals("余额")){
			DecimalFormat df = new DecimalFormat("0.00");
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的"+member.getUsername()+",您的账户资产如下:\n");
			sb.append("待收金额:"+df.format(finance.get("dueinSum"))+"\n");
			return new TextMsg(sb.toString());
		}else if(keyword.equals("收款")){
			Map<String,Object> due = new HashMap<String,Object>();
			try {
				due = null;
			} catch (Exception e) {
				e.printStackTrace();
				return new TextMsg("系统无响应,请重试!");
			}
			DecimalFormat df = new DecimalFormat("0.00");
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的"+member.getUsername()+",您的待收资产如下:\n");
			sb.append("待收笔数:"+due.getOrDefault("dueSum", 0)+"\n");
			sb.append("待收总额:"+df.format(due.getOrDefault("totalDue", 0))+"\n");
			sb.append("待收本金:"+df.format(due.getOrDefault("amountDue", 0))+"\n");
			sb.append("待收利息:"+df.format(due.getOrDefault("interestDue", 0))+"\n");
			return new TextMsg(sb.toString());
		}else if(keyword.equals("还款")){
			Map<String,Object> repay = new HashMap<String,Object>();
			try {
				repay =null;
			} catch (Exception e) {
				e.printStackTrace();
				return new TextMsg("系统无响应,请重试!");
			}
			DecimalFormat df = new DecimalFormat("0.00");
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的"+member.getUsername()+",您的待还款情况如下:\n");
			sb.append("待还笔数:"+repay.getOrDefault("sum", 0)+"\n");
			sb.append("待还总额:"+df.format(repay.getOrDefault("totalAmount", 0))+"\n");
			sb.append("待还本金:"+df.format(repay.getOrDefault("amount", 0))+"\n");
			sb.append("待还利息:"+df.format(repay.getOrDefault("interest", 0))+"\n");
			return new TextMsg(sb.toString());
		}
		else {
			//未识别关键词回复
			return new TextMsg("未识别的用户消息!");
		}
	}
	
	
	public void setAESKey(String aESKey) {
		this.AESKey = aESKey;
	}

	public String getScretKey() {
		return scretKey;
	}

	public void setScretKey(String scretKey) {
		this.scretKey = scretKey;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	protected boolean isLegal(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        System.out.println("signature:"+signature);
        return SignUtil.checkSignature(getToken(), signature, timestamp, nonce);
    }
	
	/*
	 * 1.1版本新增，重写父类方法，加入自定义微信消息处理器 不是必须的，上面的方法是统一处理所有的文本消息，如果业务觉复杂，上面的会显得比较乱
	 * 这个机制就是为了应对这种情况，每个MessageHandle就是一个业务，只处理指定的那部分消息
	 */
	/*
	 * @Override protected List<MessageHandle> initMessageHandles() {
	 * List<MessageHandle> handles = new ArrayList<MessageHandle>();
	 * handles.add(new MyMessageHandle()); return handles; }
	 * //1.1版本新增，重写父类方法，加入自定义微信事件处理器，同上
	 * 
	 * @Override protected List<EventHandle> initEventHandles() {
	 * List<EventHandle> handles = new ArrayList<EventHandle>(); handles.add(new
	 * MyEventHandle()); return handles; }
	 */
}