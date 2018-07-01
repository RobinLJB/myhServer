package com.spark.p2p.controller.admin;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.*;

import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.util.*;
import com.sparkframework.sql.DB;
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
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.service.admin.RepayAdminService;
import com.sparkframework.lang.Convert;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/repay")
public class RepayAdminController extends BaseAdminController {

    @Autowired
    private RepayAdminService repayAdminService;
    @Autowired
    private BorrowAdminService borrowAdminService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private IphoneAuthService iphoneAuthService;

    protected String request(HttpServletRequest request, String name) {
        return StringUtils.trimToEmpty(request.getParameter(name));
    }

    /**
     * 等待还款的列表
     */
    @RequestMapping(value = "alllist")
    public String alllistIndex() throws Exception {

        return view("repay/alreadyRepay-index");
    }


    /**
     * 等待还款的列表
     */
    @RequestMapping(value = "alllist/list")
    public DataTable alllistList() {
        Admin admin = getAdmin();
        //dataTable((params) -> repayAdminService.queryNotRepayBorrow(params,8));
        return dataTable((params) -> repayAdminService.queryNotRepayBorrow(params));
    }


    /**
     * 还款完成的列表
     */
    @RequestMapping(value = "repayComplete")
    public String repayCompleteIndex() throws Exception {

        return view("repay/repayComplete-index");
    }


    /**
     * 还款完成的列表
     */
    @RequestMapping(value = "repayComplete/list")
    public DataTable repayComplete() {
        Admin admin = getAdmin();
        return dataTable((params) -> repayAdminService.queryAlreadyRepayBorrow(params, 10));
    }


