package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service
public class IphoneAuthAdminService extends BaseService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private IphoneAuthService iphoneAuthService;
    @Autowired
    private BorrowAdminService borrowAdminService;

    public DataTable queryForIphoneAudit(DataTableRequest params, long roleid) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("icloudId", "like");
        String view = "(select   a.*,b.username,b.real_name,b.mobilePhone from iphone_auth_info as a, member as b where a.member_id=b.id ) as v_iphone_auth_info ";
        return pageEnableSearch(params, view, "*", map, "id desc");
    }


    public DataTable queryAppleList(DataTableRequest params, long roleid) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("real_name","like");
        map.put("mobile_phone","like");
        map.put("ident_no","like");
        map.put("apple_id", "like");
        map.put("apple_pass", "like");
        map.put("status", "=");
        return pageEnableSearch(params, "v_apple_id_pass", "*", map, "status asc");
    }

    //初审失败时更新苹果账号和密码的状态为0(未使用)
    @Transactional(rollbackFor = Exception.class)
    public long modityStatusById(String appleId,String applePass) throws SQLException {
        return new Model("apple_id_pass")
                .where("apple_id=? and apple_pass=?",appleId,applePass)
                .set("status",0).update();
    }

    //后台更新ID图片地址
    @Transactional(rollbackFor = Exception.class)
    public long updateIcloudAdress(String icloudAddress, String iphoneId)
            throws Exception {
        // 注意此处传来的Strbid和status是string，需转换成int

        int bid = Integer.valueOf(iphoneId).intValue();// 转化成int
        long ret = 0;
        Model m = new Model("iphone_auth_info");
        try {
            m.set("icloud_imgurl", icloudAddress);// 设置icloud的id信息
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
            if (m.where("id=?", bid).find() != null) {
                ret = m.where("id=?", bid).update();
                return ret;
            } else {
                m.set("id=", bid);
                ret = m.insert();
                return ret;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;

    }

    // 更新icloudId和icloud密码
    @Transactional(rollbackFor = Exception.class)
    public long updateIcloudStatus(String strId, String strStatus, String icloudId, String icloudPassword)
            throws Exception {
        // 注意此处传来的Strbid和status是string，需转换成int

        int bid = Integer.valueOf(strId).intValue();// 转化成int
        int status = Integer.valueOf(strStatus).intValue();

        Map<String, String> IphoneAuthMap = iphoneAuthService.findInfoById(bid);
        Long memberId = Long.valueOf(IphoneAuthMap.get("member_id")); // 获取memberId


        long ret = 0;
        Model m = new Model("iphone_auth_info");
        try {
            m.set("status", status);
            m.set("icloudId", icloudId);// 设置icloud的id信息
            m.set("icloudPass", icloudPassword);// 设置icloud的密码信息
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
            if (m.where("id=?", bid).find() != null) {
                ret = m.where("id=?", bid).update();
                return ret;
            } else {
                m.set("id=", bid);
                ret = m.insert();
                return ret;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;

    }

    // 复审，仅仅改变审核状态值
    @Transactional(rollbackFor = Exception.class)
    public synchronized long secondUpdateIcloudStatus(String strId, int status) throws Exception {
        // 注意此处传来的Strbid和StrStatus是string，需转换成int

        long ret;
        Model m = new Model("iphone_auth_info");
        int bid = Integer.valueOf(strId).intValue();// 转化成int

        Map<String, String> IphoneAuthMap = iphoneAuthService.findInfoById(bid);
        Long memberId = Long.valueOf(IphoneAuthMap.get("member_id")); // 获取memberId
        if (6 == (status)) {
            m.set("status", 2);
            if (m.where("id=?", bid).find() != null) {
                ret = m.where("id=?", bid).update();
            } else {
                m.set("id=", bid);
                ret = m.insert();
            }
            return ret;
        }
        if (7 == (status)) {
            m.set("status", -1);//后期如果需要区分初审失败还是复审失败，初审失败-1，复审失败-2
            if (m.where("id=?", bid).find() != null) {
                ret = m.where("id=?", bid).update();
            } else {
                m.set("id=", bid);
                ret = m.insert();
            }
            return ret;
        }
        return 0;
    }

    // 更改icloud密码
    @Transactional(rollbackFor = Exception.class)
    public long secondUpdateIcloudPasswordStatus(String strId, String strStatus, String icloudSecondPassword)
            throws Exception {
        // 注意此处传来的Strbid和status是string，需转换成int
        long ret = 0;
        Model m = new Model("iphone_auth_info");
        int bid = Integer.valueOf(strId).intValue();// 转化成int
        int status = Integer.valueOf(strStatus).intValue();
        try {
            m.set("status", status);
            m.set("secondIcloudPass", icloudSecondPassword);
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
            if (m.where("id=?", bid).find() != null) {
                ret = m.where("id=?", bid).update();
                return ret;
            } else {
                m.set("id=", bid);
                ret = m.insert();
                return ret;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;

    }

    //根据apple_id进行删除
    public long deleteInfoById(String appleId){
        return new Model("apple_id_pass").where("apple_id=?",appleId).delete();
    }

    @Transactional(rollbackFor = Exception.class)
    public long secondUpdateIcloudPassword(long bid, String icloudSecondPassword) throws Exception {
        // 注意此处传来的Strbid和status是string，需转换成int
        long ret = 0;
        Model m = new Model("iphone_auth_info");
        try {
            m.set("secondIcloudPass", icloudSecondPassword);
            if (m.where("member_id=? and iphone_key=0", bid).find() != null) {
                ret = m.where("member_id=? and iphone_key=0", bid).update();
                return ret;
            } else {
                m.set("member_id=? and iphone_key=0", bid).insert();
                return ret;
            }

        } catch (Exception e) {
        }
        return 0;

    }

}
