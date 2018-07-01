package test.controller;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import test.base.BaseControllerTest;


public class FreemarkerTest  extends BaseControllerTest{
	
	@Test
	public void test(){
		FreeMarkerConfigurer cfg = wac.getBean(FreeMarkerConfigurer.class);
		try {
			Template tpl = cfg.getConfiguration().getTemplate("/email/test.ftl");
			StringWriter out = new StringWriter();
			tpl.process(null, out);
			System.out.println(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
