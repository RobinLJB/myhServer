package com.spark.p2p.job;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.StatisticsService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.SMSUtil;
import com.sparkframework.lang.Convert;
import com.sparkframework.sql.model.Model;

public class ProcessTrasactionTask extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(DailyTask.class);
	private static MemberService memberService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		memberService = ApplicationUtil.getBean(MemberService.class);
		try {
			statisticTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void statisticTask() throws Exception{
		
		
	}

}
