package com.spark.p2p.event;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;

import com.spark.p2p.annotation.SiteSetting;
import com.spark.p2p.config.AppSetting;
import com.sparkframework.lang.Convert;

@Service
public class AppSettingEvent {
	
	/**
	 * 当键值改变时触发
	 * @param key
	 * @param value
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void onChange(String key,String value) throws IllegalArgumentException, IllegalAccessException{
		Class<AppSetting> rtClass = AppSetting.class;
		Field[] fields = rtClass.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(SiteSetting.class)){
				SiteSetting siteSetting = field.getAnnotation(SiteSetting.class);
				Class<?> type = field.getType();
				if(siteSetting.key().equals(key)){
					//SiteSetting 注解与 KEY相同
					String typeName = type.getName();
					if(typeName.equals("java.lang.String")){
						field.set(null, value);
					}
					else if(typeName.equals("int")){
						field.set(null, Convert.strToInt(value, 0));
					}
					else if(typeName.equals("double")){
						field.set(null, Convert.strToDouble(value, 0));
					}
					break;
				}
			}
		}
	}
}
