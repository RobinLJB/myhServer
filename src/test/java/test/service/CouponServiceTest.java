package test.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.Activity;
import com.spark.p2p.entity.Coupon;
import com.spark.p2p.service.CouponService;
import com.spark.p2p.service.FinanceService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.DateUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;
import com.sparkframework.sql.model.Model;

import test.base.BaseServiceTest;

public class CouponServiceTest extends BaseServiceTest{
	private CouponService service;
	private MemberService memberService;
	@Before
	public void setUp(){
		service = wac.getBean(CouponService.class);
		memberService = wac.getBean(MemberService.class);
	}
	
	@Test
	public void addActivity() throws SQLException, Exception{

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
		 int ss=(int)((Math.random()*9+1)*100000);
		 String ssn=String.valueOf(ss);
	
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
				+ "<project>"
				+ "<ver>2.00</ver>"
				+ "<orderno>"+(new Date()).getTime()+"</orderno>"
				+ "<mchnt_nm>0003720F0395576</mchnt_nm>"
				+ "<project_ssn>"+ssn+"</project_ssn>"
				+ "<project_amt>50000</project_amt>"
				+ "<expected_return>3.24</expected_return>"
				+ "<project_fee>3.24</project_fee>"
				+ "<contract_nm>"+contract_nm+"</contract_nm>"
				+ "<project_deadline>360</project_deadline>"
				+ "<raise_deadline>180</raise_deadline>"
				+ "<max_invest_num></max_invest_num>"
				+ "<min_invest_num></min_invest_num>"
				+ "<bor_nm>吴涛</bor_nm>"
				+ "<id_tp>0</id_tp>"
				+ "<id_no>34262219900129295X</id_no>"
				+ "<card_no>6217973610006488319</card_no>"
				+ "<mobile_no>18555116208</mobile_no>"
				+ "</project>";		
	     		
		String macSource = "0003720F0395576|iyqw910fydjn3is3w2k8b22ej5dohacg|"+xml;//付款
		String mac=Encrypt.MD5(macSource).toUpperCase();
		String param="merid="+merid+"&xml="+xml+"&mac="+mac;
		
		
		
		
		
		 System.out.println(param.toString());
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
	        System.out.println("+++++++++++++++++++++++");
	        System.out.println(bos.toString());
	        System.out.println("------------------------------------");
	        Map<String,String> map=new HashMap<String,String>();
			InputSource in = new InputSource(new StringReader(bos.toString()));
			in.setEncoding("UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Iterator<Element> it = elements.iterator();it.hasNext();){
			   Element element = it.next();
			   map.put(element.getName(),element.getTextTrim());
			} 
			System.out.println(map.get("project_id"));
			System.out.println(map.get("orderno"));
			System.out.println(map.get("ret"));
	}
	
	//@Test
	public void testSend() throws Exception{
		Activity act = service.findActivity(2);
		print(service.sendCoupon(42, act));
	}
	
	//@Test
	public void testSendReward() throws Exception{
		long uid = 42;
		Coupon coupon = service.findAvailableCoupon(uid, 100, 1);
		if(coupon !=null){
			//long ret = financeService.addMemberReward(uid, coupon.getFaceValue(), Const.REWARD_FEE_REDPACKET, 0, 23);
			//if(ret > 0){
				//service.updateCouponStatus(coupon.getId(), 2);
			//}
		}
	}
	
	//@Test
	public void testQuery() throws Exception{
		List<Activity> acts = service.queryNormalActivity(Const.COUPON_MODE_REG);
		System.out.println(acts.get(0).getFaceValue());
	}
	
	@Test
	public void testInvestCoupon() throws Exception{
		Coupon coupon = service.findAvailableCoupon(58,1000,1);
		System.out.println(coupon.getFaceValue());
	}
	
}
