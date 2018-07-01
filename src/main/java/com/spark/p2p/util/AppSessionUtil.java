package com.spark.p2p.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import com.spark.p2p.entity.member.Member;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class AppSessionUtil {
	public static int timeToLive = 24*3600;
	private static Ehcache cache;
	
	static{
		EhCacheFactoryBean cacheFactory = ApplicationUtil.getBean(EhCacheFactoryBean.class);
		cache = cacheFactory.getObject();
	}
	
	public static boolean removeToken(String key){
		return cache.remove(key);
	}
	
	/**
	 * 获取TokenObject
	 * @param key
	 * @return
	 */
	public static TokenObject getToken(String key){
		Element element = cache.get(key);
		if(element != null){
			return (TokenObject)element.getValue();
		}
		else return null;
	}
	
	/**
	 * 创建TokenObject
	 * @param user
	 * @return
	 */
	public static TokenObject createToken(Member user,int platform){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.SECOND, timeToLive);
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		TokenObject obj = new TokenObject();
		user.setToken(token);
		obj.set("member", user);
		obj.setExpireTime(c.getTime());
		obj.set("platform", platform);
		Element element = new Element(token,obj);
		//间隔 timeToLive没有访问后失效
		element.setTimeToIdle(timeToLive);
		cache.put(element);
		return obj;
	}
}
