package com.spark.p2p.event;

import org.springframework.stereotype.Service;

import com.spark.p2p.constant.AppConstant;
import com.sparkframework.lang.Convert;

@Service
public class KeyValueEvent {
	
	/**
	 * 当键值改变时触发
	 * @param key
	 * @param value
	 */
	public void onChange(String key,String value){
		if(key.equalsIgnoreCase("SEO_TITLE")){
			AppConstant.SEO_TITLE = value;
		}
		else if(key.equalsIgnoreCase("SEO_KEY")){
			AppConstant.SEO_KEY = value;
		}
		else if(key.equalsIgnoreCase("SEO_DESC")){
			AppConstant.SEO_DESC = value;
		}
		/*
		Class<AppConstant> rtClass = AppConstant.class;
		Field[] fields = rtClass.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(SiteSetting.class)){
				SiteSetting siteSetting = field.getAnnotation(SiteSetting.class);
				Class<?> type = field.getType();
				if(siteSetting.key().equals(key)){
					String typeName = type.getName();
					if(typeName.equals("java.lang.String")){
						field.set(null, value);
					}
					else if(typeName.equals("int")){
						
					}
					else if(typeName.equals("double")){
						
					}
					break;
				}
			}
		}*/
	}
}
