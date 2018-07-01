package test.service;


import org.junit.Before;
import org.junit.Test;

import com.spark.p2p.service.admin.SelectService;

import test.base.BaseServiceTest;

public class SelectServiceTest extends BaseServiceTest{
	private SelectService service;
	
	@Before
	public void setUp(){
		service = wac.getBean(SelectService.class);
	}
	
	

	
	@Test
	public void testQueryRole() throws Exception{
		print(service.getSelectMap("IMG_CATE"));
	}
}
