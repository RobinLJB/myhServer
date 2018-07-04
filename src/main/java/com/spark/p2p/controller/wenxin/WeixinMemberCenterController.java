package com.spark.p2p.controller.wenxin;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;

/*
 * 个人中心
 */
@Controller
@RequestMapping("/mobile/ucenter")
public class WeixinMemberCenterController extends WeixinBaseController{
	
	public static final Log log = LogFactory.getLog(WeixinMemberCenterController.class); 
	
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private MemberService memberService;

	@Autowired
	private IndexService indexService;
	@Autowired
	private CMSService cMSService;
	@Autowired
	private SelectService selectService;
	
	
	
	/**
	 * 借款列表
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "borrowList" )
	public  String borrowList(HttpServletRequest request) throws Exception {
		Member member = getUser();
		
		List<Map<String, String>> borrowList=borrowService.queryBorrowByMemberId(member.getId());
		for(int i=0;i<borrowList.size();i++){
			Map<String, String> map=borrowList.get(i);
			int borrowDate=Convert.strToInt(map.get("borrowDate"), 0)+Convert.strToInt(map.get("addBorrowDay"), 0);
			map.put("borrowDate", borrowDate+"");
		}
		request.setAttribute("borrowList", borrowList);
		return view("ucenter/borrow-list");
		
	}
	
	
	/**
	 * 借款详情
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "borrowDetails" )
	public  String borrowDetails(HttpServletRequest request) throws Exception {
		long borrowId=Convert.strToLong(request("borrowId"), 0);
		Map<String, String> borrowMap=borrowService.findBorrowById(borrowId);
		int borrowDate=Convert.strToInt(borrowMap.get("borrowDate"), 0);
		int addBorrowDay=Convert.strToInt(borrowMap.get("addBorrowDay"), 0);
		Date startDay=null;
		if(!ValidateUtil.isnull(borrowMap.get("secondAuditTime"))){
			startDay=DateUtil.strToDate(borrowMap.get("secondAuditTime"));
			int days=(int) DateUtil.diffDays(startDay,new Date());
			Date needRepayDate=DateUtil.dateAddDay(new Date(), borrowDate+addBorrowDay-days);
			borrowMap.put("newRepayDay",""+DateUtil.dateToStringDate(needRepayDate));
		}else{
			borrowMap.put("newRepayDay",null);
		}
		
		request.setAttribute("borrowMap", borrowMap);
		double total=0;
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		Map<String,String> repaymap=borrowService.findLastPay((int)borrowId);
		if(repaymap!=null){
			total=Convert.strToDouble(repaymap.get("remainBenjin"), 0)+Convert.strToDouble(repaymap.get("remainFee"), 0);
		}
		Map<String,String> overmap=borrowService.findAppayOverdueLog((int)borrowId);
		if(overmap!=null){
			total=0;
			total=Convert.strToDouble(overmap.get("remainFee"), 0)+Convert.strToDouble(overmap.get("overdueFee"), 0)+Convert.strToDouble(overmap.get("benjin"), 0);
		}
		if(repaymap==null && overmap==null){
			total=Convert.strToInt(borrowMap.get("benJin"), 0)+Convert.strToDouble(borrowMap.get("xinFee"), 0)+Convert.strToDouble(borrowMap.get("serviceFee"), 0)+Convert.strToDouble(borrowMap.get("shouFee"), 0);
		}
		
		request.setAttribute("total", df.format(total));
		return view("ucenter/loan-detail");
		
	}
	
	
	@RequestMapping(value = "cancelBorrow", method = RequestMethod.POST)
	public @ResponseBody MessageResult cancelBorrow() throws Exception {
		long borrowId=Convert.strToLong(request("borrowId"), 0);
		Map<String, String> map=borrowService.findBorrowById(borrowId);
		int status=Convert.strToInt(map.get("borrowStatus"), 0);
		if(status==3 || status==6 || status==7 || (status>=8)){
			return error("此状态不能取消申请");
		}
		long ret=borrowService.cancelBorrowApply(borrowId, 12);
		if(ret>0){
			return success("取消成功");
		}else{
			return error("取消失败");
		}
	}
	
	/**
	 * 邀请好友
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "invitation" )
	public  String invitation(HttpServletRequest request) throws Exception {
		Member member = getUser();
		request.setAttribute("member", member);
		return view("ucenter/invitation");
		
	}
	
	
	@RequestMapping(value = "withdraw", method = RequestMethod.POST)
	public @ResponseBody MessageResult doLogin() throws Exception {
		Member member = getUser();
		if(member==null){
			return error("token失效");
		}
		member=memberService.findMember(member.getId());
		Map<String, String> map=memberService.findWithdrawStatusByMid(member.getId());
		if(map!=null){
			return error("不能重复申请");
		}
		long ret=memberService.insertApplyWithdrawRecoed(member.getId(),getRemoteIp());
		if(ret>0){
			return success("申请成功");
		}else{
			return error("申请失败");
		}
	}
	
	/**
	 * 联系客服
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "customService" )
	public  String customService() throws Exception {
		return view("ucenter/customer-service");
	
	}
	
	
	/**
	 * 反馈意见
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "feedback" )
	public  String feedback() throws Exception {
		return view("ucenter/feedback");
		
	}
	
	
	/**
	 * 处理反馈意见
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "adviseSubmit", method = RequestMethod.POST)
	public @ResponseBody MessageResult adviseSubmit() throws Exception {
		Member member = getUser();
		if(member==null){
			return error("token失效");
		}
		member=memberService.findMember(member.getId());
		String details=request("details");
		if(ValidateUtil.isnull(details)){
			return error("请填写反馈内容");
		}
		long ret=memberService.insertAdvise(member.getId(),getRemoteIp(),details);
		if(ret>0){
			return success("申请成功");
		}else{
			return error("申请失败");
		}
	}
	
	
	/**
	 * 处理反馈意见
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "savePeopleImg", method = RequestMethod.POST)
	public @ResponseBody MessageResult savePeopleImg() throws Exception {
		log.info("-----------------------------保存图片");
		Member member = getUser();
		if(member==null){
			return error("token失效");
		}
		member=memberService.findMember(member.getId());
		String imgpath=request("imgpath");
		if(ValidateUtil.isnull(imgpath)){
			return error("请选择图片");
		}
		long ret=memberService.updateMemberImg(member.getId(),imgpath);
		log.info("-----------------------------保存图片-----------成功");
		if(ret>0){
			return success("保存成功");
		}else{
			return error("更改失败");
		}
	}
	
	
	
	/**
	 * 分类问题列表
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "moreQuestion" ) 
	public  String problem(HttpServletRequest request) throws Exception {
		String type=request("type");
		List<Map<String, String>> arctileList=cMSService.queryArctileByType(Convert.strToInt(type, 0));
		request.setAttribute("arctileList", arctileList);
		request.setAttribute("type", Convert.strToInt(type, 0));
		return view("ucenter/article-list");
		
	}
	
	
	/**
	 * 问题详情
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "problem/{aid}" )
	public  String problem(HttpServletRequest request,@PathVariable Integer aid) throws Exception {
		Map<String, String> arctileMap=cMSService.findArticle(aid);
		request.setAttribute("artPrevious", aid-1);
		request.setAttribute("artNext", aid+1);
		request.setAttribute("article", arctileMap);
		Integer type = requestInt("type");
		request.setAttribute("cateId", type);
		
		/*上一篇文章*/
		request.setAttribute("artPrevious",cMSService.artPrevious(aid,type));
		
		/*下一篇文章*/
		request.setAttribute("artNext",cMSService.artNext(aid,type));
		
		request.setAttribute("cate", selectService.querySelectOption("CATE_ARTICLE"));
		request.setAttribute("imgMap", indexService.findImgByCate("pc_frontarticle"));
		request.setAttribute("article", cMSService.findArticle(aid));
		return view("ucenter/article-detail");
		
	}
	
}