package com.spark.p2p.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sparkframework.lang.Convert;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.DeepUnwrap;

public class MaskDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map map, TemplateModel[] vars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String value = DirectiveUtils.getRequiredParam(map, "value");
		int start = Convert.strToInt(DirectiveUtils.getRequiredParam(map, "start"),0);
		int length = Convert.strToInt(DirectiveUtils.getRequiredParam(map, "length"),0);
		char maskChar = DirectiveUtils.getParam(map, "symbol", "*").charAt(0);
		if(StringUtils.isNotBlank(value)){
			char[] array = value.toCharArray();
			char[] newArray = new char[array.length];
			int count = 1;
			for(int i = 0;i<array.length;i++){
				if(i >= start&& count <=length){
					newArray[i] = maskChar;
					count ++;
				}
				else{
					newArray[i] = array[i];
				}
			}
			
			env.getOut().write(new String(newArray));
		}
	}

}
