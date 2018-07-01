package com.spark.p2p.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.sql.model.Model;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("ucenterProfileService")
public class ProfileService  extends BaseService{
	public DataTable queryFriends(DataTableRequest params,long userid) throws SQLException{
		Map<String, String> map = new HashMap<String, String>();
		map.put("referee_mid", "=");
		params.addColumn("referee_mid", userid+"");
		return pageEnableSearch(params, "v_member_relation", "*", map, "id desc");
	}
	
	
	/**
	 * 企业证件审核查询
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, String>> querySelectOptionByGroup(String group) throws Exception {
		return new Model("sys_select_option").where("`group`=?",group).select(); 
	}
	/**
	 * 查看企业认证资料
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryMaterialsAuth(long id) throws Exception {
		List<Map<String, String>> list = new Model("member_materials_auth").where("uid=?",id).select();
		List<Map<String, String>> option =  querySelectOptionByGroup("CATE_MATERIALS");
		if(list.isEmpty()){
			return option;
		}		
		for(int i = 0;i<option.size();i++){
			Map<String, String> optionMap = option.get(i);
			list.parallelStream().forEach(map -> {
				if(optionMap.get("key").equals(map.get("auth_type_id"))){
					optionMap.put("materials_image", map.get("image"));
					optionMap.put("auditStatus", map.get("auditStatus"));
					optionMap.put("auditOpinion", map.get("auditOpinion"));
					optionMap.put("materialsId", map.get("id"));
					if("2".equals(map.get("auditStatus"))){
						if(!option.get(0).containsKey("flag")){
							option.get(0).put("flag", "1");
						}
					}
				}
			});
			Collections.replaceAll(option, option.get(i), optionMap);
		}
		return option;
	}
}