    /**
     * 手动部分或者全额还款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "allRepayDetail/{bid}")
    public String secondAuditDetail(HttpServletRequest request,@PathVariable Integer bid) throws Exception {
        //银行卡
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        /*Map<String, String> bankMap = this.memberService.findBankCard(mid);*/
        //身份信息
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        Member member = memberService.findMember(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));

        int amount = Convert.strToInt(borrowMap.get("benJin"), 0);
        int borrowDays = Integer.valueOf(borrowMap.get("borrowDate"));
        DecimalFormat df = new DecimalFormat("######0.00");        //保留两位小数
        double xinFee = 0;
        double shouFee = 0;
        double serviceFee = 0;
        double otherFee = 0;
        xinFee = Double.valueOf(df.format(xinFee));
        shouFee = Double.valueOf(df.format(shouFee));
        serviceFee = Double.valueOf(df.format(serviceFee));

        //部分还款记录
        List<Map<String, String>> apartList = repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
        request.setAttribute("apartList", apartList);
        Map<String, String> remainMap = repayAdminService.findLastPay(bid);
        if (remainMap != null) {
            request.setAttribute("managerFee", remainMap.get("remainFee"));
            request.setAttribute("remainBenjin", remainMap.get("remainBenjin"));
        } else {
            request.setAttribute("remainBenjin", "" + amount);
            request.setAttribute("managerFee", "" + (xinFee + shouFee + serviceFee));
        }

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
        //续期记录
        List<Map<String, String>> renewalList = borrowAdminService.queryRenewalRecordByBid(bid);

        //Map<String,String> feesMap=borrowAdminService.findProductByDayAndMount(Convert.strToInt(borrowMap.get("benJin"), 0), Convert.strToInt(borrowMap.get("borrowDate"), 0));
        borrowDays = Convert.strToInt(borrowMap.get("borrowDate"), 0) + Convert.strToInt(borrowMap.get("addBorrowDay"), 0);


        borrowMap.put("borrowDate", borrowDays + "");
        //逾期记录
        Map<String, String> overdueMap = borrowAdminService.queryOverdueByBid(bid);

        double unrepaySum = 0;
        Map<String, String> remainMaps = repayAdminService.findLastPay(bid);
        int overdueFee = 0;
        if (overdueMap != null) {
            overdueFee = (int) Convert.strToDouble(overdueMap.get("overdueFee"), 0);
        } else {
            overdueFee = 0;
        }
        if (remainMaps != null) {
            unrepaySum = ((int) Convert.strToDouble(remainMap.get("remainFee"), 0) + (int) Convert.strToDouble(remainMap.get("remainBenjin"), 0) + overdueFee);
        } else {
            //unrepaySum=(Convert.strToInt(borrowMap.get("benJin"), 0)+((int)(Convert.strToDouble(feesMap.get("xinfee"), 0))+(int)(Convert.strToDouble(feesMap.get("shoufee"), 0))+(int)(Convert.strToDouble(feesMap.get("servicefee"), 0))));
            unrepaySum = amount + xinFee + shouFee + serviceFee + overdueFee;
        }
        request.setAttribute("unrepaySum", unrepaySum + "");
        request.setAttribute("overdueFee", overdueFee);
        request.setAttribute("overdueMap", overdueMap);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("member", member);
        /*request.setAttribute("bankMap", bankMap);*/
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("renewalList", renewalList);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("nowdate", DateUtil.dateToStringDate(new Date()));
        request.setAttribute("iphoneMap", iphoneMap);
        return view("repay/allRepayDetail");
    }


    /**
     * 手动还款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "manualFullRepay")
    public @ResponseBody
    MessageResult manualFullRepay() throws Exception {
        Admin admin = getAdmin();
        String bid = request("id");
        String auditOpinion = request("auditOpinion");
        String repayType = request("repayType");
        String realamount = request("realamount");
        String remainBenjin = request("remainBenjin");
        //12.1  crazy
        String overdueFee = request("overdueFee");
        /*String managerFee = request("managerFee");*/
        int status = 0;
        if (ValidateUtil.isnull(bid) || ValidateUtil.isnull(auditOpinion) || ValidateUtil.isnull(repayType) || ValidateUtil.isnull(realamount)) {
            return error("参数错误");
        }
        int mid = Convert.strToInt(borrowAdminService.findBorrowById(Convert.strToLong(bid, 0)).get("member_id"), 0);
        int realamounts = (int) Convert.strToDouble(realamount, 0);
        int remainBenjins = (int) Convert.strToDouble(remainBenjin, 0);
        int overdueFees = (int) Convert.strToDouble(overdueFee, 0);
        /*int managerFees = (int) Convert.strToDouble(managerFee, 0);*/
        if ("2".equals(repayType)) {
            //全额还款
            if (realamounts < (remainBenjins + overdueFees)) {
                return error(-1, "输入的金额小于全额还款的金额");
            } else {
                status = 3;
                overdueFees = 0;
                remainBenjins = 0;
            }
        }
        if ("1".equals(repayType)) {
            //二大爷的，1是部分还款，和数据库中的repayType数值不一致，只和前台传入的值一致
            //realamounts是前台传入的实际扣款值
            if (realamounts > 0) {
                status = 2;
                //手动还款金额大于剩余本金但是小于本金与逾期费的和
                if (realamounts > remainBenjins) {

                    /*managerFees = managerFees - (realamounts - remainBenjins);*/
                    //逾期费为逾期费-（还款金额-实际欠款）
                    overdueFees = overdueFees - (realamounts - remainBenjins);
                    remainBenjins = 0;
                    //如果逾期费小于等于0，说明也算是全额还款
                    if (overdueFees <= 0) {
                        status = 3;
                    }
                } else {
                    remainBenjins = remainBenjins - realamounts;
                }
            } else {
                return error(-2, "还款金额大于0");
            }
        }

        long ret = repayAdminService.manulFullRepayDetail(Convert.strToInt(bid, 0), status, auditOpinion, admin.getId(), realamounts, remainBenjins, overdueFees, mid, getRemoteIp());
        if (ret > 0) {
            return success("手动还款成功");
        } else {
            return error("手动还款失败");
        }
    }

    /**
     * 续期操作
     */
    @RequestMapping(value = "renew/{bid}")
    public String renewBorrow(HttpServletRequest request,@PathVariable Integer bid) throws Exception {
        //银行卡
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        //身份信息（图片）
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        /*Map<String, String> bankMap = this.memberService.findBankCard(mid);*/
        //身份信息
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(mid);
        Member member = memberService.findMember(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));

        int amount = Convert.strToInt(borrowMap.get("benJin"), 0);
        int borrowDays = Integer.valueOf(borrowMap.get("borrowDate"));
        DecimalFormat df = new DecimalFormat("######0.00");        //保留两位小数
        double xinFee = 0;
        double shouFee = 0;
        double serviceFee = 0;
        double otherFee = 0;
        xinFee = Double.valueOf(df.format(xinFee));
        shouFee = Double.valueOf(df.format(shouFee));
        serviceFee = Double.valueOf(df.format(serviceFee));

        //部分还款记录
        List<Map<String, String>> apartList = repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
        request.setAttribute("apartList", apartList);
        Map<String, String> remainMap = repayAdminService.findLastPay(bid);
        if (remainMap != null) {
            request.setAttribute("managerFee", remainMap.get("remainFee"));
            request.setAttribute("remainBenjin", remainMap.get("remainBenjin"));
        } else {
            request.setAttribute("remainBenjin", "" + amount);
            request.setAttribute("managerFee", "" + (xinFee + shouFee + serviceFee));
        }

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

        //续期记录
        List<Map<String, String>> renewalList = borrowAdminService.queryRenewalRecordByBid(bid);

        //Map<String,String> feesMap=borrowAdminService.findProductByDayAndMount(Convert.strToInt(borrowMap.get("benJin"), 0), Convert.strToInt(borrowMap.get("borrowDate"), 0));
        borrowDays = Convert.strToInt(borrowMap.get("borrowDate"), 0) + Convert.strToInt(borrowMap.get("addBorrowDay"), 0);


        borrowMap.put("borrowDate", borrowDays + "");
        //逾期记录
        Map<String, String> overdueMap = borrowAdminService.queryOverdueByBid(bid);

        double unrepaySum = 0;
        Map<String, String> remainMaps = repayAdminService.findLastPay(bid);
        int overdueFee = 0;
        if (overdueMap != null) {
            overdueFee = (int) Convert.strToDouble(overdueMap.get("overdueFee"), 0);
        } else {
            overdueFee = 0;
        }
        if (remainMaps != null) {
            unrepaySum = ((int) Convert.strToDouble(remainMap.get("remainFee"), 0) + (int) Convert.strToDouble(remainMap.get("remainBenjin"), 0) + overdueFee);
        } else {
            //unrepaySum=(Convert.strToInt(borrowMap.get("benJin"), 0)+((int)(Convert.strToDouble(feesMap.get("xinfee"), 0))+(int)(Convert.strToDouble(feesMap.get("shoufee"), 0))+(int)(Convert.strToDouble(feesMap.get("servicefee"), 0))));
            unrepaySum = amount + xinFee + shouFee + serviceFee + overdueFee;
        }
        request.setAttribute("unrepaySum", unrepaySum + "");
        request.setAttribute("overdueFee", overdueFee);
        request.setAttribute("overdueMap", overdueMap);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("member", member);
        /*request.setAttribute("bankMap", bankMap);*/
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("renewalList", renewalList);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("nowdate", DateUtil.dateToStringDate(new Date()));
        request.setAttribute("iphoneMap", iphoneMap);
        return view("repay/renewBorrowDetail");
    }


    @RequestMapping(value = "renew/borrow")
    public @ResponseBody MessageResult renewBorrow() throws Exception {
        String bid = request("id");
        String renewDays = request("renewDays");
        String renewFee = request("renewFee");
        Map<String, String> borrowMap = borrowService.findBorrowById(Long.parseLong(bid));
        String memberId = borrowMap.get("member_id");
        Member member = memberService.findMember(Long.parseLong(memberId));
        borrowService.insertFundRecord(Long.parseLong(memberId), Convert.strToInt(borrowMap.get("id"), 0)/*, bankCardNo*/,
                renewFee + "", "扣除续期费用", getRemoteIp(), 3);
        // 发送短信
        //SMSUtil.sendContent(member.getMobilePhone(), "您的编号" + borrowMap.get("borrowNo") + "申请，申请续期" + borrowDays
        //		+ "天成功，续期费用" + deduceSum + "元，扣款卡号" + bankCardNo + "，请到时正常还款后继续续期！");

        String borrowNo = borrowMap.get("borrowNo");
        String content = SMSUtil.renewalBorrow(borrowNo, Integer.parseInt(renewDays), Double.parseDouble(renewFee)/*,bankCardNo*/);
        long ret = borrowService.borrowOverdueHandle(borrowMap, Double.parseDouble(renewFee), Integer.parseInt(renewDays), getRemoteIp()/*, bankCardNo*/);
        if (ret>0) {
            SMSUtil.sendContent(member.getMobilePhone(), content);
            return success("续期成功");
        }else {
            DB.rollback();
            return error("续期失败");
        }
    }


    /**
     * 逾期列表
     */
    @RequestMapping(value = "overdue")
    public String overdueListIndex() throws Exception {
        return view("repay/overdueRepay-index");
    }


    /**
     * 逾期列表
     */
    @RequestMapping(value = "overdueList/list")
    public DataTable overdueList() {
        return dataTable((params) -> repayAdminService.queryAlreadyRepayBorrow(params, 9));
    }


    /**
     * 逾期详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "overdueRepayDetail/{bid}")
    public String overdueRepayDetail(HttpServletRequest request,@PathVariable Integer bid) throws Exception {
        //借款表
        Map<String, String> borrowMap = borrowAdminService.findBorrowById(bid);
        long mid = Convert.strToLong(borrowMap.get("member_id"), 0);
        Member member = memberService.findMember(mid);
        //个人信息认证
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

        //手机认证，芝麻信息
        Map<String, String> auditMap = memberService.findMemberAuditChain(mid);
        //身份认证
        Map<String, String> cardMap = memberService.findMemberIdentyByMemId(mid);
        //审核意见
        Map<String, String> reviewMap = memberService.findFristReviewn(Convert.strToInt(borrowMap.get("id"), 0));
       /* Map<String, String> bankMap = this.memberService.findBankCard(mid);*/
        //续期记录
        List<Map<String, String>> renewalList = borrowAdminService.queryRenewalRecordByBid(bid);
        //还款记录
        List<Map<String, String>> apartList = repayAdminService.queryApartRepayList(Convert.strToInt(borrowMap.get("id"), 0));
        //逾期记录
        Map<String, String> overdueMap = borrowAdminService.queryOverdueByBid(bid);
        double benjin = Convert.strToDouble(overdueMap.get("benjin"), 0);
        double overdueFee = Convert.strToDouble(overdueMap.get("overdueFee"), 0);
        overdueMap.put("total", (benjin + overdueFee) + "");
        request.setAttribute("apartList", apartList);
        /*request.setAttribute("bankMap", bankMap);*/
        request.setAttribute("overdueMap", overdueMap);
        request.setAttribute("borrowMap", borrowMap);
        request.setAttribute("member", member);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("auditMap", auditMap);
        request.setAttribute("cardMap", cardMap);
        request.setAttribute("reviewMap", reviewMap);
        request.setAttribute("renewalList", renewalList);
        request.setAttribute("linkmansize", linkmansize);
        return view("repay/overdueRepay-detail");
    }


    /**
     * 手动逾期还款
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "manalOverdueRepay")
    public @ResponseBody
    MessageResult manalOverdueRepay() throws Exception {
        Admin admin = getAdmin();
        String bid = request("id");
        String status = request("status");
        String review = request("opinion");
        String realamount = request("realamount");
        if (ValidateUtil.isnull(bid) || ValidateUtil.isnull(review) || ValidateUtil.isnull(status) || ValidateUtil.isnull(realamount)) {
            return error("参数错误");
        }
        long ret = repayAdminService.manulOverdueRepay(Convert.strToInt(bid, 0), status, review, admin.getId(), realamount, getRemoteIp(), admin.getId());
        if (ret > 0) {
            return success("手动还款成功");
        } else {
            return error("手动还款失败");
        }
    }


    /**
     * 导出未还款订单信息
     *
     * @return
     */
    @RequestMapping("allReypayDetail/excel")
    @ResponseBody
    public MessageResult queryAllReypayDetailExcel(HttpServletRequest request, final HttpServletResponse response) throws Exception {
        StringBuffer condition = new StringBuffer("");
        String searchDateRange = request(request, "searchDateRange");
        if (!StringUtils.isEmpty(searchDateRange)) {
            if (searchDateRange.length() > 13) {
                String begin = searchDateRange.substring(0, 10) + " 00:00:00";
                String end = searchDateRange.substring(searchDateRange.length() - 10, searchDateRange.length()) + " 23:59:59";
                condition.append(" appointmentTime between '" + begin + "' and '" + end + "' and ");
            }
        }
        condition.append(" 1=1");
        List<Map<String, String>> list = repayAdminService.queryAllRepayExcel(condition.toString());
        // 列名
        String[] columnNames = {"借款订单号", "真实姓名", "手机号", "借款金额","icloudId", "放款时间", "约定还款时间", "借款状态"};
        // map中的key
        String keys[] = {"borrowNo", "real_name", "mobilePhone", "benJin","icloudId",  "realLoanTime", "appointmentTime", "borrowStatus"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createRepaymentWorkBook(list, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(
                            ("未还款订单信息_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls").getBytes(),
                            "utf-8"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            return error(500, "系统错误");
        }
        return success();
    }
}

	

