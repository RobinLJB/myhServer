package com.spark.p2p.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spark.p2p.service.admin.BaseService;


public class CardIdCheck  {
	public static final String ACCEPT = "";
	public static final int EIGHTEEN_IDCARD = 18;
	public static final int FIFTEEN_IDCARD = 15;
	public static final int MAX_MAINLAND_AREACODE = 659004;
	public static final int MIN_MAINLAND_AREACODE = 110000;
	public static final int HONGKONG_AREACODE = 810000;
	public static final int TAIWAN_AREACODE = 710000;
	public static final int MACAO_AREACODE = 820000;
	private static final int MAN_SEX = 1;
	private static final int WOMAN_SEX = 2;
	private static final String[] SORTCODES = { "1", "0", "X", "9", "8", "7",
			"6", "5", "4", "3", "2" };
	private static final Integer[] a = { Integer.valueOf(7),
			Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(5),
			Integer.valueOf(8), Integer.valueOf(4), Integer.valueOf(2),
			Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(3),
			Integer.valueOf(7), Integer.valueOf(9), Integer.valueOf(10),
			Integer.valueOf(5), Integer.valueOf(8), Integer.valueOf(4),
			Integer.valueOf(2) };

	// 根据身份证号码返回性别、年龄、地区
	public static Map<String, String> getCardInfo(String cardId){
		if(cardId==null || cardId.length() < 18){
			return null;
		}
		// data.substr(16,1)%2?"男":"女";
		int sex_int=Integer.valueOf(cardId.substring(16,17));
		String sex = (sex_int % 2 != 0) ? "男" : "女";
		int year=Integer.valueOf(cardId.substring(6,10));
		int age = Calendar.getInstance().get(Calendar.YEAR) - year;
		Map<String,String> map = new HashMap<String,String>();
		map.put("11","北京");map.put("12","天津");map.put("13","河北");map.put("14","山西");map.put("15","内蒙古");map.put("21","辽宁");map.put("22","吉林");map.put("23","黑龙江");map.put("31","上海");map.put("32","江苏");map.put("33","浙江");map.put("34","安徽");map.put("35","福建");map.put("36","江西");map.put("37","山东");map.put("41","河南");map.put("42","湖北");map.put("43","湖南");map.put("44","广东");map.put("45","广西");map.put("46","海南");map.put("50","重庆");map.put("51","四川");map.put("52","贵州");map.put("53","云南");map.put("54","西藏");map.put("61","陕西");map.put("62","甘肃");map.put("63","青海");map.put("64","宁夏");map.put("65","新疆");map.put("71","台湾");map.put("81","香港");map.put("82","澳门");map.put("91","国外");
		String area = map.get(cardId.substring(0,2));
		
		Map<String,String> res_map = new HashMap<String,String>();
		res_map.put("age", age+"");
		res_map.put("sex", sex);
		res_map.put("area", area);
		
		return res_map;
	}
	
	public static String chekIdCard(int sex, String idCardInput) {
		if ((idCardInput == null) || ("".equals(idCardInput)))
			return "身份证号码为必填";
		if ((idCardInput.length() != 18) && (idCardInput.length() != 15))
			return "身份证号码位数不符";
		if (idCardInput.length() == 15) {
			return checkIdCard15(sex, idCardInput);
		}
		return checkIdCard18(sex, idCardInput);
	}

	private static String checkIdCard15(int sex, String idCardInput) {
		String numberResult = checkNumber(15, idCardInput);
		if (!"".equals(numberResult)) {
			return numberResult;
		}
		String areaResult = checkArea(idCardInput);
		if (!"".equals(areaResult)) {
			return areaResult;
		}
		String birthResult = checkBirthDate(15, idCardInput);
		if (!"".equals(birthResult)) {
			return birthResult;
		}
		String sortCodeResult = checkSortCode(15, sex, idCardInput);
		if (!"".equals(sortCodeResult)) {
			return sortCodeResult;
		}
		String checkCodeResult = checkCheckCode(15, idCardInput);
		if (!"".equals(checkCodeResult)) {
			return checkCodeResult;
		}
		return "success";
	}

	public static String checkIdCard18(int sex, String idCardInput) {
		String numberResult = checkNumber(18, idCardInput);
		if (!"".equals(numberResult)) {
			return numberResult;
		}
		String areaResult = checkArea(idCardInput);
		if (!"".equals(areaResult)) {
			return areaResult;
		}
		String birthResult = checkBirthDate(18, idCardInput);
		if (!"".equals(birthResult)) {
			return birthResult;
		}
		String sortCodeResult = checkSortCode(18, sex, idCardInput);
		if (!"".equals(sortCodeResult)) {
			return sortCodeResult;
		}
		String checkCodeResult = checkCheckCode(18, idCardInput);
		if (!"".equals(checkCodeResult)) {
			return checkCodeResult;
		}
		return "success";
	}

