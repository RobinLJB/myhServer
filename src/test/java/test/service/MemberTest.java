package test.service;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.spark.p2p.exception.AuthenticationException;
import com.spark.p2p.service.MemberService;


import test.base.BaseServiceTest;

public class MemberTest extends BaseServiceTest{
	private MemberService service;
	@Before
	public void setUp(){
		service = wac.getBean(MemberService.class);
	}
	
	
	//@Test
	public  void findUser() throws Exception{
		print(service.checkMemberIndentity("18356705512"));
	}
	
	@Test
	public void auditCompany() throws Exception{
		Map<String,String> map= null;//service.queryfeeConstant(7);
		JSONObject myJsonObject = new JSONObject(map.get("value")); 
        
        System.out.println("xinFee"+myJsonObject.getDouble("xinFee")); 
        System.out.println("shouFee"+myJsonObject.getDouble("shouFee")); 
        System.out.println("serviceFee"+myJsonObject.getDouble("serviceFee")); 
	}
}
