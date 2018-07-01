package test.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.spark.p2p.constant.Const;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.NoticeService;
import com.spark.p2p.service.admin.RolePermissionService;
import com.spark.p2p.service.admin.SiteService;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;

import test.base.BaseServiceTest;

public class SiteTest extends BaseServiceTest{
	private SiteService service;
	private NoticeService noticeService;
	private MemberService memberService;
	@Before
	public void setUp(){
		service = wac.getBean(SiteService.class);
		noticeService = wac.getBean(NoticeService.class);
		memberService = wac.getBean(MemberService.class);
	}
	
	//@Test
	public void testQueryOption(){
		DataTableRequest params = new DataTableRequest();
		params.setDraw(11);
		params.setStart(0);
		params.setLength(10);
		Map<String,DataTableRequest.DataColumn> columns = new HashMap<String,DataTableRequest.DataColumn>();
		DataTableRequest.DataColumn column = new DataTableRequest().new DataColumn();
		column.setData("group_key");
		column.setSearchValue("SYSTEM");
		//columns.put("group_key", column);
		params.columns = columns;
		
		try {
			DataTable table = service.queryOption(params);
			print(table.getData());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//@Test
	public void testNotice() throws Exception{
		Member m = memberService.findMember(2);
		Map<String,String> data = new HashMap<String,String>();
		data.put("userName","yanqizheng");
		data.put("amount", "100.00");
		//print(noticeService.addNotice(m, Const.NOTICE_RECHARGE, data));
	}
	
	
}
