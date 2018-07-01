package test.base;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sparkframework.sql.model.Model;

public class MemberServiceTest {
	
	ApplicationContext context;
	
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	}
	
	@Test
	public void insert() throws SQLException{
		Model member = new Model("identity_check");
        member.set("member_id", 123123);
        member.set("real_name", "骆俊宾");
        
        long ret = member.insert();
        if(ret > 0){
        	System.out.println("大于0");
        }
        System.out.println("ret result : " +ret);
	}
}
