package com.spark.p2p.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.p2p.util.SecurityUtil;
import com.sparkframework.sql.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.spark.p2p.util.DateUtil;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.Parameter;
import com.sparkframework.sql.ParameterDirection;
import com.sparkframework.sql.model.Model;


@Repository
public class MemberDao {
	

	//推广注册
	public long spreadRegister(String phone, String password,long memberId) throws Exception {
		Map<String,String> m=new Model("member").where("id=?",memberId).find();
		String memberNo=m.get("memberNo");
		password = SecurityUtil.md5(password);
		long ret= new Model("ucenter_member")
				.set("mobile", phone)
				.set("memberNo", memberNo)
				.set("password", password)
				.set("reg_time", DateUtil.getDateTime())
				.insert();
		return ret;
	}

	/**
	 * 用户注册
	 * @param
	 * @param password
	 * @param
	 * @return
	 */
	public long register(String phone, String password) throws SQLException {

		long ret= new Model("ucenter_member")
				.set("mobile", phone)
				.set("memberNo", DateUtil.getDateYMD() + (System.currentTimeMillis() + "").substring(6, 12))
				.set("password", password)
				.set("reg_time", DateUtil.getDateTime())
				.insert();
		return ret;
	}

	public long createMember(long ucId, String phone) throws Exception {
		Model um = new Model("ucenter_member");
		Map<String,String> map=um.where("mobile=?",phone).find();
		String memberNo=map.get("memberNo");
		Model m = new Model("member");
		m.set("id", ucId);
		m.set("memberNo", memberNo);
		m.set("mobilePhone", phone);
		m.set("create_time", DateUtil.getDateTime());
		return m.insert();
	}

	public long getMemberId(String phone) throws SQLException, DataException {
		String sql="select id from ucenter_member where mobile=?";
		List<Map<String, String>> list = DB.query(sql, phone);
		if (list.size() > 0) {
			Map map = list.get(0);
			String memberId = map.get("id").toString().trim();
	 		return Long.parseLong(memberId);
		}
		return -1;
	}
	
	
	/**
	 * 添加银行卡信息
	 * @param uid
	 * @param bankName
	 * @param branchName
	 * @param cardUserName
	 * @param cardNo
	 * @return
	 * @throws SQLException 
	 */
	public long createBankCard(long uid,String bankName,String branchName,String cardUserName,String cardNo) throws SQLException{
		Model m = new Model("member_bankcard");
		m.set("userId", uid);
		m.set("bankName", bankName);
		m.set("branchBankName", branchName);
		m.set("cardUserName", cardUserName);
		m.set("cardNo", cardNo);
		return m.insert();
	}
	
	public long updateBankCard(long uid,String bankName,String branchName,String cardUserName,String cardNo) throws SQLException{
		Model m = new Model("member_bankcard");
		m.set("bankName", bankName);
		m.set("branchBankName", branchName);
		m.set("cardUserName", cardUserName);
		m.set("cardNo", cardNo);
		return m.where("userId = ?",uid).update();
	}
	
	/**
	 * 手势登录
	 * @param username
	 * @param gesture
	 * @param ip
	 * @return
	 */
	public Map<String,String> loginWithGesture(String username,String gesture,String ip){
		Map<String,String> map = null;
		try {
			Parameter param1 = new Parameter(Types.VARCHAR,ParameterDirection.IN,username);
			Parameter param2 = new Parameter(Types.VARCHAR,ParameterDirection.IN,gesture);
			Parameter param3 = new Parameter(Types.VARCHAR,ParameterDirection.IN,ip);
			Parameter param4 = new Parameter(Types.INTEGER,ParameterDirection.INOUT,0);
			Parameter param5 = new Parameter(Types.VARCHAR,ParameterDirection.INOUT,"");
			Parameter[] params = new Parameter[]{param1,param2,param3,param4,param5};
			List<Object> out = DB.executeProcedure("p_member_login_with_gesture", params);
			if(out != null){
				map = new HashMap<String,String>();
				map.put("code", out.get(0).toString());
				map.put("msg", out.get(1).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
