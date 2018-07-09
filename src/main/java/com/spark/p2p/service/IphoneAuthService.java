package com.spark.p2p.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spark.p2p.entity.Apple;
import com.spark.p2p.entity.BorrowAmount;
import com.spark.p2p.util.GeneratorUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.p2p.service.admin.BaseService;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.DB;
import com.sparkframework.sql.model.Model;

/**
 * iphone及icloud身份认证以及更新admin后台传入的auditId和auditPassword
 * 已经向前台传入后台已录入的随机账号和密码
 */

@Service
public class IphoneAuthService extends BaseService {

	public static final Log log = LogFactory.getLog(IphoneAuthService.class);
	
	/**
	 * 更新数据库中的苹果手机型号和内存信息
	 * @param uid,imgpath
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateIphoneTypeStorage(long uid,String type, int storage,String iphoneImg){
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		m.set("type", type);
		m.set("storage",storage);
		m.set("iphone_imgurl",iphoneImg);
		//不添加这句话无法存入member_id，有毒
		m.set("member_id",uid);

		try {
			/* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("member_id=? and iphone_key=0", uid).find() != null) {
				ret = m.where("member_id=? and iphone_key=0", uid).update();
				return ret;
			} else {
				ret = m.where("member_id=? and iphone_key=0", uid).insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 更新iphone的身份认证信息
	 *
	 * @param uid,imgpath
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateIphoneImg(long uid, String imgpath) throws Exception {

		Model m = new Model("iphone_auth_info");
		long ret = 0;
		m.set("iphone_imgurl", imgpath);// 传入图片地址
		m.set("status", 0);
		try {
			/* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("member_id=? and iphone_key=0", uid).find() != null) {
				ret = m.where("member_id=? and iphone_key=0", uid).update();
				return ret;
			} else {
				ret = m.where("member_id=? and iphone_key=0", uid).insert();
				return ret;
			}

		} catch (Exception e) {
			e.printStackTrace();
			DB.rollback();
		}

		return 0;
	}
	
	/**
	 * 根据路径和id查询iphone的身份认证信息
	 *
	 * @param uid,imgpath
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findIphoneImg(long uid, String imgpath) throws Exception {
		return new Model("iphone_auth_info").where("member_id=? and iphone_imgurl=?", uid, imgpath).find();
	}

	/**
	 * 根据id查询认证信息
	 *
	 * @param uid,imgpath
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findInfoById(long id) throws Exception {
		return new Model("iphone_auth_info").find(id);
	}

	public Map<String, String> getInfoByMemberId(long memberId) throws Exception {
		return new Model("iphone_auth_info").where("member_id=? and iphone_key=0", memberId).find();
	}

	// 通过id和借款状态查询
	public Map<String, String> getInfoByMemberIdAndStatus(long memberId) throws Exception {
		return new Model("iphone_auth_info").where("member_id=? and iphone_key=0", memberId).find();
	}
	// "id = ? and dealpwd = ?", uid, pwdv

	/**
	 * 更新icloud的身份认证信息
	 *
	 * @param uid,imgpath
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateIphoneKeyById(long uid) throws Exception {
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		m.set("member_id",uid);
		m.set("iphone_key",1);
		try {
			if (m.where("member_id=? and iphone_key=0", uid).find() != null) {
				ret = m.where("member_id=? and iphone_key=0", uid).update();
				return ret;
			} else {
				ret = m.where("member_id=? and iphone_key=0", uid).insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	//更新苹果账号的使用状态
	@Transactional(rollbackFor = Exception.class)
	public long updateIcloudImg(long uid, String imgpath) throws Exception {
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		m.set("icloud_imgurl", imgpath);// 传入图片地址
		m.set("member_id",uid);
		try {
			/* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("member_id=? and iphone_key=0", uid).find() != null) {
				ret = m.where("member_id=? and iphone_key=0", uid).update();
				return ret;
			} else {
				ret = m.where("member_id=? and iphone_key=0", uid).insert();
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 查询icloud的身份认证信息
	 *
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findIcloudImg(long uid, String imgpath) throws Exception {
		return new Model("iphone_auth_info").where("member_id=? and icloud_imgurl=?", uid, imgpath).find();
	}

	/*// 获取数据库中apple_id_pass其中一组数据作为账号和密码
	public Apple findApple() throws Exception {
		Apple apple;
		List<Apple> appleList = new Model("apple_id_pass").select(Apple.class);
		int j = (GeneratorUtil.getRandomNumber(0, appleList.size()-1));
		apple = appleList.get(j);
		return apple;
	}*/
// 获取数据库中apple_id_pass其中一组数据作为账号和密码,通过status判断其有没有被使用过
	public synchronized  Apple findApple() throws Exception {
		Apple apple;
		List<Apple> appleList = new Model("apple_id_pass").where("status=?",0).select(Apple.class);
		int j = (GeneratorUtil.getRandomNumber(0, appleList.size()-1));
		apple = appleList.get(j);
		long id=apple.getId();
		long ret=new  Model("apple_id_pass").set("status",1).update(id);
		return apple;
	}

   //通过内存大小和手机型号获得map
	public Map<String,String>  getMaxMoney(String type,int storage) throws Exception {
		return new Model("iphone_type_balance").where("type=? and storage=?",type, storage).find();
	}

	//通过金额获取所有的可借金额，利息和到账金额
	public List getBorrowMoneyDetail(List list) throws Exception {
		List list1=new ArrayList<>();
		for(int i=list.size()-1;i>=0;i--){
			log.info(list.get(i));
			String amount=(int)list.get(i)+"";
			list1.add(new Model("balance_service_paid").where("amount=?", amount).find(BorrowAmount.class));
		}
		return list1;
	}

	/**
	 * 更新id和password的信息
	 *
	 * @param auditId,tring
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateAuditIdAndAuditPassword(long uid, String auditId, String auditPassword,String status)  {
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		m.set("icloudId", auditId);// 传入前台传入的id
		m.set("icloudPass", auditPassword);// 传入前台传入的password
		if("3".equals(status)){//此处的status是前台审核的状态
			m.set("status", -1);
		}
		if("4".equals(status)){
			m.set("status",1);
		}
		try {
			/* ret=m.update(Convert.strToLong(auditMap.get("id"), 0)); */
			if (m.where("member_id=? and iphone_key=0", uid).find() != null) {
				ret = m.where("member_id=? and iphone_key=0", uid).update();
				return ret;
			} else {
				ret = m.where("member_id=? and iphone_key=0", uid).insert();
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 更新iPhone 序列号认证信息
	 * @param map
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public long updateSerialNumberByAuditId(Map<String,String> map)  {
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		try {
			m.set("serialNum", map.get("serialNum"));
			m.set("imeiNum", map.get("imeiNum"));
			m.set("status", 1);
			ret = m.update(Convert.strToLong(map.get("id"), 0));
			return ret;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 更新iPhone 序列号认证信息
	 * @param map
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public long insertSerialNumberByAuditId(Map<String,String> map)  {
		Model m = new Model("iphone_auth_info");
		long ret = 0;
		try {
			m.set("member_id", map.get("member_id"));
			m.set("serialNum", map.get("serialNum"));
			m.set("imeiNum", map.get("imeiNum"));
			m.set("status", 1);
			ret = m.insert();
			return ret;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
}
