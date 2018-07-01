package test.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import test.base.BaseControllerTest;

public class AdminControllerTest extends BaseControllerTest{
	
	@Test
	public void testA(){
		System.out.println("Begin TestA ...");
		try {
			MvcResult result = mockMvc.perform(get("article/category/1.html"))
					.andDo(print())
					.andReturn();
			print(result.getRequest().getAttribute("roles"));
			Assert.assertNotNull(result.getModelAndView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
