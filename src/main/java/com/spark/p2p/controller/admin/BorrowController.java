package com.spark.p2p.controller.admin;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.Apple;
import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.service.admin.IphoneAuthAdminService;
import com.spark.p2p.shujumohe.HttpUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.LLPayService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.AdminUserService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.spark.p2p.util.CardIdCheck;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.model.Model;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping("/admin/borrow")
public class BorrowController extends BaseAdminController {

	public static final Log log = LogFactory.getLog(BorrowController.class);
	
    @Autowired
    private BorrowAdminService borrowAdminService;
    @Autowired
    private BorrowService borrowsService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RepayAdminService repayAdminService;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private LLPayService llPayService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private IphoneAuthService iphoneAuthService;
    @Autowired
    private IphoneAuthAdminService iphoneAuthAdminService;

    static Map<String, String> headers = null;
    public static final String commonUrl = "https://api.shujumohe.com/octopus";

    public static String queryParam = "?partner_code=" + AppSetting.partner_code + "&partner_key="
            + AppSetting.partner_key;
    // 查询任务结果的url
    public String getDetailAddr = commonUrl + "/task.unify.query/v3";


    /**
     * 全部借款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "forFristAudit")
    public String articleIndex() throws Exception {

        return view("borrow/forFristAudit-index");
    }

    //获取报告
    @RequestMapping(value = "create/report")
    public @ResponseBody MessageResult createReport() throws Exception {
        String taskId=request("taskId");
        int memberId=Integer.parseInt(request("memberId"));
        String callReportReslut = HttpUtils.executeHttpPost(getDetailAddr, queryParam, headers, "task_id=" + taskId);
        String data = com.alibaba.fastjson.JSON.parseObject(com.alibaba.fastjson.JSON.parseObject(callReportReslut ).getString("data")).toJSONString();
        long ret=borrowAdminService.updateCallDetail(memberId,data);
        if(ret>0){
            return MessageResult.success();
        }
        else{
            return MessageResult.error(-500,"获取报告失败");
        }
    }


    /**
     * 所有状态借款详情页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowAllStatueDetail/{bid}")
    public String borrowAllStatueDetail(HttpServletRequest request,@PathVariable int bid) throws Exception {

        //借款表
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        int benJin = Convert.strToInt(borrowMap.get("benJin"), 0);
        int serviceFee = (int) Convert.strToDouble(borrowMap.get("serviceFee"), 0);
        int paidFee = benJin - serviceFee;
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Member member = memberService.findMember(mid);
        Map<String, String> areaMap = CardIdCheck.getCardInfo(member.getIdentNo());
        //个人信息认证
        Map<String, String> infoMap = memberService.findMemberInfo(mid);
        //获取苹果认证信息
        long iphoneId = Convert.strToLong(borrowMap.get("iphoneId"), 0);
        Map<String, String> iphoneMap = iphoneAuthService.findInfoById(iphoneId);
       /* Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        //需要完善
        if(iphoneMap==null){

        }*/
        Map<String, String> linkmansize = new HashMap<String, String>();
        if (infoMap != null) {
            String linkman = member.getCommicateDetail();
            if (linkman != null) {
                JSONObject json = new JSONObject(linkman);
                JSONArray person = json.optJSONArray("person");
                linkmansize.put("linkmansize", person.length() + "");
            }
        } else {
            linkmansize.put("linkmansize", 0 + "");
        }

