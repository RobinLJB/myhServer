package com.spark.p2p.core;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.spark.p2p.dao.admin.RolePermissionDao;
import com.spark.p2p.entity.Menu;
import com.sparkframework.sql.DB;

/**
 * 权限定义
 * 
 * @author yanqizheng
 *
 */
public class ShiroChainDefinition implements FactoryBean<Ini.Section> {
	@Autowired
	private RolePermissionDao resourceDao;
	private String filterChainDefinitions;
	private DataSource dataSource;
	
	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[%s]";

	public Section getObject() throws Exception {
		new DB(dataSource, true);
		List<Menu> list = resourceDao.queryRules();
		Ini ini = new Ini();
		// 加载默认的规则
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 键就是链接URL,值就是存在什么条件才能访问该链接
		for (Iterator<Menu> it = list.iterator(); it.hasNext();) {
			Menu menu = it.next();
			// 如果不为空值添加到section中
			if (StringUtils.isNotEmpty(menu.getUrl()) && StringUtils.isNotEmpty(menu.getName())) {
				section.put(menu.getUrl() + "*", String.format(PREMISSION_STRING, menu.getName()));
			}
		}
		section.put("/admin/**", "authc");
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
