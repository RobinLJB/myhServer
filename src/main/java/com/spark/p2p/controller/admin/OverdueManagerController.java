package com.spark.p2p.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.AdminUserService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.OverdueBorrowService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.CardIdCheck;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;

@Controller
@RequestMapping("/admin/overdue")
public class OverdueManagerController extends BaseAdminController {
	
	
	@Autowired
	private OverdueBorrowService overdueBorrowService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private BorrowService borrowService;
	
	
	@Autowired
	private BorrowAdminService borrowAdminService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private RepayAdminService repayAdminService;
	
	
	/**
	 * 等待逾期分配列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distribution")
	public String distributionIndex() throws Exception {
		
		return view("overdue/distribution-index");
	}
	
	
	/**
	 * 等待分配列表
	 * @return
	 */
	@RequestMapping(value = "distribution/list")
	public DataTable distributionList() {
		return dataTable((params) -> overdueBorrowService.queryWaitForDistribution(params,0));
	}
	
	
	/**
	 * 等待分配详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distribution/{bid}")
	public String distributionDetail(HttpServletRequest request, @PathVariable Integer bid) throws Exception {
		//获取分配人员
		List<Map<String, String>> adminList=adminUserService.queryAdmin(12);
		//获取借款详情
		Map<String, String> borrowMap=borrowService.findBorrowById(bid);
		//
		Member member=memberService.findMember(Convert.strToInt(borrowMap.get("member_id"), 0));
		//获取逾期记录
		Map<String, String> overdueMap=borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		Map<String,String> idcardMap=memberService.findMemberIdentyByMemId(Convert.strToInt(borrowMap.get("member_id"), 0));
		Map<String, String> infoMap=CardIdCheck.getCardInfo(member.getIdentNo());
		//身份信息
		Map<String, String> cardMap = memberService.findMemberIdentyByMemId(member.getId());
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("cardMap",cardMap);
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("member", member);
		request.setAttribute("adminList", adminList);
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("overdueMap", overdueMap);
		request.setAttribute("idcardMap", idcardMap);
		request.setAttribute("infoMap", infoMap);
		return view("overdue/distribution-detail");
	}
	
	
	/**
	 * 已经逾期分配列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distributed")
	public String distributedIndex() throws Exception {
		
		return view("overdue/distributed-index");
	}
	
	
	/**
	 * 已经分配列表
	 * @return
	 */
	@RequestMapping(value = "distributed/list")
	public DataTable distributedList() {
		return dataTable((params) -> overdueBorrowService.queryWaitForDistribution(params,1));
	}
	
	
	/**
	 * 查看分配的催收情况
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distributeds/{bid}")
	public String distributedDetails(HttpServletRequest request,@PathVariable int bid) throws Exception {
		
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(bid);
		//催收记录
		List<Map<String, String>> pressList=overdueBorrowService.queryPressRecord(bid);
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("pressList", pressList);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//手机认证，芝麻信息
		//Map<String, String> phoneAndAplipayMap =memberService.findMemberAuditChain(mid);
		//request.setAttribute("phoneAndAplipayMap", phoneAndAplipayMap);
		//认证信息
		Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap);
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(bid);
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		
		
		return view("overdue/distributed-detail");
	}
	

	/**
	 * 已分配的查看催收记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distributed/{bid}")
	public String distributedDetail(HttpServletRequest request,@PathVariable int bid) throws Exception {
		
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(bid);
		request.setAttribute("borrowMap", borrowMap);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//手机认证，芝麻信息
		//Map<String, String> phoneAndAplipayMap =memberService.findMemberAuditChain(mid);
		//request.setAttribute("phoneAndAplipayMap", phoneAndAplipayMap);
		//认证信息
		Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap);
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(bid);
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		Map<String,String> idcardMap=memberService.findMemberIdentyByMemId(Convert.strToInt(borrowMap.get("member_id"), 0));
		request.setAttribute("idcardMap", idcardMap);
		return view("overdue/distribution-detail");
	}
	
		
	/**
	 * 保存逾期分配人员
	 * @return
	 * @throws Exception
	*/ 
	@RequestMapping(value = "distributionUpdate")
	public @ResponseBody MessageResult distributionUpdate(HttpServletRequest request) throws Exception {
		String adminId=request("adminId");
		String borrowId=request("borrowId");
		if(ValidateUtil.isnull(adminId) || ValidateUtil.isnull(borrowId)){
			return error("参数错误");
		}
		long ret=overdueBorrowService.updateOverdueDistribution(Convert.strToInt(adminId, 0),Convert.strToInt(borrowId, 0),Convert.strToInt(borrowAdminService.findBackAuditByBId(Convert.strToInt(borrowId, 0)).get("id"), 0));
		if(ret>0){
			return success();
		}else{
			return error("分配失败");
		}
		
	}
	
