package com.spark.p2p.controller.admin;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spark.p2p.service.IphoneAuthService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.service.admin.AdminUserService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.CardIdCheck;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DataException;


/**
 * 平台数据统计
 * 
 * @author yanqizheng
 *
 */
@Controller
@RequestMapping("/admin")
public class StatisticsController extends BaseAdminController {
	@Autowired
	private StatisticsService ss;
	@Autowired
	private BorrowAdminService borrowService;
	@Autowired
	private BorrowService borrowsService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private RepayAdminService repayAdminService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private IphoneAuthService iphoneAuthService;
	
	
	// 平台数据报表
	@RequestMapping(value = "statistics")
	public String statistics() {
		return view("statistics/index");
	}
	
	@RequestMapping("statistics/list")
	public DataTable statisticsList()   {
		DataTable dataTable = dataTable((params) -> ss.queryAllStatistics(params, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession()));
		return dataTable;
	}
	
	/**
	 * 会员统计
	 * @return
	 */
	@RequestMapping("statistics/member")
	public String memberChart(){
		return view("statistics/member");
	}
	
	@RequestMapping("statistics/member/data")
	public @ResponseBody MessageResult loadMemberChartData() throws Exception{
		String dateEnd = request("dateEnd");
		String dateBegin = request("dateBegin");
		List<Map<String,String>> list = ss.queryStatisticsByDate("member_register,member_total,date", dateBegin, dateEnd);
		return success(list);
	}
	
	/**
	 * 投资统计图
	 * @return
	 */
	@RequestMapping("statistics/invest")
	public String investChart(){
		return view("statistics/invest");
	}
	
	@RequestMapping("statistics/invest/data")
	public @ResponseBody MessageResult loadInvestChartData() throws Exception{
		String dateEnd = request("dateEnd");
		String dateBegin = request("dateBegin");
		List<Map<String,String>> list = ss.queryStatisticsByDate("invest_amount,investor_count,invest_count,total_invest_amount,date", dateBegin, dateEnd);
		return success(list);
	}
	
	@RequestMapping("statistics/finance")
	public String financeChart(){
		return view("statistics/finance");
	}
	
