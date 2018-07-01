package com.spark.p2p.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import com.spark.p2p.entity.SelectOption;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.util.ApplicationUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class OptionDirective implements TemplateDirectiveModel{

	/**
	 * 显示分类子项名称
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String group = DirectiveUtils.getRequiredParam(params, "group");
		String key = DirectiveUtils.getRequiredParam(params, "key");
		SelectService service = ApplicationUtil.getBean(SelectService.class);
		try {
			SelectOption option = service.findSelectOption(group, key);
			env.getOut().write(option.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
