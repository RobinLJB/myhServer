package com.spark.p2p.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.p2p.entity.UcenterMember;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.spark.p2p.dao.MemberDao;
import com.spark.p2p.entity.Admin;
import com.spark.p2p.entity.CompanyProfile;
import com.spark.p2p.entity.member.Bank;
import com.spark.p2p.entity.member.Identity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.entity.PersonProfile;
import com.spark.p2p.exception.AuthenticationException;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.SecurityUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;
import com.sparkframework.sql.model.Model;
import com.sparkframework.util.BeanMapUtils;

@Service
public class MemberService {
    private static Log log = LogFactory.getLog(MemberService.class);
    @Autowired
    private MemberDao memberDao;

    Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Transactional(rollbackFor = Exception.class)
    public long login(String username, String password) throws Exception {
        long memberId = 0;
        long ret = 0;
        password = SecurityUtil.md5(password);
        Model model = new Model("ucenter_member");
        UcenterMember member = null;
        model.where(" mobile = ?and password = ?", username, password);
        member = model.find(UcenterMember.class);
        if (member == null) {
            return -1;
        }
        return member.getId();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param
     * @return memberId 用户中心编号
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public long spread(String username, String pwd, String ip, String reruestType, String requestCode) throws Exception {
        long ret = 0;
        long memberid = 0;
        pwd = SecurityUtil.md5(pwd);
        Member member = findMemberByPhonea(username);
        Member upmember = null;
        // 更新会员的推荐信息,个人推荐和团队推荐
        if (reruestType != null && requestCode != null) {
            if ("1".equals(reruestType)) {
                upmember = findMemberByRequestCode(requestCode);
            }

        }

        // 如果存在，只记录登陆
        // 如果不存在，记录登陆和注册会员
        if (member == null) {
            Model m = new Model("member");
            m.set("mobilePhone", username);
            m.set("username", username);
            m.set("create_ip", ip);
            m.set("memberNo", DateUtil.getDateYMD() + (System.currentTimeMillis() + "").substring(6, 12));
            ret = m.insert();
            logger.info("ret={}",ret);
         /* if ("1".equals(reruestType)) {
                m.set("refferee", upmember.getId());
            } else if ("2".equals(reruestType)) {
                m.set("jiLiangNo", requestCode);
            }
            m.set("create_time", DateUtil.getDateTime());
            ret = m.insert();
            // String context="恭喜您成为现金驿站会员，请尽快完成认证，谢谢您的关注！";
            // SMSUtil.sendContent(username,context );
            if (!ValidateUtil.isnull(reruestType)) {
                Model ms = new Model("member_ralation");
                if ("1".equals(reruestType)) {
                    upmember = findMemberByRequestCode(requestCode);
                    ms.set("memberId", upmember.getId());
                    ms.set("requestType", 1);
                } else {
                    ms.set("jiLiangNo", requestCode);
                    ms.set("requestType", 2);
                }
                ms.set("afterMemberId", ret);
                ms.set("invateIp", ip);
                ms.set("invateTime", DateUtil.getDateTime());
                ms.insert();
            }*/

            // 记录团队或者个人邀请的数量
               /* if ("1".equals(reruestType)) {
                    long upmid = upmember.getId();
                    Model msa = new Model("member");
                    msa.set("invateSum", upmember.getInviteSum());
                    msa.update(upmid);

                } else if ("2".equals(reruestType)) {
                    Map<String, String> map = findJiliangByNo(requestCode);
                    Model msa = new Model("jiliang_extension");
                    //msa.set("invitesum", Convert.strToInt(map.get("invitesum"), 0) + 1);
                    msa.update(Convert.strToLong(map.get("id"), 0));
                }*/

        } else {
            Model m = new Model("login_log");
            m.set("member_id", member.getId());
            m.set("login_ip", ip);
            m.set("login_time", DateUtil.getDateTime());
            ret = m.insert();
        }

        if (ret > 0) {
            Member members = findMemberByPhonea(username);
            memberid = members.getId();
        }
        return memberid;

    }

    /**
     * 用户登录成功后下载的统计
     *
     * @param username
     * @param
     * @return memberId 用户中心编号
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public long generalize(String username, String reruestType, String requestCode) throws Exception {
        long ret = 0;
        long memberid = 0;

        Member member = findMemberByPhonea(username);
        Member upmember = null;
        Model m = new Model("member");
        if ("1".equals(reruestType)) {
            m.set("refferee", upmember.getId());
        } else if ("2".equals(reruestType)) {
            m.set("jiLiangNo", requestCode);
        }
        m.set("create_time", DateUtil.getDateTime());
        m.where("username=?",username).update();
        if (!ValidateUtil.isnull(reruestType)) {
            Model ms = new Model("member_ralation");
            if ("1".equals(reruestType)) {
                upmember = findMemberByRequestCode(requestCode);
                ms.set("memberId", upmember.getId());
                ms.set("requestType", 1);
            } else {
                ms.set("jiLiangNo", requestCode);
                ms.set("requestType", 2);
            }
            ms.set("afterMemberId", member.getId());
            ms.set("invateTime", DateUtil.getDateTime());
            ms.set("invite_status",1);
            ms.insert();
        }
        if (ret > 0) {
            Member members = findMemberByPhonea(username);
            memberid = members.getId();
        }
        return memberid;

    }

    /**
     * 用户注册
     *
     * @param
     * @param password
     * @param phone
     * @param
     * @return
     * @throws AuthenticationException
     * @throws SQLException
     */
    @Transactional(rollbackFor = Exception.class)
    public long register(String phone, String password) throws Exception {
        password = SecurityUtil.md5(password);
        long memberId = memberDao.register(phone, password);
        if (memberId <= 0) {
            return -1;
        }
        // 添加会员账号
        long mid = memberDao.createMember(memberId, phone);
        return mid;
    }

    @Transactional(rollbackFor = Exception.class)
    public long createMemer(String phone, String password, long memberId) throws Exception {
        long mid = memberDao.spreadRegister(phone, password, memberId);
        return mid;
    }


    /**
     * 通过手机号和密码查询到memberNo
     */
    public String getMemberNo(String username) throws Exception {
        Map<String, String> map = new Model("ucenter_member").
                where(" mobile = ?", username).
                find();
        if (map == null) {
            return null;
        } else {
            return map.get("memberNo");
        }
    }

    /**
     * 通过手机号和密码查询到memberNo
     */
    public String getMemberNo(String username, String password) throws Exception {
        password = SecurityUtil.md5(password);
        Map<String, String> map = new Model("ucenter_member").
                where(" mobile = ?and password = ?", username, password).
                find();
        if (map == null) {
            return null;
        } else {
            return map.get("memberNo");
        }
    }

    /**
     * 通过memberNo查询到memberId信息
     */
    public String getMemberIdByMemberNo(String memberNo) throws Exception {
        return new Model("member").where("memberNo=?", memberNo).find().get("id");
    }

    /**
     * 更改用户密码
     *
     * @param pwd
     * @return
     * @throws SQLException
     */
    public long updatePwd(String pwd, String phone) throws SQLException {
        pwd = SecurityUtil.md5(pwd);
        return new Model("ucenter_member").where("mobile = ?", phone)
                .set("password", pwd)
                .set("update_time", DateUtil.getDateTime()).update();
    }

    /**
     * 检测用户标识是否存在
     *
     * @param
     * @return
     * @throws Exception
     */

    public boolean checkMemberIndentity(String identityId) throws Exception {
        Model user = new Model("member");
        return user.field("id").where("mobilePhone=? or email = ?", identityId, identityId).find() == null ? false
                : true;
    }

    /**
     * 查询银行卡
     * @return
     * @throws Exception
     */
    /*public List<Map<String,String>> queryBanks() throws Exception{
        return new Model("db_bank")
                .select();
    }*/

    /**
     * 根据会员标识查找会员ID
     *
     * @param identityId
     * @return
     * @throws Exception
     */
    public int findMemberId(String identityId) throws Exception {
        Model user = new Model("member");
        Map<String, String> map = user.field("id").where("mobilePhone=? or email = ?", identityId, identityId).find();
        if (map == null) {
            return -1;
        } else {
            return Convert.strToInt(map.get("id"), -1);
        }
    }

    /**
     * 查看实名认证状态
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public Map<String, String> findRealName(long uid) throws Exception {
        return new Model("member_realname_auth").where("uid = ?", uid).find();
    }

    public boolean isMemberExists(long uid) {
        return new Model("member").where("id = ?", uid).count() == 1;
    }

    public long setRoleStatus(long uid, int status) throws SQLException {
        Model m = new Model("member");
        m.where("id  = ?", uid);
        return m.setField("role", status);
    }

    /**
     * 更新交易密码
     *
     * @param uid
     * @param pwd
     * @return
     * @throws SQLException
     */
    public long updateDealpwd(long uid, String pwd) throws SQLException {
        pwd = SecurityUtil.md5(pwd);
        return new Model("member").where("id = ?", uid).setField("dealpwd", pwd);
    }

   /* @Transactional(rollbackFor = Exception.class)
    public long bindBankCard(long uid, String bankName, String branchName, String cardNo, String realName)
            throws SQLException {
        int count = new Model("member_bankcard").where("userId = ?", uid).count();
        long ret = -1;
        if (count > 0) {
            ret = memberDao.updateBankCard(uid, bankName, branchName, realName, cardNo);
        } else {
            ret = memberDao.createBankCard(uid, bankName, branchName, realName, cardNo);
        }
        return ret;
    }
*/

    /**
     * 查询用户资金
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public Map<String, Double> findMemberFinance(long uid) throws Exception {
        Map<String, String> map = new Model("member").field("freeze_balance as freezeSum,usable_balance as usableSum")
                .where("id = ?", uid).find();
        Map<String, String> dueinMap = new Model("loan_invest_repayment").field("sum(principal+interest) as dueinSum")
                .where("investor = ? and status = 1", uid).find();
        Map<String, Double> finance = new HashMap<String, Double>();
        map.putAll(dueinMap);
        finance.put("usableSum", Convert.strToDouble(map.get("usableSum"), 0));
        finance.put("freezeSum", Convert.strToDouble(map.get("freezeSum"), 0));
        finance.put("dueinSum", Convert.strToDouble(map.get("dueinSum"), 0));
        finance.put("total", finance.get("usableSum") + finance.get("freezeSum") + finance.get("dueinSum"));

        return finance;
    }

    /**
     * 检查交易密码是否正确
     *
     * @param uid
     * @param rawPwd
     * @return
     */
    public boolean checkDealpwd(long uid, String rawPwd) {
        String pwd = SecurityUtil.md5(rawPwd);
        return new Model("member").where("id = ? and dealpwd = ?", uid, pwd).count() == 1;
    }

    /**
     * 检查登录密码
     *
     * @param uid
     * @param rawPwd
     * @return
     * @throws Exception
     */
    public boolean checkPasswd(long uid, String rawPwd) throws Exception {
        String pwd = SecurityUtil.md5(rawPwd);
        Map<String, String> userMap = new Model("member").where("id = ?", uid).find();
        if (userMap == null) {
            return false;
        }
        long ucid = Convert.strToLong(userMap.get("id"), 0);
        Map<String, String> passMap = new Model("ucenter_member").where("id = ? and password = ?", ucid, pwd).find();
        if (passMap == null) {
            return false;
        } else
            return true;

    }


    /**
     * 修改登录密码
     *
     * @param uid
     * @param newPasswd
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public long updatePasswd(long uid, String newPasswd) throws Exception {
        String pwd = SecurityUtil.md5(newPasswd);
        Map<String, String> userMap = new Model("member").where("id = ?", uid).find();
        long ucid = Convert.strToLong(userMap.get("member_id"), 0);
        return new Model("ucenter_member").where("id = ?", ucid).setField("password", pwd);
    }

    public String getEmail(Long id) throws Exception {
        return new Model("member").field("email").where("id = ?", id).find().get("email");
    }

    public String getMobilePhone(long uid) throws Exception {
        return new Model("member").field("mobilePhone").where("id = ?", uid).find().get("mobilePhone");
    }

    public Map<String, String> findMemberBaseInfo(long uid) throws Exception {
        return new Model("member").field("id,username,mobilePhone,email").find(uid);
    }

    public Member findMember(long uid) throws Exception {
        return new Model("member").where("id = ?", uid).find(Member.class);
    }

    public Map<String, String> findBankCard(long uid) throws Exception {
        return new Model("member_bank").field("*").where("member_id = ? and status = 1", uid).find();
    }

    /**
     * 自动实名认证提交
     *
     * @param userid
     * @param realName
     * @param idNo
     * @param
     * @param
     * @return
     * @throws SQLException
     */
    @Transactional(rollbackFor = Exception.class)
    public long realNameAuth(long userid, String realName, String idNo, Integer checkMode) throws Exception {
        Model auth = new Model("member_realname_auth");
        auth.set("uid", userid);
        auth.set("realName", realName);
        auth.set("idCardNum", idNo);
        auth.set("submitTime", DateUtil.getDateTime());
        auth.set("status", 1);
        auth.set("checkMode", checkMode);
        auth.insert();
        Model m = new Model("member");
        m.set("real_name", realName);
        m.set("ident_no", idNo);
        return m.update(userid);
    }

    /**
     * 人工实名认证提交
     *
     * @param userid
     * @param realName
     * @param idNo
     * @param cardFrontImg
     * @param cardBackImg
     * @return
     * @throws SQLException
     */
    public long realNameAuthentication(long userid, String realName, String idNo, String cardFrontImg,
                                       String cardBackImg) throws SQLException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model m = new Model("member_realname_auth");
        m.set("uid", userid);
        m.set("realName", realName);
        m.set("cardFrontImg", cardFrontImg);
        m.set("cardBackImg", cardBackImg);
        m.set("idCardNum", idNo);
        m.set("submitTime", sdf.format(now));
        return m.insert();
    }

