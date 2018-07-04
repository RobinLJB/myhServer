package com.spark.p2p.controller.app;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.service.IphoneAuthService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import com.spark.p2p.entity.BorrowMain;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.LimuAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.BorrowAdminService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.SMSUtil;
import com.spark.p2p.util.ValidateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.model.Model;


@Controller
@ResponseBody
@RequestMapping("/app/uc/borrow/")
public class AppBorrowController extends AppBaseController {

	private static Log log = LogFactory.getLog(AppIphoneAuthController.class);
	@Autowired
	private MemberService memberService;

	@Autowired
	private BorrowAdminService borrowAdminService;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private LimuAuthService limuAuthService;

	@Autowired
	private IphoneAuthService iphoneAuthService;
	/**
	 * 点击我要借款（底部按钮），判断进入那个页面
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "borrowPath", method = RequestMethod.POST)
	public @ResponseBody MessageResult borrowPath(HttpServletRequest request) throws Exception {
		Member member = getMember();
		
		MessageResult mr =new MessageResult();
		//判断是否有借款
		//判断条件：借款状态是未还款的，或者逾期的
		Map<String, String> map = borrowService.findHasNotGiveBorrow(member.getId());
		Map<String, String> resultMap=new HashMap<String, String>();
		
		List<Map<String, String>> list = borrowService.findBorrowByMemberAndStatusFinish(member.getId(),10);
		int borrowTimes = 0;
		if(list != null){
			borrowTimes = list.size();
		}
		
		resultMap.put("borrowTimes", "" + borrowTimes); 
		
		if(map == null) { 
			//查询是否有通过初审的借款
			Map<String, String> mapa=borrowService.findBorrowByMemberAndStatus(member.getId(),4);
			Map<String, String> mapc=borrowService.findBorrowByMemberAndStatus(member.getId(),2);
			Map<String, String> mapd=borrowService.findBorrowByMemberAndStatus(member.getId(),1);
			Map<String, String> mape=borrowService.findBorrowByMemberAndStatus(member.getId(),5);
			Map<String, String> mapf=borrowService.findBorrowByMemberAndStatus(member.getId(),6);
			if(mapd!=null){
				mr.setCode(1);
				resultMap.put("a_borrowId",mapd.get("id"));
				resultMap.put("a_msg","有提交申请的借款，等待审核");
			}
			if(mapc!=null){
				mr.setCode(2);
				resultMap.put("b_borrowId",mapc.get("id"));
				resultMap.put("b_msg","有正在审核的借款");
			}
			if(mapa!=null){
				mr.setCode(3);
				resultMap.put("c_borrowId",mapa.get("id"));
				resultMap.put("c_msg","有通过初审的借款");
			}
			if(mape!=null){
				mr.setCode(4);
				resultMap.put("e_borrowId",mape.get("id"));
				resultMap.put("e_msg","有等待复审的借款");
			}
			if(mapf!=null){
				mr.setCode(7);
				resultMap.put("f_borrowId",mapf.get("id"));
				resultMap.put("f_msg","有等待放款的借款");
			}
			
			if(mapa == null && mapf == null && mapc == null && mapd == null && mape == null ){
				mr.setCode(0);
				resultMap.put("null_msg","没有任何借款信息");
			}
			mr.setData(resultMap);
			return mr;
		} else { 
			//没有超过还款期限
			//显示剩余天数和要还的本金
			Map<String,String> normalBorrowMap=borrowService.findBorrowByMemberAndStatus(member.getId(),8);
			int benjin=0;
			double remainFee=0;
			if(normalBorrowMap!=null){
				mr.setCode(5);
				request.setAttribute("borrowId",normalBorrowMap.get("id"));
				//是否有部分还款
				Map<String,String> remainMap=borrowService.findLastPay(Convert.strToInt(normalBorrowMap.get("id"), 0));
				if(remainMap!=null){
					benjin=(int)Convert.strToDouble(remainMap.get("remainBenjin"), 0);
					remainFee=Convert.strToDouble(remainMap.get("remainFee"), 0);
					resultMap.put("benJin",remainMap.get("remainBenjin"));
				}else{
					benjin=Convert.strToInt(normalBorrowMap.get("benJin"), 0);
					int borrowDays = Integer.valueOf( normalBorrowMap.get("borrowDate") );
					
					DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
					double xinFee = 0;
					double shouFee = 0;
					double serviceFee =0;
					double otherFee = 0;  
					xinFee = Double.valueOf(df.format(xinFee));
					shouFee = Double.valueOf(df.format(shouFee));
					serviceFee = Double.valueOf(df.format(serviceFee));

					resultMap.put("xinFee",""+xinFee);
					resultMap.put("shouFee",""+shouFee);
					resultMap.put("serviceFee",""+serviceFee);
					resultMap.put("otherFee","0");

					remainFee=xinFee+shouFee+serviceFee+otherFee;
					resultMap.put("benJin",normalBorrowMap.get("benJin"));
				}
			
				int borrowDate=Convert.strToInt(normalBorrowMap.get("borrowDate"), 0);
				int addBorrowDay=Convert.strToInt(normalBorrowMap.get("addBorrowDay"), 0);
				String realLoanTime=normalBorrowMap.get("realLoanTime");
				//realLoanTime=realLoanTime.substring(0, 10)+" 00:00:00";
				Date startDay=DateUtil.strToDate(realLoanTime);
				int days=(int) DateUtil.diffDays(startDay,new Date());
				
				Date date=DateUtil.strToDate(realLoanTime);
				//Date appiontDate=DateUtil.dateAddDay(startDay, (addBorrowDay+borrowDate));
				//resultMap.put("appiontDate",DateUtil.dateToString(appiontDate));
				resultMap.put("appointmentTime",normalBorrowMap.get("appointmentTime"));
				resultMap.put("startDayLong",date.getTime()+"");
				resultMap.put("borrowId",normalBorrowMap.get("id"));
				resultMap.put("remainday",(borrowDate+addBorrowDay-days)+"");
				/*resultMap.put("bankCardNo",normalBorrowMap.get("bankCardNo"));*/
				resultMap.put("feetotal",""+remainFee);
				resultMap.put("realLoanTime",realLoanTime);
				resultMap.put("total",""+(benjin+remainFee));
				mr.setData(resultMap);
				
			}
			
			//逾期中                                            
			Map<String,String> overdueBorrowMap = borrowService.findBorrowByMemberAndStatus(member.getId(),9);
			if(overdueBorrowMap!=null) { 
				request.setAttribute("borrowId",overdueBorrowMap.get("id"));
				mr.setCode(6);
				int benjins=0;
				double totalfee=0;
				double overdueFee=0;
				request.setAttribute("borrowStatus", 9);
				int borrowDate=Convert.strToInt(overdueBorrowMap.get("borrowDate"), 0);
				int addBorrowDay=Convert.strToInt(overdueBorrowMap.get("addBorrowDay"), 0);
				String realLoanTime=overdueBorrowMap.get("realLoanTime");
				realLoanTime=realLoanTime.substring(0, 10)+" 00:00:00";
				Date startDay=DateUtil.strToDate(realLoanTime);
				int days=(int) DateUtil.diffDays(startDay,new Date());
				//Date appiontDate=DateUtil.dateAddDay(startDay, (addBorrowDay+borrowDate));
				//resultMap.put("appiontDate",DateUtil.dateToString(appiontDate));
				resultMap.put("appiontDate",overdueBorrowMap.get("appointmentTime"));
				resultMap.put("overduedays",(days-addBorrowDay-borrowDate)+"");
				/*resultMap.put("bankCardNo",overdueBorrowMap.get("bankCardNo"));*/
				resultMap.put("secondAuditTime",overdueBorrowMap.get("secondAuditTime"));
				Map<String,String> feeMap=borrowService.findAppayOverdueLog(Convert.strToInt(overdueBorrowMap.get("id"), 0));
				if(feeMap!=null){
					overdueFee=Convert.strToDouble(feeMap.get("overdueFee"), 0);
					resultMap.put("overdueFee",feeMap.get("overdueFee"));
				}else{
					overdueFee=0;
					resultMap.put("overdueFee","0");
				}
				double xinFee=0;
				double shouFee=0;
				double otherFee=0;
				double serviceFee=0;
				
				resultMap.put("benJin",feeMap.get("benjin"));
				resultMap.put("xinFee",xinFee+"");
				resultMap.put("shouFee",shouFee+"");
				resultMap.put("serviceFee",serviceFee+"");
				resultMap.put("otherFee",otherFee+"");
				resultMap.put("feetotal",feeMap.get("remainFee"));
				resultMap.put("total",""+((int)Convert.strToDouble(feeMap.get("benjin"), 0)+overdueFee));
				mr.setData(resultMap);
			}
			
			return mr;
		}
	}


	/**
	 *进入系统时，通过前端app抓取到的手机型号和内存，金额和利息等信息的返回
	 */
	@RequestMapping(value="checkIphone",method = RequestMethod.POST)
	public @ResponseBody MessageResult getAmoutByIphoneTypeStorage() throws Exception {
		long memberId=this.getMember().getId();
		String type=request("type");
		//设备获取到的真实容量
		String trueStorage=request("storage");
		//判断传入的手机型号是不是符合要求
		Boolean flag=Arrays.asList(AppSetting.ALLOW_IPHONE_LIST).contains(type);
		log.info(flag);
		if(!flag){
			return MessageResult.error(500,"目前仅支持iphone6及以上机型");
		}
		//排除掉不能借款的手机型号之后根据现在的手机型号和内存去查询最大借款金额
		//需要对手机内存进行处理，因为获取到的存储值可能不是整数,需要通过估计数值进行判断内存大小
		int standardizeStorage=getStandardizeStorage(Double.parseDouble(trueStorage));
		//将手机容量和类型存入到数据库中，便于初审（实际发现还是最后借款的时候把型号传过来最为稳妥）
		/*long ret=iphoneAuthService.updateIphoneTypeStorage(type,standardizeStorage,memberId);
		if(ret<0){
			return error("数据保存失败");
		}*/
		Map<String,String> map = iphoneAuthService.getMaxMoney(type,standardizeStorage);
		double maxMoney=Double.valueOf(map.get("amount"));

		//获取到最大金额之后再获取到其余低于该金额的数组
		int allowAmountList[]=AppSetting.ALLOW_AMOUNT_LIST;

		//获取小于该金额的list集合
		List list=new ArrayList();
		for(int i=0;i<allowAmountList.length-1;i++){
			if(allowAmountList[i]<=maxMoney){
				list.add(allowAmountList[i]);
			}
		}
		log.info(list);

		//通过金额去查询可借金额和利息和到账金额
		List borrowAmountList=iphoneAuthService.getBorrowMoneyDetail(list);

		log.info(borrowAmountList);
		return success(borrowAmountList);
	}

	//将传入的不标准的存储值转换为标准值
	public int getStandardizeStorage(double storage){
		int standardizeStorage=0;
		//暂时容量不小于8G
		if(storage<=8){
			return standardizeStorage=0;
		}else if(storage>8&&storage<=16){
			return  standardizeStorage=16;
		}else if(storage>16&&storage<=32){
			return  standardizeStorage=32;
		}else if(storage>32&&storage<=64){
			return  standardizeStorage=64;
		}else if(storage>64&&storage<=128){
			return  standardizeStorage=128;
		}else if(storage>128&&storage<=256){
			return  standardizeStorage=256;
		}
		//暂时手机还没有超过256G的，后期容量可能有超过的再添加
		else{
			return 0;
		}
	}

	/**
	 * 在点击借款金额的时候，要判断初审条件是否符合（包括手机型号是否满足要求）
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "beforeBorrowCheck", method = RequestMethod.POST)
	public @ResponseBody MessageResult sendCheckCode() throws Exception {
		long memberId = this.getMember().getId();
		String type=request("type");//手机型号
		return this.borrowService.borrowCheck(memberId,type);
	}
	
	/**
	 * 计算借款的相关费用
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "calculateFee", method = RequestMethod.POST)
	public @ResponseBody MessageResult calculateFee() throws Exception {
		int amount = Convert.strToInt(request("amount"), 0);
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		if(amount==0 ){
			return error("请输入借款金额");
		}
		if(borrowDays==0){
			return error("请输入借款天数");
		}
		//计算相关费用
		DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
		double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (amount/100) * borrowDays;
		double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (amount/100) * borrowDays;
		double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (amount/100) * borrowDays;
		double otherFee = 0;  
		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
		
		MessageResult mr=new MessageResult();
		Map<String, String> map=new HashMap<String,String>();
		map.put("xinFee",""+xinFee);
		map.put("shouFee",""+shouFee);
		map.put("serviceFee",""+serviceFee);
		map.put("otherFee",""+otherFee);
		mr.setCode(0);
		mr.setData(map);
		return mr;
	}
	
	
	/**
	 * 提交借款的申请
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "submitBorrow", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public @ResponseBody MessageResult submitBorrow() throws Exception {
		log.info("submitBorrow==============>>>>>start");
		//判断是否有等待审核的借款
		Member member = getMember();
		long memberId = member.getId();
		MessageResult mr = new MessageResult();
		String type=request("type");
		String trueStorage=request("storage");
		mr = this.borrowService.borrowCheckIphone(memberId);

		log.info("borrowService.borrowCheckIphone：{}"+JSONObject.toJSONString(mr));
		if(mr.getCode() != 0){
			return mr;
		}
		/*if (ValidateUtil.isnull(request("imgpath"))) {
			return error("请先上传图片");
		}*/
		// 不上传图片序列号,认证通过 authSerialNumberAndIMEI
		String imgpath = request("imgpath");

		int standardizeStorage=getStandardizeStorage(Double.parseDouble(trueStorage));
		Map<String,String> map2=borrowService.iphoneTypeStorageCheck(type,standardizeStorage);
		log.info("borrowService.iphoneTypeStorageCheck：{}"+JSONObject.toJSONString(mr));
		if(map2==null){
			return error("手机型号暂不支持，请联系客服");
		}

		mr = this.borrowService.borrowCheck(memberId,type);
		log.info("borrowService.borrowCheck：{}"+JSONObject.toJSONString(mr));
		if(mr.getCode() != 0){
			return mr;
		}
		//此处主要是插入一个空的苹果的内存和型号信息
		long ret=iphoneAuthService.updateIphoneTypeStorage(member.getId(),type,standardizeStorage,imgpath);
		//获取插入之后的苹果账号Map,取出其中的iphoneId
		Map<String,String> iphoneMap=iphoneAuthService.getInfoByMemberIdAndStatus(memberId);
		log.info("ret：{}"+ret+"borrowService.borrowCheck：{}"+JSONObject.toJSONString(iphoneMap));
		String iphoneId=iphoneMap.get("id");
		if(ret<0){
			return MessageResult.error(500,"订单提交失败");
		}
		/*秒易花金额固定，不用从前台传值回来
		int amount = Convert.strToInt(request("amount"), 0);
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		int serviceAmount=Convert.strToInt(request("serviceAmount"),0);*/
		Map<String,String> map = iphoneAuthService.getMaxMoney(type,standardizeStorage);
		if(map==null){
			return error("金额获取失败");
		}
		int amount=Double.valueOf(map.get("amount")).intValue();
		int serviceAmount=Double.valueOf(map.get("service_amount")).intValue();
		int borrowDays = Convert.strToInt(request("borrowDays"), 0);
		/*if(amount==0){
			return error("请输入借款金额");
		}
		if(borrowDays==0){
			return error("请输入借款周期");
		}*/

		log.info("开始生成借款订单：map{}"+JSONObject.toJSONString(map));
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		double xinFee = 0;
		double shouFee = 0;
		double serviceFee = serviceAmount;
		double otherFee = 0;

		xinFee = Double.valueOf(df.format(xinFee));
		shouFee = Double.valueOf(df.format(shouFee));
		serviceFee = Double.valueOf(df.format(serviceFee));
		
		String borrowNo="MYXD"+DateUtil.getDateYMD()+(System.currentTimeMillis()+"").substring(6, 12);
		ret=borrowService.insertBorrowApply(member.getId(),iphoneId,borrowNo,xinFee,shouFee,serviceFee,
				otherFee,amount,borrowDays,member.getMemberStatus());
		if(ret > 0){
			if("4".equals(borrowService.findBorrowById(ret).get("borrowStatus"))){
				//SMSUtil.sendContent(member.getMobilePhone(), "你的借款编号"+borrowNo+"已自动通过初审，请完成后续认证");
				String content = SMSUtil.autotrialBorrow(borrowNo);
				SMSUtil.sendContent(member.getMobilePhone(), content);
			}else{
				//SMSUtil.sendContent(member.getMobilePhone(), "你的借款编号"+borrowNo+"已提交到后台初审，请耐心等待");
				String content = SMSUtil.subtrialBorrow(borrowNo);
				SMSUtil.sendContent(member.getMobilePhone(), content);
			}
			memberService.updateMemberStatus((int)member.getId(), 1);
			mr.setCode(0);
			mr.setData(ret);
			mr.setMessage("提交借款申请成功");
		} else {
			mr.setCode(-1);
			mr.setMessage("提交借款申请失败");
		}
		log.info("submitBorrow==============>>>>>end:mr{}"+JSONObject.toJSONString(mr));
		return mr;
	}
	
	
	/**
	 * 点击申请借款后，查询借款的审核状态
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "queryBorrowStatus", method = RequestMethod.POST)
	public @ResponseBody MessageResult queryBorrowStatus() throws Exception {
		Member member = getMember();
		
		String borrowIds = request("borrowId");
		if(ValidateUtil.isnull(borrowIds)){
			return error("请输入借款编号");
		}
		int borrowId = Convert.strToInt(borrowIds, 0);
		Map<String, String> statusMap=borrowService.findBorrowById(borrowId);
		int borrowStatus=Convert.strToInt(statusMap.get("borrowStatus"), 0);
		MessageResult mr=new MessageResult();
		if(borrowStatus==1){
			mr.setCode(1);
			mr.setMessage("已提交");
		}
		if(borrowStatus==2){
			mr.setCode(2);
			mr.setMessage("正在审核");
		}
		if(borrowStatus==3){
			mr.setCode(3);
			mr.setMessage("初审失败");
		}
		if(borrowStatus==4){
			mr.setCode(4);
			mr.setMessage("初审成功，等待绑定银行卡");
		}
		if(borrowStatus==5){
			mr.setCode(5);
			mr.setMessage("银行卡已绑定，等待复审");
		}
		
		if(borrowStatus==6){
			mr.setCode(6);
			mr.setMessage("复审成功");
		}
		if(borrowStatus==7){
			mr.setCode(7);
			mr.setMessage("复审失败");
		}
		if(borrowStatus==8){
			mr.setCode(8);
			mr.setMessage("放款成功");
		}
		return mr;
	}
	
	
	
	/**
	 * 选择银行
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "listConstantBank", method = RequestMethod.POST)
	public @ResponseBody MessageResult listConstantBank() throws Exception {
		List<Map<String,String>> list=memberService.listBank();
		MessageResult mr=new MessageResult();
		mr.setCode(0);
		mr.setData(list);
		mr.setMessage("保存银行的code");
		return mr;
		
	}
	
	
	/**
	 * 选择省
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "provinceList", method = RequestMethod.POST)
	public @ResponseBody MessageResult provinceList() throws Exception {
		MessageResult mr=new MessageResult();
		List<Map<String,String>> list=memberService.queryPlace(1);
		mr.setCode(0);
		mr.setData(list);
		mr.setMessage("返回对应的ID");
		return mr;
	}
	
	
	/**
	 * 选择市
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "cityList", method = RequestMethod.POST)
	public @ResponseBody MessageResult cityList() throws Exception {
		int provinceId = Convert.strToInt(request("provinceId"), 0);
		if(provinceId==0){
			return error("请选择省");
		}
		MessageResult mr=new MessageResult();
		List<Map<String,String>> list=memberService.queryPlace(provinceId);
		mr.setCode(0);
		mr.setData(list);
		mr.setMessage("返回城市对应的ID");
		return mr;
	}
	
	
	

	/**
	 * 选择完银行卡后，调用的接口
	 * 1：第一次申请借款，---直接保存银行卡
	 * 2：优质客户，选择完银行卡，执行放款
	 * 3：争议客户，保存银行卡，等待复审
	 * @return
	 * @throws Exception 
	*/
