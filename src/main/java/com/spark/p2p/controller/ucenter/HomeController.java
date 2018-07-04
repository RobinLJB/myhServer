package com.spark.p2p.controller.ucenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.FinanceService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.NoticeService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.IdCard;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;

@Controller
public class HomeController extends UCenterController {
	
	public static final Log log = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private FinanceService financeService;
	@Autowired
	private MemberService memberService;

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MemberService mbService;

	
	/**
	 * 用户中心主页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "home")
	public String home() throws Exception {
		return view("home");
	}

	// 美图秀秀上传头像
	@RequestMapping(value = "updateHead", method = RequestMethod.GET)
	public String updateHeadImg(HttpServletRequest request) throws Exception {
		log.info(AppSetting.SITE_HOST);
		request.setAttribute("urlWeb", AppSetting.SITE_HOST);
		return view("update-headImg-meitu");
	}
	
	
	/**
	 * 获取
	 * @return
	 */
	public List<String> getLateast3Month(){
		Calendar calendar = Calendar.getInstance();
		int i = 1;
		List<String> list = new ArrayList<String>();
		while(i <= 90){
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			list.add(DateUtil.YYYY_MM_DD.format(calendar.getTime()));
			i ++;
		}
		return list;
	}
	
	/**
	 * 解析统计数据
	 * @param dateList
	 * @param list
	 * @return
	 */
	public List<Double> processData(List<String> dateList,List<Map<String,String>> list){
		List<Double> dataList = new ArrayList<Double>();
		for(int i=0;i<dateList.size();i++){
			dataList.add(0D);
		}
		for(Map<String,String> map:list){
			String date = map.get("date");
			Double amount = Convert.strToDouble(map.get("amount"), 0);
			int index = dateList.indexOf(date);
			if(index >= 0){
				dataList.set(index, amount);
			}
		}
		return dataList;
	}
	
	
	/**
	 * 身份验证
	 * @throws Exception 
	 */
	@RequestMapping(value="aaa/cardIdCheckedPost",method=RequestMethod.POST)
	public @ResponseBody MessageResult cardIdCheckedPost() throws Exception { 
		int ids=Convert.strToInt(request("ids"), 0);
		String realName=request("realName");
		String cardNo=request("cardNo");
		String cardImgurl=request("cardImgurl");
		String peopleImgurl=request("peopleImgurl");
		Member member =(Member) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(Const.SESSION_MEMBER);
		if(cardNo==null || cardNo.length()==0){
			return error("请输入身份证");
		}
		String flag=IdCard.validate(cardNo);
		if(flag!="SUCCESS"){
			return error("身份证格式出错");
		}
		if(ids<0){
			Identity identitya=memberService.findMemberIdentyByCardNo(cardNo);
			if(identitya!=null){
				return error("该身份证已经验证了");
			}
		}
		
		Identity identity=new Identity();
		identity.setMemberId(member.getId());
		identity.setRealName(realName);
		identity.setCardNo(cardNo);
		//identity.setCardImgurl(cardImgurl);
		//identity.setPeopleImgurl(peopleImgurl);//暂未看干嘛用的,9.27
		identity.setId(ids);
		if(ids<0){
			identity.setStatus(0);
		}else{
			identity.setStatus(1);
		}
		long ret=0;//memberService.updateXqdIdentityCheck(identity);
		
		if(ret>0){
			return success("保存成功");
		}else{
			return error("保存失败");
		}
		
	}
	
	
	
	/*静态借款列表*/
	@RequestMapping(value = "loanList", method = RequestMethod.GET)
	public String loanlist() throws Exception {
		return view("loan-list");
	}
	
	/*静态借款详情*/
	@RequestMapping(value = "loanDetail", method = RequestMethod.GET)
	public String loanletail() throws Exception {
		return view("loan-detail");
	}
	/*静态邀请好友*/
	@RequestMapping(value = "invitation", method = RequestMethod.GET)
	public String invitation() throws Exception {
		return view("invitation");
	}
	/*邀请好友（二维码下载页面）*/
	@RequestMapping(value = "customerDownload", method = RequestMethod.GET)
	public String customerDownload() throws Exception {
		return view("customer-download");
	}
	/*邀请好友并发送给朋友*/
	@RequestMapping(value = "customerWithdraw", method = RequestMethod.GET)
	public String customerWithdraw() throws Exception {
		return view("customer-withdraw");
	}
	
	/*客服服务（联系客服）*/
	@RequestMapping(value = "customerService", method = RequestMethod.GET)
	public String customerService1() throws Exception {
		return view("customer-service");
	}
	
	
	/*静态平台反馈*/
	@RequestMapping(value = "feedback", method = RequestMethod.GET)
	public String feedback() throws Exception {
		return view("feedback");
	}
	
	/*静态常见问题*/
	@RequestMapping(value = "moreQuestion", method = RequestMethod.GET)
	public String moreQuestion() throws Exception {
		return view("more-question");
	}
	
	/*静态常见问题详情*/
	@RequestMapping(value = "answer", method = RequestMethod.GET)
	public String answer() throws Exception {
		return view("answer");
	}
	/*邀请好友赚佣金*/
	@RequestMapping(value = "commission", method = RequestMethod.GET)
	public String commission() throws Exception {
		return view("commission");
	}
	
	
}