    /**
     * 修改绑定手机
     *
     * @param id
     * @param phone
     * @param username
     * @return
     * @throws SQLException
     * @throws DataException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long updateMobilePhone(Long id, String phone, String username) throws SQLException, DataException {
        Model m = new Model("member");
        m.set("mobilePhone", phone);
        long ret = m.update(id);
        if (ret > 0) {
            ret = DB.exec("UPDATE ucenter_member SET mobile=? WHERE username =?  LIMIT 1", phone, username);
        } else {
            ret = -1;
        }
        return ret;
    }

    /**
     * 查询用户实名认证信息
     *
     * @param
     * @return
     * @throws Exception
     */
    public Map<String, String> findMemberInfo(Long mid) throws Exception {
        return new Model("member_info").where("member_id = ? ", mid).find();
    }

    public Map<String, String> detailMemberInfo(Long uid) throws Exception {
        return new Model("member").field("detail").where("id = ?", uid).find();
    }

    /**
     * 安全中心首页
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, String> findSafetyInfo(Long id) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Member user = this.findMember(id);
        if (user != null && !user.equals("")) {
            map.put("mobilePhone", user.getMobilePhone());
            map.put("email", user.getEmail());
            map.put("real_name", user.getRealName());
            map.put("ident_no", user.getIdentNo());
        }
        /*Map<String, String> bankMap = new HashMap<String, String>();
        bankMap = this.findBankCard(id);
        if (bankMap != null && !bankMap.isEmpty()) {
            map.put("bank_card", bankMap.get("cardNo"));
        }*/
        return map;
    }

    /**
     * 查找托管账户
     *
     * @param id
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    public long queryRegIpsAcct(long id) throws NumberFormatException, Exception {
        return Long.parseLong(new Model("member").field("member_id").find(id).get("member_id").toString());
    }

    /**
     * 保存操作信息
     *
     * @param bizType(1注册 2充值 3提现)
     * @param userid
     * @param merBillNo
     * @param
     * @param json
     * @return
     * @throws SQLException
     */
    public long updateIpsRegOrder(long userid, String merBillNo, int userType, int bizType, String json)
            throws SQLException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        Model m = new Model("merchant_fundgw_log");
        m.set("userid", userid);
        m.set("merchant_bill_no", merBillNo);
        m.set("req_time", df.format(new Date()));
        m.set("userType", userType);
        m.set("biz_type", bizType);
        m.set("request", json);
        return m.insert();
    }

    /**
     * 操作成功更新开户记录
     *
     * @param bizType(1注册 2充值 3提现)
     * @param userid
     * @param ipsBillNo
     * @param status
     * @param ipsDoTime
     * @param response
     * @throws SQLException
     */
    public void updateIpsAcctNo(long userid, String ipsBillNo, int status, int bizType, String ipsDoTime,
                                JSONObject response) throws SQLException {
        Model m = new Model("merchant_fundgw_log");
        m.set("out_biz_no", ipsBillNo);
        m.set("resp_time", ipsDoTime);
        m.set("status", status);
        m.set("response", response);
        m.where("userid=? and biz_type=? ORDER BY `id` DESC LIMIT 1", userid, bizType).update();
    }

    /**
     * 查看开户,充值提现等信息 信息
     *
     * @param bizType(1注册 2充值 3提现)
     * @param userid
     * @return
     * @throws Exception
     */
    public Map<String, String> findIpsRegInfo(long userid, int bizType) throws Exception {
        Model m = new Model("merchant_fundgw_log");
        return m.where("userid=? and biz_type=? ORDER BY `id` DESC LIMIT 1", userid, bizType).find();
    }

    /**
     * 更新普通用户信息
     *
     * @param userid
     * @param realName
     * @param identNo
     * @throws SQLException
     */
    public void updatePersonRealName(long userid, String realName, String ipsAcctNo, String identNo)
            throws SQLException {
        Model m = new Model("member");
        m.set("real_name", realName);
        m.set("out_name", ipsAcctNo);
        m.set("role", 1);
        m.set("ident_no", identNo);
        m.update(userid);
    }

    /**
     * 更新企业用户信息
     *
     * @param userid
     * @param realName
     * @param identNo
     * @param enterName
     * @param orgCode
     * @throws SQLException
     */
    public void updateEnterpriseInfo(long userid, String realName, String ipsAcctNo, String identNo, String enterName,
                                     String orgCode) throws SQLException {
        Model m = new Model("member");
        m.set("real_name", realName);
        m.set("ident_no", identNo);
        m.set("out_name", ipsAcctNo);
        m.set("enterprise_name", enterName);
        m.set("enterprise_code", orgCode);
        m.set("role", 2);
        m.update(userid);
    }

    /**
     * 判断是否已经注册过
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean hasRegIpsAcct(long id) throws Exception {
        Model m = new Model("member");
        Map<String, String> map = new HashMap<String, String>();
        map = m.field("real_name").find(id);
        return map.get("real_name") != null ? true : false;
    }

    /**
     * 推荐关系表
     *
     * @param memberId
     * @param reffereeId
     * @throws SQLException
     */
    public void updateRelation(Long memberId, long reffereeId, int relation) throws SQLException {
        Model m = new Model("member_relation");
        m.set("member_id", memberId);
        m.set("referee_mid", reffereeId);
        m.set("relation", relation);
        m.insert();
    }

    public long updateReferee(long uid, long refereeId) throws SQLException {
        Model m = new Model("member");
        m.where("id = ?", uid);
        return m.setField("refferee", refereeId);
    }

    public long updateEmail(long id, String email) throws SQLException {
        Model m = new Model("member");
        m.set("email", email);
        return m.update(id);
    }

    /**
     * 查找用户直接推荐人
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public long findMemberReferee(long uid) throws Exception {
        Model m = new Model("member_relation");
        Map<String, String> map = m.field("referee_mid").where("member_id = ? and relation = 1", uid).find();
        if (map == null) {
            return -1;
        } else {
            return Convert.strToLong(map.get("referee_mid"), -1);
        }
    }

    public int findMemberByPhone(String phone) throws Exception {
        Model user = new Model("member");
        Map<String, String> map = user.field("id").where("mobilePhone=?", phone).find();
        if (map == null) {
            return -1;
        } else {
            return Convert.strToInt(map.get("id"), -1);
        }
    }

    /**
     * 更新用户信息
     *
     * @throws Exception
     */
    public long updateMemberInfo(long id, String profile) throws Exception {
        Model m = new Model("member");
        return m.where("id = ?", id).setField("detail", profile);
    }

    public Map<String, String> findMemberInfoAudit(long id) throws Exception {
        return new Model("member_workauth").where("memberId = ?", id).find();
    }

    public Map<String, String> findMemberCompany(long id) throws Exception {
        return new Model("member_company_audit").where("uid = ?", id).find();
    }

    // 企业新增
    public long insertMemberCompany(long id, CompanyProfile profile) throws SQLException {
        Model m = new Model("member_company_audit");
        m.set("company_name", profile.getCompany_name());
        m.set("registered_fund", profile.getRegistered_fund());
        m.set("address", profile.getAddress());
        m.set("license_no", profile.getLicense_no());
        m.set("license_address", profile.getLicense_address());
        m.set("license_expire_date", profile.getLicense_expire_date());
        m.set("business_scope", profile.getBusiness_scope());
        m.set("telephone", profile.getTelephone());
        m.set("email", profile.getEmail());
        m.set("organization_no", profile.getOrganization_no());
        m.set("summary", profile.getSummary());
        m.set("legal_person", profile.getLegal_person());
        m.set("cert_no", profile.getCert_no());
        m.set("legal_person_phone", profile.getLegal_person_phone());
        /*m.set("bank_code", profile.getBank_code());*/
        m.set("province", profile.getProvince());
        m.set("city", profile.getCity());
        /*m.set("bank_branch", profile.getBank_branch());*/
        /*m.set("bank_account_no", profile.getBank_account_no());*/
        m.set("assetyyzz", profile.getAssetyyzz());
        m.set("assetzzjgz", profile.getAssetzzjgz());
        m.set("assetswdjz", profile.getAssetswdjz());
        m.set("assetjsxkz", profile.getAssetjsxkz());
        m.set("assetfrzjz", profile.getAssetfrzjz());
        m.set("assetfrzjf", profile.getAssetfrzjf());
        m.set("uid", id);

        return m.insert();
    }

    // 企业修改
    public long updateMemberCompany(int ids, long id, CompanyProfile profile) throws SQLException {
        Model m = new Model("member_company_audit");
        m.set("company_name", profile.getCompany_name());
        m.set("registered_fund", profile.getRegistered_fund());
        m.set("address", profile.getAddress());
        m.set("license_no", profile.getLicense_no());
        m.set("license_address", profile.getLicense_address());
        m.set("license_expire_date", profile.getLicense_expire_date());
        m.set("business_scope", profile.getBusiness_scope());
        m.set("telephone", profile.getTelephone());
        m.set("email", profile.getEmail());
        m.set("organization_no", profile.getOrganization_no());
        m.set("summary", profile.getSummary());
        m.set("legal_person", profile.getLegal_person());
        m.set("cert_no", profile.getCert_no());
        m.set("legal_person_phone", profile.getLegal_person_phone());
        /*m.set("bank_code", profile.getBank_code());*/
        m.set("province", profile.getProvince());
        m.set("city", profile.getCity());
        /*m.set("bank_branch", profile.getBank_branch());
        m.set("bank_account_no", profile.getBank_account_no());*/
        m.set("assetyyzz", profile.getAssetyyzz());
        m.set("assetzzjgz", profile.getAssetzzjgz());
        m.set("assetswdjz", profile.getAssetswdjz());
        m.set("assetjsxkz", profile.getAssetjsxkz());
        m.set("assetfrzjz", profile.getAssetfrzjz());
        m.set("assetfrzjf", profile.getAssetfrzjf());
        m.set("uid", id);
        m.set("status", 0);
        return m.update(ids);
    }

    // 个人新增
    public long insertMemberInfoCheck(PersonProfile profile, long id) throws SQLException {
        Model m = new Model("member_workauth");
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m.set("memberId", id);
        m.set("realname", profile.getRealname());
        m.set("sex", profile.getSex());
        m.set("income", profile.getIncome());
        m.set("marry", profile.getMarry());
        m.set("education", profile.getEducation());
        m.set("workCity", profile.getProvince() + profile.getCity());
        m.set("industry", profile.getIndustry());
        m.set("jobs", profile.getJobs());
        m.set("hasHourse", profile.getHasHourse());
        m.set("hasCar", profile.getHasCar());
        m.set("birthday", profile.getBirthday());
        m.set("socialSecurity", profile.getSocialSecurity());
        m.set("submitTime", sdf.format(now));
        return m.insert();
    }

    // 个人修改
    public long updateMemberInfoCheck(PersonProfile profile, long id, long memberId) throws SQLException {
        Model m = new Model("member_workauth");
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m.set("memberId", memberId);
        m.set("realname", profile.getRealname());
        m.set("sex", profile.getSex());
        m.set("income", profile.getIncome());
        m.set("marry", profile.getMarry());
        m.set("education", profile.getEducation());
        m.set("workCity", profile.getProvince() + profile.getCity());
        m.set("industry", profile.getIndustry());
        m.set("jobs", profile.getJobs());
        m.set("hasHourse", profile.getHasHourse());
        m.set("hasCar", profile.getHasCar());
        m.set("birthday", profile.getBirthday());
        m.set("submitTime", sdf.format(now));
        m.set("auditStatus", 0);

        return m.update(id);
    }

    // 个人信用审核
    public Map<String, String> creditreview(long id) throws Exception {
        return new Model("member_creditreview").where("uid = ?", id).find();
    }

    // 个人信用审核新增
    public long insertcreditreview(long id, String idcard, String booklet, String earning, String credit, String house,
                                   String work) throws SQLException {
        Model m = new Model("member_creditreview");
        m.set("uid", id);
        m.set("idcard", idcard);
        m.set("booklet", booklet);
        m.set("earning", earning);
        m.set("credit", credit);
        m.set("house", house);
        m.set("work", work);
        m.set("role", 1);
        return m.insert();
    }

    // 个人信用审核修改
    public long updatecreditreview(long id, String idcard, String booklet, String earning, String credit, String house,
                                   String work) throws SQLException {
        Model m = new Model("member_creditreview");
        m.set("idcard", idcard);
        m.set("booklet", booklet);
        m.set("earning", earning);
        m.set("credit", credit);
        m.set("house", house);
        m.set("work", work);
        return m.where("uid = ?", id).update();
    }

    /**
     * 查询用户资金
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public Map<String, String> queryMemberFinance(long uid) throws Exception {
        return new Model("member_fundrecord").field("usableSum,freezeSum,dueinSum").order("id desc")
                .where("userId = ?", uid).find();
    }

    public String findMemberProfile(long uid) throws Exception {
        return new Model("member").where("id = ?", uid).field("detail").find().get("detail");
    }

    @Transactional
    public long loginWithGesture(String username, String gesture, String ip)
            throws SQLException, AuthenticationException {
        long memberId = -1;
        gesture = SecurityUtil.md5(gesture);
        Map<String, String> map = memberDao.loginWithGesture(username, gesture, ip);
        if (map != null && map.get("code") != null) {
            memberId = Convert.strToLong(map.get("code").toString(), -1);
        }
        if (memberId <= 0) {
            throw new AuthenticationException(map.get("msg"));
        }
        Model ucModel = new Model("ucenter_member");
        ucModel.set("last_login_time", DateUtil.getDateTime());
        ucModel.set("last_login_ip", ip);
        ucModel.update(memberId);
        return memberId;
    }

    /**
     * 检测用户名是否重复
     *
     * @param
     * @return
     * @throws Exception
     */
    public boolean checkUserPhone(String phone) throws Exception {
        Model user = new Model("member");
        return user.field("id").where("mobilePhone=?", phone).find() == null ? false : true;
    }

    public long updateUCMemberGesture(long mid, String gesture) throws SQLException {
        gesture = SecurityUtil.md5(gesture);
        Model m = new Model("ucenter_member");
        return m.where("id = ?", mid).setField("gesture_sign", gesture);
    }

    public long enableMemberGesture(long mid, int enableGesture) throws SQLException {
        return new Model("member").where("id = ?", mid).setField("enable_gesture", enableGesture);
    }

    public Map<String, String> findCompanyAudit(long uid) throws Exception {
        return new Model("member_company_audit").where("uid = ?", uid).find();
    }

    /**
     * 企业认证审核结果
     *
     * @param
     * @param
     * @throws SQLException
     */
    @Transactional(rollbackFor = Exception.class)
    public long companyAuditResult(String order_no, int status) throws Exception {
        Model m = new Model("member_company_audit");
        Map<String, String> companyMap = m.where("order_no=?", order_no).find();
        if (companyMap == null) {
            return -1;
        }
        long uid = Convert.strToLong(companyMap.get("uid"), 0);
        if (status == 2) {
            Model member = new Model("member");
            member.set("enterprise_name", companyMap.get("company_name"));
            member.set("enterprise_code", companyMap.get("license_no"));
            member.set("detail", companyMap.get("company_detail"));
            member.set("real_name", companyMap.get("legal_person"));
            member.set("ident_no", companyMap.get("cert_no"));
            member.update(uid);
        }
        return new Model("member_company_audit").where("order_no=?", order_no).setField("status", status);
    }

    public long updateMemberHeadImg(long mid, String headImg) throws SQLException {
        return new Model("member").where("id = ?", mid).setField("avatar", headImg);
    }

    /**
     * 根据微信OpenId查找用户
     *
     * @param wechatId
     * @return
     * @throws Exception
     */
    public Member findMemberByWechatId(String wechatId) throws Exception {
        return new Model("member").where("wechat_id = ?", wechatId).find(Member.class);
    }

    /**
     * 设置member wechat_id
     *
     * @param uid
     * @param wechatId
     * @return
     * @throws SQLException
     */
    public long updateMemberWechatId(long uid, String wechatId, String headImgUrl) throws SQLException {
        return new Model("member").where("id = ?", uid).set("headImgUrl", headImgUrl).set("wechat_id", wechatId)
                .update();
    }

    /**
     * @throws SQLException
     */
    public Member findMemberByPhonea(String phone) throws Exception {
        return new Model("member").where("mobilePhone=? or username=?", phone, phone).find(Member.class);
    }

    public Member findMemberByRequestCode(String requestCode) throws Exception {
        return new Model("member").where("memberNo=? ", requestCode).find(Member.class);
    }

    public Member findMemberByMemberNo(String memberNo) throws Exception {
        return new Model("member").where("memberNo=? ", memberNo).find(Member.class);
    }

    public long insertAdvisePlat(String content, long id) throws SQLException {
        long ret = 0;
        Model member = new Model("advise");
        member.set("content", content);
        member.set("member_id", id);
        member.set("submit_time", DateUtil.getDateTime());
        ret = member.insert();
        return ret;
    }

    public Map<String, String> findMemberIdentyByMemId(long mid) throws Exception {
        return new Model("identity_check").where("member_id=? ", mid).find();
    }

    public Identity findMemberIdentyByCardNo(String cardNo) throws Exception {
        return new Model("identity_check").where("card_no=? ", cardNo).find(Identity.class);
    }

    public Identity findMemberIdentyByMid(long mid) throws Exception {
        return new Model("identity_check").where("member_id=? ", mid).find(Identity.class);
    }

    /**
     * 首次提交----增加条记录 再次提交，修改记录 审核失败后，再次提交，修改记录
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public long updateXqdIdentityCheck(Identity identity, long memberId) throws Exception {
        long ret = 0;
        Identity identitya = findMemberIdentyByMid(memberId);
        if (identitya == null) {
            Model member = new Model("identity_check");
            member.set("member_id", memberId);
            member.set("real_name", identity.getRealName());
            member.set("card_no", identity.getCardNo());
            member.set("card_imgA", identity.getCardImgA());
            member.set("card_imgB", identity.getCardImgB());
            member.set("handle_img", identity.getHandleImg());
            member.set("qq_mail", identity.getQqMail());
            member.set("now_address", identity.getNowAddress());
            member.set("true_address", identity.getTrueAddress());
            member.set("submit_time", DateUtil.getDateTime());
            ret = member.insert();
        } else {
            Model member = new Model("identity_check");
            member.set("member_id", memberId);
            member.set("real_name", identity.getRealName());
            member.set("card_no", identity.getCardNo());
            member.set("card_imgA", identity.getCardImgA());
            member.set("card_imgB", identity.getCardImgB());
            member.set("handle_img", identity.getHandleImg());
            member.set("qq_mail", identity.getQqMail());
            member.set("now_address", identity.getNowAddress());
            member.set("true_address", identity.getTrueAddress());
            member.set("submit_time", DateUtil.getDateTime());
            ret = member.update(identitya.getId());
        }

        if (ret > 0) {
            Map<String, String> map = findMemberAuditChain(identity.getMemberId());
            if (map == null) {
                Model membera = new Model("audit_chain");
                membera.set("member_id", identity.getMemberId());
                membera.set("identity_status", 1);
                ret = membera.insert();
            } else {
                Model membera = new Model("audit_chain");
                membera.set("identity_status", 1);
                ret = membera.update(Convert.strToLong(map.get("id"), 0));
            }
        }

        Model mr = new Model("member");
        mr.set("real_name", identity.getRealName());
        mr.set("ident_no", identity.getCardNo());
        ret = mr.update(memberId);
        return ret;
    }

//	public Map<String, String> findMemberBank(long mid) throws Exception {
//		return new Model("bank").where("member_id=? ", mid).find();
//	}

    public Map<String, String> findMemberAuditChain(long mid) throws Exception {
        return new Model("audit_chain").where("member_id=? ", mid).find();
    }

    public List<Map<String, String>> listBank() throws Exception {
        return new Model("constant_bank").select();
    }


    @Transactional(rollbackFor = Exception.class)
    public long updateMemberDetail(long id, String qqno, String weino, String xueli, String name, String phone,
                                   String relation) throws Exception {
        long ret = 0;
        Model ma = new Model("member_info");
        ma.set("audit_time", DateUtil.getDateTime());
        ma.set("member_id", id);
        ma.set("qqno", qqno);
        ma.set("weino", weino);
        ma.set("xueli", xueli);
        ma.set("name", name);
        ma.set("phone", phone);
        ma.set("relation", relation);
        ret = ma.insert();

        Map<String, String> map = findMemberAuditChain(id);
        if (map == null) {
            Model m = new Model("audit_chain");
            m.set("member_id", id);
            m.set("self_info_status", 1);
            ret = m.insert();
        } else {
            Model m = new Model("audit_chain");
            m.set("self_info_status", 1);
            ret = m.update(Convert.strToLong(map.get("id"), 0));
        }

        return ret;
    }

    public Map<String, String> checkIdentityStatus(long mid) throws Exception {
        return new Model("audit_chain").where("member_id=? ", mid).find();
    }

    public long updateMobileIddentity(int id) throws SQLException {
        Model m = new Model("audit_chain");
        m.set("phone_status", 1);
        m.set("phone_audit_time", DateUtil.getDateTime());
        return m.update(id);
    }

    public long updatePhoneIdentity(long days, long mid, String id_card, String full_name, String phone)
            throws Exception {
        long ret = 0;
        Map<String, String> map = findMemberAuditChain(mid);
        if (map == null) {
            Model m = new Model("audit_chain");
            m.set("phone_status", 1);
            m.set("phone_use_month", days);
            m.set("phone_audit_time", DateUtil.getDateTime());
            m.set("member_id", mid);
            ret = m.insert();
        } else {
            Model m = new Model("audit_chain");
            m.set("phone_status", 1);
            m.set("phone_use_month", days);
            m.set("phone_audit_time", DateUtil.getDateTime());
            ret = m.update(Convert.strToLong(map.get("id"), 0));
        }
        if (findPhoneAudit(mid) == null) {
            Model mob = new Model("phone_audit");
            mob.set("mid", mid);
            mob.set("user_day", days);
            mob.set("id_card", id_card);
            mob.set("full_name", full_name);
            mob.set("audit_time", DateUtil.getDateTime());
            mob.set("phone", phone);
            mob.set("status", 1);
            ret = mob.insert();
        } else {
            Model mob = new Model("phone_audit");
            mob.set("mid", mid);
            mob.set("user_day", days);
            mob.set("id_card", id_card);
            mob.set("full_name", full_name);
            mob.set("audit_time", DateUtil.getDateTime());
            mob.set("phone", phone);
            mob.set("status", 1);
            ret = mob.update(Convert.strToInt(findPhoneAudit(mid).get("id"), 0));
        }

        return ret;

    }

    public long updateMemberStatus(int strToInt, int i) throws SQLException {
        Model m = new Model("member");
        m.set("member_status", i);
        return m.update(strToInt);
    }

//	public List<Map<String, String>> queryBankCardList(long id) throws Exception {
//		return new Model("bank").where("member_id=? and fuyouStatus=1", id).select();
//	}

    public List<Map<String, String>> queryPlace(int parent) throws Exception {
        return new Model("db_nation").where("parent= ?", parent).select();
    }

    public Map<String, String> findPhoneAudit(long mid) throws Exception {
        return new Model("phone_audit").where("mid= ?", mid).find();
    }

    // 通过memberId查询手机认证状态
    public long findPhoneAuditById(long mid) throws Exception {
        Map<String, String> map = findMemberAuditChain(mid);
        if (map == null) {
            Model m = new Model("audit_chain");
            m.set("phone_status", 0);
            m.set("member_id", mid);
            m.insert();
            return 0;
        } else {
            Map<String, String> map1 = new Model("audit_chain").field("phone_status").where("member_id = ?", mid)
                    .find();
            long phoneStatus = Long.valueOf(map.get("phone_status"));
            return phoneStatus;
        }
    }

    public Map<String, String> findMemberRelation(long mid) throws Exception {
        return new Model("member_ralation").where("afterMemberId= ?", mid).find();
    }

    public List<Map<String, String>> findMemberRelationByInviteId(long mid) throws Exception {
        return new Model("member_ralation").where("memberId= ?", mid).select();
    }

    @Transactional(rollbackFor = Exception.class)
    public long updateCommision(Member getMember, String jiLiangNo, Member member, Map<String, String> borrowMap,
                                long relationId) throws Exception {
        int commisionFee = Convert.strToInt(findConstantVariable("COMMISIONFEE").get("value"), 0);
        if (getMember != null) {
            Model m = new Model("member");
            m.set("commisionSum", getMember.getCommisionSum() + commisionFee);
            m.update(getMember.getId());

            Model ma = new Model("commision_record");
            ma.set("memberId", getMember.getId());
            ma.set("mid", member.getId());
            ma.set("amount", commisionFee);
            ma.set("occurTime", DateUtil.getDateTime());
            ma.insert();
        } else {
            Map<String, String> map = findJiliangByNo(jiLiangNo);
            Model m = new Model("jiliang_extension");
            m.set("successBorrowSum", Convert.strToInt(map.get("successBorrowSum"), 0) + 1);
            m.update(Convert.strToInt(map.get("id"), 0));

        }

        Model mas = new Model("member_ralation");
        mas.set("status", 1);
        return mas.update(relationId);

    }

    public Map<String, String> findJiliangByNo(String jiLiangNo) throws Exception {
        return new Model("jiliang_extension").where("onlyKey= ?", jiLiangNo).find();
    }

    public Map<String, String> findConstantVariable(String name) throws Exception {
        return new Model("constant_variable").where("name= ?", name).find();
    }

    public long updateMemberImg(long id, String imgpath) throws SQLException {
        Model ma = new Model("member");
        ma.set("memberImgPath", imgpath);
        return ma.update(id);
    }

    public long updateMemberBasic(long mid, String mobilePhone, int member_status) throws SQLException {
        Model ma = new Model("member");
        ma.set("mobilePhone", mobilePhone);
        ma.set("username", mobilePhone);
        ma.set("member_status", member_status);
        return ma.update(mid);
    }

//	public Map<String, String> findBankCardByCardNo(String bankCardNo) throws Exception {
//		return new Model("bank").where("cardNo= ?", bankCardNo).find();
//
//	}

//	public long updateMemberBankCard(int bankId, String bankCardNo, String bankName, String mobilePhone, long mid,
//			String city, String province, String bankNo, String cityNo, String branchBankName) throws SQLException {
//		Model member = new Model("bank");
//		member.set("member_id", mid);
//		member.set("cardNo", bankCardNo);
//		member.set("phone", mobilePhone);
//		member.set("province", province);
//		member.set("city", city);
//		member.set("bankNo", bankNo);
//		member.set("cityNo", cityNo);
//		member.set("branchBank", branchBankName);
//		member.set("bankName", bankName);
//		member.set("create_time", DateUtil.getDateTime());
//		return member.update(bankId);
//	}

    // 人脸识别
    @Transactional(rollbackFor = Exception.class)
    public long updateZmFaceStatus(int faceStatus, String bizNo, long memberId) throws Exception {
        Model m = new Model("audit_chain");
        try {
            m.set("face_status", 1);
            m.set("biz_no", bizNo);
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
            if (m.where("member_id=?", memberId).find() != null) {
                m.where("member_id=?", memberId).update();
            } else {
                m.set("member_id", memberId);
                m.insert();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;

    }

    @Transactional(rollbackFor = Exception.class)
    public long updateZhimaStatus(long mid, String openid, int socre) throws Exception {
        Model member = new Model("member");
        member.set("zhimaOpenid", openid);
        long ret = member.update(mid);

        Map<String, String> auditMap = findMemberAuditChain(mid);
        Model m = new Model("audit_chain");
        m.set("alipay_status", 1);
        m.set("alipay_score", socre);
        m.set("alipay_audit_time", DateUtil.getDateTime());
        ret = m.update(Convert.strToLong(auditMap.get("id"), 0));
        return ret;
    }

    public Map<String, String> findFristReviewn(long bid) throws Exception {
        return new Model("back_audit_log").where("borrow_id= ?", bid).find();
    }

    public Map<String, String> findOverdueByBid(int bid) throws Exception {
        return new Model("overdue_record").where("borrow_id= ?", bid).find();
    }

    public List<Map<String, String>> queryAllAuditCheck() throws Exception {
        return new Model("audit_chain").select();
    }

    public Map<String, String> findWithdrawStatusByMid(long mid) throws Exception {
        return new Model("withdraw_commision").where("mid= ? and status=0", mid).find();
    }

    public long insertApplyWithdrawRecoed(long mid, String ip) throws SQLException {
        Model m = new Model("withdraw_commision");
        m.set("mid", mid);
        m.set("applyTime", DateUtil.getDateTime());
        m.set("status", 0);
        return m.insert();
    }

    public long insertAdvise(long mid, String remoteIp, String details) throws SQLException {
        Model m = new Model("advise");
        m.set("member_id", mid);
        m.set("submit_time", DateUtil.getDateTime());
        m.set("status", 1);
        m.set("ip", remoteIp);
        m.set("content", details);
        return m.insert();
    }

    public long insertMobileAudit(String phoneToken, String sid, int status, long mid, String mobile)
            throws SQLException {
        Model m = new Model("phone_audit");
        m.set("token", phoneToken);
        m.set("sid", sid);
        m.set("status", status);
        m.set("mid", mid);
        m.set("phone", mobile);
        return m.insert();
    }

    public List<Map<String, String>> queryAllPhoneAudit() throws Exception {
        return new Model("phone_audit").select();
    }

    public long updateMobileAudit(String phoneToken, String sid, int status, long id, String mobilePhone)
            throws SQLException {
        Model m = new Model("phone_audit");
        m.set("token", phoneToken);
        m.set("sid", sid);
        m.set("status", status);
        m.set("mid", id);
        m.set("phone", mobilePhone);
        return m.insert();
    }

    public long updateMobileAudit(String phoneToken, String sid, int status, int strToInt, String mobilePhone)
            throws SQLException {
        Model m = new Model("phone_audit");
        m.set("token", phoneToken);
        m.set("sid", sid);
        m.set("status", status);
        m.set("phone", mobilePhone);
        return m.update(strToInt);
    }

    public long updateConstantByid(int strToInt, int fuyouNum) throws SQLException {
        Model m = new Model("constant_variable");
        m.set("value", fuyouNum);
        m.set("updateTime", DateUtil.getDateTime());
        return m.update(strToInt);
    }

    public Map<String, String> findCommicateById(long mid) throws Exception {
        return new Model("member").where("id= ?", mid).find();
    }

    public long updateMemberCommicate(long id, String detail) throws SQLException {
        try {
            Model m = new Model("member");
            detail = FileUtil.filterEmoji(detail);
            m.set("commicateDetail", detail);
            //加入通讯录上传状态的判断
            m.set("commicateStatus", 1);
            return m.update(id);
        } catch (Exception e) {
            log.info("updateMemberCommicate | commicateDetail=\n" + detail);
            e.printStackTrace();
        }
        return -1;
    }

    public long updateMemberPhoneRecordUrl(long id, String phoneRecordUrl) throws SQLException {
        Model m = new Model("member");
        m.set("phoneRecordUrl", phoneRecordUrl);
        return m.update(id);
    }

    public long updateMemberPhoneStatus(long id, String reportStatus) throws SQLException {
        Model m = new Model("member");
        m.set("reportStatus", reportStatus);
        return m.update(id);
    }

    public List<Map<String, String>> queryAreaProviceList() throws Exception {
        return new Model("db_nation").where("islimit=1 and parent=1").select();
    }

    public Map<String, String> memberByid(long id) throws Exception {
        return new Model("member").where("id=? ", id).find();
    }

    public void updateMemberBorrowStatistics(int mid, int alreadyBorrowSum, int alreadyRepaySum, int overdueSum,
                                             int dieSum) throws SQLException {
        Model m = new Model("member");
        m.set("alreadyBorrowSum", alreadyBorrowSum);
        m.set("alreadyRepaySum", alreadyRepaySum);
        m.set("overdueSum", overdueSum);
        m.set("dieSum", dieSum);
        m.update(mid);
    }

    public void updateMemberInvateorrowStatiscs(int upmid, int invateActive, int invateLoadsum, int invateRepaysum,
                                                int invateOverduesum, int invateDieSum) throws Exception {
        Map<String, String> map = memberByid(upmid);
        Model m = new Model("member");
        m.set("invateActive", Convert.strToInt(map.get("invateActive"), 0) + invateActive);
        m.set("invateLoadsum", Convert.strToInt(map.get("invateLoadsum"), 0) + invateLoadsum);
        m.set("invateRepaysum", Convert.strToInt(map.get("invateRepaysum"), 0) + invateRepaysum);
        m.set("invateOverduesum", Convert.strToInt(map.get("invateOverduesum"), 0) + invateOverduesum);
        m.set("invateDieSum", Convert.strToInt(map.get("invateDieSum"), 0) + invateDieSum);
        m.update(upmid);
    }

    public void updateJiliangBorrowStatiscs(int jiliangId, int successBorrowMemberSum, int successBorrowSum,
                                            int repaySum, int overdueSums, int dieSums) throws Exception {
        Map<String, String> map = findJiliangById(jiliangId);
        Model m = new Model("jiliang_extension");
        m.set("successBorrowMemberSum",
                Convert.strToInt(map.get("successBorrowMemberSum"), 0) + successBorrowMemberSum);
        m.set("successBorrowSum", Convert.strToInt(map.get("successBorrowSum"), 0) + successBorrowSum);
        m.set("repaySum", Convert.strToInt(map.get("repaySum"), 0) + repaySum);
        m.set("overdueSum", Convert.strToInt(map.get("overdueSum"), 0) + overdueSums);
        m.set("dieSum", Convert.strToInt(map.get("dieSum"), 0) + dieSums);
        m.update(jiliangId);

    }

    public Map<String, String> findJiliangById(long id) throws Exception {
        return new Model("jiliang_extension").where("id=? ", id).find();
    }

    public void updateMemberInviteDate(int strToInt, int activesum, int invateLoadsum, int invateRepaysum,
                                       int invateOverduesum, int invateDieSum) throws SQLException {
        Model m = new Model("member");
        m.set("invateActive", activesum);
        m.set("invateLoadsum", invateLoadsum);
        m.set("invateRepaysum", invateRepaysum);
        m.set("invateOverduesum", invateOverduesum);
        m.set("invateDieSum", invateDieSum);
        m.update(strToInt);

    }

    public void updateGroupInviteDate(int strToInt, int activesum, int invateLoadsum, int invateRepaysum,
                                      int invateOverduesum, int invateDieSum) throws SQLException {
        Model m = new Model("jiliang_extension");
        m.set("successBorrowMemberSum", activesum);
        m.set("successBorrowSum", invateLoadsum);
        m.set("repaySum", invateRepaysum);
        m.set("overdueSum", invateOverduesum);
        m.set("dieSum", invateDieSum);
        m.update(strToInt);

    }

    public void updatePhoneStatus(int strToInt, int i) throws SQLException {
        Model m = new Model("audit_chain");
        m.set("phone_status", i);
        m.update(strToInt);
    }

    public List<Map<String, String>> queryJiLiang() throws Exception {
        return new Model("jiliang_extension").select();
    }

    public List<Map<String, String>> queryMemberByJiliangNo(String onlyKey) throws Exception {
        return new Model("member").where("jiLiangNo=?", onlyKey).select();
    }

    public void updateJiliangBorrowDetail(int jiliangId, int successBorrowSum, int repaySum, int overdueSum, int dieSum)
            throws SQLException {
        Model m = new Model("jiliang_extension");
        m.set("successBorrowSum", successBorrowSum);
        m.set("repaySum", repaySum);
        m.set("overdueSum", overdueSum);
        m.set("dieSum", dieSum);
        m.update(jiliangId);
    }

    public void updateJiliangSuccessSum(int jiliangid, int successBorrowMemberSum) throws SQLException {
        Model m = new Model("jiliang_extension");
        m.set("successBorrowMemberSum", successBorrowMemberSum);
        m.update(jiliangid);
    }

    public void updateRelationStatus(int relationId) throws SQLException {
        Model m = new Model("member_ralation");
        m.set("status", 1);
        m.update(relationId);
    }

    public long addMemberBank(long member_id, String cardNo, String bankCode) throws Exception {

        Map<String, String> map = new Model("db_bank").where("bank_code=?", bankCode).find();
        String bankName = map.get("bank_name");
        logger.info("bankName:", bankName);
        log.info(bankName);
        if (bankName == null) {
            return -1;
        }
        Model m = new Model("member_bank");
        m.set("member_id", member_id);
        m.set("cardNo", cardNo);
        m.set("bank_code", bankCode);
        m.set("bank_name", bankName);
        m.set("status", 0);
        m.set("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        long ret = -1;
        if (m.where("member_id = ?", member_id).find() != null) {
            ret = m.where("member_id = ?", member_id).update();
        } else {
            ret = m.insert();
        }
        return ret;
    }
    /**
     * 有盾认证后保存用户邦卡信息及更新用户邦卡状态
     * @param member_id
     * @param cardNo
     * @param bankCode
     * @param bankName
     * @return
     * @throws Exception
     */
    public long addMemberBankYD(long member_id, String cardNo, String bankCode,String bankName ) throws Exception {
        Model m = new Model("member_bank");
        m.set("member_id", member_id);
        m.set("cardNo", cardNo);
        m.set("bank_code", bankCode);
        m.set("bank_name", bankName);
        m.set("status", 1);
        m.set("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        long ret = -1;
        if (m.where("member_id = ?", member_id).find() != null) {
            ret = m.where("member_id = ?", member_id).update();
        } else {
            ret = m.insert();
        }
        return ret;
    }
    public long updateMemberAgreeNo(long uid, String agreeNo) throws SQLException {
        new Model("member_bank").where("member_id = ?", uid).setField("status", 1);
        // 银行卡认证成功后，把 借款状态值status (4：初审成功) 变为 (5：等待复审)
        new Model("borrow_main").where("member_id=? and borrowStatus=4", uid).setField("borrowStatus", 5);
        return new Model("member").where("id = ?", uid).setField("agreeNo", agreeNo);
    }
    /**
     * 更新 实名认证状态(新)
     * @param memberId
     * @param identity
     * @return
     * @throws SQLException
     */
    public long updateIdentityStatus(Identity identity,long memberId) throws Exception {
        long ret = 0;
        Identity identitya = findMemberIdentyByMid(memberId);
        if (identitya == null) {
            Model member = new Model("identity_check");
            member.set("member_id", memberId);
            member.set("real_name", identity.getRealName());
            member.set("card_no", identity.getCardNo());
            member.set("card_imgA", identity.getCardImgA());
            member.set("card_imgB", identity.getCardImgB());
            member.set("handle_img", identity.getHandleImg());
            //member.set("qq_mail", identity.getQqMail());
            //member.set("now_address", identity.getNowAddress());
            member.set("true_address", identity.getTrueAddress());
            member.set("submit_time", DateUtil.getDateTime());
            ret = member.insert();
        } else {
            Model member = new Model("identity_check");
            member.set("member_id", memberId);
            member.set("real_name", identity.getRealName());
            member.set("card_no", identity.getCardNo());
            member.set("card_imgA", identity.getCardImgA());
            member.set("card_imgB", identity.getCardImgB());
            member.set("handle_img", identity.getHandleImg());
            //member.set("qq_mail", identity.getQqMail());
            //member.set("now_address", identity.getNowAddress());
            member.set("true_address", identity.getTrueAddress());
            member.set("submit_time", DateUtil.getDateTime());
            ret = member.update(identitya.getId());
        }

        if (ret > 0) {
            Map<String, String> map = findMemberAuditChain(memberId);
            if (map == null) {
                Model membera = new Model("audit_chain");
                membera.set("member_id", memberId);
                membera.set("identity_status", 1);
                ret = membera.insert();
            } else {
                Model membera = new Model("audit_chain");
                membera.set("identity_status", 1);
                ret = membera.update(Convert.strToLong(map.get("id"), 0));
            }
        }

        Model mr = new Model("member");
        mr.set("real_name", identity.getRealName());
        mr.set("ident_no", identity.getCardNo());
        ret = mr.update(memberId);
        return ret;
    }

    /**
     * 更新借款次数
     * @param member
     * @throws SQLException 
     */
	public void updateSuccessBorrowTimes(Member member) throws SQLException {
		// TODO Auto-generated method stub
		Model mr = new Model("member");
		mr.set("successBorrowTimes", member.getSuccessBorrowTimes());
		mr.update(member.getId());
	}
}