	private static String checkArea(String idCardInput) {
		String subStr = idCardInput.substring(0, 6);
		int areaCode = Integer.parseInt(subStr);
		if ((areaCode != 810000) && (areaCode != 710000)
				&& (areaCode != 820000)
				&& ((areaCode > 659004) || (areaCode < 110000)))
			return "输入的身份证号码地域编码不符合大陆和港澳台规则";
		return "";
	}

	private static String checkNumber(int idCardType, String idCard) {
		if(idCard==null || idCard.length() < 15){
			return "身份证号码不能小于15位";
		}
		char[] chars = idCard.toCharArray();
		if (idCardType == 15) {
			for (int i = 0; i < chars.length; i++)
				if (chars[i] > '9')
					return idCardType + "位身份证号码中不能出现字母";
		} else {
			for (int i = 0; i < chars.length; i++) {
				if (i < chars.length - 1) {
					if (chars[i] > '9')
						return "18位身份证号码中前17不能出现字母";
				} else if ((chars[i] > '9') && (chars[i] != 'X')) {
					return idCardType + "位身份证号码中最后一位只能是数字0~9或字母X";
				}
			}

		}

		return "";
	}

	private static String checkBirthDate(int idCardType, String idCardInput) {
		String yearResult = checkBirthYear(idCardType, idCardInput);
		if (!"".equals(yearResult)) {
			return yearResult;
		}
		String monthResult = checkBirthMonth(idCardType, idCardInput);
		if (!"".equals(monthResult)) {
			return monthResult;
		}
		String dayResult = checkBirthDay(idCardType, idCardInput);
		if (!"".equals(dayResult)) {
			return dayResult;
		}
		return "";
	}

	private static String checkBirthYear(int idCardType, String idCardInput) {
		if (idCardType == 15) {
			int year = Integer.parseInt(idCardInput.substring(6, 8));
			if ((year < 0) || (year > 99))
				return idCardType + "位的身份证号码年份须在00~99内";
		} else {
			int year = Integer.parseInt(idCardInput.substring(6, 10));
			int yearNow = getYear();
			if ((year < 1900) || (year > yearNow))
				return idCardType + "位的身份证号码年份须在1900~" + yearNow + "内";
		}
		return "";
	}

	private static String checkBirthMonth(int idCardType, String idCardInput) {
		int month = 0;
		if (idCardType == 15)
			month = Integer.parseInt(idCardInput.substring(8, 10));
		else {
			month = Integer.parseInt(idCardInput.substring(10, 12));
		}
		if ((month < 1) || (month > 12)) {
			return "身份证号码月份须在01~12内";
		}
		return "";
	}

	private static String checkBirthDay(int idCardType, String idCardInput) {
		boolean bissextile = false;
		int day;
		int year;
		int month;

		if (idCardType == 15) {
			year = Integer.parseInt("19" + idCardInput.substring(6, 8));
			month = Integer.parseInt(idCardInput.substring(8, 10));
			day = Integer.parseInt(idCardInput.substring(10, 12));
		} else {
			year = Integer.parseInt(idCardInput.substring(6, 10));
			month = Integer.parseInt(idCardInput.substring(10, 12));
			day = Integer.parseInt(idCardInput.substring(12, 14));
		}
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			bissextile = true;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if ((day < 1) || (day > 31))
				return "身份证号码大月日期须在1~31之间";
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if ((day < 1) || (day > 30))
				return "身份证号码小月日期须在1~30之间";
			break;
		case 2:
			if (bissextile) {
				if ((day < 1) || (day > 29))
					return "身份证号码闰年2月日期须在1~29之间";
			} else if ((day < 1) || (day > 28)) {
				return "身份证号码非闰年2月日期年份须在1~28之间";
			}
			break;
		}
		return "";
	}

	private static String checkSortCode(int idCardType, int sex,
			String idCardInput) {
		int sortCode = 0;
		if (idCardType == 15)
			sortCode = Integer.parseInt(idCardInput.substring(12, 15));
		else {
			sortCode = Integer.parseInt(idCardInput.substring(14, 17));
		}
		return "";
	}

	private static String checkCheckCode(int idCardType, String idCard) {
		if (idCardType == 18) {
			int sum = 0;
			int sigma = 0;
			for (int i = 0; i < 17; i++) {
				int ai = Integer.parseInt(idCard.substring(i, i + 1));
				int wi = a[i].intValue();
				sigma += ai * wi;
			}
			int number = sigma % 11;
			String check_number = SORTCODES[number];

			if (!check_number.equals(idCard.substring(17))) {
				return "身份中的校验码不正确";
			}
		}
		return "";
	}

	private static int getYear() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
		String nowStr = format.format(now);
		return Integer.parseInt(nowStr.substring(0, 4));
	}
}
