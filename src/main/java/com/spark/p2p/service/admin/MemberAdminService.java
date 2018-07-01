package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.p2p.entity.BorrowMain;
import com.sparkframework.sql.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.p2p.dao.UserDao;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;
import com.sparkframework.util.BeanMapUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberAdminService extends BaseService {

    @Autowired
    private UserDao userDao;

    public Member findByMemberId(long memberId) throws Exception {
        return new Model("member").where("id = ?", memberId).find(Member.class);
    }

    public Map<String, String> findMemberById(long uid) throws Exception {
        return new Model("member").find(uid);
    }

    /**
     * 读取普通用户
     *
     * @param params
     * @return
     * @throws SQLException
     */
    public DataTable queryAllUser(DataTableRequest params, int status) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "=");
        map.put("username", "like");
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        map.put("member_status", "=");

        map.put("create_time", "between");
        String time = params.getSearchValue("create_time");
        if (time.length() > 13) {
            params.addColumn("min_create_time", time.substring(0, 10) + " 00:00:00");
            params.addColumn("max_create_time", time.substring(13) + " 23:59:59");
        }
        params.addColumn("member_status", status + "");
        return pageEnableSearch(params, "v_member_detail", "*", map, "create_time desc");
    }

    /**
     * 读取用户资料信息
     *
     * @param
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Map<String, String> findPerson(long uid) throws Exception {
        return new Model("member_person").where("uid = ?", uid).find();
    }

    /**
     * 查询用户信息，导出excel
     */
    public List<Map<String, String>> queryAllMemberExcel(String condition) throws Exception {
        return new Model("v_member_excel_detail").where(condition).order("create_time desc").select();
    }

    /**
     * 实名认证申请列表
     *
     * @param params
     * @param
     * @param
     * @param
     * @return
     * @throws SQLException
     */
    public DataTable queryRealnamelist(DataTableRequest params)
            throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "=");
        map.put("username", "like");
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        return pageEnableSearch(params, "identity_detail", "*", map, "submit_time desc");
    }

    /**
     * 实名认证申请资料详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, String> queryRealnameDetail(Integer id) throws Exception {
        return new Model("v_realname_audit").where("uid = ?", id).find();
    }

    /**
     * 删除member信息
     */
    @Transactional
    public long deleteMemberInfoById(long memberId) throws Exception {
        long ret,ret1,ret2, ret3,res, res2, res3, res4, res5, res6, res7, res8,res9 = 0;
        //查询所有某一个id的客户的所有借款订单编号
        List<BorrowMain> borrowList = new Model("borrow_main").where("member_id=?", memberId).select(BorrowMain.class);
        if (borrowList.size() > 0) {
            for (int i = 0; i <= borrowList.size() - 1; i++) {
                //删除后台借款记录
                ret = new Model("back_audit_log").where("borrow_id=?", borrowList.get(i).getId()).delete();
                //删除后台续期记录
                res = new Model("back_manul_log").where("borrow_id=?", borrowList.get(i).getId()).delete();
                res2 = new Model("renewal_record").where("borrowId=?", borrowList.get(i).getId()).delete();
                //删除后台借款记录
                res3 = new Model("borrow_main").where("id=?", borrowList.get(i).getId()).delete();
                //删除后台资金记录
                res4 = new Model("fund_record").where("borrowId=?", borrowList.get(i).getId()).delete();
                //删除后台逾期记录
                res5 = new Model("overdue_press_record").where("borrowId=?", borrowList.get(i).getId()).delete();
                res6 = new Model("overdue_record").where("borrowId=?", borrowList.get(i).getId()).delete();
                //删除用户还款记录表
                res7 = new Model("repay_main").where("borrowId=?", borrowList.get(i).getId()).delete();
            }
        }
        res8 = new Model("identity_check").where("member_id=?", memberId).delete();
        ret1 = new Model("iphone_auth_info").where("member_id=?", memberId).delete();
        //找到member对应的memberNo
        String memberNo = new Model("member").find(memberId).get("memberNo");
        ret2 = new Model("ucenter_member").where("memberNo=?", memberNo).delete();
        ret3=new Model("member").delete(memberId);
        return 1;
    }


    public Long realnameUpdate(Integer id, Integer status, String checkOpinion) throws SQLException {
        Model m = new Model("member_realname_auth");
        m.set("status", status);
        m.set("checkOpinion", checkOpinion);
        return m.update(id);
    }

    /**
     * 查询用户的资金记录，userid为空查询所有
     *
     * @param params
     * @param
     * @return
     * @throws SQLException
     */
    public DataTable queryFundList(DataTableRequest params, long mid) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (mid != -1) {
            sb.append(" mid = " + mid);
        }
        return page("v_member_fund_detail", "*", sb.toString(), "", params.getStart(), params.getLength());
    }


    public DataTable queryBorrowList(DataTableRequest params, long member_id) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        map.put("borrowStatus", "=");
        map.put("fristSubmitTime", "between");
        map.put("member_id", "=");
        params.addColumn("member_id", member_id + "");
        return pageEnableSearch(params, "borrow_main", "*", map, "id desc");

    }

    public DataTable queryMemberRelationList(DataTableRequest params, long mid) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (mid != -1) {
            sb.append(" memberId = " + mid);
        }
        return page("v_member_relation", "*", sb.toString(), "", params.getStart(), params.getLength());
    }


    public DataTable queryCommicateList(DataTableRequest params, long mid) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (mid != -1) {
            sb.append(" mid = " + mid);
        }
        return page("member_contact", "*", sb.toString(), "", params.getStart(), params.getLength());
    }


    public Map<String, String> queryMemberCompany(Integer id) throws Exception {
        return new Model("member_company_audit").where("id = ?", id).find();
    }

    public Long companyUpdate(Integer id, int status) throws SQLException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model m = new Model("member_company_audit");
        m.set("status", status);
        m.set("audit_time", sdf.format(now));
        return m.update(id);
    }

    public DataTable queryMemberInfo(DataTableRequest params) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("realname", "like");
        map.put("auditStatus", "=");
        return pageEnableSearch(params, "member_workauth", "*", map, "id desc");
    }

    public DataTable queryMemberConmpany(DataTableRequest params) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("company_name", "like");
        map.put("status", "=");
        return pageEnableSearch(params, "member_company_audit", "*", map, "id desc");
    }

    public Map<String, String> queryPersonInfo(Integer id) throws Exception {
        return new Model("member_workauth").where("id = ?", id).find();
    }

    public long personUpdate(int id, int status, String check_view) throws SQLException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Model m = new Model("identity_check");
        m.set("status", status);
        m.set("check_view", check_view);
        m.set("check_time", sdf.format(now));
        return m.update(id);
    }

    /**
     * 查看用户关系
     *
     * @param params
     * @return
     * @throws SQLException
     */
    public DataTable queryRelationList(DataTableRequest params) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        //map.put("musername", "like");
        //map.put("mreal_name", "like");
        //map.put("mphone", "like");
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        return pageEnableSearch(params, "member", "*", map, "");
    }

    public Map<String, String> findMemberByName(String username, String realname) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("  1=1 and  enable=1 ");
        if (!username.isEmpty()) {
            sb.append(" and `username` ='" + username + "' ");
        }
        if (!realname.isEmpty()) {
            sb.append(" and `real_name` ='" + realname + "' ");
        }
        return new Model("member")
                .field("role,real_name,ident_no,id,enterprise_code, email, mobilePhone,usable_balance,enterprise_name,username")
                .where(sb.toString()).find();
    }

    public Map<String, String> queryPhoneDetail(int uid) throws Exception {
        Model m = new Model("member");
        return m.where("id=?", uid).find();
    }


    public Map<String, String> queryIdentity(int id) throws Exception {
        return new Model("identity_detail").where("id = ?", id).find();
    }

    public Map<String, String> findMemberInfoByMid(long mid) throws Exception {
        return new Model("member_info").where("member_id = ?", mid).find();
    }

    public List<Map<String, String>> queryMemberCommicateByMid(long mid) throws Exception {
        return new Model("member_info").where("member_id = ?", mid).select();
    }

    public List<Map<String, String>> queryMemberInfo(long mid) throws Exception {
        return new Model("member_info").where("member_id = ?", mid).select();
    }

    public Map<String, String> findMemberAuditByMid(long mid) throws Exception {
        return new Model("audit_chain").where("member_id = ?", mid).find();
    }

    public Map<String, String> findMemberIdentityMid(long mid) throws Exception {
        return new Model("identity_check").where("member_id = ?", mid).find();
    }

    public List<Map<String, String>> queryMemberCommision(long mid) throws Exception {
        return new Model("commision_record").where("memberId = ?", mid).select();
    }

    public List<Map<String, String>> queryMemberPhoneTalkDetail1(long mid) throws Exception {
        return new Model("mobile_talk_detail").where("member_id = ?", mid).select();
    }

    public DataTable queryMemberPhoneTalkDetail(DataTableRequest params, long mid) throws SQLException {
        return page("mobile_talk_detail", "*", "member_id=" + mid, "", params.getStart(), params.getLength());

    }


    public DataTable queryCommicateList(DataTableRequest params) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        return pageEnableSearch(params, "member", "*", map, "");
    }


}