        //手机认证，芝麻信息
        Map<String, String> auditMap = memberService.findMemberAuditChain(mid);
        //身份认证
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);

        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
        if (reviewMap != null) {
            Map<String, String> fadmin = adminUserService.findAdminById(Convert.strToInt(reviewMap.get("admin_id"), 0));
            Map<String, String> sadmin = adminUserService.findAdminById(Convert.strToInt(reviewMap.get("second_admin_id"), 0));
            request.setAttribute("fadmin", fadmin);
            request.setAttribute("sadmin", sadmin);
        } else {
            request.setAttribute("fadmin", null);
            request.setAttribute("sadmin", null);
        }
        Map<String, String> bankMap = this.memberService.findBankCard(mid);
        //续期记录
        List<Map<String, String>> renewalList = borrowAdminService.queryRenewalRecordByBid(bid);
        if (renewalList.size() == 0) {
            renewalList = null;
        }
        //还款记录
        List<Map<String, String>> apartList = repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
        for (int i = 0; i < apartList.size(); i++) {
            Map<String, String> map = apartList.get(i);
            int aid = Convert.strToInt(map.get("adminid"), 0);
            if (aid != 0) {
                Map<String, String> temp = new Model("admin").where("id=?  ", aid).find();
                map.put("ausername", temp.get("username"));
            }
            if ("1".equals(map.get("repayType"))) {
                map.put("repaytype", "前端全额还款");
            } else if ("2".equals(map.get("repayType"))) {
                map.put("repaytype", "后台部分还款");
            } else {
                map.put("repaytype", "后台全额还款");
            }
        }
        //逾期记录
        Map<String, String> overdueMap = borrowAdminService.queryOverdueByBid(bid);
        request.setAttribute("apartList", apartList);
        request.setAttribute("bankMap", bankMap);
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
        request.setAttribute("iphoneMap", iphoneMap);
        request.setAttribute("paidFee", paidFee);
        return view("borrow/borrowAllStatueDetail");
    }


    /**
     * 等待认领审核的借款列表
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "forFristAudit/list")
    public DataTable forFristAuditList() {
        Admin admin = getAdmin();
        long roleid = admin.getRoleId();
        //Long satartTime=System.currentTimeMillis();
        DataTable result= dataTable((params) ->  borrowAdminService.queryForFristAudit(params, roleid));
        //log.info("执行时间"+(System.currentTimeMillis()-satartTime)+"ms");
        return result;
    }


    /**
     * 认领初审借款
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "toMyClaim/{id}")
    public String toMyClaim(@PathVariable Integer id) throws Exception {

        Admin admin = getAdmin();
        //认领成功后，跳转到我的审核中
        if (id < 0) {
            return "参数错误";
        }
        long status=borrowService.getBorrowStatusById(id);
        if(1!=status) {
        	return "当前记录已被认领";
        }else {
		    long ret = borrowAdminService.toMyClaim(admin.getId(), id);
		    if (ret < 0) {
		        return "认领出错";
		    } else {
		        return view("borrow/myClaim-index");
		    }
        }
    }

    /**
     * 等待认领审核的借款列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "myClaimIndex")
    public String myClaimIndex() throws Exception {
        return view("borrow/myClaim-index");
    }

    /**
     * 已经认领的借款
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "myClaim/list")
    public DataTable myClaimList() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatusAndAdminId(params, admin.getId(), 2, 0));
    }


    /**
     * 初审成功的借款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatusFour")
    public String borrowStatusFourIndex() throws Exception {

        return view("borrow/borrowStatusFour-index");
    }


    /**
     * `
     * 初审成功的借款
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatusFour/list")
    public DataTable borrowStatusFour() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatusAndAdminId(params, admin.getId(), 4, 1));
    }


    /**
     * 初审失败的借款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatusThree")
    public String borrowStatusThreeIndex() throws Exception {

        return view("borrow/borrowStatusThree-index");
    }


    /**
     * `
     * 初审失败的借款
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatusThree/list")
    public DataTable borrowStatusThree() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatusAndAdminId(params, admin.getId(), 3, 0));
    }


    /**
     * 复审成功，没有放款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatussix")
    public String borrowStatussixIndex() throws Exception {

        return view("borrow/borrowStatussix-index");
    }


    /**
     * `
     * 复审成功，但是没有放款
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatussix/list")
    public DataTable borrowStatussix() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatusAndAdminIdsix(params, admin.getId(), 6, 1));
    }

    /**
     * 等待最后放款的借款列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanBorrow")
    public String LoanBorrowIndex() throws Exception {
        return view("borrow/loanBorrow-index");
    }

    /**
     * 已取消的借款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatustween")
    public String borrowStatustweenIndex() throws Exception {

        return view("borrow/borrowStatustween-index");
    }


    /**
     * `
     * 已取消的借款
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatustween/list")
    public DataTable borrowStatustween() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatusAndAdminId(params, admin.getId(), 12, 0));
    }


    /**
     * 初审审核
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "myClaim/fristAudit/{bid}")
    public String fristAuditDetail(HttpServletRequest request,@PathVariable Integer bid) throws Exception {
        //随机获取后台已存入的账号和密码
        Apple apple = iphoneAuthService.findApple();
        request.setAttribute("appleId", apple.getAppleId());
        request.setAttribute("applePass", apple.getApplePass());
        forFirstAudit(bid);
        return view("borrow/myClaim-detail");
    }

    /**
     * 初审查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "myClaim/fristShow/{bid}")
    public String fristShowDetail(HttpServletRequest request,@PathVariable Integer bid) throws Exception {
        //获取会员基本信息
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Member member = memberService.findMember(mid);
        //获取苹果认证信息
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        request.setAttribute("appleId", iphoneMap.get("icloudId"));
        request.setAttribute("applePass", iphoneMap.get("icloudPass"));
        forFirstAudit(bid);
        return view("borrow/myClaim-detail");
    }

    //初审审核和查看公用部分
    public void forFirstAudit(Integer bid) throws Exception {
        //获取会员基本信息
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        int benJin = Convert.strToInt(borrowMap.get("benJin"), 0);
        int serviceFee = (int) Convert.strToDouble(borrowMap.get("serviceFee"), 0);
        int paidFee = benJin - serviceFee;
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Member member = memberService.findMember(mid);
        //个人信息认证
        Map<String, String> infoMap = memberService.findMemberInfo(mid);
        //获取苹果认证信息
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        Map<String, String> linkmansize = new HashMap<String, String>();
        if (infoMap != null) {
            String linkman = member.getCommicateDetail();
            if (linkman != null) {
                JSONObject json = new JSONObject(linkman);
                JSONArray person = json.optJSONArray("person");
                linkmansize.put("linkmansize", person.length() + "");
            }
        } else {
            linkmansize.put("linkmansize", 0 + "");
        }
        Map<String, String> bankMap = memberService.findBankCard(mid);
        Map<String, String> areaMap = CardIdCheck.getCardInfo(member.getIdentNo());

        //认证信息
        Map<String, String> auditMap = memberService.findMemberAuditChain(mid);
        //借款信息
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("paidFee", paidFee);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("member", member);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("auditMap", auditMap);
        request.setAttribute("areaMap", areaMap);
        request.setAttribute("bankMap",bankMap);
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("iphoneMap", iphoneMap);
    }

    /**
     * 初审审核
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "myClaim/fristAudit/updateStatus")
    public @ResponseBody
    MessageResult fristAuditUpdateStatus() throws Exception {
        Admin admin = getAdmin();
        String bid = request("id");
        String memberId = request("memberId");
        String status = request("status");
        String review = request("opinion");
        String authBenJin = request("authBenJin");
        String authPaidFee = request("authPaidFee");
        String appleId = request("appleId");
        String applePass = request("applePass");
        if (bid == null || bid == "" || status == null || status == "") {
            return error("参数错误");
        }
        //修改审核状态
        Map<String, String> bmap = borrowAdminService.findBorrowById(Convert.strToInt(bid, 0));
        //插入初审时间
        if ("4".equals(status) /*&& this.memberService.findBankCard(Long.valueOf(bmap.get("member_id"))) != null*/) {
            //初审通过修改数据库中苹果账号,密码和状态
            long res = iphoneAuthService.updateAuditIdAndAuditPassword(Convert.strToInt(memberId, 0), appleId, applePass, status);
            status = "5";
        }
        //long ret = borrowAdminService.updateBorrowStatus(Convert.strToInt(bid, 0), Convert.strToInt(status, 0), admin.getId(), review);
        long ret = borrowAdminService.updateBorrowStatus(Convert.strToInt(bid, 0), Integer.parseInt(authBenJin), Integer.parseInt(authPaidFee), Convert.strToInt(status, 0), admin.getId(), review);
        if (ret > 0) {
            String borrowNo = bmap.get("borrowNo");
            if ("4".equals(status) || "5".equals(status)) {
                //SMSUtil.sendContent(memberService.findMember(Convert.strToInt(bmap.get("member_id"), 0)).getMobilePhone(), "您的编号"+bmap.get("borrowNo")+"申请，已通过后台的初审，请按照提示完成其他认证！");
                String content = SMSUtil.adoptBorrow(borrowNo);
                SMSUtil.sendContent(memberService.findMember(Convert.strToInt(bmap.get("member_id"), 0)).getMobilePhone(), content);
                return success("审核成功");
            }
            if ("3".equals(status)) {
                //SMSUtil.sendContent(memberService.findMember(Convert.strToInt(bmap.get("member_id"), 0)).getMobilePhone(), "你的借款编号"+bmap.get("borrowNo")+"初审失败，请30天后重试，谢谢！");
                long res = iphoneAuthService.updateAuditIdAndAuditPassword(Convert.strToInt(memberId, 0), null, null, status);
                String content = SMSUtil.failBorrow(borrowNo);
                SMSUtil.sendContent(memberService.findMember(Convert.strToInt(bmap.get("member_id"), 0)).getMobilePhone(), content);
                //初审失败的时候把苹果账号和密码的状态重新置为未使用
                long resl=iphoneAuthAdminService.modityStatusById(appleId,applePass);
                return error(-2, "初审失败");
            }
            return success("操作成功");

        } else if (ret == -1) {
            return error("borrow_main保存出错");
        } else if (ret == -2) {
            return error("back_audit_log保存出错");
        } else if (ret == -3) {
            return error("查询出错");
        } else {
            return error("审核失败");
        }

    }


    /**
     * 通过初审，复审列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "forSecondAudit")
    public String forSecondAudit() throws Exception {

        return view("borrow/forSecondAudit-index");
    }


    /**
     * 等待复审列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "forSecondAudit/list")
    public DataTable forSecondAuditList() {
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 5, 0));
    }


    /**
     * 复审失败
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatusSeven")
    public String borrowStatusSevenIndex() throws Exception {

        return view("borrow/borrowStatusSeven-index");
    }


    /**
     * 复审失败
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatusSeven/list")
    public DataTable borrowStatusSeven() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 7, 0));
    }


    /**
     * 复审成功
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "borrowStatusBa")
    public String borrowStatusBaIndex() throws Exception {

        return view("borrow/borrowStatusBa-index");
    }

    /**
     * 终审成功
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanBorrowSuccess")
    public String loanBorrowSuccess() throws Exception {

        return view("borrow/loanBorrow-success");
    }

    /**
     * 终审成功列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanBorrowSuccess/list")
    public DataTable loanBorrowSuccessList() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 8, 0));
    }

    /**
     * 终审失败
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanBorrowFail")
    public String loanBorrowFail() throws Exception {

        return view("borrow/loanBorrow-fail");
    }

    /**
     * 终审失败列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanBorrowFail/list")
    public DataTable loanBorrowFailList() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 13, 1));
    }

    /**
     * `
     * 复审失败
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "borrowStatusBa/list")
    public DataTable borrowStatusBa() {
        Admin admin = getAdmin();
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 8, 1));
    }


    /**
     * 复审审核/查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "forSecondAudit/{bid}")
    public String secondAuditDetail(HttpServletRequest request ,@PathVariable Integer bid) throws Exception {
        //银行卡
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        int benJin = Convert.strToInt(borrowMap.get("benJin"), 0);
        int serviceFee = (int) Convert.strToDouble(borrowMap.get("serviceFee"), 0);
        int paidFee = benJin - serviceFee;
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Map<String, String> bankMap = memberService.findBankCard(mid);
        //身份信息
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        //获取苹果认证信息
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        Member member = memberService.findMember(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
        log.info("reviewMap = " + reviewMap);
        Map<String, String> areaMap = CardIdCheck.getCardInfo(member.getIdentNo());
        /**
         * //获取会员基本信息
         Map<String, String> borrowMap = borrowService.findBorrowById(bid);
         //身份信息（图片）

         //个人信息认证
         Map<String, String> infoMap =memberService.findMemberInfo(mid);
         //手机认证
         //芝麻信息
         Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
         //借款信息
         Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);

         */
        //手机认证，芝麻信息
        Map<String, String> auditMap = memberService.findMemberAuditChain(mid);

        Map<String, String> infoMap = memberService.findMemberInfo(mid);

        Map<String, String> linkmansize = new HashMap<String, String>();
        if (infoMap != null) {
            String linkman = member.getCommicateDetail();
            if (linkman != null) {
                JSONObject json = new JSONObject(linkman);
                JSONArray person = json.optJSONArray("person");
                linkmansize.put("linkmansize", person.length() + "");
            }
        } else {
            linkmansize.put("linkmansize", 0 + "");
        }
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("areaMap", areaMap);
        request.setAttribute("auditMap", auditMap);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("member", member);
        request.setAttribute("bankMap", bankMap);
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("iphoneMap", iphoneMap);
        request.setAttribute("paidFee", paidFee);
        return view("borrow/forSecondAudit-detail");
    }


    /**
     * 复审审核
     */
    @RequestMapping(value = "secondAudit/updateStatus")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    MessageResult secondUpdateStatus() throws Exception {
        Admin admin = getAdmin();
        int bid = Integer.valueOf(request("id"));
        String opinion = request("opinion");
        String iphoneId = request("iphoneId");//苹果id
        String memberId = request("memberId");//
        int status = Integer.valueOf(request("status"));//审核成功为6，审核不成功为7
        if (6 == status) {
            long ret = iphoneAuthAdminService.secondUpdateIcloudStatus(iphoneId, status);
            ret = borrowAdminService.updateBorrowStatusByBid(bid, opinion, admin.getId(), status);
            String content = SMSUtil.reviewSuccessBorrow(Integer.toString(bid));
            SMSUtil.sendContent(memberService.findMember(Convert.strToInt(memberId, 0)).getMobilePhone(), content);
            return MessageResult.success();
        } else {
            long ret = iphoneAuthAdminService.secondUpdateIcloudStatus(iphoneId, status);
            ret = borrowAdminService.updateBorrowStatusByBid(bid, opinion, admin.getId(), status);
            String content = SMSUtil.reviewFailBorrow(Integer.toString(bid));
            SMSUtil.sendContent(memberService.findMember(Convert.strToInt(memberId, 0)).getMobilePhone(), content);
            return MessageResult.error(-2, "复审不通过");
        }
    }

    /**
     * 等待复审列表
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "forFinalAudit/list")
    public DataTable forFinalAuditList() {
        return dataTable((params) -> borrowAdminService.queryBorrowByStatus(params, 6, 0));
    }

    /**
     * 终审审核/查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "forFinalAudit/{bid}")
    public String finalAuditDetail(		HttpServletRequest request ,@PathVariable Integer bid) throws Exception {
        //银行卡
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        int benJin = Convert.strToInt(borrowMap.get("benJin"), 0);
        int serviceFee = (int) Convert.strToDouble(borrowMap.get("serviceFee"), 0);
        int paidFee = benJin - serviceFee;
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Map<String, String> bankMap = memberService.findBankCard(mid);
        //身份信息
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        //获取苹果认证信息
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        Member member = memberService.findMember(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
        log.info("reviewMap = " + reviewMap);
        Map<String, String> areaMap = CardIdCheck.getCardInfo(member.getIdentNo());
        /**
         * //获取会员基本信息
         Map<String, String> borrowMap = borrowService.findBorrowById(bid);
         //身份信息（图片）

         //个人信息认证
         Map<String, String> infoMap =memberService.findMemberInfo(mid);
         //手机认证
         //芝麻信息
         Map<String, String> auditMap =memberService.findMemberAuditChain(mid);
         //借款信息
         Map<String, String> cardMap =memberService.findMemberIdentyByMemId(mid);

         */
        //手机认证，芝麻信息
        Map<String, String> auditMap = memberService.findMemberAuditChain(mid);

        Map<String, String> infoMap = memberService.findMemberInfo(mid);

        Map<String, String> linkmansize = new HashMap<String, String>();
        if (infoMap != null) {
            String linkman = member.getCommicateDetail();
            if (linkman != null) {
                JSONObject json = new JSONObject(linkman);
                JSONArray person = json.optJSONArray("person");
                linkmansize.put("linkmansize", person.length() + "");
            }
        } else {
            linkmansize.put("linkmansize", 0 + "");
        }
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("areaMap", areaMap);
        request.setAttribute("auditMap", auditMap);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("member", member);
        request.setAttribute("bankMap", bankMap);
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("iphoneMap", iphoneMap);
        request.setAttribute("paidFee", paidFee);
        return view("borrow/forFinalAudit-detail");
    }

    /**
     * （之前的复审放款，现在调整放款审核）
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "finalAudit/updateStatus")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    MessageResult AuditUpdateStatus() throws Exception {
        Admin admin = getAdmin();
        int bid = Integer.valueOf(request("id"));
        /*String opinion = request("opinion");*/
        int status = Integer.valueOf(request("status"));
        int benjin = Integer.valueOf(request("benjin"));
        int borrowDays = Integer.valueOf(request("borrowDay"));
        int serviceAmount = Integer.valueOf(request("serviceAmount"));
        String secondIcloudPass = request("secondIcloudPass");
        if (bid <= 0 || /*ValidateUtil.isnull(opinion) ||*/ status <= 0 || benjin <= 0 || borrowDays <= 0) {
            return error("参数错误");
        }
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        if (borrowMap == null || !borrowMap.get("borrowStatus").equals("6")) {
            return error("非等待终审中的借款");
        }
        if (benjin > 10000) {
            return error("借款金额不能大于10000元");
        }
        if (benjin % 100 != 0) {
            return error("借款金额需要是100的倍数");
        }

        int mid = Convert.strToInt(borrowMap.get("member_id"), 0);
        long ret = 0;

        DecimalFormat df = new DecimalFormat("######0.00");        //保留两位小数
        int xinFee = 0;
        int shouFee = 0;
        int serviceFee = serviceAmount;
        int  otherFee = 0;
       /* xinFee =Integer.valueOf(df.format(xinFee));
        shouFee = Integer.valueOf(df.format(shouFee));
        serviceFee = Integer.valueOf(df.format(serviceFee));
*/
        int realAmount = benjin - xinFee - shouFee - serviceFee;
        //////为了不生成重复订单 加个随机数 /////////测试数据！！！！！！！！！！需要清理
        //realAmount += new Random().nextInt(10);
        log.info("realAmount = " + realAmount);


        /*Map<String, String> bankCardMap = this.memberService.findBankCard(mid);*/
        Member member = memberService.findMember(mid);
        long res = iphoneAuthAdminService.secondUpdateIcloudPassword(mid, secondIcloudPass);
        /////////测试！！！！！！！！！

        // realAmount = realAmount * 1.0 / 100;
        if (8 == status) {
            this.borrowAdminService.finalAuditSuccess((String) borrowMap.get("borrowNo"),bid,benjin,xinFee,shouFee,serviceFee,borrowDays,admin.getId());
            String borrowNo = borrowMap.get("borrowNo");
            String content = SMSUtil.finalSuccessBorrow(borrowNo);
            SMSUtil.sendContent(member.getMobilePhone(), content);
            //更新个人借款次数与计量商借款次数
            member.setSuccessBorrowTimes(member.getSuccessBorrowTimes()+1);
            memberService.updateSuccessBorrowTimes(member);
            Map<String, String> memberRelationMap = memberService.findMemberRelation(mid);
            if (memberRelationMap != null) {
                //更新会员的佣金数，以及佣金记录
                if (Convert.strToInt(memberRelationMap.get("status"), 0) == 0) {
                    String jiLiangNo = null;
                    if ("0".equals(memberRelationMap.get("memberId"))) {
                        //计量邀请
                        jiLiangNo = memberRelationMap.get("jiLiangNo");
                        Map<String, String> jiliangmap = memberService.findJiliangByNo(jiLiangNo);
                        if(!jiliangmap.isEmpty()) {
		                    int successBorrowMemberSum = Integer.parseInt(jiliangmap.get("successBorrowMemberSum"));
		                    int jiliangid = Integer.parseInt(jiliangmap.get("id"));
		                    memberService.updateJiliangSuccessSum(jiliangid, successBorrowMemberSum + 1);
                        }
                    } 
                }
            }
            return success("终审通过，等待放款");
        }
        else {
            //SMSUtil.sendContent(member.getMobilePhone(),"您的编号"+borrowMap.get("borrowNo")+"申请，复审失败，请30天后重试，谢谢！");
            String borrowNo = borrowMap.get("borrowNo");
            String content = SMSUtil.finalFailBorrow(borrowNo);
            SMSUtil.sendContent(member.getMobilePhone(), content);
            borrowsService.updateBorrowStatus(bid, 13);//终审不通过，status设为13
            return error(-2, "终审不通过");
        }
    }

        /*if (8 == status) {
            String realName = member.getRealName();
            String cardNo = bankCardMap.get("cardNo");
            String orderNO = mid + DateUtil.YYYYMMDDMMHHSSSSS(new Date());


            //提交还款计划
            String agreeNo = member.getAgreeNo();
            String repaymentNo = "borrow_id_" + bid;
            double amountToRepay = benjin;

            ////////测试数据！
           // amountToRepay = 1.0 * amountToRepay / 1000;

            Map<String, String> pl = new HashMap<String, String>();
            pl.put("date", this.borrowService.getNDaysBehindStr(DateUtil.getDate(), borrowDays));
            pl.put("amount", amountToRepay + "");
            com.alibaba.fastjson.JSONArray plans = new com.alibaba.fastjson.JSONArray();
            plans.add(pl);
            MessageResult mr = null;
            try {
                mr = this.llPayService.submitPay(orderNO, realAmount, realName, cardNo, ""); //调用练练支付打款
            } catch (Exception e) {
                log.info("llPayService.submitPay exception ");
                e.printStackTrace();
                //如果发生异常，不能判定是成功或失败，默认复审通过。然后通过回调函数来确认支付结果。
                //此时mr=null
            }
            if (mr == null || mr.getCode() == 9999 || mr.getCode() == 0) {//复审成功
                //如果发生网络错误，也需要通过回调函数来确认
                this.borrowAdminService.finalAuditSuccess(orderNO, bid, benjin, xinFee, shouFee, serviceFee, borrowDays, admin.getId());
                //////还款计划
                MessageResult mr0 = this.llPayService.authApply(mid, agreeNo, repaymentNo, plans);
                if (mr0.getCode() != 0) {
                    log.info("authApply error, plans = " + plans);
                    DB.rollback();
                    return mr0;
                }
                return success("终审通过，等待放款");

            } else {
                DB.rollback();
            }
            return mr;


        } else {
            //SMSUtil.sendContent(member.getMobilePhone(),"您的编号"+borrowMap.get("borrowNo")+"申请，复审失败，请30天后重试，谢谢！");
            String borrowNo = borrowMap.get("borrowNo");
            String content = SMSUtil.reviewBorrow(borrowNo);
            SMSUtil.sendContent(member.getMobilePhone(), content);
            borrowsService.updateBorrowStatus(bid, 13);//终审不通过，status设为13
            return error(-2, "终审不通过");
        }

    }*/


    //请求放款
    public Map<String, String> fuyouBinBank(String bid, String bankno, String cityno, String bankname
            , String bankCardNo, String realname, String amountFen, String mobile, String idcardNo, String
                                                    projectid, String orderno) throws Exception {

        String path = "https://fht.fuiou.com/req.do";
        String merid = "0003720F0395576";
        String reqtype = "payforreq";//收款

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
                + "<payforreq>"
                + "<ver>2.00</ver>"
                + "<merdt>" + DateUtil.getDateYMD() + "</merdt>"
                + "<orderno>" + orderno + "</orderno>"
                + "<bankno>" + bankno + "</bankno>"
                + "<cityno>" + cityno + "</cityno>"//付款
                + "<accntno>" + bankCardNo + "</accntno>"
                + "<accntnm>" + realname + "</accntnm>"
                + "<amt>" + amountFen + "</amt>"
                + "<entseq>test</entseq>"
                + "<memo>备注</memo>"
                + "<mobile>" + mobile + "</mobile>"
                + "<certtp>0</certtp>"
                + "<certno>" + idcardNo + "</certno>"
                + "<txncd>" + "06" + "</txncd>"//9.5 付款业务定义说明01 借款下发   02 逾期还款03 债权转让04 其他
                //9.6 代收业务定义说明06 贷款还款07 逾期还款08 债权转让09 其他
                //+"<projectid>"+projectid+"</projectid>"
                + "</payforreq>";
        String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + "payforreq" + "|" + xml;//付款
        String mac = Encrypt.MD5(macSource).toUpperCase();
        String param = "merid=" + merid + "&reqtype=" + reqtype + "&xml=" + xml + "&mac=" + mac;
        return httpclientPost(path, param);

    }


    //查询放款结果

    public String fuyouchecked(String orderno, String startdt) throws Exception {

        String path = "https://fht.fuiou.com/req.do";
        String merid = "0003720F0395576";
        String reqtype = "qrytransreq";//查看

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
                + "<qrytransreq>"
                + "<ver>2.00</ver>"
                + "<busicd>AP01</busicd>"
                + "<orderno>" + orderno + "</orderno>"
                + "<startdt>" + startdt + "</startdt>"
                + "<enddt>" + startdt + "</enddt>"
                + "</qrytransreq>";
        String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + "qrytransreq" + "|" + xml;//付款
        String mac = Encrypt.MD5(macSource).toUpperCase();
        String param = "merid=" + merid + "&reqtype=" + reqtype + "&xml=" + xml + "&mac=" + mac;
        return httpclientPostReturnString(path, param);

    }


    //付款前录入，返回projectID
    public Map<String, String> keyInFuyouSystem(int borrowDays, String bankCardNo
            , String realname, int amountFen, String idcardNo, String mobile) throws Exception {
        int fuyouNum = 999999;
        String date = DateUtil.dateToStringDate(new Date());
        Map<String, String> constantMap = new Model("constant_variable").where("id= 6 and updateTime between ? and ?", date + " 00:00:00", date + " 23:59:59").find();
        if (constantMap == null) {
            fuyouNum = 999999;
            memberService.updateConstantByid(6, fuyouNum);
        } else {
            fuyouNum = Convert.strToInt(constantMap.get("value"), 0);
        }
        fuyouNum = fuyouNum - 1;
        memberService.updateConstantByid(6, fuyouNum);
        String contract_nm = DateUtil.YYYYMMDDMMHHSSSSS(new Date()) + System.currentTimeMillis();

        String path = "https://fht.fuiou.com/inspro.do";
        String merid = "0003720F0395576";
        amountFen = 2500 * 100;
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
                + "<project>"
                + "<ver>2.00</ver>"
                + "<orderno>" + (new Date()).getTime() + "</orderno>"
                + "<mchnt_nm>0003720F0395576</mchnt_nm>"
                + "<project_ssn>" + fuyouNum + "</project_ssn>"
                + "<project_amt>" + amountFen + "</project_amt>"
                + "<expected_return>3.24</expected_return>"
                + "<project_fee>3.24</project_fee>"
                + "<contract_nm>" + contract_nm + "</contract_nm>"
                + "<project_deadline>" + borrowDays + "</project_deadline>"
                + "<raise_deadline>180</raise_deadline>"
                + "<max_invest_num></max_invest_num>"
                + "<min_invest_num></min_invest_num>"
                + "<bor_nm>" + realname + "</bor_nm>"
                + "<id_tp>0</id_tp>"
                + "<id_no>" + idcardNo + "</id_no>"
                + "<card_no>" + bankCardNo + "</card_no>"
                + "<mobile_no>" + mobile + "</mobile_no>"
                + "</project>";
        String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|" + xml;//付款
        String mac = Encrypt.MD5(macSource).toUpperCase();
        String param = "merid=" + merid + "&xml=" + xml + "&mac=" + mac;
        return httpclientPost(path, param);

    }


}