	/**
	 * 查看已分配的催收记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressRecord")
	public String pressRecordIndex() throws Exception {
		
		return view("overdue/pressRecord-index");
	}
	
	
	/**
	 * 查看已分配的催收记录
	 * @return
	 
	@RequestMapping(value = "pressRecord/list")
	public DataTable pressRecordList() {
		return dataTable((params) -> overdueBorrowService.queryPressRecordList(params));
	}
	*/
	
	/**
	 * 指派给我的逾期
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mymanager")
	public String distributedToMeIndex(HttpServletRequest request) throws Exception {
		request.setAttribute("status", "1");
		return view("overdue/distributedToMe-index");
	}
	
	
	/**
	 *指派给我的逾期
	 * @return
	 */
	@RequestMapping(value = "distributedToMe/list/{status}")
	public DataTable distributedToMeList(@PathVariable int status) {
		Admin admin=getAdmin();
		return dataTable((params) -> overdueBorrowService.queryDistributedToMe(params,admin.getId(),1));
	}
	
	
	/**
	 * 催收及借款详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "distributedToMe/{bid}")
	public String distributedToMeDetail(HttpServletRequest request,@PathVariable int bid) throws Exception {
		
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(bid);
		request.setAttribute("borrowMap", borrowMap);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//手机认证，芝麻信息
		//Map<String, String> phoneAndAplipayMap =memberService.findMemberAuditChain(mid);
		//request.setAttribute("phoneAndAplipayMap", phoneAndAplipayMap);
		//认证信息
		Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap);
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(bid);
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		
		
		return view("overdue/distributedToMe-detail");
	}
	
	
	/**
	 * 保存催收记录
	 * @return
	 * @throws Exception
	*/ 
	@RequestMapping(value = "pressRecordUpdate")
	public @ResponseBody MessageResult pressRecordUpdate() throws Exception {
		String phoneCall=request("phoneCall");
		String borrowId=request("borrowId");
		String repayStatus=request("repayStatus");
		String auditOpinion=request("auditOpinion");
		if(ValidateUtil.isnull(auditOpinion)){
			return error("请输入催收结果");
		}
		if(ValidateUtil.isnull(repayStatus)){
			repayStatus="2";
		}
		Admin admin=getAdmin();
		if(ValidateUtil.isnull(repayStatus) || ValidateUtil.isnull(phoneCall) || ValidateUtil.isnull(borrowId)){
			return error("参数错误");
		}
		
		long ret=overdueBorrowService.addPressRecord(Convert.strToInt(borrowId, 0),admin.getId(),Convert.strToInt(phoneCall, 0),Convert.strToInt(repayStatus, 0),auditOpinion,borrowService.findBorrowById(Convert.strToInt(borrowId, 0)).get("member_id"));
		if(ret>0){
			if("1".equals(repayStatus)){
				return success("已还款");
			}else if("2".equals(repayStatus)){
				return error(-2,"已催收未还款");
			}else if("3".equals(repayStatus)){
				return error(-3,"已催收死账");
			}else{
				return error("操作成功");
			}
			
		}else{
			return error("操作成功");
		}
		
	}
	
	
	
