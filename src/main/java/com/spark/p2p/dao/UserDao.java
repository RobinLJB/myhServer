package com.spark.p2p.dao;

import java.sql.Connection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sparkframework.sql.model.Model;

@Repository
public class UserDao {
	
	/**
	 * 根据member_id
	 * @param conn
	 * @param mid
	 * @return
	 * @throws Exception 
	 */
	public Map<String,String> findByMemberId(long mid) throws Exception{
		Model user = new Model("member");
		return user.field("*").where("id=?",mid).find();
	}
}
