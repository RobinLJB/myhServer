package test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spark.p2p.service.admin.BorrowAdminService;

import test.base.BaseServiceTest;

public class LLService extends BaseServiceTest{
	@Autowired
	private BorrowAdminService borrowAdminService;
	
	@Test
	public void updateBorrowStatusByOrderNo() throws Exception{
		long ret = borrowAdminService.updateBorrowStatusByOrderNo("66220170915171552817",8);
	}
	
}
