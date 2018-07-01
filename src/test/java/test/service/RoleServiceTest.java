package test.service;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.spark.p2p.service.admin.RolePermissionService;

import test.base.BaseServiceTest;

public class RoleServiceTest extends BaseServiceTest{
	private RolePermissionService service;
	
	@Before
	public void setUp(){
		service = wac.getBean(RolePermissionService.class);
	}
	
	
	public void testUpdateRights(){
		Long[] rights = new Long[]{-7L,578L,580L,581L};
		try {
			service.updateRoleRights(26, rights);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void testAddRole() throws SQLException{
		print(service.addRole("测试", "测试"));
	}
	
	@Test
	public void testQueryRole(){
		print(service.queryAllRole());
	}
}
