package com.spark.p2p.service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.spark.p2p.config.AppSetting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.dao.MemberDao;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.CardIdCheck;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service
public class BorrowService extends BaseService {
    private static Log log = LogFactory.getLog(BorrowService.class);
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberService memberService;
    @Autowired
    private IphoneAuthService iphoneAuthService;

    public MessageResult borrowCheck(long memberId, String type) throws Exception {
        Boolean flag = Arrays.asList(AppSetting.ALLOW_IPHONE_LIST).contains(type);
        if (!flag) {
            return MessageResult.error(500, "暂不支持的手机型号");
        }
        log.info(flag);
        //check(memberId);方法提取出来出现问题，11.14
        MessageResult mr = new MessageResult();
        Map<String, String> checkMap = memberService.checkIdentityStatus(memberId);
        //主要处理通讯录
        Map<String, String> commicateMap = memberService.findCommicateById(memberId);
        if (checkMap == null) {
            return MessageResult.error(-1, "请先到个人中心认证");
        }
        String identity_status = checkMap.get("identity_status");
        String phone_status = checkMap.get("phone_status");
        String phone_audit_time = checkMap.get("phone_audit_time");
        String commicate_status = commicateMap.get("commicateStatus");
        if ("0".equals(identity_status)) {
            return MessageResult.error(-1, "身份信息没有认证");
        }
        if ("0".equals(phone_status)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if (ValidateUtil.isnull(phone_audit_time)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if ("0".equals(commicate_status)) {
            return MessageResult.error(-1, "通讯录没有认证");
        }
        long phoneDay = DateUtil.diffDays(DateUtil.strToDate(phone_audit_time), new Date());
        if (phoneDay > 30) {
            return MessageResult.error(-1, "手机认证过期，请重新认证");
        }

      /*  if (this.memberService.findBankCard(memberId) == null) {
            return MessageResult.error(-1, "请先进行银行卡认证");
        }*/

        //如果复审失败，三十天内不能再申请
        Map<String, String> failMap = findBorrowByMemberAndStatus(memberId, 7);
        Map<String, String> failsMap = findBorrowByMemberAndStatus(memberId, 3);
        String reapply = new Model("constant_variable").where("name= ?", "REAPPLYDAY").find().get("value");
        if (failMap != null) {
            String secondAuditTime = failMap.get("secondAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());

                if (days < Convert.strToInt(reapply, 0)) {
                    return MessageResult.error(-1, "距离上次复审失败没有超过30天，请等待");
                }
            }
        }
        if (failsMap != null) {
            String secondAuditTime = failsMap.get("fristAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());
                if (days < Convert.strToInt(reapply, 0)) {
                    mr.setCode(-13);
                    mr.setMessage("距离上次初审失败没有超过30天，请等待");
                    return mr;
                }
            }
        }

        Member member = this.memberService.findMember(memberId);
        //判断地区是否限制
        String identyNo = member.getIdentNo();
        String area = CardIdCheck.getCardInfo(identyNo).get("area");
        List<Map<String, String>> list = memberService.queryAreaProviceList();
        for (int i = 0; i < list.size(); i++) {
            String province = list.get(i).get("province");
            if (province.contains(area)) {
                mr.setCode(-14);
                mr.setMessage("本地区暂时未开通借款业务");
                return mr;
            }
        }

        //年龄判断
        int age=Convert.strToInt(CardIdCheck.getCardInfo(identyNo).get("age"), 0);
        int allowMaxAge=Convert.strToInt(new Model("constant_variable").where("name= ?","ALLOWMAXAGE").find().get("value"),0);
        int allowMinAge=Convert.strToInt(new Model("constant_variable").where("name= ?","ALLOWMINAGE").find().get("value"),0);
        if(!(age>=allowMinAge && age<=allowMaxAge)){
            mr.setCode(-15);
            mr.setMessage("年龄不在系统允许范围内");
            return mr;
        }

        if (member.getMemberStatus() == 3) {
            mr.setCode(-1);
            mr.setMessage("你是黑名单，请联系客服");
            return mr;
        }
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(memberId);
        if (iphoneMap!=null) {
            String icloudImg = iphoneMap.get("icloud_imgurl");
            if (icloudImg != null) {
                return MessageResult.error(-7, "已提交Id账号，请耐心等待");
            }
        }
        //查询未还款的借款
        Map<String, String> notRepay = this.getBorrowByMemberAndStatus(memberId, "(1,2,4,5,6,8,9)");
        if (notRepay != null) {
            String msgarr[] = {
                    "",//0
                    "您已经有提交过的借款，请等待审核",//1
                    "您已经有提交过的借款，请等待审核",//2
                    "",//3
                    "您已有通过初审的借款，请等待复审", //4
                    "您已有等待复审的借款", //5
                    "您已有等待放款的借款", //6
                    "",//7
                    "您有未还的借款",//8
                    "您有逾期的借款，请先还款",//9

            };
            /*1：提交审核，2：认领中，3：初审失败，4：初审成功，需认证银行卡，5：等待复审，6：复审成功，7：复审失败，
            8：还款期间(确定已收到打款)，9：逾期中，10：还款完成，11：未还完，12:已取消，13:终审失败*/
            int borrowStatus = Integer.valueOf(notRepay.get("borrowStatus"));
            if ("1".equals(borrowStatus + "") || "2".equals(borrowStatus + "")) {
                return MessageResult.error(-5, "您已有提交的订单，请等待审核，5分钟内请注意接听客服电话！");
            } else if ("4".equals(borrowStatus + "") || "5".equals(borrowStatus + "")) {
                return MessageResult.error(-6, "您的订单已通过初审，请根据提示进行ID认证");
            } else {
                return MessageResult.error(1, msgarr[borrowStatus]);
            }
        }
        return MessageResult.success("请根据提示进行序列号认证");
    }

    //增加一个查询手机型号和内存容量查询,防止手机型号更新
    public Map<String,String> iphoneTypeStorageCheck(String type,int storage ) throws Exception {
        return iphoneAuthService.getMaxMoney(type,storage);
    }

    //iphone认证接口之前的检查
    public MessageResult borrowCheckIphone(long memberId) throws Exception {
        MessageResult mr = check(memberId);
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(memberId);
        if (iphoneMap != null) {
            String iphoneStatus = iphoneMap.get("status");
            if ("1".equals(iphoneStatus)) {
                return MessageResult.success("iphone已认证通过");
            }
        }
        //查询未还款的借款
        Map<String, String> notRepay = this.getBorrowByMemberAndStatus(memberId, "(1,2,4,5,6,8,9)");
        if (notRepay != null) {
            String msgarr[] = {
                    "",//0
                    "您已经有提交过的订单，请等待审核",//1
                    "您已经有提交过的订单，请等待审核",//2
                    "",//3
                    "您已有通过初审的订单，请等待复审", //4
                    "您已有等待复审的订单", //5
                    "您已有等待放款的订单", //6
                    "",//7
                    "您有未还的订单",//8
                    "您有逾期的订单，请先处理",//9

            };
            int borrowStatus = Integer.valueOf(notRepay.get("borrowStatus"));
            return MessageResult.error(1, msgarr[borrowStatus]);
        }

        return MessageResult.success("请根据提示进行ID认证");
    }

    //icloud认证接口之前的检查
    public MessageResult borrowCheckIcloud(long memberId) throws Exception {
        MessageResult mr = new MessageResult();
        Map<String, String> checkMap = memberService.checkIdentityStatus(memberId);
        //主要处理通讯录
        Map<String, String> commicateMap = memberService.findCommicateById(memberId);
        if (checkMap == null) {
            return MessageResult.error(-1, "请先到个人中心认证");
        }
        String identity_status = checkMap.get("identity_status");
        String phone_status = checkMap.get("phone_status");
        String phone_audit_time = checkMap.get("phone_audit_time");
        String commicate_status = commicateMap.get("commicateStatus");
        if ("0".equals(identity_status)) {
            return MessageResult.error(-1, "身份信息没有认证");
        }
        if ("0".equals(phone_status)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if (ValidateUtil.isnull(phone_audit_time)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if ("0".equals(commicate_status)) {
            return MessageResult.error(-1, "通讯录没有认证");
        }
        long phoneDay = DateUtil.diffDays(DateUtil.strToDate(phone_audit_time), new Date());
        if (phoneDay > 30) {
            return MessageResult.error(-1, "手机认证过期，请重新认证");
        }

        /*if (this.memberService.findBankCard(memberId) == null) {
            return MessageResult.error(-1, "请先进行银行卡认证");
        }*/

        //如果复审失败，三十天内不能再申请
        Map<String, String> failMap = findBorrowByMemberAndStatus(memberId, 7);
        Map<String, String> failsMap = findBorrowByMemberAndStatus(memberId, 3);
        String reapply = new Model("constant_variable").where("name= ?", "REAPPLYDAY").find().get("value");
        if (failMap != null) {
            String secondAuditTime = failMap.get("secondAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());

                if (days < Convert.strToInt(reapply, 0)) {
                    return MessageResult.error(-1, "距离上次复审失败没有超过30天，请等待");
                }
            }
        }
        if (failsMap != null) {
            String secondAuditTime = failsMap.get("fristAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());
                if (days < Convert.strToInt(reapply, 0)) {
                    mr.setCode(-13);
                    mr.setMessage("距离上次初审失败没有超过30天，请等待");
                    return mr;
                }
            }
        }

        Member member = this.memberService.findMember(memberId);
        //判断地区是否限制
        String identyNo = member.getIdentNo();
        String area = CardIdCheck.getCardInfo(identyNo).get("area");
        List<Map<String, String>> list = memberService.queryAreaProviceList();
        for (int i = 0; i < list.size(); i++) {
            String province = list.get(i).get("province");
            if (province.contains(area)) {
                mr.setCode(-14);
                mr.setMessage("本地区暂时未开通借款业务");
                return mr;
            }
        }
        if (member.getMemberStatus() == 3) {
            mr.setCode(-1);
            mr.setMessage("你是黑名单，请联系客服");
            return mr;
        }
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(memberId);
        if (iphoneMap != null) {
            String iphoneStatus = iphoneMap.get("status");
            if ("2".equals(iphoneStatus)) {
                return MessageResult.error(-1, "icloud已认证通过");
            }
        }
        //查询未还款的借款
        /*1：提交审核，2：认领中，3：初审失败，4：初审成功，需认证银行卡，5：等待复审，6：复审成功，7：复审失败，
            8：还款期间(确定已收到打款)，9：逾期中，10：还款完成，11：未还完，12:已取消，13:终审失败*/
        Map<String, String> notRepay = this.getBorrowByMemberAndStatus(memberId, "(1,2,3,4,6,8,9)");
        if (notRepay != null) {
            String msgarr[] = {
                    "",//0
                    "您已经有提交过的借款，请等待审核",//1
                    "您已经有提交过的借款，请等待审核",//2
                    "您初审失败，请重新进行初审",//3
                    "您已有通过初审的借款，请等待复审", //4
                    "您已有等待放款的借款", //6
                    "",//7
                    "您有未还的借款",//8
                    "您有逾期的借款，请先还款",//9

            };
            int borrowStatus = Integer.valueOf(notRepay.get("borrowStatus"));
            if ("1".equals(borrowStatus + "") || "2".equals(borrowStatus + "")) {
                return MessageResult.error(-5, "您已有提交初审的订单，请等待审核");
            } else if ("4".equals(borrowStatus + "") || "5".equals(borrowStatus + "")) {
                return MessageResult.error(-6, "您已有通过初审的订单，请根据提示进行ID认证");
            } else {
                return MessageResult.error(1, msgarr[borrowStatus]);
            }
        }
        return MessageResult.success("ok");
    }
    //status: (1,2,4,5,6,8,9)

    public Map<String, String> getBorrowByMemberAndStatus(long mid, String status) throws Exception {
       /* Map<String, String> bank = new Model("member_bank").where("member_id=? and status=1", mid).find();*/
        Map<String, String> map = new Model("borrow_main")
                .where("borrowStatus in  " + status + " and member_id =  ?", mid)
                .find();

       /* if (map != null && bank != null) {
            map.put("bankCardNo", bank.get("cardNo"));
        }*/

        return map;
    }

    public Map<String, String> findBorrowByIphoneId(long iphoneId) throws Exception {
        return new Model("borrow_main").where("iphoneId=?", iphoneId).find();
    }

    public MessageResult check(long memberId) throws Exception {
        MessageResult mr = new MessageResult();
        Map<String, String> checkMap = memberService.checkIdentityStatus(memberId);
        //主要处理通讯录
        Map<String, String> commicateMap = memberService.findCommicateById(memberId);
        if (checkMap == null) {
            return MessageResult.error(-1, "请先到个人中心认证");
        }
        String identity_status = checkMap.get("identity_status");
        String phone_status = checkMap.get("phone_status");
        String phone_audit_time = checkMap.get("phone_audit_time");
        String commicate_status = commicateMap.get("commicateStatus");
        if ("0".equals(identity_status)) {
            return MessageResult.error(-1, "身份信息没有认证");
        }
        if ("0".equals(phone_status)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if (ValidateUtil.isnull(phone_audit_time)) {
            return MessageResult.error(-1, "手机信息没有认证");
        }
        if ("0".equals(commicate_status)) {
            return MessageResult.error(-1, "通讯录没有认证");
        }
        long phoneDay = DateUtil.diffDays(DateUtil.strToDate(phone_audit_time), new Date());
        if (phoneDay > 30) {
            return MessageResult.error(-1, "手机认证过期，请重新认证");
        }

       /* if (this.memberService.findBankCard(memberId) == null) {
            return MessageResult.error(-1, "请先进行银行卡认证");
        }*/

        //如果复审失败，三十天内不能再申请
        Map<String, String> failMap = findBorrowByMemberAndStatus(memberId, 7);
        Map<String, String> failsMap = findBorrowByMemberAndStatus(memberId, 3);
        String reapply = new Model("constant_variable").where("name= ?", "REAPPLYDAY").find().get("value");
        if (failMap != null) {
            String secondAuditTime = failMap.get("secondAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());

                if (days < Convert.strToInt(reapply, 0)) {
                    return MessageResult.error(-1, "距离上次复审失败没有超过30天，请等待");
                }
            }
        }
        if (failsMap != null) {
            String secondAuditTime = failsMap.get("fristAuditTime");
            if (!ValidateUtil.isnull(secondAuditTime)) {
                Date oldDate = DateUtil.strToDate(secondAuditTime);
                long days = DateUtil.diffDays(oldDate, new Date());
                if (days < Convert.strToInt(reapply, 0)) {
                    mr.setCode(-13);
                    mr.setMessage("距离上次初审失败没有超过30天，请等待");
                    return mr;
                }
            }
        }

        Member member = this.memberService.findMember(memberId);
        //判断地区是否限制
        String identyNo = member.getIdentNo();
        String area = CardIdCheck.getCardInfo(identyNo).get("area");
        List<Map<String, String>> list = memberService.queryAreaProviceList();
        for (int i = 0; i < list.size(); i++) {
            String province = list.get(i).get("province");
            if (province.contains(area)) {
                mr.setCode(-14);
                mr.setMessage("本地区暂时未开通借款业务");
                return mr;
            }
        }
        if (member.getMemberStatus() == 3) {
            mr.setCode(-1);
            mr.setMessage("你是黑名单，请联系客服");
            return mr;
        }
        return mr;
    }

    public double getSysParamByKey(String key) {
        try {
            Map<String, String> map = new Model("sys_params").field("value").where("`key`=?", key).find();
            return Double.valueOf(map.get("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //借款申请

    public long insertBorrowApply(long mid, String iphoneId, String borrowNo, double xinFee, double shouFee, double serviceFee, double otherFee, int amount, int borrowDate, int memberStatus) throws Exception {
        synchronized (this) {
            if (new Model("borrow_main").where("borrowStatus in (1,2,4,5,6) and member_id=  ? ", mid).find() != null) {
                return -1;
            }
            Model m = new Model("borrow_main");
            m.set("member_id", mid);
            m.set("xinFee", xinFee);
            m.set("shouFee", shouFee);
            m.set("serviceFee", serviceFee);
            m.set("otherFee", otherFee);
            m.set("benJin", amount);
            m.set("totalFee", xinFee + shouFee + serviceFee);
            m.set("borrowDate", borrowDate);
            m.set("borrowStatus", 1);
            m.set("iphoneId", iphoneId);
            m.set("borrowNo", borrowNo);
            m.set("fristSubmitTime", DateUtil.getDateTime());
            return m.insert();

        }
    }

    //苹果提交手机图片之后的申请（仅是创建一个信息未完善的借款信息，方便后续的认领工作）
    public long insertBorrowApplyAfterPhone(long mid, String borrowNo, String iphoneId) throws Exception {
        synchronized (this) {
            if (new Model("borrow_main").where("borrowStatus in (1,2,4,5,6) and member_id=  ? ", mid).find() != null) {
                return -1;
            }
            Model m = new Model("borrow_main");
            m.set("member_id", mid);
            m.set("borrowStatus", 1);
            m.set("iphoneId", iphoneId);
            m.set("borrowNo", borrowNo);
            m.set("claimTime", DateUtil.getDateTime());
            return m.insert();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public long updateIndetityForImage(int id, String imgPath, long borrowId) throws SQLException {
        Model m = new Model("identity_check");
        m.set("people_imgurl", imgPath);
        m.set("submit_time", DateUtil.getDateTime());
        long ret = m.update(id);
        if (ret > 0) {
            //修改借款未等待复审
            Model ma = new Model("borrow_main");
            ma.set("borrowStatus", 5);
            ret = ma.update(borrowId);
        }
        return ret;
    }

    public Map<String, String> findComingBorrow(long mid, int status) throws Exception {
        return new Model("borrow_main").where("member_id = ? and borrowStatus=?", mid, status).find();
    }

    @Transactional(rollbackFor = Exception.class)
    public long updateBorrowStatus(long bid, int status) throws Exception {
        Model m = new Model("borrow_main");
        m.set("borrowStatus", status);
        if (status == 7) {
            m.set("secondAuditTime", DateUtil.getDateTime());
            m.set("isSecondFail", 1);
        }
        /*if(status==13){
            m.set("secondAuditTime", DateUtil.getDateTime());
			m.set("isSecondFail",1);
		}*/
        return m.update(bid);

    }


    public double getRepaySum(long borrowId) throws Exception {
        Map<String, String> borrowMap = this.findBorrowById(borrowId);
        log.info("getRepaySum|borrowMap = " + borrowMap);
        if (borrowMap == null) {
            return 0;
        }
        log.info(borrowMap);
        // 正常还款或者逾期还款
        long bid = Long.valueOf(borrowMap.get("id"));

        Map<String, String> overdueMap = findAppayOverdueLog(bid);
        double repaySum = 0;
        if (overdueMap == null) {//还未逾期，正常还款
            Map<String, String> lastRepayMap = findLastPay((int) bid);

            if (lastRepayMap == null) {
                repaySum = repaySum + Convert.strToInt(borrowMap.get("benJin"), 0);
                int borrowDays = Integer.valueOf(borrowMap.get("borrowDate"));
                int benJin = Integer.valueOf(borrowMap.get("benJin"));
                DecimalFormat df = new DecimalFormat("######0.00");        //保留两位小数
                double xinFee = 0;
                double shouFee = 0;
                double serviceFee = 0;
                double otherFee = 0;
                xinFee = Double.valueOf(df.format(xinFee));
                shouFee = Double.valueOf(df.format(shouFee));
                serviceFee = Double.valueOf(df.format(serviceFee));
                //还款金额即为本金 2017.11.16
                repaySum = repaySum + xinFee + shouFee + serviceFee + otherFee;
            } else {
                // 从还款表获取费用
                repaySum = repaySum + Convert.strToDouble(lastRepayMap.get("remainBenjin"), 0);
                //
                repaySum = repaySum + Convert.strToDouble(lastRepayMap.get("remainFee"), 0);
            }
        } else {//已逾期
            repaySum = Convert.strToDouble(overdueMap.get("benjin"), 0)
                    + Convert.strToDouble(overdueMap.get("overdueFee"), 0)
                    + Convert.strToDouble(overdueMap.get("remainFee"), 0);
        }

        return repaySum;
    }

    @Transactional(rollbackFor = Exception.class)
    public long initBorrowRepayOrderNo(long bid, String repayOrderNo) throws Exception {
        Model m = new Model("borrow_main");
        m.set("repayOrderNo", repayOrderNo);
        m.set("payStatus", 1);
        long ret = m.where("payStatus=0 and (borrowStatus=8 or borrowStatus=9) and id=?", bid).update();
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    public long initBorrowRepayStatus(long bid) throws Exception {
        Model m = new Model("borrow_main");
        m.set("borrowStatus", 10);

        return m.update(bid);
    }

    @Transactional(rollbackFor = Exception.class)
    public long repayBorrowFailed(String repayOrderNo, String ip) throws Exception {
        long ret = new Model("borrow_main")
                .where("repayOrderNo = ?", repayOrderNo)
                .setField("borrowStatus", 8);
        return ret;
    }

    //原来叫做updateBorrowStatusRelation，现在改成repayBorrowSuccess
    @Transactional(rollbackFor = Exception.class)
    public long repayBorrowSuccess(String repayOrderNo, String ip) {

        log.info("repayBorrowSuccess|start...");
        try {
            long ret = 0;
            Map<String, String> borrow = new Model("borrow_main")
                    .where("repayOrderNo = ?", repayOrderNo)
                    .find();
            log.info("borrow=" + borrow);
            if (borrow == null) {
                return -1;
            }
            long memberId = Long.valueOf(borrow.get("member_id"));
            long bid = Long.valueOf(borrow.get("id"));
           /* Map<String, String> bank = memberService.findBankCard(memberId);*/
           /* log.info("bank=" + bank);
            String bankCardNo = bank.get("cardNo");*/
            double repaySum = this.getRepaySum(bid);
            int pressStatus = Integer.valueOf(borrow.get("pressStatus"));
            log.info("repaySum=" + repaySum + ", pressStatus=" + pressStatus);

            Model m = new Model("borrow_main");
            m.set("borrowStatus", 10);
            m.set("repayType", 1);
            if (pressStatus != 0) {
                m.set("pressStatus", 3);
            }
            m.set("finalRepayTime", DateUtil.getDateTime());
            ret = m.update(bid);
            log.info("ret = " + ret);

            if (ret >= 0) {
                log.info("repay_main");
                Model ma = new Model("repay_main");
                ma.set("repayTime", DateUtil.getDateTime());
                ma.set("borrowId", bid);
                ma.set("memberId", memberId);
                /*ma.set("bankCardNo", bankCardNo);*/
                ma.set("remainBenjin", 0);
                ma.set("amount", repaySum);
                ma.set("repayType", 1);
                ma.set("ip", ip);
                ret = ma.insert();
                log.info("repay_main insert, ret=" + ret);
            }
            if (pressStatus == 0) {
                ret = insertFundRecord(memberId, bid, /*bankCardNo,*/ repaySum + "", "正常还款", ip, 2);
            } else {
                ret = insertFundRecord(memberId, bid, /*bankCardNo,*/ repaySum + "", "逾期还款", ip, 5);
            }

            Member member = findMember(memberId);
            int times = member.getSuccessBorrowTimes();
            Model ma = new Model("member");
            if (pressStatus == 0) {
                ma.set("member_status", 1);
            }
            int alreadyRepaySum = member.getAlreadyRepaySum();
            ma.set("alreadyRepaySum", alreadyRepaySum + (int) repaySum);
            if (pressStatus != 0) {
                //从member表中减去逾期金额
                int overdueSum = member.getOverdueSum();
                int benjin = (int) Convert.strToDouble(new Model("overdue_record")
                        .where("borrow_id=?  ", bid).find().get("benjin"), 0);
                ma.set("overdueSum", overdueSum - benjin);
            }
            ma.set("successBorrowTimes", times + 1);
            ma.update(memberId);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("repayBorrowSuccess|end...");
        return -1;
    }


    public Map<String, String> findHasNotGiveBorrow(long memberId) throws Exception {
        return new Model("borrow_main").where("member_id = ? and borrowStatus in(8,9)", memberId).find();
    }

    public List<Map<String, String>> queryBorrowByMemberId(long memberId) throws Exception {
        return new Model("v_borrow_list").where("member_id = ? ", memberId).order("id desc").select();
    }


    public Map<String, String> findBorrowById(long bid) throws Exception {
        Map<String, String> borrow = new Model("borrow_main").where("id = ? ", bid).find();
        if (borrow != null) {
            /*Map<String, String> bank = new Model("member_bank").where("member_id=? and status=1", borrow.get("member_id")).find();
            if (bank != null) {
                borrow.put("bankCardNo", bank.get("cardNo"));
            }*/
        }

        return borrow;
    }

    public Map<String, String> findBorrowAuditById(long mid) throws Exception {
        return new Model("audit_chain").where("member_id = ? ", mid).find();
    }

    public long overdueHandle(Map<String, String> borrowMap, double xinFee, double shouFee, double serviceFee,
                              double otherFee) {
        // TODO Auto-generated method stub
        return 0;
    }


    //返回N天后的日期字符串
    public String getNDaysBehindStr(String dateStr, int nDays) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateStr.substring(0, 10))); //当天
        c.add(Calendar.DAY_OF_YEAR, nDays); //下一天
        String theDate = DateUtil.YYYY_MM_DD.format(c.getTime());

        return theDate;

    }

    @Transactional(rollbackFor = Exception.class)
    public long borrowOverdueHandle(Map<String, String> borrowMap, double totalFee
            , int applyOverDay, String ip/*, String bankCardNo*/) throws Exception {
        Model ma = new Model("borrow_main");
        ma.set("borrowStatus", 8);
        ma.set("overdueTimes", Convert.strToInt(borrowMap.get("overdueTimes"), 0) + 1);
        ma.set("addBorrowDay", Convert.strToInt(borrowMap.get("addBorrowDay"), 0) + applyOverDay);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(borrowMap.get("realLoanTime"))); //当天
        c.add(Calendar.DAY_OF_YEAR, Integer.valueOf(borrowMap.get("addBorrowDay")) + applyOverDay + Integer.valueOf(borrowMap.get("borrowDate"))); //下一天
        String dateBegins = DateUtil.YYYY_MM_DD.format(c.getTime());
        //Date appiontDate=DateUtil.dateAddDay(DateUtil.strToDate(borrowMap.get("realLoanTime")) , Convert.strToInt(borrowMap.get("addBorrowDay"), 0)+applyOverDay+Convert.strToInt(borrowMap.get("borrowDate"), 0));
        ma.set("appointmentTime", dateBegins);
        long ret = ma.update(Convert.strToLong(borrowMap.get("id"), 0));
        if (ret > 0) {
            Model m = new Model("renewal_record");
            m.set("borrowId", Convert.strToLong(borrowMap.get("id"), 0));
            m.set("benJin", Convert.strToDouble(borrowMap.get("benJin"), 0));
            m.set("renewalFee", totalFee);
            m.set("renewalDays", applyOverDay);
            m.set("renewalTime", DateUtil.getDateTime());
            m.set("renewalIp", ip);
            /*m.set("bankCardNo", bankCardNo);*/
            ret = m.insert();
        }
        return ret;
    }

    public List<Map<String, String>> queryAllBorrowStatus(int status) throws Exception {
        return new Model("borrow_main").where("borrowStatus =  ?", status).select();
    }

    @Transactional(rollbackFor = Exception.class)
    public long updateBorrowBenJin(long bid, double benJin, double amount, String ip) throws SQLException {
        //记录repay_main
        Model m = new Model("repay_main");
        m.set("borrowId", bid);
        m.set("repayFee", 0);
        m.set("remainBenjin", 0);
        m.set("repayTime", DateUtil.getDateTime());
        m.set("ip", ip);
        m.set("amount", amount);
        return m.insert();

    }

    public Map<String, String> findLastPay(int bid) throws Exception {
        return new Model("repay_main").where("borrowId =  ?", bid).order("id desc").find();
    }

    public Map<String, String> findAppayOverdueLog(long bid) throws Exception {
        return new Model("overdue_record").where("borrow_id =  ?", bid).find();

    }

    public long updateOverdueFee(int bid, int aid, double overdueFee) throws SQLException {

        Model ma = new Model("borrow_main");
        ma.set("overdueFee", overdueFee);
        long ret = ma.update(bid);
        if (ret > 0) {
            Model m = new Model("overdue_record");
            m.set("overdueFee", overdueFee);
            ret = m.update(aid);
        }
        return ret;
    }

    public long updateBorrowOverfee(int rid, double afterFee) throws SQLException {
        Model ma = new Model("repay_main");
        ma.set("afterFee", afterFee);
        return ma.update(rid);

    }

    public Map<String, String> findBorrowByMemberAndStatus8_9(long mid) throws Exception {
       /* Map<String, String> bank = new Model("member_bank").where("member_id=? and status=1", mid).find();*/
        Map<String, String> map = new Model("borrow_main")
                .where(" (borrowStatus = 8 or borrowStatus = 9 ) and member_id =  ?", mid)
                .find();

        /*if (map != null && bank != null) {
            map.put("bankCardNo", bank.get("cardNo"));
        }
*/
        return map;
    }

    //根据id获取借款状态值
    public long getBorrowStatusById(long borrowId) throws Exception {
        Map<String, String> map = new Model("borrow_main")
                .field("borrowStatus")
                .where("id = ?", borrowId)
                .find();
        long borrowStatus = Long.valueOf(map.get("borrowStatus"));
        return borrowStatus;
    }

    //获取还未到还款期的借款(borrowStatus=1,2,4,5,6)
    public Map<String, String> getMyNotRepayBorrow(long mid) throws Exception {
       /* Map<String, String> bank = new Model("member_bank").where("member_id=? and status=1", mid).find();*/
        Map<String, String> map = new Model("borrow_main")
                .where(" borrowStatus in (1,2,4,5,6) and member_id =  ?", mid)
                .find();

        /*if (map != null && bank != null) {
            map.put("bankCardNo", bank.get("cardNo"));
        }*/

        return map;
    }

    public Map<String, String> findBorrowByMemberAndStatus(long mid, int status) throws Exception {
       /* Map<String, String> bank = new Model("member_bank").where("member_id=? and status=1", mid).find();*/
        Map<String, String> map = new Model("borrow_main").where("borrowStatus =  ? and member_id =  ?", status, mid).find();

       /* if (map != null *//*&& bank != null*//*) {
            map.put("bankCardNo"*//*, bank.get("cardNo")*//*);
        }*/

        return map;
    }

    public List<Map<String, String>> findBorrowByMemberAndStatusFinish(long mid, int status) throws Exception {
        return new Model("borrow_main").where("borrowStatus =  ? and member_id =  ?", status, mid).select();
    }


    public List<Map<String, String>> findFinisnBorrowByMemberAndStatus(long mid) throws Exception {
        return new Model("borrow_main").where("borrowStatus = 10 ? and member_id =  ?", mid).select();
    }


    public long updateBorrowBankCard(long borrowId, int borrowDate, String bankCardNo, int memberStatus, int flag, String project_id) throws SQLException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model ma = new Model("borrow_main");
        ma.set("bankCardNo", bankCardNo);
        if (flag == 1) {
            ma.set("borrowStatus", 5);
        }
        if (memberStatus == 1) {
            ma.set("borrowStatus", 8);
            ma.set("secondAuditTime", DateUtil.getDateTime());
            Date appiontDate = DateUtil.dateAddDay(now, borrowDate);
            ma.set("appointmentTime", DateUtil.dateToString(appiontDate));
            ma.set("projectid", project_id);
        }

        return ma.update(borrowId);
    }

   /* public Map<String, String> findBanCardByMemberId(long memberId) throws Exception {
        return new Model("bank").where("member_id = ? and status = 1", memberId).find();
    }*/

    public Map<String, String> findBanCardById(int id) throws Exception {
        return new Model("bank").where("id =  ?", id).find();
    }

    /*public long updateBankCardStatus(int bankCardID, int status) throws SQLException {
        Model ma = new Model("bank");
        ma.set("fuyouStatus", status);
        return ma.update(bankCardID);
    }*/

    public long insertFundRecord(long mid, long bid/*, String bankCardNo*/, String amount, String remark, String ip, int type) throws SQLException {
        Model ma = new Model("fund_record");
        ma.set("occurTime", DateUtil.getDateTime());
        ma.set("memberId", mid);
        ma.set("borrowId", bid);
        /*ma.set("bankCardNo", bankCardNo);*/
        ma.set("amount", amount);
        ma.set("remark", remark);
        ma.set("type", type);
        ma.set("ip", ip);
        return ma.insert();
    }

    @Transactional(rollbackFor = Exception.class)
    public long insertWithdrawCommision(long mid, double amounts, String remoteIp, double commisionSum/*, String cardNo*/) throws Exception {
        long ret = 0;
        Model ma = new Model("member");
        ma.set("occurTime", DateUtil.getDateTime());
        ma.set("commisionSum", commisionSum - amounts);
        ma.set("freezeCommision", amounts);
        ret = ma.update(mid);
        if (ret > 0) {
            Model xwc = new Model("withdraw_commision");
            xwc.set("time", DateUtil.getDateTime());
            xwc.set("mid", commisionSum - amounts);
            xwc.set("amount", amounts);
            /*xwc.set("cardNo", cardNo);*/
            xwc.set("status", 0);
            ret = xwc.insert();
        }
        return ret;
    }

    public long borrowOverdueHandles(Map<String, String> tempMap, int dayGap, double overdueFee, double amount, double remainfee) throws Exception {
        long ret = 0;
        Model mem = new Model("member");
        mem.set("overdueSum", (int) amount);//增加用户的统计数据
        if (dayGap > 0 && dayGap < 3) {
            mem.set("member_status", 2);
            mem.update(Convert.strToLong(tempMap.get("member_id"), 0));
        }
        if (dayGap >= 3) {
            mem.set("member_status", 3);
            mem.update(Convert.strToLong(tempMap.get("member_id"), 0));
        }
        Map<String, String> overMap = new Model("overdue_record").where("borrow_id = ?", Convert.strToLong(tempMap.get("id"), 0)).find();
        if (overMap == null) {
            Model xwc = new Model("overdue_record");
            xwc.set("beginTime", DateUtil.getDateTime());
            xwc.set("borrow_id", Convert.strToLong(tempMap.get("id"), 0));
            xwc.set("xinFee", Convert.strToLong(tempMap.get("xinFee"), 0));
            xwc.set("shouFee", Convert.strToLong(tempMap.get("shouFee"), 0));
            xwc.set("serviceFee", Convert.strToLong(tempMap.get("serviceFee"), 0));
            xwc.set("benjin", amount);
            xwc.set("overdueFee", overdueFee);
            xwc.set("remainFee", remainfee);
            xwc.set("overDays", dayGap);
            ret = xwc.insert();
        } else {
            Model xwc = new Model("overdue_record");
            xwc.set("overdueFee", overdueFee);
            xwc.set("overDays", dayGap);
            xwc.set("benjin", amount);
            xwc.set("remainFee", remainfee);
            xwc.set("updateTime", DateUtil.getDateTime());
            ret = xwc.update(Convert.strToLong(overMap.get("id"), 0));
        }
        return ret;

    }


    public Member findMember(long uid) throws Exception {
        return new Model("member").where("id = ?", uid).find(Member.class);
    }

    public List<Map<String, String>> queryRepayedBorrowByBid(int bid) throws Exception {
        return new Model("back_manul_log").where("borrowId = ?", bid).select();
    }

    public long updateRepayBankCard(long strToLong, String bankCardNo) throws SQLException {
        Model xwc = new Model("borrow_main");
        xwc.set("repayBankCardNo", bankCardNo);
        return xwc.update(strToLong);
    }

    public long updateBorrowSendMessage(int i, int borrowId) throws SQLException {
        Model ma = new Model("borrow_main");
        ma.set("isSendMessage", i);
        return ma.update(borrowId);
    }

    public Map<String, String> findBankByCode(String bankNo) throws Exception {
        return new Model("constant_bank").where("code = ?", bankNo).find();
    }

    public Map<String, String> findBorrowByOrderno(String orderno) throws Exception {
        //return new Model("borrow_main").where("fuyouOrderno = ?", orderno).find();
        return new Model("borrow_main").where("payOrderno = ?", orderno).find();
    }

    public Map<String, String> findProductByDayAndMount(int amount, int borrowDays) throws Exception {
        return new Model("borrow_product").where("amount = ? and borrowdays=?", amount, borrowDays).find();
    }

    public long cancelBorrowApply(long strToLong, int i) throws SQLException {
        Model ma = new Model("borrow_main");
        ma.set("isCancel", 1);
        ma.set("borrowStatus", 12);
        return ma.update(strToLong);
    }

    public void updateBorrowStatusByDaily(String benjin, String borrowDate, String bid, String bankCardNo
            , String member_id) throws Exception {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model ma = new Model("borrow_main");
        ma.set("borrowStatus", 8);
        ma.set("loanStatus", 2);
        //ma.set("secondAuditTime", sdf.format(now));
        ma.set("realLoanTime", sdf.format(now));

        //Date appiontDate=DateUtil.dateAddDay(now, Convert.strToInt(borrowDate, 0)-1);
        //ma.set("appointmentTime", DateUtil.dateToString(appiontDate));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, Convert.strToInt(borrowDate, 0));
        String dateBegin = DateUtil.YYYY_MM_DD.format(calendar.getTime());
        ma.set("appointmentTime", dateBegin);
        ma.update(Convert.strToInt(bid, 0));
        //记录放款记录
        /*Map<String, String> bankMap = findBanCardByMemberId(Long.valueOf(member_id));*/
        Model ms = new Model("fund_record");
        ms.set("occurTime", sdf.format(now));
        ms.set("memberId", Convert.strToInt(member_id, 0));
        ms.set("borrowId", Convert.strToInt(bid, 0));
       /* ms.set("bankCardNo", bankMap.get("cardNo"));*/
        ms.set("amount", benjin);
        ms.set("type", 1);
        ms.set("remark", "放款成功");
        ms.insert();


    }

    public void updateBorrowLoanStatus(int strToInt, int i, int j, String orderno) throws SQLException {
        Model m = new Model("borrow_main");
        m.set("borrowStatus", i);
        m.set("loanStatus", j);
        m.set("fuyouOrderno", orderno);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m.set("secondAuditTime", sdf.format(now));
        m.update(strToInt);
    }

    public long updateSecondBorrowStatus(int bid, long mid, int borrowDate, int benjin, String remoteIp, String project_id, Map<String, String> map) throws SQLException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model ma = new Model("borrow_main");
        ma.set("borrowStatus", 8);
        ma.set("loanStatus", 2);
        ma.set("realLoanTime", sdf.format(now));
        ma.set("projectid", project_id);
        Date appiontDate = DateUtil.dateAddDay(now, borrowDate);
        ma.set("appointmentTime", DateUtil.dateToString(appiontDate));
        ma.update(bid);
        //记录放款记录

        Model ms = new Model("fund_record");
        ms.set("occurTime", sdf.format(now));
        ms.set("memberId", mid);
        ms.set("borrowId", bid);
        /*ms.set("bankCardNo", map.get("cardNo"));*/
        ms.set("amount", benjin);
        ms.set("ip", remoteIp);
        ms.set("type", 1);
        ms.set("remark", "放款成功");
        return ms.insert();

    }

    public List<Map<String, String>> myNews() throws Exception {
        return new Model("cms_article").where("cate_id = ?", 2).select();
    }


}
