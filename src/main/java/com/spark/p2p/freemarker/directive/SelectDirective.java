package com.spark.p2p.freemarker.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alibaba.druid.util.StringUtils;
import com.spark.p2p.entity.SelectOption;
import com.spark.p2p.service.admin.SelectService;
import com.spark.p2p.util.ApplicationUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SelectDirective implements TemplateDirectiveModel{

	
	/**
	 * 自动生成分类项标签
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String group = DirectiveUtils.getRequiredParam(params, "group");
		String selectedKey = DirectiveUtils.getParam(params, "selected", "");
		String type = DirectiveUtils.getParam(params, "type", "html");
		SelectService service = ApplicationUtil.getBean(SelectService.class);
		List<SelectOption> options;
		try {
			options = service.querySelectOption(group);
			StringBuffer sb = new StringBuffer();
			String tpl = "<option %s value=\"%s\">%s</option>";
			if(type.equalsIgnoreCase("html")){
				for(SelectOption option:options){
					if(option.getKey().equals(selectedKey)){
						sb.append(String.format(tpl, "selected",option.getKey(),option.getName()));
					}
					else if(StringUtils.isEmpty(selectedKey)&&option.getIsDefault() == 1){
						sb.append(String.format(tpl, "selected",option.getKey(),option.getName()));
					}
					else{
						sb.append(String.format(tpl, "",option.getKey(),option.getName()));
					}
				}
			}
			else if(type.equalsIgnoreCase("json")){
				JSONObject json = new JSONObject();
				for(SelectOption option:options){
					json.put(option.getKey(), option.getName());
				}
				sb.append(json.toString());
			}
			env.getOut().write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