//	@RequestMapping(value = "selectBankCard", method = RequestMethod.POST)
//	public @ResponseBody MessageResult selectBankCard() throws Exception {
//
//		Member member = getMember();
//		
//		Map<String, String> map=borrowService.findBorrowByMemberAndStatus(member.getId(),4);
//		Map<String, String> repaymap=borrowService.findBorrowByMemberAndStatus(member.getId(),8);
//		Map<String, String> overdueMap=borrowService.findBorrowByMemberAndStatus(member.getId(),9);
//		String bankCardNo=request("bankCardNo");
//		if(ValidateUtil.isnull(bankCardNo)){
//			return error("请选择银行卡");
//		}
//		Map<String, String> cardMap=memberService.findBankCardByCardNo(bankCardNo);
//		if(cardMap!=null){
//			if(!"1".equals(cardMap.get("fuyouStatus"))){
//				return error("该卡没有签约，请重新签约");
//			}
//		}else{
//			return error("该卡没有录入本平台");
//		}
//		if(map!=null){
//			//此时是选择借款的银行卡
//			//如果是第二次借款，身份信息已认证，直接进入复审
//			int flag=0;
//			Map<String,String> identityMap=memberService.findMemberIdentyByMemId(member.getId());
//			if(identityMap!=null){
//				String people_imgurl=identityMap.get("people_imgurl");
//				if(!ValidateUtil.isnull(people_imgurl)){
//					flag=1;
//				}
//			}
//			
//			long ret=0;
//			//如果是优质客户，直接放款
//			if(member.getMemberStatus()==1){
//				//录入到富有
//				//录入时的金额，要大于还款金额
//				Map<String,String> keyMap=keyInFuyouSystem(Convert.strToInt(map.get("borrowDate"), 0), bankCardNo, member.getRealName(), 1300*100, member.getIdentNo(), member.getMobilePhone());
//				if("0000".equals(keyMap.get("ret"))){
//					String project_id=keyMap.get("project_id");
//					//String orderno=keyMap.get("orderno");
//					String currenttime=System.currentTimeMillis()+"";
//					//执行放款
//					//Map<String, String> productMap=borrowService.findProductByDayAndMount(Convert.strToInt(map.get("benJin"), 0), Convert.strToInt(map.get("borrowDate"), 0));
//					//int amount=0;
//					//amount=Convert.strToInt(map.get("benJin"), 0)-(int)(Convert.strToDouble(productMap.get("xinfee"), 0))-(int)(Convert.strToDouble(productMap.get("shoufee"), 0))-(int)(Convert.strToDouble(productMap.get("servicefee"), 0));
//					int benjin = Integer.valueOf(map.get("benJin"));
//					int borrowDays = Integer.valueOf(map.get("borrowDate"));
//					DecimalFormat    df   = new DecimalFormat("######0.00");        //保留两位小数
//					double xinFee = this.borrowService.getSysParamByKey("xinfeeRate") * (benjin/100) * borrowDays;
//					double shouFee = this.borrowService.getSysParamByKey("shoufeeRate") * (benjin/100) * borrowDays;
//					double serviceFee = this.borrowService.getSysParamByKey("servicefeeRate") * (benjin/100) * borrowDays;
//					
//					xinFee = Double.valueOf(df.format(xinFee));
//					shouFee = Double.valueOf(df.format(shouFee));
//					serviceFee = Double.valueOf(df.format(serviceFee));
//					
//					int amount = 0;
//					amount = (int)(benjin - xinFee - shouFee - serviceFee);
//					
//					amount=amount*100;
//					Map<String,String> fuyouMap=borrowMoney(cardMap.get("bankNo"),cardMap.get("cityNo"),cardMap.get("bankName"),cardMap.get("cardNo"),member.getRealName() , amount, member.getMobilePhone(),member.getIdentNo(),currenttime);
//					if(fuyouMap!=null){
//						String fuyouRet=fuyouMap.get("ret");
//						if("000000".equals(fuyouRet)){
//							//发送放款请求成功
//							//查询放款状态
//							ret=borrowAdminService.updateBorrowLoanStatusAndBankCardNo(Convert.strToInt(map.get("id"), 0),6,1,currenttime,"自动放款",project_id,0,bankCardNo);
//							
//							
//						}else if("200053".equals(fuyouRet)){
//							return error("平台账户余额不足，请稍等");
//						}else{
//							return error(fuyouMap.get("memo"));
//						}
//					}
//				}else{
//					return error("富有录入失败");
//				}
//				
//			}
//			
//			//争议客户，保存银行卡，等待复审
//			if(member.getMemberStatus()==0 || member.getMemberStatus()==2){
//				ret=borrowService.updateBorrowBankCard(Convert.strToLong(map.get("id"), 0),Convert.strToInt(map.get("borrowDate"), 0),bankCardNo,member.getMemberStatus(),flag,"");
//			}
//			
//		}else{
//			//此时是选择扣款的银行卡
//			long bid=0;
//			if(overdueMap!=null){
//				bid=Convert.strToLong(overdueMap.get("id"), 0);
//			}
//			if(repaymap!=null){
//				bid=Convert.strToLong(repaymap.get("id"), 0);
//			}
//			
//			long rets=borrowService.updateRepayBankCard(bid,bankCardNo);
//			if(rets>0){
//			
//				return error(100,"选择扣款银行卡成功");
//			}else{
//				return error("保存失败");
//			}
//		
//		}
//		return success();
//	
//	}
//	
//	
	
	
	/**
	 * 身份认证，返回身份信息
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "identity", method = RequestMethod.POST)
	public @ResponseBody MessageResult identity() throws Exception {
		String borrowId= request("borrowId");
		Map<String, String> borrowMap=borrowService.findBorrowById(Convert.strToInt(borrowId, 0));
		/*String bankCardNo=borrowMap.get("bankCardNo");
		if(ValidateUtil.isnull(bankCardNo)){
			return error("请选择银行卡");
		}*/
		
		Member member = getMember();
		
		Map<String,String> identityMap=memberService.findMemberIdentyByMemId(member.getId());
		MessageResult mr =new MessageResult();
		Map<String,String> resultMap=new HashMap<String,String>();
		resultMap.put("realname",identityMap.get("real_name"));
		resultMap.put("cardNo",identityMap.get("card_no"));
		resultMap.put("borrowId",borrowId);
		mr.setCode(0);
		mr.setData(resultMap);
		return mr;
		
	}
	
	
	/**
	 * 身份认证，上传图片，等待复审
	 * @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "identityForImage", method = RequestMethod.POST)
	public @ResponseBody MessageResult identityForImage() throws Exception {
		int borrowId = Convert.strToInt(request("borrowId"), 0);
		Map<String, String> borrowMap=borrowService.findBorrowById(borrowId);
		String bankCardNo=borrowMap.get("bankCardNo");
		if(ValidateUtil.isnull(bankCardNo)){
			return error("请选择银行卡");
		}
		Member member = getMember();
		if(member==null){
			return error("token失效");
		}
		
		Map<String,String> identityMap=memberService.findMemberIdentyByMemId(member.getId());
		String imgPath = request("imgPath");
		if(imgPath==null){
			return success("请上传图片");
		}
		long ret=borrowService.updateIndetityForImage(Convert.strToInt(identityMap.get("id"), 0),imgPath,borrowId);
		
		if(ret>0){
			return success("提交成功，等待审核");
		}else{
			return error("身份图片保存失败，请重试");
		}
		
	}
	
	
	/**
	 * 
	 *富有绑定银行卡-----未使用
	 */
	public String fuyouBinBank(String bankCardNo,String realname,String cardNo,String phone,String sign,long random) throws Exception{
	
    	String path="http://www-1.fuiou.com:18670/mobile_pay/checkCard/checkCardDebit.pay";
    	String param="FM=<FM><MchntCd>0002900F0096235</MchntCd><Ono>"+bankCardNo+"</Ono><Onm>"
    	+URLEncoder.encode(realname,"UTF-8")+"</Onm>=<OCerTp>0</OCerTp><OCerNo>"+cardNo+"</OCerNo>"
    			+"<Mno>"+phone+"</Mno><Sign>"+sign+"</Sign><Ver>1.30</Ver><OSsn>"+random+"</OSsn></FM>";
        URL url = new URL(path.trim());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");// 提交模式
        httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
        httpURLConnection.setReadTimeout(500000);//读取超时 单位毫秒
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream os = httpURLConnection.getOutputStream();    
        os.write(param.getBytes());
        if (httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");  
            
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return bos.toString();
	}
        
	
	/*
	 * 富有银行卡签约第一步
	 */
	public Map<String,String> qianyueStepA(String bankCardNo,String bankNo,String realName,String idCardNo,String mobileNo) throws Exception{
		String isCallback ="0";
        String busiCd ="AC01" ;
        String credtTp = "0";
        String srcChnl ="APP";
        String acntTp = "01";
        String mchntCd ="0003720F0395576";//正式
        String reserved1 ="备注";
        
        ArrayList<String> list=new ArrayList<String>();
		list.add(isCallback);
		list.add(busiCd);
		list.add(credtTp);
		list.add(bankCardNo);
		list.add(bankNo);
		list.add(realName);
		list.add(idCardNo);
		list.add(srcChnl);
		list.add(acntTp);
		list.add(mobileNo);
		list.add(mchntCd);
		list.add(reserved1);
		
		
		String[] strs = new String[list.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = list.get(i);
		}
		Arrays.sort(strs);//集合自动排序
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		log.info(bigstr);
		String signature = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+"iyqw910fydjn3is3w2k8b22ej5dohacg");
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<custmrBusi>"
				+ "<srcChnl>" + srcChnl + "</srcChnl>"
				+ "<busiCd>" + busiCd + "</busiCd>"
				+ "<bankCd>" + bankNo + "</bankCd>"
				+ "<userNm>" + realName + "</userNm>"
				+ "<mobileNo>" + mobileNo + "</mobileNo>"
				+ "<credtTp>" + credtTp + "</credtTp>"
				+ "<credtNo>" + idCardNo + "</credtNo>"
				+ "<acntTp>" + acntTp + "</acntTp>"
				+ "<acntNo>" + bankCardNo + "</acntNo>"
				+ "<mchntCd>" + mchntCd + "</mchntCd>"
				+ "<isCallback>" + isCallback + "</isCallback>"
        		+ "<reserved1>" + reserved1 + "</reserved1>"
        		+ "<signature>" + signature + "</signature>"
        		+ "</custmrBusi>";
		String path="https://fht.fuiou.com/api_contract5.do";
		String param="xml="+xml;
		return httpclientPost( path, param );
	}
	
	
	/*
	 * 富有银行卡签约第二步
	 */
	public Map<String,String> qianyuemsg(String bankCardNo,String verifyCode) throws Exception{

        String mchntCd ="0003720F0395576";    
        ArrayList<String> list=new ArrayList<String>();
		list.add(bankCardNo);
		list.add(mchntCd);
		list.add(verifyCode);
		
		String[] strs = new String[list.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = list.get(i);
		}
		Arrays.sort(strs);//集合自动排序
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		log.info(bigstr);
		String signature = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+"iyqw910fydjn3is3w2k8b22ej5dohacg");
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<custmrBusi>"
				+ "<acntNo>" + bankCardNo + "</acntNo>"
				+ "<mchntCd>" + mchntCd + "</mchntCd>"
				+ "<verifyCode>" + verifyCode + "</verifyCode>"
				+ "<signature>" + signature + "</signature>"
        		+ "</custmrBusi>";
	
		String path="https://fht.fuiou.com/api_verifyMsg.do";
		String param="xml="+xml;
		return httpclientPost( path, param );
	}
	
	
	/*付款前录入，返回projectID
	 * 
	 */
	public Map<String,String> keyInFuyouSystem(int borrowDays ,String bankCardNo
				,String realname ,int amountFen,String idcardNo,String mobile) throws Exception{
			int fuyouNum=999999;
			String date=DateUtil.dateToStringDate(new Date());
			Map<String,String> constantMap=new Model("constant_variable").where("id= 6 and updateTime between ? and ?",date+" 00:00:00",date+" 23:59:59").find();
			if(constantMap==null){
				fuyouNum=999999;
				memberService.updateConstantByid(6,fuyouNum);
			}else{
				fuyouNum=Convert.strToInt(constantMap.get("value"), 0);
			} 
			fuyouNum=fuyouNum-1;
			memberService.updateConstantByid(6,fuyouNum);
			String contract_nm=DateUtil.YYYYMMDDMMHHSSSSS(new Date())+System.currentTimeMillis();
			
			String path="https://fht.fuiou.com/inspro.do";
			String merid="0003720F0395576";
			amountFen=2500*100;
		     		
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
					+ "<project>"
					+ "<ver>2.00</ver>"
					+ "<orderno>"+(new Date()).getTime()+"</orderno>"
					+ "<mchnt_nm>0003720F0395576</mchnt_nm>"
					+ "<project_ssn>"+fuyouNum+"</project_ssn>"
					+ "<project_amt>"+amountFen+"</project_amt>"
					+ "<expected_return>3.24</expected_return>"
					+ "<project_fee>3.24</project_fee>"
					+ "<contract_nm>"+contract_nm+"</contract_nm>"
					+ "<project_deadline>"+borrowDays+"</project_deadline>"
					+ "<raise_deadline>180</raise_deadline>"
					+ "<max_invest_num></max_invest_num>"
					+ "<min_invest_num></min_invest_num>"
					+ "<bor_nm>"+realname+"</bor_nm>"
					+ "<id_tp>0</id_tp>"
					+ "<id_no>"+idcardNo+"</id_no>"
					+ "<card_no>"+bankCardNo+"</card_no>"
					+ "<mobile_no>"+mobile+"</mobile_no>"
					+ "</project>";			
			String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+xml;//付款
			String mac=Encrypt.MD5(macSource).toUpperCase();
			String param="merid="+merid+"&xml="+xml+"&mac="+mac;
		   
			return httpclientPost( path, param );
		}
		
		
	/*放款
	 * 
	 */
	public Map<String,String> borrowMoney(String bankno,String cityno,String bankname
			,String bankCardNo,String realname,int amountFen,String mobile,String idcardNo,String orderno) throws Exception{
		
		String path="https://fht.fuiou.com/req.do";
		String merid="0003720F0395576";
		String reqtype="payforreq";//放款
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
	     		+ "<payforreq>"
	     		+ "<ver>2.00</ver>"
	     		+ "<merdt>"+DateUtil.getDateYMD()+"</merdt>"
	     		+ "<orderno>"+orderno+"</orderno>"
	     		+ "<bankno>"+bankno+"</bankno>"
	     		+ "<cityno>"+cityno+"</cityno>"//付款
	     		+ "<accntno>"+bankCardNo+"</accntno>"
	     		+ "<accntnm>"+realname+"</accntnm>"
	     		+ "<amt>"+amountFen+"</amt>"
	     		+ "<entseq>test</entseq>"
	     		+ "<memo>备注</memo>"
	     		+ "<mobile>"+mobile+"</mobile>"
	     		+ "<certtp>0</certtp>"
	     		+ "<certno>"+idcardNo+"</certno>"
	     		+"<txncd>"+"06"+"</txncd>"//9.5 付款业务定义说明01 借款下发   02 逾期还款03 债权转让04 其他
	     		//9.6 代收业务定义说明06 贷款还款07 逾期还款08 债权转让09 其他
	     		//+"<projectid>"+"0003720F0395576_20170602_0001"+"</projectid>"
	     		+ "</payforreq>";
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+"payforreq"+"|"+xml;//付款
		String mac=Encrypt.MD5(macSource).toUpperCase();
		String param="merid="+merid+"&reqtype="+reqtype+"&xml="+xml+"&mac="+mac;
		return httpclientPost(path, param);
	}	
	
	
	/*查询放款结果
	 * 
	 */
	public Map<String,String> fuyouchecked(String orderno,String startdt) throws Exception{
		
		String path="https://fht.fuiou.com/req.do";
		String merid="0003720F0395576";
		String reqtype="qrytransreq";//查看
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
	     		+ "<qrytransreq>"
	     		+ "<ver>2.00</ver>"
	     		+"<busicd>AP01</busicd>"
	     		+"<orderno>"+orderno+"</orderno>"
	     		+"<startdt>"+startdt+"</startdt>"
	     		+"<enddt>"+startdt+"</enddt>"
	     		+ "</qrytransreq>";
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+"qrytransreq"+"|"+xml;//付款
		String mac=Encrypt.MD5(macSource).toUpperCase();
		String param="merid="+merid+"&reqtype="+reqtype+"&xml="+xml+"&mac="+mac;
		return httpclientPost(path, param);

	}	
	
	
	//解析xml
	public Map<String,String> analysisXML(String xml) throws DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		InputSource in = new InputSource(new StringReader(xml));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for(Iterator<Element> it = elements.iterator();it.hasNext();){
		   Element element = it.next();
		   map.put(element.getName(),element.getTextTrim());
		} 
		
		return map;
		
	}
	
	/**
	 * 获取 首页借款状态
	 * @return
	 */
	@RequestMapping("checkBorrowAuditStatus")
    public @ResponseBody
    MessageResult checkBorrowAuditStatus()  throws Exception{
		MessageResult mr=new MessageResult();
		Map<String,String> data=new HashMap<>();
		String approve_state="0";//默认待申请
		String approve_display="待申请";//默认待申请
		Member user = getMember();
		if(user==null){
			return error(4102,"token失效");
		}
		user=memberService.findMember(user.getId());
		/*Member user=new Member();
		user.setId(22439);*/
		List<Map<String, String>> list=borrowService.queryBorrowByMemberId(user.getId());
		log.info("用户首页借款状态查询======>>>"+JSONObject.toJSONString(list));
		if(!list.isEmpty()) {
			//只查看最近一次借款
			Map<String, String> map=list.get(0);
			//拿到借款状态根据状态作区分
			int borrowStatus=Convert.strToInt(map.get("borrowStatus"), 0);
			mr.setCode(0);
			//过滤状态
			switch (borrowStatus) {
				case 1:
				case 10:
				case 12:
				case 13:
					approve_state="0";
					approve_display="待申请";
					break;
				case 3:
					approve_state="-1";
					approve_display="初审不通过";
					break;

				case 4:
					approve_state="1";
					approve_display="初审通过";
					break;
				case 2:
				case 5:
				case 6:
					approve_state="2";
					approve_display="审核中";
					break;
				case 7:
					approve_state="-2";
					approve_display="复审未通过";
					break;
				case 8:
				case 9:
				case 11:
					approve_display="租赁中";
					break;
				default:
					mr.setCode(400);
					mr.setMessage("状态有误");
					break;
			}
			if(mr.getCode()!=4102) {
				mr.setMessage("请求成功");
			}
		}else {
			mr.setCode(0);
			mr.setMessage("无借款信息");
		}
		data.put("approve_state", approve_state);
		data.put("approve_display", approve_display);
		mr.setData(data);
		log.info("用户首页借款状态查询end======>>>"+JSONObject.toJSONString(mr));
		return mr;
    }
}
