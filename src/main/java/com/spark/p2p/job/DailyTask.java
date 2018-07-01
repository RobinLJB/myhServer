package com.spark.p2p.job;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DateUtil;

public class DailyTask extends QuartzJobBean  {
	private static Log log = LogFactory.getLog(DailyTask.class);
	public static StatisticsService ss;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		ss = ApplicationUtil.getBean(StatisticsService.class);
		log.info("每日数据统计开始...");
		statisticTask();
		log.info("每日数据统计结束...");
	}
	
	/**
	 * 每日统计
	 */
	public void statisticTask(){
		try{	
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			String lastDay = DateUtil.YYYY_MM_DD.format(calendar.getTime());
			Map<String,Object> dataMap = ss.analyseDailyData(lastDay);
			log.info(dataMap);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}