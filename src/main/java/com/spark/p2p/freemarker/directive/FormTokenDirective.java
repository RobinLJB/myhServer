package com.spark.p2p.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spark.p2p.core.RequestHolder;
import com.spark.p2p.util.FormUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FormTokenDirective implements TemplateDirectiveModel {
	@Override
	public void execute(Environment env, Map map, TemplateModel[] vars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String name = FormUtil.TOKEN_NAME_PRIFIX + DirectiveUtils.getRequiredParam(map, "name");
		HttpServletRequest request = RequestHolder.getRequest();
		System.out.println(name);
		String value = (String)request.getSession().getAttribute(name);
		System.out.println(value);
		String content = String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />",
				name,value);
		env.getOut().write(content);
	}
}
