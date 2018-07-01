package com.spark.p2p.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spark.p2p.util.SecurityUtil;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;
import com.sparkframework.sql.model.Model;

/**
 * 用户中心
 * 
 * @author caojian
 */
@Service
public class HomeService {
	// 实名认证
	public Long realName(Long userid, String username, String idnumber) throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("member_realname_auth");
		m.set("uid", userid);
		m.set("realName", username);
		m.set("idCardNum", idnumber);
		m.set("status", 0);
		m.set("submitTime", sdf.format(now));
		return m.insert();
	}

	// 用户修改登录密码
	public Long updatePassword(Long uid, String password, String oldpassword) throws SQLException {
		password = SecurityUtil.md5(password);
		oldpassword = SecurityUtil.md5(oldpassword);
		return DB.exec("update ucenter_member set password = ? where id = ? and password = ?", password, uid,
				oldpassword);
	}

	// 用户修改交易密码
	public Long updateTransPassword(Long uid, String password, String oldpassword) throws SQLException {
		password = SecurityUtil.md5(password);
		oldpassword = SecurityUtil.md5(oldpassword);

		return DB.exec("update member set dealpwd = ? where member_id = ? and dealpwd = ?", password, uid, oldpassword);
	}

	// 用户绑定手机号
	public Long bindPhone(Long uid, String mobile) throws SQLException {
		Model m = new Model("ucenter_member");
		m.set("mobile", mobile);
		return m.update(uid);
	}

	// 添加银行卡
	public Long bindBank(Long uid, String username, String banknumber, String bankname, String bankaddress) throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("member_bankcard");
		m.set("userId", uid);
		m.set("bankName", bankname);
		m.set("branchBankName", bankaddress);
		m.set("cardNo", banknumber);
		m.set("cardUserName", username);
		m.set("cardStatus", "2");
		m.set("commitTime", sdf.format(now));
		return m.insert();
	}

	// 删除银行卡
	public Long deleteBank(Long uid, Integer bankid) throws SQLException {
		if (uid < 0) {
			return null;
		}
		return DB.exec("delete from member_bankcard  where id = ? and userId = ?", bankid, uid);
	}

	// 根据用户名获取银行卡列表
	public List<Map<String, String>> banklist(Long uid) throws SQLException, DataException {
		if (uid < 0) {
			return null;
		}
		return DB.query("select * from member_bankcard where userId = ?", uid);
	}
}
