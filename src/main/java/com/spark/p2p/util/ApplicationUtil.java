package com.spark.p2p.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.context.ContextLoader;
public class ApplicationUtil {
	/**
	 * 获取全局变量
	 * @param attrName 变量名称
	 * @return
	 */
	public static Object getAttribute(String attrName){
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(attrName);
	}
	
	public static void setAttribute(String attrName,Object attr){
		 ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute(attrName, attr);
	}
	
	/**
	 * 获取bean
	 * @param className
	 * @return
	 */
	public static <T> T getBean(Class<T> className){
		return ContextLoader.getCurrentWebApplicationContext().getBean(className);
	}
	
 

	public static boolean timeAfter(String t1, String t2) {
		Date date1, date2;
		DateFormat formart = new SimpleDateFormat("HH:mm");
		try {
			date1 = formart.parse(t1);
			date2 = formart.parse(t2);
			return date1.compareTo(date2) >= 0;
		} catch (ParseException e) {
			System.out.println("date init fail!");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean timeBefore(String t1, String t2) {
		Date date1, date2;
		DateFormat formart = new SimpleDateFormat("HH:mm");
		try {
			date1 = formart.parse(t1);
			date2 = formart.parse(t2);
			return date1.compareTo(date2) <= 0;
		} catch (ParseException e) {
			System.out.println("date init fail!");
			e.printStackTrace();
			return false;
		}
	}
}