	/**
	 * 充值提现数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("statistics/finance/data")
	public @ResponseBody MessageResult loadFinanceChartData() throws Exception{
		String dateEnd = request("dateEnd");
		String dateBegin = request("dateBegin");
		List<Map<String,String>> list = ss.queryStatisticsByDate("recharge_amount,withdraw_amount,total_recharge_amount,total_withdraw_amount,date", dateBegin, dateEnd);
		return success(list);
	}
	
	
	@RequestMapping("statistics/loan")
	public String loanChart(){
		return view("statistics/loan");
	}
	
	/**
	 * 借款数据统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("statistics/loan/data")
	public @ResponseBody MessageResult loadLoanChartData() throws Exception{
		String dateEnd = request("dateEnd");
		String dateBegin = request("dateBegin");
		List<Map<String,String>> list = ss.queryStatisticsByDate("borrow_amount,total_borrow_amount,borrow_count,total_borrow_count,total_duein_amount,date", dateBegin, dateEnd);
		return success(list);
	}
	
	
	
	//////////后台数据的统计和展示
	
	/**
	 * 每日统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/daily")
	public String dailyIndex() throws Exception {
		return view("statistics/daily-index");
	}
	
	
	/**
	 * 每日统计
	 * @return
	 */
	@RequestMapping(value = "daily/list")
	public DataTable dailyList() {
		return dataTable((params) -> ss.queryDailyList(params));
	}
	
	
	/**
	 * 申请统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/apply")
	public String applyIndex() throws Exception {
		return view("statistics/apply-index");
	}
	
	
	/**
	 * 申请统计
	 * @return
	 */
	@RequestMapping(value = "apply/list")
	public DataTable applyList(HttpServletRequest request) {
		return dataTable((params) -> ss.queryBorrowApplyList(params));
	}
	
	
	/**
	 * 所有申请借款的详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrowAllStatueDetail/{bid}")
	public String borrowAllStatueDetail(HttpServletRequest request,@PathVariable int bid) throws Exception {
		
		//借款表
		Map<String, String> borrowMap = borrowService.findBorrowById(bid);
		long mid=Convert.strToLong(borrowMap.get("member_id"), 0);
		Member member =memberService.findMember(mid);
		Map<String, String> areaMap=CardIdCheck.getCardInfo(member.getIdentNo());
		//个人信息认证
		Map<String, String> infoMap =memberService.findMemberInfo(mid);
		//获取苹果认证信息
		long iphoneId=Convert.strToLong(borrowMap.get("iphoneId"),0);
		Map<String,String> iphoneMap=iphoneAuthService.findInfoById(iphoneId);
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
		
		//手机认证，芝麻信息
		Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
		//身份认证
		Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);
		
		//审核意见
		Map<String, String> reviewMap =memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
		if(reviewMap!=null){
			Map<String, String> fadmin=adminUserService.findAdminById(Convert.strToInt(reviewMap.get("admin_id"), 0));
			Map<String, String> sadmin=adminUserService.findAdminById(Convert.strToInt(reviewMap.get("second_admin_id"), 0));
			request.setAttribute("fadmin", fadmin);
			request.setAttribute("sadmin", sadmin);
		}else{
			request.setAttribute("fadmin", null);
			request.setAttribute("sadmin", null);
		}
		/*Map<String, String> bankMap=this.memberService.findBankCard(mid);*/
		//续期记录
		List<Map<String,String>> renewalList=borrowService.queryRenewalRecordByBid(bid);
		if(renewalList.size()==0){
			renewalList=null;
		}
		//还款记录
		List<Map<String,String>> apartList=repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
		//逾期记录
		Map<String, String> overdueMap=borrowService.queryOverdueByBid(bid);
		request.setAttribute("apartList", apartList);
		/*request.setAttribute("bankMap", bankMap);*/
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("member", member);
		request.setAttribute("infoMap", infoMap);
		request.setAttribute("auditMap", auditMap);
		request.setAttribute("cardMap", cardMap);
		request.setAttribute("reviewMap", reviewMap);
		request.setAttribute("renewalList", renewalList);
		request.setAttribute("overdueMap", overdueMap);
		request.setAttribute("areaMap", areaMap);
		request.setAttribute("linkmansize", linkmansize);
		request.setAttribute("iphoneMap",iphoneMap);
		return view("statistics/borrowDetail");
	}
	
	
	/**
	 * 初审失败统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/fristAuditFail")
	public String fristAuditFailIndex() throws Exception {
		return view("statistics/fristAuditFail-index");
	}
	
	
	/**
	 * 初审失败统计
	 * @return
	 */
	@RequestMapping(value = "fristAuditFail/list")
	public DataTable fristAuditFailList() {
		return dataTable((params) -> ss.queryBorrowAuditFailList(params,3));
	}
	
	
	/**
	 * 复审失败统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/secondAuditFail")
	public String secondAuditFailIndex() throws Exception {
		return view("statistics/secondAuditFail-index");
	}
	
	
	/**
	 * 复审失败统计
	 * @return
	 */
	@RequestMapping(value = "secondAuditFail/list")
	public DataTable secondAuditFailList() {
		return dataTable((params) -> ss.queryBorrowAuditFailList(params,7));
	}
	
	/**
	 * 放款统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/loanBorrow")
	public String loanBorrowIndex() throws Exception {
		return view("statistics/loanBorrow-index");
	}
	
	
	/**
	 * 放款统计
	 * @return
	 */
	@RequestMapping(value = "loanBorrow/list")
	public DataTable loanBorrowList() {
		return dataTable((params) -> ss.queryloanBorrowList(params));
	}
	
	
	/**
	 * 续期统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/renewal")
	public String renewalIndex() throws Exception {
		return view("statistics/renewal-index");
	}
	
	
	/**
	 * 续期统计
	 * @return
	 */
	@RequestMapping(value = "renewal/list")
	public DataTable renewalList() {
		return dataTable((params) -> ss.queryRenewalList(params));
	}
	
	/**
	 * 还款统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/repay")
	public String repayIndex() throws Exception {
		return view("statistics/repay-index");
	}
	
	
	/**
	 * 还款统计
	 * @return
	 */
	@RequestMapping(value = "repay/list")
	public DataTable repayList() {
		return dataTable((params) -> ss.queryRepayList(params));
	}
	
	
	/**
	 * 逾期统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/overdue")
	public String overdueIndex() throws Exception {
		return view("statistics/overdue-index");
	}
	
	
	/**
	 * 逾期统计
	 * @return
	 */
	@RequestMapping(value = "overdue/list")
	public DataTable overdueList() {
		return dataTable((params) -> ss.queryOverdueList(params,0));
	}
	
	
	/**
	 * 死账统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "statistics/dieBorrow")
	public String dieBorrowIndex() throws Exception {
		return view("statistics/dieBorrow-index");
	}
	
	
	/**
	 * 死账统计
	 * @return
	 */
	@RequestMapping(value = "dieBorrow/list")
	public DataTable dieBorrowList() {
		return dataTable((params) -> ss.queryOverdueList(params,4));
	}
	
	
}
