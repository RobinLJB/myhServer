package com.spark.p2p.util;

import com.sparkframework.lang.Convert;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
	public static final DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

	public static final DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static final DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String dateToString(Date date) {
		return YYYY_MM_DD_MM_HH_SS.format(date);
	}
	
	public static String dateToStringDate(Date date) {
		return YYYY_MM_DD.format(date);
	}
	public static String YYYYMMDDMMHHSSSSS(Date date) {
		return YYYYMMDDMMHHSSSSS.format(date);
	}
	
	
	/**
	 * 获取当时日期时间串 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return YYYY_MM_DD_MM_HH_SS.format(new Date());
	}

	/**
	 * 获取当时日期串 格式 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDate() {
		return YYYY_MM_DD.format(new Date());
	}
	
	public static String getDateYMD() {
		return YYYYMMDD.format(new Date());
	}

	public static Date strToDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD_MM_HH_SS.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date strToYYMMDDDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long diffDays(Date startDate, Date endDate) {
		long days = 0L;
		long start = startDate.getTime();
		long end = endDate.getTime();
		days = (end - start) / 86400000L;
		return days;
	}

	public static Date dateAddMonth(Date date, int month) {
		return add(date, 2, month);
	}

	public static Date dateAddDay(Date date, int day) {
		return add(date, 6, day);
	}

	public static Date dateAddYear(Date date, int year) {
		return add(date, 1, year);
	}

	public static String remainDateToString(Date startDate, Date endDate) {
		StringBuilder result = new StringBuilder();
		if (endDate == null) {
			return "过期";
		}
		long times = endDate.getTime() - startDate.getTime();
		if (times < -1L) {
			result.append("过期");
		} else {
			long temp = 86400000L;

			long d = times / temp;

			times %= temp;
			temp /= 24L;
			long m = times / temp;

			times %= temp;
			temp /= 60L;
			long s = times / temp;

			result.append(d);
			result.append("天");
			result.append(m);
			result.append("小时");
			result.append(s);
			result.append("分");
		}
		return result.toString();
	}

	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	public static String getLinkUrl(boolean flag, String content, String id) {
		if (flag) {
			content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
		}
		return content;
	}

	public static long getTimeCur(String format, String date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(sf.format(date)).getTime();
	}

	public static long getTimeCur(String format, Date date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(sf.format(date)).getTime();
	}

	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		long lcc_time = Long.valueOf(cc_time).longValue();
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	public static String getTimeCurS(String format, Date date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return Convert.strToStr(String.valueOf(sf.parse(sf.format(date)).getTime()), "");
	}

	public static String getFormatTime(DateFormat format, Date date) throws ParseException {
		return format.format(date);
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	public static String getWeekDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch(dayOfWeek){
		case 1:return "周日";
		case 2:return "周一";
		case 3:return "周二";
		case 4:return "周三";
		case 5:return "周四";
		case 6:return "周五";
		case 7:return "周六";
		}
		return  "";
	}
}