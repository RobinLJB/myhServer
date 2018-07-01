package com.spark.p2p.controller.admin;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.Activity;
import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.PersonProfile;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.CouponService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.FinanceService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;

@Controller
@RequestMapping("/admin/extension")
public class ActivityController extends BaseAdminController {
	
	public static final Log log = LogFactory.getLog(ActivityController.class);
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private BorrowAdminService borrowService;
	
	
	/**
	 * 佣金用户
	 * @return
	 */
	@RequestMapping(value="person")
	public String activityIndex(){
		return view("activity/person-index");
	}
	
	
	@RequestMapping("person/list")
	public DataTable actList(){
		return dataTable((params)->couponService.queryActivity(params));
	}
	
	
	/**
	 * 佣金用户提现
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("member/detail/{mid}")
	public String memberRecord(HttpServletRequest request,@PathVariable long mid) throws Exception {
		Member member=memberService.findMember(mid);
//		List<Map<String,String>> list=memberService.queryBankCardList(mid);
//		request.setAttribute("list", list);
		/*Map<String,String> bank = this.memberService.findBankCard(mid);*/
		/*request.setAttribute("bank", bank);*/
		request.setAttribute("member", member);
		return view("activity/withdraw-detail");
	}
	
	
	
	
	/**
	 * 用户佣金提现列表
	 * @return
	 */
	@RequestMapping("member/detail/list/{mid}")
	public DataTable memberFinanceDetailList(@PathVariable long mid) {
		return dataTable((params) -> financeService.queryBorrowByStatusAndAdminId(params,mid));
	}
	
	
	/**
	 * 查看计量推广邀请的用户的借款详情
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("group/subMember/{mid}")
	public String jiLiangSubMemberBorrowDetail(HttpServletRequest request,@PathVariable long mid) throws Exception {
		List<Map<String,String>> borrowList=borrowService.queryBorrowByMid(mid);
		request.setAttribute("borrowList", borrowList);
		request.setAttribute("mid", mid);
		return view("activity/borrow-index");
	}
	
	
	/**
	 * 查看计量推广邀请的用户的借款详情
	 * @return
	 */
	@RequestMapping("group/subMember/list/{mid}")
	public DataTable jiLiangSubMemberBorrowDetailList(@PathVariable long mid) {
		return dataTable((params) -> financeService.jiLiangSubMemberBorrowDetailList(params,mid));
	}
	
	/**
	 * 用户佣金提现操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "member/withdraw")
	public @ResponseBody MessageResult handleWithdraw() throws Exception {
		double withdrawSum = Convert.strToDouble(request("withdrawSum"), 0);
		int mid = Convert.strToInt(request("mid"), 0);
		Member member=memberService.findMember(mid);
		if(withdrawSum>member.getCommisionSum()){
			log.error("请输入小于总额的金额");
			return error("请输入小于总额的金额");
		}
		String withdrawReview=request("withdrawReview");
		String cardNo=request("cardNo");
		long result = 0l;
		Admin admin=getAdmin();
		result=financeService.insertCommisionWithdrawRecord(withdrawSum,withdrawReview,admin.getId(),mid,cardNo,member.getCommisionSum(),getRemoteIp());
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}
	
	
	/**
	 * 下级用户详情----已申请
	 * @return
	 */
	@RequestMapping(value="subPersonA/{mid}")
	public String subPersonAIndex(HttpServletRequest request,@PathVariable int mid){
		request.setAttribute("mid", mid);
		return view("activity/subPerson-index"); 
	}
	
	
	@RequestMapping("subPersonA/list/{mid}")
	public DataTable subPersonAList(@PathVariable int mid){
		return dataTable((params)->couponService.queryBorrowByStatusAndAdminId(params,mid,1,1));
	}
	
	
	/**
	 * 下级用户详情----已放款
	 * @return
	 */
	@RequestMapping(value="subPersonB/{mid}")
	public String subPersonBIndex(HttpServletRequest request,@PathVariable int mid){
		request.setAttribute("mid", mid);
		return view("activity/submember-eight-index");
	}
	
	
	@RequestMapping("subPersonB/list/{mid}")
	public DataTable subPersonBList(@PathVariable int mid){
		return dataTable((params)->couponService.queryBorrowByStatusAndAdminId(params,mid,8,0));
	}
	
	
	/**
	 * 下级用户详情----已拒绝
	 * @return
	 */
	@RequestMapping(value="subPersonC/{mid}")
	public String subPersonCIndex(HttpServletRequest request,@PathVariable int mid){
		request.setAttribute("mid", mid);
		return view("activity/submember-seren-index");
	}
	
	
	@RequestMapping("subPersonC/list/{mid}")
	public DataTable subPersonCList(@PathVariable int mid){
		return dataTable((params)->couponService.queryBorrowByStatusAndAdminId(params,mid,7,0));
	}
	
	
	
	/**
	 * 计量用户
	 * @return
	 */
	@RequestMapping(value="group")
	public String jiliangIndex(){
		return view("activity/jiliang-index");
	}
	
	
	@RequestMapping("jiliang/list")
	public DataTable jiliangList() throws Exception{
		Admin admin=getAdmin();
		long roleid=admin.getRoleId();
		if(roleid==26){
			Map<String,String> map=couponService.findActivityByAid(admin.getId());
			int jiangliangId=Convert.strToInt(map.get("id"), 0);
			return dataTable((params)->couponService.queryJiliangList(params,roleid,jiangliangId));
		}else{
			return dataTable((params)->couponService.queryJiliangList(params,roleid,0));
		}
		
	}
	
	
	/**
	 * 计量用户----计量详情
	 * @return
	 */
	@RequestMapping(value="jiliangDetail/{keys}")
	public String jiliangDetailIndex(HttpServletRequest request,@PathVariable long keys){
		request.setAttribute("keys", keys);
		return view("activity/jiliang-member-detail");
	}
	
	
	@RequestMapping("jiliangDetail/list/{keys}")
	public DataTable jiliangDetailList(@PathVariable int keys){
		return dataTable((params)->couponService.queryActivityByjiLiang(params,keys));
	}
	
	
	
	
	/**
	 * 生成计量用户
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("create/group/{keys}")
	public String createGroup(HttpServletRequest request,@PathVariable long keys) throws Exception {
		String url=couponService.queryfeeConstant().get("value");
		request.setAttribute("url", url);
		if(keys==-1){
			request.setAttribute("resultMap", null);
		}else{
			Map<String, String> resultMap=couponService.findJiliangDetail(keys);
			request.setAttribute("resultMap", resultMap);
		}
		
		return view("activity/jiliang-detail");
	}
	
	
	/**
	 * 计量删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "group/delete/{id}")
	public @ResponseBody MessageResult articleDelete(@PathVariable Integer id) {
		if (id <= 0) {
			return error("参数错误");
		}
		Long result = 0l;
		try {
			result = couponService.deleteJiliang(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}
	
	/**
	 * 用户佣金提现操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "group/update" , method = RequestMethod.POST)
	public @ResponseBody MessageResult handleGroup(
			@RequestParam(value = "id", required = false, defaultValue = "-1") int id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "key") String key,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "phone") String phone,
			@RequestParam(value = "qq") String qq,
			@RequestParam(value = "review") String review,
			@RequestParam(value = "isShow") String isShow
			) throws Exception {
		if(ValidateUtil.isnull(key)){
			return error("请输入计量标识");
		}
		if(ValidateUtil.isnull(username)){
			return error("请输入登陆用户名");
		}
		if(ValidateUtil.isnull(password)){
			return error("请输入查询密码");
		}
		//检查计量用户的key是否重复
		if(couponService.findJiliangBykey(key)!=null){
			return error("计量的唯一标示已经存在");
		}
		String url=couponService.queryfeeConstant().get("value");
		url=url+"kssdre.html?reruestType=2&requestCode="+key;
		long result = 0L;
		Admin admin=getAdmin();
		result=couponService.insertJiliangExten(id,name,key,username,password,phone,qq,review,Convert.strToInt(isShow, 0),admin.getId(),url,getRemoteIp());
		return result > 0l ? success("操作成功!") : error("删除失败!");
	}
	

}
