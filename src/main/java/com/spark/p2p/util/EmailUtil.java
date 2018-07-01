package com.spark.p2p.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.spark.p2p.config.AppSetting;


public class EmailUtil {
	private JavaMailSenderImpl senderImpl;
	private static EmailUtil instance;
	
	private static EmailUtil getInstance(){
		if(instance == null){
			instance = new EmailUtil();
		}
		return instance;
	}
	
	public EmailUtil(){
		String host = AppSetting.MAIL_HOST;
		String username =AppSetting.MAIL_USERNAME;
		String password = AppSetting.MAIL_PASSWORD;
		
		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(host);
		senderImpl.setUsername(username);
		senderImpl.setPassword(password);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(javaMailProperties);
	}
	
	public void sendEmail(String subject,String receiver,String content) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		helper.setFrom(senderImpl.getUsername());
		helper.setTo(receiver);
		helper.setSubject(subject);
		helper.setText(content, true);
		senderImpl.send(msg);
	}
	
	public void sendAsyncEmail(String subject,String receiver,String content){
		ThreadPoolTaskExecutor  taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(30);
		taskExecutor.setCorePoolSize(10);
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendEmail(subject,receiver,content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
