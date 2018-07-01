package com.spark.p2p.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.entity.NoticeTpl;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.entity.SiteNotice;
import com.spark.p2p.service.admin.BaseService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.EmailUtil;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.sql.model.Model;


/**
 * 用户消息处理
 * @author yanqizheng
 *
 */
@Service
public class NoticeService extends BaseService {
 
	public static final Log log = LogFactory.getLog(NoticeService.class);
	
	public NoticeTpl findNoticeTpl(String name) throws Exception{
		return new Model("member_notice_tpl").where("name = ?",name).find(NoticeTpl.class);
	}
	/**
	 * 
	 * @param member
	 * @param tpl
	 * @param data
	 * @param type all:发送所有类型   sms:发送短信和站内信  email:发送邮件和站内信
	 * @return
	 * @throws Exception
	 */
	public long addNotice(Member member,NoticeTpl tpl,Map<String,String> data,String type) throws Exception{
		String smsContent = tpl.getSiteTpl();
		String siteContent = tpl.getSiteTpl();
		String emailContent = tpl.getEmailTpl();
		for(Entry<String,String> entry:data.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			log.info(key+"=>"+value);
			if(StringUtils.isNotBlank(smsContent)){
				smsContent = smsContent.replace("{"+key+"}", value);
			}
			if(StringUtils.isNotBlank(siteContent)){
				siteContent = siteContent.replace("{"+key+"}", value);
			}
			if(StringUtils.isNotBlank(emailContent)){
				emailContent = emailContent.replace("{"+key+"}", value);
			}
		}
		Model noticeModel = new Model("member_notice");
		noticeModel.set("uid", member.getId());
		noticeModel.set("site_content", siteContent);
		noticeModel.set("sms_content", smsContent);
		noticeModel.set("email_content", emailContent);
		noticeModel.set("email", member.getEmail());
		noticeModel.set("mobilePhone", member.getMobilePhone());
		noticeModel.set("add_time", DateUtil.getDateTime());
		if("sms".equals(type)||"all".equals(type)){
			//发送短信
			String smsRet =  SMSUtil.sendCRSMS(member.getMobilePhone(), smsContent);
			if("success".equals(smsRet)){
				noticeModel.set("sms_status", 1);
			}else{
				noticeModel.set("sms_status", 3);
			}
		}
		if("email".equals(type)||"all".equals(type)){
			//发送邮件
			if(StringUtils.isNotEmpty(member.getEmail())){
				EmailUtil emailUtil = new EmailUtil();
				emailUtil.sendEmail("会员消息通知", member.getEmail(), emailContent);
				noticeModel.set("email_status", 1);
			}else{
				noticeModel.set("email_status", 3);
			}
		}
		return noticeModel.insert();
	}
	
	public long addNotice(Member member,String tplName,Map<String,String> data,String type) throws Exception{
		NoticeTpl tpl = findNoticeTpl(tplName);
		if(tpl == null)return 0;
		else return addNotice(member,tpl,data,type);
	}
 
	public void addBatchNotice(List<Member> members,String tplName,List<Map<String,String>> datas) throws Exception{
		int index = 0;
		for(Member m :members){
			addNotice(m,tplName,datas.get(index),"all");
			index ++ ;
		}
	}
	
	/**
	 * 查找用户站内通知
	 * @param page
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public DataTable querySiteNotice(DataTableRequest params,long uid) throws SQLException{
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", "=");
		params.addColumn("uid", uid+"");
		return pageEnableSearch(params, "member_notice", "*", map, "id desc");
	}

	/**
	 * 手机端获取数据
	 */
	public List<Map<String, String>> querySiteNotice2(long uid) throws Exception{
		return new Model("member_notice").where("uid= ?",uid).order(" id desc ").select();
	}
	
	/**
	 * 查询是否有未读信息
	 */
	public int queryNewNotice(long uid) throws Exception{
		return new Model("member_notice").where("uid= ? and site_status = 0",uid).count();
	}
	
	/**
	 * 手机修改信息为已读状态
	 */
	public long updatSiteNotice(long uid) throws SQLException {
		Model m = new Model("member_notice");
		m.set("site_status", 1);
		return m.where("uid = ?", uid).update();
	}
}
