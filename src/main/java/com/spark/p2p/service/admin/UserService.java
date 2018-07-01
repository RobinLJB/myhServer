package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.p2p.dao.UserDao;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

@Service
public class UserService extends BaseService {
	
	@Autowired
	private UserDao userDao;
	
	public Member findByMemberId(long memberId) throws Exception{
		Member user = new Member();
		Map<String,String> userMap = userDao.findByMemberId(memberId);
		if(userMap != null){
			user.setId(Convert.strToLong(userMap.get("id"), -1));
			user.setUsername(userMap.get("username"));
		}
		return user;
	}
	
	public Map<String,String> find(long uid) throws Exception{
		return new Model("member").find(uid);
	}
	
	/**
	 * 读取普通用户
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryAllUser(DataTableRequest params) throws SQLException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", "like");
		map.put("realName", "like");
		map.put("mobilePhone", "like");
		return pageEnableSearch(params,"v_member_detail","*",map,"");
	}
	
	
	/**
	 * 读取交易中心s
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public DataTable queryAllTUcenter(DataTableRequest params) throws SQLException{
		return page("member_detail","*","role = 1","",params.getStart(),params.getLength());
	}
	
	public Map<String,String> findPerson(long uid) throws Exception{
		return new Model("member_person").where("userId = ?",uid).find();
	}
}
