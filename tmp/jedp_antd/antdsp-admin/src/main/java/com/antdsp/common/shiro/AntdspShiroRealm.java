package com.antdsp.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.antdsp.dao.jpa.UserJpa;
import com.antdsp.dao.jpa.system.MenuJpa;
import com.antdsp.data.entity.User;
import com.antdsp.data.entity.system.SystemMenu;
import com.antdsp.data.entityeenum.UserStatus;

public class AntdspShiroRealm extends AuthorizingRealm{
	
	@Autowired
	private UserJpa userJpa;
	
	@Autowired
	private MenuJpa menuJpa;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		User user = (User) principals.getPrimaryPrincipal();
		List<SystemMenu> permissMenus = this.menuJpa.findPermissMenus(user.getId());
		Set<String> permisses = new HashSet<>();
		for(SystemMenu menu : permissMenus) {
			String permission = menu.getPermission();
			if(!StringUtils.isEmpty(permission)) {
				permisses.add(permission);
			}
		}
		
		info.addStringPermissions(permisses);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		
		String username = passwordToken.getUsername();
		String password = new String(passwordToken.getPassword());
		
		User user = userJpa.queryUserByLoginName(username);
		if(user == null) {
			throw new UnknownAccountException("用户名/密码错误");
		}
		if(!user.getPassword().equals(password)) {
			throw new UnknownAccountException("用户名/密码错误");
		}
		if(UserStatus.DISABLED.equals(user.getStatus())) {
			throw new DisabledAccountException("用户名已经禁止使用");
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user , user.getPassword() , getName());
		
		return info;
	}

}
