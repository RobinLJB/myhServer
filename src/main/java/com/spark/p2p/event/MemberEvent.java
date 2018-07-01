/**
 * 
 */
package com.spark.p2p.event;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.Activity;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.CouponService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.NoticeService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.model.Model;


/**
 * @author yanqizheng
 * 用户注册、登录、登出事件
 */
@Service
public class MemberEvent {
	@Autowired
	private MemberService memberService;

	@Autowired
	private CouponService couponService;
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 用户注册成功后处理
	 * @param memberId
	 * @param regTime
	 * @param regIp
	 * @throws Exception 
	 */
	public MessageResult onRegister(Member member,long reffereeId,String regIp) throws Exception{
		//新浪开户
		MessageResult result =null;// sinaPayService.createActiveMember(member.getId(),1,regIp);
		//1、发放活动红包、优惠券、体验金等卡券
		List<Activity> acts = couponService.queryNormalActivity(Const.COUPON_MODE_REG);
		double amount = 0;
		for(Activity act:acts){
			couponService.sendCoupon(member.getId(), act);
			amount+=act.getFaceValue();
		}
		
		//2、注册积分
		
		//更新推荐关系表,保留两级
		if (reffereeId>0) {
			memberService.updateReferee(member.getId(),reffereeId);
			memberService.updateRelation(member.getId(),reffereeId,1);
			/*long pReffereeId = memberService.findMemberReferee(reffereeId);
			if(pReffereeId > 0){
				memberService.updateRelation(member.getId(),pReffereeId,2);
			}*/
		}
		//3 注册成功通知
		Map<String,String> noticeMap = new HashMap<String,String>();
		noticeMap.put("amount", amount+"");
		noticeService.addNotice(member,"MEMBER_REG",noticeMap,"sms");
		return result;
	}
	
	
	
	/**
	 * 资金操作通知
	 * @param uid
	 * @param amount
	 * @throws Exception 
	 */
	public void onFinance(long uid, Double amount,String type) throws Exception {
		Member member = memberService.findMember(uid);
		//发送通知
		Map<String,String> noticeMap = new HashMap<String,String>();
		noticeMap.put("amount", amount+"");
		noticeMap.put("userName", member.getRealName());
		noticeService.addNotice(member,type,noticeMap,"sms");
	}
}
