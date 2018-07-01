package test.base;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.sparkframework.sql.DB;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:mvc.xml"}) 
public class BaseControllerTest {
	@Autowired  
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;
	
 
	@Before
    public void setUp() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	protected MockHttpServletRequestBuilder get(String url){
		return MockMvcRequestBuilders.get(url);
	}
	
	protected MockHttpServletRequestBuilder post(String url){
		return MockMvcRequestBuilders.post(url);
	}
	
	protected ViewResultMatchers view(){
		MockMvcResultMatchers.model();
		return MockMvcResultMatchers.view();
	}
	
	protected ModelResultMatchers model(){
		return MockMvcResultMatchers.model();
	}
	
	protected ResultHandler print(){
		return MockMvcResultHandlers.print();
	}
	
	@After
	public void tearUp() {
        //tear down  
    }
	
 
	public void print(Object out){
		System.out.println(out);
	}
	
}