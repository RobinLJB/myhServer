package com.spark.p2p.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.entity.Activity;
import com.spark.p2p.entity.Coupon;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.model.Model;

@Service
public class CouponService extends BaseService {


    public DataTable queryActivity(DataTableRequest params) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        map.put("adminUsername", "like");

        return pageEnableSearch(params, "member", "*", map, "id desc");
    }


    public DataTable querySubMemberDetail(DataTableRequest params, int mid) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        map.put("adminUsername", "like");
        map.put("mid", "=");
        params.addColumn("mid", mid + "");
        return pageEnableSearch(params, "v_subMember", "*", map, "id desc");
    }


    public Map<String, String> queryfeeConstant() throws Exception {
        return new Model("constant_variable").where("name= ?", "REGESITERURL").find();

    }


    public Map<String, String> findJiliangDetail(long key) throws Exception {
        return new Model("jiliang_extension").where("id = ?", key).find();
    }

    public Map<String, String> findJiliangBykey(String key) throws Exception {
        return new Model("jiliang_extension").where("onlyKey = ?", key).find();
    }

    public DataTable queryJiliangList(DataTableRequest params, long roleid, int jiliangId) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "like");
        map.put("id", "=");
        if (roleid == 26) {
            params.addColumn("id", "" + jiliangId);
        }
        return pageEnableSearch(params, "jiliang_extension", "*", map, "id desc");
    }


    public long insertJiliangExten(int jid, String name, String key, String username, String password, String phone, String qq,
                                   String review, int isShow, long aid, String url, String remoteIp) throws Exception {
        long ret = 0;

        Model model = new Model("admin");
        model.set("username", username);
        model.set("password", Encrypt.MD5(password + AppSetting.MD5_KEY));
        model.set("enable", isShow);
        model.set("roleId", 26);
        Map<String, String> map = model.where("username=?", username).find();
        if (map == null) {
            ret = model.insert();
        } else {
            ret = Long.parseLong(map.get("id"));
        }


        Model m = new Model("jiliang_extension");
        m.set("name", name);
        m.set("aid", ret);
        m.set("onlyKey", key);
        m.set("password", password);
        m.set("status", isShow);
        m.set("phone", phone);
        m.set("qq", qq);
        m.set("review", review);
        m.set("url", url);
        m.set("ip", remoteIp);
        m.set("createtime", DateUtil.getDateTime());
        if (jid > 0) {
            ret = m.update(jid);
        } else {
            ret = m.insert();
        }

        return ret;

    }


    public DataTable queryBorrowByStatusAndAdminId(DataTableRequest params, long upmemberid, int borrowStatus, int flag) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("real_name", "like");
        map.put("mobilePhone", "like");
        map.put("adminUsername", "like");
        map.put("upmemberid", "=");
        if (flag == 0) {
            map.put("borrowStatus", "=");
        } else {
            map.put("borrowStatus", ">=");
        }

        params.addColumn("upmemberid", upmemberid + "");
        params.addColumn("borrowStatus", borrowStatus + "");
        return pageEnableSearch(params, "v_submember_borrow_detail", "*", map, "id desc");
    }


    public DataTable queryActivityByjiLiang(DataTableRequest params, int jiliangID) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();

        map.put("create_time", "between");
        map.put("id", "=");
        map.put("real_name", "like");
    	 params.addColumn("id", jiliangID + "");
       
        String time = params.getSearchValue("create_time");
        
        if (time.length() > 13) {
            params.addColumn("min_create_time", time.substring(0, 10) + " 00:00:00");
            params.addColumn("max_create_time", time.substring(13) + " 23:59:59");
        }
        
        return pageEnableSearch(params, "v_jiliang_sub_detail", "*", map, " id desc");
    }

    /**
     * 查询平台活动
     *
     * @param
     * @throws Exception
     */


    public List<Activity> queryNormalActivity(int mode) throws Exception {
        Model m = new Model("activity");
        m.where("mode = ? and (no_expired = 1 or expired_time > ?) and `status` = 1", mode, DateUtil.getDateTime());
        return m.order("id desc").select(Activity.class);
    }

    /**
     * 查询活动下的卡券
     *
     * @param params
     * @param actId
     * @return
     * @throws SQLException
     */
    public DataTable queryActivityCoupon(DataTableRequest params, long actId) throws SQLException {
        return page("v_member_coupon", "*", "act_id = " + actId, "id desc", params.getStart(), params.getLength(), Coupon.class);
    }


    /**
     * 查找活动详情
     *
     * @param id 活动编号
     * @return
     * @throws Exception
     */
    public Activity findActivity(long id) throws Exception {
        return new Model("activity")
                .where("id = ?", id)
                .find(Activity.class);
    }

    /**
     * 添加活动
     *
     * @param act
     * @return
     * @throws SQLException
     */
    public long addActivity(Activity act) throws SQLException {
        Model m = new Model("activity");
        m.set("code_prefix", act.getCodePrefix());
        m.set("type", act.getType());
        m.set("face_value", act.getFaceValue());
        m.set("usable_period", act.getUsablePeriod());
        m.set("min_invest_amt", act.getMinInvestAmt());
        m.set("create_time", DateUtil.getDateTime());
        m.set("no_expired", act.getNoExpired());
        if (act.getNoExpired() == 0) {
            m.set("expired_time", act.getExpiredTime());
        }
        m.set("title", act.getTitle());
        m.set("status", act.getStatus());
        m.set("mode", act.getMode());
        return m.insert();
    }

    /**
     * 删除活动
     *
     * @param actId
     * @return
     */
    public long deleteActivity(long actId) {
        return new Model("activity")
                .where("id = ?", actId)
                .delete();
    }

    public long updateActivity(long actId, int status) throws SQLException {
        return new Model("activity")
                .where("id = ?", actId)
                .setField("status", status);
    }

    /**
     * 查找可用的卡券
     *
     * @param uid       用户id
     * @param investAmt 投资额度
     * @param type      卡券类型
     * @return
     * @throws Exception
     */
    public Coupon findAvailableCoupon(long uid, double investAmt, int type) throws Exception {
        return new Model("member_coupon")
                .where("uid = ? and min_invest_amt <= ? and type = ? and status = 1", uid, investAmt, type)
                .order("face_value desc")
                .find(Coupon.class);
    }

    public List<Coupon> queryCoupon(long uid, int status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("uid = " + uid);
        if (status >= 0) {
            sb.append("  and status=" + status);
        }
        return new Model("member_coupon")
                .where(sb.toString())
                .order("face_value desc")
                .select(Coupon.class);
    }

    public List<Coupon> queryMemberCoupon(long uid, int type) throws Exception {
        return new Model("member_coupon")
                .where("uid = ? and type = ? ", uid, type)
                .order("id desc")
                .select(Coupon.class);
    }

    public Coupon findCoupon(long id, long uid) throws Exception {
        return new Model("member_coupon")
                .where("id = ? and uid = ?", id, uid)
                .find(Coupon.class);
    }

    public long updateCouponStatus(long id, int status) throws SQLException {
        Model m = new Model("member_coupon")
                .where("id = ?", id);
        m.set("status", status);
        if (status == 2) {
            m.set("used_time", DateUtil.getDateTime());
        }
        return m.update();
    }


    public void queryMemberCoupon(long uid) {

    }

    /**
     * 发放单个卡券
     *
     * @param uid
     * @param act
     * @return
     * @throws SQLException
     */
    public long sendCoupon(long uid, Activity act) throws SQLException {
        Model m = new Model("member_coupon");
        m.set("uid", uid);
        m.set("type", act.getType());
        m.set("face_value", act.getFaceValue());
        m.set("create_time", DateUtil.getDateTime());
        m.set("min_invest_amt", act.getMinInvestAmt());
        m.set("act_id", act.getId());
        Calendar calander = Calendar.getInstance();

        calander.add(Calendar.DAY_OF_YEAR, act.getUsablePeriod());
        m.set("expired_time", DateUtil.YYYY_MM_DD_MM_HH_SS.format(calander.getTime()));
        long couponId = m.insert();
        String token = act.getCodePrefix() + couponId + GeneratorUtil.getNonceString(4);
        new Model("member_coupon").where("id = ?", couponId).setField("token", token);
        return couponId;
    }


    public Long deleteJiliang(Integer id) {
        return new Model("jiliang_extension").delete(id);
    }


    public Map<String, String> findActivityByAid(long aid) throws Exception {
        return new Model("jiliang_extension").where("aid=?", aid).find();
    }

}