	/**
	 * 已催收未还款
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressedUnrepay")
	public String pressedUnrepayIndex() throws Exception {
		
		return view("overdue/pressedUnrepay-index");
	}
	
	
	/**
	 *指派给我的逾期
	 * @return
	 */
	@RequestMapping(value = "pressedUnrepay/list")
	public DataTable pressedUnrepayList() {
		Admin admin=getAdmin();
		return dataTable((params) -> overdueBorrowService.queryDistributedToMe(params,admin.getId(),2));
	}
	
	
	/**
	 * 再次催收
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "presssecond/{auditId}")
	public String presssecondDetail(HttpServletRequest request,@PathVariable int auditId) throws Exception {
		
		//审核表
		Map<String, String> auditMap=overdueBorrowService.findBackAuditByBId(auditId);
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(Convert.strToInt(auditMap.get("borrow_id"), 0));
		//催收记录
		List<Map<String, String>> pressList=overdueBorrowService.queryPressRecord(Convert.strToInt(auditMap.get("borrow_id"), 0));
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("pressList", pressList);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//手机认证，芝麻信息
		//Map<String, String> phoneAndAplipayMap =memberService.findMemberAuditChain(mid);
		//request.setAttribute("phoneAndAplipayMap", phoneAndAplipayMap);
		//认证信息
		Map<String, String> auditMap1 =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap1);
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(Convert.strToInt(auditMap.get("borrow_id"), 0));
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		
		
		return view("overdue/pressedUnrepay-detail");
	}
	
	
	
	/**
	 * 已催收  已还款
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressedAlreadyepay")
	public String pressedAlreadyepayIndex() throws Exception {
		
		return view("overdue/pressedAlreadyepay-index");
	}
	
	
	/**
	 *已催收  已还款
	 * @return
	 */
	@RequestMapping(value = "pressedAlreadyepay/list")
	public DataTable pressedAlreadyepayList() {
		Admin admin=getAdmin();
		return dataTable((params) -> overdueBorrowService.queryDistributedToMe(params,admin.getId(),3));
	}
	
	
	/**
	 * 显示成功还款的催收
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressedAlreadyepay/{bid}")
	public String pressedAlreadyepayDetail(HttpServletRequest request,@PathVariable int bid) throws Exception {
		
		//审核表
		//Map<String, String> auditMap=overdueBorrowService.findBackAuditByBId(auditId);
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(bid);
		//催收记录
		List<Map<String, String>> pressList=overdueBorrowService.queryPressRecord(bid);
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("pressList", pressList);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//认证信息
		Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap);
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(bid);
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		
		
		return view("overdue/pressedAlreadyepay-detail");
	}
	
	/**
	 * 已催收  死账
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressedDieaBorrow")
	public String pressedDieaBorrowIndex() throws Exception {
		
		return view("overdue/pressedDieaBorrow-index");
	}
	
	
	/**
	 *已催收  死账
	 * @return
	 */
	@RequestMapping(value = "pressedDieaBorrow/list")
	public DataTable pressedDieaBorrowList() {
		Admin admin=getAdmin();
		return dataTable((params) -> overdueBorrowService.queryDistributedToMe(params,admin.getId(),4));
	}
	
	
	/**
	 * 显示成功还款的催收
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pressedDieaBorrow/{auditId}")
	public String pressedDieaBorrowDetail(HttpServletRequest request,@PathVariable int auditId) throws Exception {
		
		//审核表
		Map<String, String> auditMap=overdueBorrowService.findBackAuditByBId(auditId);
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(Convert.strToInt(auditMap.get("borrow_id"), 0));
		//催收记录
		List<Map<String, String>> pressList=overdueBorrowService.queryPressRecord(Convert.strToInt(auditMap.get("borrow_id"), 0));
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("pressList", pressList);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		request.setAttribute("member", member);
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		
		Map<String,String> linkmansize = new HashMap<String,String>();
		if(infoMap!=null){
			String linkman = member.getCommicateDetail();
			if(linkman!=null){
				JSONObject json = new JSONObject(linkman);
				JSONArray person = json.optJSONArray("person");
				linkmansize.put("linkmansize", person.length()+"");
			}
		}else{
			linkmansize.put("linkmansize", 0+"");
		}
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("infoMap", infoMap);
		//手机认证，芝麻信息
		//Map<String, String> phoneAndAplipayMap =memberService.findMemberAuditChain(mid);
		//request.setAttribute("phoneAndAplipayMap", phoneAndAplipayMap);
		//认证信息
		Map<String, String> auditMap1 =memberService.findMemberAuditChain(mid);
		request.setAttribute("auditMap", auditMap1);

		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("reviewMap", reviewMap);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		request.setAttribute("areaMap", areaMap);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		request.setAttribute("cardMap", cardMap);
		
		//续期记录
		List<Map<String,String>> renewalList=new ArrayList<Map<String,String>>();
		renewalList=borrowAdminService.queryRenewalRecordByBid(Convert.strToInt(auditMap.get("borrow_id"), 0));
		request.setAttribute("renewalList", renewalList);
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("apartList", apartList);
		//逾期记录
		Map<String, String> overdueMap =borrowService.findAppayOverdueLog(Convert.strToInt(borrowMap.get("id"), 0));
		request.setAttribute("overdueMap", overdueMap);
		
		
		return view("overdue/pressedAlreadyepay-detail");
	}
	
}
