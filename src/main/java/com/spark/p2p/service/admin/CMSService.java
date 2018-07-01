package com.spark.p2p.service.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.spark.p2p.entity.Pagination;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.DataException;
import com.sparkframework.sql.model.Model;

/**
 * 内容管理
 * 
 * @author caojian
 *
 */
@Service
public class CMSService extends BaseService {
	// 获取文章列表
	public DataTable queryByArticle(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "like");
		map.put("cate_id", "=");
		return pageEnableSearch(params, "cms_article", "*", map, "id desc");
	}
	
	
	public DataTable queryByMemberImg(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "like");
		return pageEnableSearch(params, "v_member_img_detail", "*", map, "id desc");
	}
	
	
	public DataTable queryAdvise(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		return pageEnableSearch(params, "v_advise_detail", "*", map, "id desc");
	}

	public long updateBannerA(String address){
		Model m=new Model("banner_img");
		long ret=0;
		try {
			m.set("path1", address);// 设置icloud的id信息
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("id=?", 1).find() != null) {
				ret = m.where("id=?", 1).update();
				return ret;
			} else {
				m.set("id=", 1);
				ret = m.insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	public long updateBannerB(String address){
		Model m=new Model("banner_img");
		long ret=0;
		try {
			m.set("path2", address);// 设置icloud的id信息
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("id=?", 1).find() != null) {
				ret = m.where("id=?", 1).update();
				return ret;
			} else {
				m.set("id=", 1);
				ret = m.insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	public long updateBannerC(String address){
		Model m=new Model("banner_img");
		long ret=0;
		try {
			m.set("path3", address);// 设置icloud的id信息
            /* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("id=?", 1).find() != null) {
				ret = m.where("id=?", 1).update();
				return ret;
			} else {
				m.set("id=", 1);
				ret = m.insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	// 删除文章
	public long deleteByArticleId(Integer id) throws SQLException, DataException {
		return new Model("cms_article").delete(id);
	}
	
	// 删除意见
	public long deleteByAdviseId(Integer id) throws SQLException, DataException {
		return new Model("advise").delete(id);
	}

	// 修改文章
	public long updateArticle(Integer id, String title, String summary, String cover, String type, String isShow,
			String sort, String content, String link) throws SQLException {
		Model m = new Model("cms_article");
		m.set("title", title);
		m.set("summary", summary);
		m.set("cover", cover);
		m.set("cate_id", type);
		m.set("sort", sort);
		m.set("content", content);
		m.set("link", link);
		return m.update(id);
	}

	// 添加文章
	public long addArticle(String title, String summary, String cover, String type, String isShow, String sort,
			String content, String link) throws SQLException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Model m = new Model("cms_article");
		m.set("title", title);
		m.set("summary", summary);
		m.set("cover", cover);
		m.set("cate_id", type);
		m.set("sort", sort);
		m.set("content", content);
		m.set("publishTime", sdf.format(now));
		m.set("link", link);
		return m.insert();
	}

	// 获取图片列表
	public DataTable queryByImage(DataTableRequest params) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cate", "=");
		return pageEnableSearch(params, "cms_image", "*", map, "publishTime desc ");
	}

	// 修改图片
	public long updateImage(Integer id, String title, String path, String cate, String sort, String link)
			throws SQLException {
		Model m = new Model("cms_image");
		m.set("title", title);
		m.set("path", path);
		m.set("cate", cate);
		m.set("sort", sort);
		m.set("link", link);
		return m.update(id);
	}

	// 添加图片
	public long addImage(String title, String path, String cate, String sort, String link) throws SQLException {
		Model m = new Model("cms_image");
		m.set("title", title);
		m.set("path", path);
		m.set("cate", cate);
		m.set("sort", sort);
		m.set("link", link);
		m.set("publishTime", DateUtil.getDateTime());
		return m.insert();
	}

	// 删除图片
	public long deleteByImageId(Integer id) throws SQLException, DataException {
		return new Model("cms_image").delete(id);
	}

	// 删除附件
	public long deleteByAttachId(Integer id) throws SQLException, DataException {
		return new Model("cms_attach").delete(id);
	}

	// 获取图片详情
	public List<Map<String, String>> queryByImageId(long id) throws SQLException, DataException {
		if (id == -1) {
			return null;
		}
		return DB.query("select * from cms_image where id = ?", id);
	}

	// 修改&保存站点信息
	public long updateSite(Integer id, String sitename, String companyname, String email, String address,
			String chargename, String contactname, String mobilephone, String telephone, String fax,
			String contactemail, String qq, String serverphone, String icpnumber, String domainname, String personnum,
			String completeamount, String platformname) throws SQLException {
		Model m = new Model("cms_site_information");
		m.set("siteName", sitename);
		m.set("companyName", companyname);
		m.set("postcode", email);
		m.set("address", address);
		m.set("principal", chargename);
		m.set("contact", contactname);
		m.set("telephone", mobilephone);
		m.set("cellphone", telephone);
		m.set("fax", fax);
		m.set("email", contactemail);
		m.set("qq", qq);
		m.set("servicePhone", serverphone);
		m.set("certificate", icpnumber);
		m.set("regionName", domainname);
		m.set("joinperson", personnum);
		m.set("totalmoney", completeamount);
		m.set("platformName", platformname);
		if (id == -1) {
			return m.insert();
		}
		return m.update(id);
	}

	public Map<String,String> getBannerImg() throws Exception {
		return  new Model("banner_img").where("status=1").find();
	}

	// 获取图片详情
	public List<Map<String, String>> queryBySite() throws SQLException, DataException {
		return DB.query("select * from cms_site_information");
	}

	// 信息管理列表
	public DataTable queryMessage(DataTableRequest params) throws SQLException {
		return page("cms_message", "*", "", " publishTime asc ", params.getStart(), params.getLength());
	}

	// 删除信息
	public long deleteByMessageId(Integer id) throws SQLException, DataException {
		return new Model("cms_message").delete(id);
	}

	// 信息详情
	public List<Map<String, String>> queryByMessageId(Integer id) throws SQLException, DataException {
		return DB.query("select * from cms_message where id = ?", id);
	}

	// 跟新&添加信息
	public long updateMessage(Integer id, String columName, String content, String sort, Integer typeId)
			throws SQLException {
		Model m = new Model("cms_message");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m.set("columName", columName);
		m.set("content", content);
		m.set("sort", sort);
		m.set("typeId", typeId);
		if (id == -1) {
			m.set("publishTime", sdf.format(now));
			return m.insert();
		}
		return m.update(id);

	}

	// 查看文章
	public Map<String, String> findArticle(long id) throws Exception {
			return new Model("cms_article").find(id);
	}
	/*上一篇文章*/
	public int artPrevious(long id,int type) throws Exception { 
		Map<String, String> map = new Model("cms_article").field("id")
				.where("id > ? and cate_id = ?", id,type).order("id asc").limit(1).find();
		if (map == null)
			return -1;
		else
			return Convert.strToInt(map.get("id"), -1);
}
	/*下一篇文章*/
	public int artNext(long id,int type) throws Exception {
		Map<String, String> map = new Model("cms_article").field("id")
				.where("id < ? and cate_id = ?", id,type).order("id desc").limit(1).find();
		if (map == null)
			return -1;
		else
			return Convert.strToInt(map.get("id"), -1);
}
	
	public List<Map<String, String>> queryAritcleByCate(Pagination page,long cid) throws Exception {
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		page.setTotalRecord(new Model("cms_article").where("cate_id = ?", cid).count());
		return new Model("cms_article").where("cate_id = ?", cid).order("id desc")
				.limit(start, page.getPageSize()).select();
	}

	public List<Map<String, String>> queryLastestArticle(long cid, int limit) throws Exception {
		if (limit == -1) {
			return new Model("cms_article").where("cate_id = ?", cid).order("id desc").select();
		} else {
			return new Model("cms_article").where("cate_id = ?", cid).limit(limit).order("id desc").select();
		}
	}

	public DataTable queryAttach(DataTableRequest params) throws SQLException {
		return page("cms_attach", "*", "", "publishTime desc ", params.getStart(), params.getLength());
	}

	public Map<String, String> findAttach(long id) throws Exception {
		if (id == -1) {
			return null;
		}
		return new Model("cms_attach").where("id = ?", id).find();
	}

	/**
	 * 更新附件
	 * 
	 * @param id
	 * @param title
	 * @param path
	 * @param filename
	 * @param sort
	 * @return
	 * @throws SQLException
	 */
	public long updateAttach(long id, String title, String path, String filename, String sort) throws SQLException {
		Model m = new Model("cms_attach");
		m.set("title", title);
		m.set("path", path);
		m.set("filename", filename);
		m.set("sort", sort);
		return m.update(id);
	}

	// 添加附件
	public long addAttach(String title, String path, String filename, String sort) throws SQLException {
		Model m = new Model("cms_attach");
		m.set("title", title);
		m.set("path", path);
		m.set("sort", sort);
		m.set("filename", filename);
		m.set("publishTime", DateUtil.getDateTime());
		return m.insert();
	}

	public List<Map<String, String>>  queryArctileByType(int type) throws Exception {
		return new Model("cms_article").where("cate_id = ?", type).order("id desc").select();
		
	}

	public Map<String, String> queryByMemberImageId(Integer id) throws Exception {
		return new Model("v_member_img_detail").where("id = ?", id).find();
	}

	

	

}
