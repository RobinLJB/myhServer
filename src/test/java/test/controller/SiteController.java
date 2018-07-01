package test.controller;

import java.io.StringWriter;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import test.base.BaseControllerTest;

public class SiteController extends BaseControllerTest{
	
	
	@Test
	public void testQueryOption() throws Exception{
		MvcResult result = mockMvc.
				perform(get("/admin/site/option.json?draw=1&start=0&length=10&columns[0][data]=group_key&columns[0][search][value]=&columns[0][searchable]=true"))
				.andDo(print())
				.andReturn();
	}
}
