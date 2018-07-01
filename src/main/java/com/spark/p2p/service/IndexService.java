package com.spark.p2p.service;

 
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
 
 
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.service.admin.BaseService;
 
import com.sparkframework.sql.DB;
 
import com.sparkframework.sql.Parameter;
import com.sparkframework.sql.ParameterDirection;
import com.sparkframework.sql.model.Model;

@Service
public class IndexService extends BaseService{
	// 获取首页图片轮播图
	public List<Map<String, String>> bannerlist(String cate,int num) throws Exception {
		if(num>0){
			return new Model("cms_image").where("cate=?", cate).limit(num).order("sort,id asc").select();
		}
		return new Model("cms_image").where("cate=?", cate).order("sort,id asc").select();
	}
	public Map<String, String> bannerList() throws Exception {
		return new Model("banner_img").where("status=?",1).find();
	}
	public List<Map<String, String>> investList2(int limit) throws Exception {
		return new Model("loan").where("status in(2,3,4,5) and display = 1").limit(limit).order("status").select();
	}

	public List<Map<String, String>> investList(int limit) throws Exception {
		return new Model("loan").where("status in(2,3,4,5)").limit(limit).order("status").select();
	}
	
	public List<Map<String, String>> LatestPJBorrow(Integer productType, int limit) throws Exception {
		if (productType == 0) {
			return new Model("loan").where("status in(2,3,4,5)").limit(limit).order("status").select();
		} else {
			return new Model("loan").where("status in(2,3,4,5) and loan_cate = ? ", productType).limit(limit)
					.order("status").select();
		}
	}

	public Map<String, String> indexTotal() {
		Map<String, String> ret = new HashMap<String, String>();
		try {
			Parameter param1 = new Parameter(Types.DECIMAL, ParameterDirection.OUT, 0);
			Parameter[] params = new Parameter[] { param1 };
			List<Object> out = DB.executeProcedure("p_platform_statistics", params);
			if (out != null) {
				ret.put("123", out.get(0).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 功德墙
	 * @param page
	 * @throws SQLException 
	 */
	public void queryMeritList(Pagination page) throws SQLException {
		fillPage(page,"v_member_merits","*","","id desc");
	}

	public Map<String,String> findImgByCate(String cate) throws Exception {
		return new Model("cms_image").where("cate=?",cate).order("id desc").find();
	}

}
