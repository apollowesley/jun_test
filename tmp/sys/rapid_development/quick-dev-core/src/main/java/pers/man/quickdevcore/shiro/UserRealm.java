package pers.man.quickdevcore.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:56
 * @project quick-dev
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * 加密盐值
     */
    @Value("${shiro.salt}")
    private String salt;

    /**
     * 授权认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户信息
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();

        //查询数据库 获取用户的角色或权限
        HashSet<String> roles = new HashSet<>();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //把AuthenticationToken转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //从UsernamePasswordToken中获取username
        String username = usernamePasswordToken.getUsername();

        //查询数据库

        //用户不存在,则可以抛出UnknownAccountException
        if (Objects.equals(username, "")) {
            throw new UnknownAccountException("此用户不存在");
        }
        //用户是否被ban
        if (Objects.equals(username, "")) {
            throw new LockedAccountException("该账户已被锁定,请联系管理员解锁");
        }

        //principal:认证的实体信息,可以是username,也可以是数据表对应的用户实体类
        Object principal = username;
        //credentials:密码
        Object credentials = "";
        //realmName:当前realm对象的name,调用父类的getName();
        String realmName = getName();
        //盐值 指定盐值+用户名
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt + username);
        //根据用户信息,构建AuthenticationInfo对象并返回
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return authenticationInfo;
    }
}
