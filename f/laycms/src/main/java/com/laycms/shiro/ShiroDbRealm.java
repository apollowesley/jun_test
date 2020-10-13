package com.laycms.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.laycms.member.MemberService;
import com.laycms.member.entity.Member;
import com.laycms.member.entity.UserType;

public class ShiroDbRealm extends AuthorizingRealm{
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
		
	protected String authenticationQuery = "SELECT memberId, username,passwd,realName,locked, registerTime, salt FROM member WHERE username = ?";

    protected String userRolesQuery = "SELECT r.roleName FROM member u,memberrole ur,role r WHERE u.id=ur.id AND ur.roleId=r.id AND u.username=?";

    protected String permissionsQuery = "SELECT * FROM menu m,rolemenu rm,role r WHERE m.id=rm.menuId AND rm.roleId=r.id AND r.roleName=?";

    @Autowired
    private MemberService memberService;
  
    //登录认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (StringUtils.isEmpty(username)) {
			return null;
		}
    
        Member user = memberService.findByLoginName(username);
        if(user == null) {
        	log.error("This account[" + username + "] not found");
        	return null;
        }
        
        if(!user.getUserType().equals(UserType.CMSUSER)) {
			return null;
		}
        if(user.getLocked() != null && user.getLocked()) {
        	throw new UnknownAccountException("This account[" + username + "] has bean locked");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),user.getUsername());
        return info;
       

	}

    //权限认证
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
        String username = getAvailablePrincipal(principals).toString();
        Member user = memberService.findByLoginName(username);
        Set<String> roles = memberService.getRoleName(user);
       
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        
       /* Set<String> stringPermissions = new HashSet<>();
        stringPermissions.add("user:save");
        info.setStringPermissions(stringPermissions);*/
        
        return info;
	}

}
