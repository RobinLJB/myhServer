package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.spark.p2p.entity.Pagination;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.sparkframework.sql.model.Model;
import com.sparkframework.util.SqlInfusion;

public class BaseService {
	/**
	 * 分页读取
	 * @throws SQLException 
	 */
	public DataTable page(String table,String fields,String condition,String order,int start,int limit) throws SQLException{
		DataTable dt = new DataTable();
		Model m = new Model(table);
		try{
			int count = m.where(condition).count();
			dt.setRecordsTotal(count);
			dt.setRecordsFiltered(count);
			dt.setData(m.order(order).limit(start, limit).select());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public DataTable pageEnableSearch(DataTableRequest params,String table,String fields,Map<String,String> condiMap,String order) throws SQLException{
		List<Object> vals = new ArrayList<Object>();
		StringBuffer condition = new StringBuffer();
		condiMap.forEach((key,value)->{
			if(params.isSearchNotEmpty(key)){
				if(value.equalsIgnoreCase("between")){
					condition.append((vals.size()>0?" and ":"")+key +" between '"+ params.getSearchValue("min_"+key)+"' and '"+params.getSearchValue("max_"+key)+"' ");
				}else{
					condition.append(((vals.size()>0||condition.length()>0)?" and ":"") + key+" "+value+" ?");
				}
				if(value.equalsIgnoreCase("like")){
					vals.add("%"+params.getSearchValue(key)+"%");
				}
				else if(!value.equalsIgnoreCase("between")){
					vals.add(params.getSearchValue(key));
				}
			}
		});
		return page(table,fields,condition.toString(),order,params.getStart(),params.getLength(),vals);
	}
	
	public DataTable page(String table,String fields,String condition,String order,int start,int limit,Object ... params) throws SQLException{
		DataTable dt = new DataTable();
		Model m = new Model(table);
		try{
			if(params.length > 0){
				m.where(condition,params);
			}
			else m.where(condition);
			int count = m.count();
			dt.setRecordsTotal(count);
			dt.setRecordsFiltered(count);
			dt.setData(m.order(order).limit(start, limit).select());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public DataTable page(String table,String fields,String condition,String order,int start,int limit,List<Object> params) throws SQLException{
		DataTable dt = new DataTable();
		Model m = new Model(table);
		try{
			if(params.size() > 0){
				m.where(condition,params.toArray());
			}
			else m.where(condition);
			int count = m.count();
			dt.setRecordsTotal(count);
			dt.setRecordsFiltered(count);
			dt.setData(m.order(order).limit(start, limit).select());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	/**
	 * 分页：对查询结果无需处理时调用该方法
	 * @param page
	 * @param table
	 * @param fields
	 * @param condition
	 * @param order
	 * @throws SQLException
	 */
	public void fillPage(Pagination page,String table,String fields,String condition,String order) throws SQLException{
		Model m = new Model(table);
		try{
			int count = m.where(condition).count();
			int start = (page.getCurrentPage()-1)*page.getPageSize();
			page.setTotalRecord(count);
			page.setItems(m.order(order).limit(start, page.getPageSize()).select());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 分页：对返回结果需要进一步处理时调用该方法
	 * @param page
	 * @param table
	 * @param fields
	 * @param condition
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public List<Map<String,String>> PageList(Pagination page,String table,String fields,String condition,String order) throws SQLException{
		Model m = new Model(table);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();;
		try{
			int count = m.where(condition).count();
			int start = (page.getCurrentPage()-1)*page.getPageSize();
			page.setTotalRecord(count);
			//page.setItems(m.order(order).limit(start, page.getPageSize()).select());
			list = m.order(order).limit(start, page.getPageSize()).select();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			return list;
		}
	}	
	public void fillPage(Pagination page,String table,String fields,String condition,String order,Object ...params) throws SQLException{
		Model m = new Model(table);
		try{
			int count = m.where(condition,params).count();
			int start = (page.getCurrentPage()-1)*page.getPageSize();
			page.setTotalRecord(count);
			page.setItems(m.order(order).limit(start, page.getPageSize()).select());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void fillPage(Pagination page,String table,Class<?> bean,String condition,String order,Object ...params) throws SQLException{
		Model m = new Model(table);
		try{
			int count = m.where(condition,params).count();
			int start = (page.getCurrentPage()-1)*page.getPageSize();
			page.setTotalRecord(count);
			page.setItems(m.order(order).limit(start, page.getPageSize()).select(bean));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public <T> DataTable  page(String table, String fields, String condition, String order, int start, int limit,Class<T> entity)
			throws SQLException {
		DataTable dt = new DataTable();
		Model m = new Model(table);
		try {
			int count = m.where(condition).count();
			dt.setRecordsTotal(count);
			dt.setRecordsFiltered(count);
			m.order(order);
			if (limit > 0) {
				m.limit(limit);
				if (start > 0) {
					m.limit(start, limit);
				}
			}
			dt.setData(m.select(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}
}
