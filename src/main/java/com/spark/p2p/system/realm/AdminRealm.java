package com.spark.p2p.system.realm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.constant.Const;
import com.spark.p2p.dao.admin.AdminUserDao;
import com.spark.p2p.entity.Admin;
import com.spark.p2p.service.admin.RolePermissionService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DateUtil;

/**
 * 用户与权限验证
 * 
 * @author yanqizheng
 *
 */
public class AdminRealm extends AuthorizingRealm {
	private static Logger log = LoggerFactory.getLogger(AdminRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String currentUsername = (String) getAvailablePrincipal(principals);
		log.info("doGetAuthorizationInfo,user:" + currentUsername);
		List<String> permissionList = new ArrayList<String>();
		RolePermissionService rp = ApplicationUtil.getBean(RolePermissionService.class);
		Admin admin = (Admin) getSession(Const.SESSION_ADMIN);
		if (null == admin) {
			throw new AuthorizationException();
		}
		try {
			// 获取当前用户权限列表
			for (Map<String, String> pmss : rp.queryByRoleId(admin.getRoleId())) {
				if (!StringUtils.isEmpty(pmss.get("name"))) {
					permissionList.add(pmss.get("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthorizationException();
		}
		log.info("permission list {}", permissionList);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String password = String.valueOf(token.getPassword());
		log.info("password : " + password);
		String username = token.getUsername();

		Admin admin = null;
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getCredentials(),
				this.getName());
		AdminUserDao userDao = ApplicationUtil.getBean(AdminUserDao.class);
		try {
			admin = userDao.login(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (admin == null) {
			throw new AuthenticationException("用户名或密码错误");
		} else if (admin.getEnable() != 1) {
			throw new AuthenticationException("该用户已被禁用");
		} else {
			// 添加登录日志
			try {
				userDao.updateLoginLog(admin.getId(), token.getHost(), DateUtil.getDateTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSession(Const.SESSION_ADMIN, admin);
			return authcInfo;
		}
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			log.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	private Object getSession(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			return currentUser.getSession().getAttribute(key);
		}
		return null;
	}
}
