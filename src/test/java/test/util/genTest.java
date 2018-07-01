package test.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.GeneratorUtil;
import com.spark.p2p.util.ZipUtil;

public class genTest {
	
	//@Test
	public void testNumber(){
		for(int i=0;i<10;i++){
			System.out.println(GeneratorUtil.getRandomNumber(100, 10000));
		}
	}
	
	//@Test
	public void testList(){
		String info = "{\"company_name\":\"宁波众富世纪进出口有限公司\",\"company_code\":\"91330203MA281CAF2T(1-1)\",\"register_date\":\"2015年12月29日\",\"register_capital\":\"100万\",\"compay_address\":\"宁波市海曙区大沙泥街54号\",\"prev_annual_turnover\":\"0\"}";
		try{
			JSONObject json = new JSONObject(info);
			System.out.println(json.get("company_name"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
 
	//@Test
	public void testZip(){
		String path = "/Users/yanqizheng/Documents/zip";
		String targetFile = "/Users/yanqizheng/Documents/output/zip.zip";
		ZipUtil zip = new ZipUtil();
		zip.doZip(path,targetFile,"zip");
	}
 
	//@Test
	public void testCalendar(){
		Calendar calendar = Calendar.getInstance();
		int i = 1;
		List<String> list = new ArrayList<String>();
		while(i < 30){
			calendar.add(Calendar.DAY_OF_YEAR, -i);
			list.add(DateUtil.YYYY_MM_DD.format(calendar.getTime()));
			i ++;
		}
		System.out.println(list);
	}
	//@Test
	public void testTime() throws ParseException{
		String time = DateUtil.getFormatTime(DateUtil.YYYY_MM_DD, DateUtil.dateAddDay(DateUtil.dateAddMonth(DateUtil.strToDate("2017-06-13"+" 00:00:00"), -1),1));
		System.out.println(time);
	}
}
