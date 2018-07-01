package com.spark.p2p.controller.app;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.AppRevision;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.AppSessionUtil;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Controller
@ResponseBody
@RequestMapping("/app/uc/center")
public class AppMemberCenterController extends AppBaseController{
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private SiteService siteService;
	
	
	/**
	 * 更改图像
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "home", method = RequestMethod.POST)
	public @ResponseBody MessageResult home() throws Exception {
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		
		user=memberService.findMember(user.getId());
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		user.setCommisionSum(Convert.strToDouble(df.format(user.getCommisionSum()), 0));; 
		MessageResult mr=new MessageResult();
		mr.setData(user);
		return mr;
	}
	
	/**
	 * 推广链接
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "extensionLink", method = RequestMethod.POST)
	public @ResponseBody MessageResult extensionLink() throws Exception {
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		user=memberService.findMember(user.getId());
		String url=new Model("constant_variable").where("name= ?","REGESITERURL").find().get("value");
		user=memberService.findMember(user.getId());
		MessageResult mr=new MessageResult();
		Map<String, String> map=new HashMap<>();
		map.put("reruestType","1");
		map.put("requestCode",user.getMemberNo()+"");
		map.put("url",url+"kssdre.html?reruestType=1&requestCode="+user.getMemberNo());
		mr.setData(map);
		return mr;
	}
	
	
	/**
	 * 更改图像
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "memberImg", method = RequestMethod.POST)
	public @ResponseBody MessageResult memberImg() throws Exception {		
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		user=memberService.findMember(user.getId());
		String imgpath=request("imgpath");
		if(ValidateUtil.isnull(imgpath)){
			return error("请选择图片");
		}
		long ret=memberService.updateMemberImg(user.getId(),imgpath);
		if(ret>0){
			return success("更新成功");
		}else{
			return error("更新失败");
		}
		
	}
	
	
	
	/**
	 * 我的借款
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "myBorrow", method = RequestMethod.POST)
	public @ResponseBody MessageResult myBorrow() throws Exception {		
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		user=memberService.findMember(user.getId());
		List<Map<String, String>> list=borrowService.queryBorrowByMemberId(user.getId());
		for(int i=0;i<list.size();i++){
			Map<String, String> map=list.get(i);
			int borrowDate=Convert.strToInt(map.get("borrowDate"), 0);
			int addBorrowDay=Convert.strToInt(map.get("addBorrowDay"), 0);
			String phoneType=map.get("type");
			map.put("borrowDate", (addBorrowDay+borrowDate)+"");
			map.put("phoneType",phoneType);
		}
		MessageResult mr=new MessageResult();
		mr.setCode(0);
		mr.setData(list);
		return mr;
	}
	
	
	/**
	 * 借款详情
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "myBorrowDetail", method = RequestMethod.POST)
	public @ResponseBody MessageResult myBorrowDetail() throws Exception {		
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		user=memberService.findMember(user.getId());
		String borrowId=request("borrowId");
		if(ValidateUtil.isnull(borrowId)){
			return error("请选择借款");
		}
		Map<String, String> bMap=borrowService.findBorrowById(Convert.strToInt(borrowId, 0));
		Map<String, String> rMap=borrowService.findLastPay(Convert.strToInt(bMap.get("id"), 0));
		Date date=null;
		if(!ValidateUtil.isnull(bMap.get("secondAuditTime"))){
			date=DateUtil.strToDate(bMap.get("secondAuditTime"));
			bMap.put("startDayLong",date.getTime()+"");
		}else{
			bMap.put("startDayLong","");
		}
		
		if(rMap!=null){
			bMap.put("realRepayDate",rMap.get("repayTime"));
		}else{
			bMap.put("realRepayDate","0");
		}
		double total=0;
		Map<String,String> repaymap=borrowService.findLastPay(Convert.strToInt(borrowId, 0));
		if(repaymap!=null){
			total=Convert.strToDouble(repaymap.get("remainBenjin"), 0)+Convert.strToDouble(repaymap.get("remainFee"), 0);
		}
		Map<String,String> overmap=borrowService.findAppayOverdueLog(Convert.strToInt(borrowId, 0));
		if(overmap!=null){
			total=0;
			total=Convert.strToDouble(overmap.get("remainFee"), 0)+Convert.strToDouble(overmap.get("overdueFee"), 0)+Convert.strToDouble(overmap.get("benjin"), 0);
		}
		if(repaymap==null && overmap==null){
			total=Convert.strToInt(bMap.get("benJin"), 0)+Convert.strToDouble(bMap.get("xinFee"), 0)+Convert.strToDouble(bMap.get("serviceFee"), 0)+Convert.strToDouble(bMap.get("shouFee"), 0);
		}
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		bMap.put("total", df.format(total));
		MessageResult mr=new MessageResult();
		mr.setCode(0);
		mr.setData(bMap);
		return mr;
	}
	
	
	/**
	 * 提现佣金
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "withdrawCommision", method = RequestMethod.POST)
	public @ResponseBody MessageResult withdrawCommision() throws Exception {
		Member user = getMember();
		if(user==null){
			return error("token失效");
		}
		user=memberService.findMember(user.getId());
		String amount=request("amount");
		if(ValidateUtil.isnull(amount)){
			return error("请输入提现金额");
		}
		
		double amounts=Convert.strToDouble(amount, 0);
		if(amounts<=0){
			return error("请输入正确提现金额");
		}
		if(user.getCommisionSum()<amounts){
			return error("请输入小于"+user.getCommisionSum()+"金额");
		}
		/*String bankCardNo=request("bankCardNo");
		if(ValidateUtil.isnull(bankCardNo)){
			return error("请输入银行卡号");
		}*/
		long ret=borrowService.insertWithdrawCommision(user.getId(),amounts,getRemoteIp(),user.getCommisionSum()/*, bankCardNo*/);
		if(ret>0){
			return success("申请提现成功");
		}else{
			return error("申请提现失败");
		}
		
	}
	
	/**
	 * 申请取消借款
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "applyDisConut", method = RequestMethod.POST)
	public @ResponseBody MessageResult applyDisConut(HttpServletRequest request) throws Exception {
		String borrowId=request("borrowId");
		if(ValidateUtil.isnull(borrowId)){
			return error("请输入借款编号");
		}
		Map<String, String> map=borrowService.findBorrowById(Convert.strToLong(borrowId, 0));
		int status=Convert.strToInt(map.get("borrowStatus"), 0);
		if(status==3 || status==6 || status==7 || status>=8){
			return error("此状态不能取消申请");
		}
		long ret=borrowService.cancelBorrowApply(Convert.strToLong(borrowId, 0), 12);
		if(ret>0){
			return success("成功取消借款");
		}else{
			return error("取消失败");
		}
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "loginOut", method = RequestMethod.POST)
	public @ResponseBody MessageResult loginOut(HttpServletRequest request) {
		String token = request.getHeader("token");
		AppSessionUtil.removeToken(token);
		return AppSessionUtil.removeToken(token)?success("OK"):error("退出失败，请重试");
	}
	
	
	/**
	 * 获取通讯录
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "getCommicateRecord", method = RequestMethod.POST)
	public @ResponseBody MessageResult getCommicateRecord() throws SQLException {
		Member user = getMember();
		String detail = request("detail");
		if(ValidateUtil.isnull(detail)){
			return error("获取通讯录为空");
		}
		
		memberService.updateMemberCommicate(user.getId(),detail);
		return success();
	}
	
	
	/**
	 * 检查版本更新
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "checkNewEdition", method = RequestMethod.POST)
	public @ResponseBody MessageResult checkNewEdition() throws Exception {
		String editionNo = request("editionNo");
		if(ValidateUtil.isnull(editionNo)){
			return error("请输入版本号");
		}
		AppRevision app=siteService.findLateastRevision(2);
		
		if(app != null && Convert.strToDouble(app.getRevision(), 0) > Convert.strToDouble(editionNo, 0)) {
			String newno=app.getRevision();
			MessageResult mr=new MessageResult();
			Map<String, String> map=new HashMap<String,String>();
			mr.setCode(0);
			map.put("type", "Android");
			map.put("number", newno);
			map.put("path", app.getUrl());
			mr.setData(map);
			
			return mr;
		}else{
			return error("当前是最新版本");
		}
		
	}
	
	/**
	 * 消息列表接口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "myNews", method = RequestMethod.POST)
	public @ResponseBody MessageResult myNews() throws Exception {		
		Member user = getMember();
		if(user==null){
			return error(400,"token失效");
		}
		List<Map<String, String>> list=borrowService.myNews();
		MessageResult mr=new MessageResult();
		mr.setCode(0);
		mr.setData(list);
		return mr;
	}
	
	/**
	 * 手机认证协议
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "telProtocol", method = RequestMethod.POST)
	public @ResponseBody String telProtocol() throws Exception {		
		//return "http://www.baihengtouzi.com/regesister.html";
		//return "http://www.yongjd.com:8080/regesister.html";
		return null;
	}
	
	/**
	 * 借款认证协议
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "loanProtocol", method = RequestMethod.POST)
	public @ResponseBody String loanProtocol() throws Exception {		
		//return "http://www.baihengtouzi.com/regesister.html";
		//return "http://www.yongjd.com:8080/regesister.html";
		return null;
	}
	
	
	
}
