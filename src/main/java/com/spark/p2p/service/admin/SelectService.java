package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.spark.p2p.entity.SelectOption;
import com.sparkframework.sql.model.Model;

@Service
public class SelectService extends BaseService {
	//取出数据
	public Map<String,String> getSelectMap(String selectKey) throws Exception{
		Map<String,String> ret = new Model("sys_select")
				.where("`key` = ?",selectKey)
				.find();
		if(ret == null) return null;
		
		String content = ret.get("options");
		JSONObject json = new JSONObject(content);
		Map<String,String> map = new HashMap<String,String>();
		/*for(String key:json.keySet()){
			map.put(key,json.get(key).toString());
		}*/
		
		return map;
	}
	
	
	//保存数据
	public int setSelectMap(String key,Map<String,String> cates) throws JSONException, SQLException {
		JSONArray json = new JSONArray(cates);
		System.out.println(json.get(0));
		Model m = new  Model("sys_select");
		return (int) m.where(" `key` = ?",key).setField("options",json.get(0));
	}
	
	
	/**
	 * 查询option数据
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public List<SelectOption> querySelectOption(String group) throws Exception{
		return new Model("sys_select_option")
				.where("`group` = ?",group)
				.order("sort asc,id asc")
				.select(SelectOption.class);
	}
	
	public Map<String,String> querySelectAsMap(String group) throws Exception{
		Map<String,String> map =new HashMap<String,String>();
		List<SelectOption> options = querySelectOption(group);
		for(SelectOption option:options){
			map.put(option.getKey(), option.getName());
		}
		return map;
	}
	
	public SelectOption findSelectOption(String group,String key) throws Exception{
		return new Model("sys_select_option")
				.where("`group` = ? and `key` = ?",group,key)
				.find(SelectOption.class);
	}
	
	public void processSelect(List<Map<String,String>> list,String group,String fieldName) throws Exception{
		Map<String,String> map = querySelectAsMap(group);
		for(Map<String,String> item:list){
			String key = item.get(fieldName);
			String value = map.containsKey(key)?map.get(key):"";
			item.put(fieldName+"_name", value);
		}
	}
}
