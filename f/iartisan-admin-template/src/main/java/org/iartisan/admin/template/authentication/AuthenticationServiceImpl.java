package org.iartisan.admin.template.authentication;

import org.apache.shiro.authc.AuthenticationException;
import org.iartisan.admin.template.dao.model.SystemUserDO;
import org.iartisan.admin.template.enums.PageModelEnum;
import org.iartisan.runtime.constants.DataStatus;
import org.iartisan.runtime.env.EnvContextConfig;
import org.iartisan.runtime.web.authentication.AuthenticationService;
import org.iartisan.runtime.web.authentication.MenuTree;
import org.iartisan.runtime.web.authentication.RealmBean;
import org.iartisan.runtime.web.contants.WebConstants;
import org.iartisan.runtime.web.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * @author King
 * @since 2018/2/9
 */
@Service
public class AuthenticationServiceImpl extends AuthenticationService {

    @Autowired
    private UserSupportService userService;

    @Autowired
    private ResourceSupportService resourceSupportService;

    @Autowired
    private MenuSupportService menuSupportService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected RealmBean getRealmBean(String userName, String userPwd) {
        try {
            //判断用户名密码是否正确
            SystemUserDO userDO = userService.login(userName, userPwd);
            if (null == userDO) {
                throw new AuthenticationException("用户名或者密码错误");
            }
            if (userDO.getStatus().equals(DataStatus.D.name())) {
                throw new AuthenticationException("用户已注销");
            }
            //加载用户信息
            RealmBean bean = new RealmBean();
            bean.setUserName(userDO.getUserName());
            bean.setUserId(userDO.getUserId());
            //加载用户菜单列表
            List<MenuTree> trees = menuSupportService.getMenus(userDO.getUserId());
            if (null == trees) {
                throw new AuthenticationException("用户没有相应的操作权限");
            }
            bean.setMenuTrees(trees);
            WebUtil.getShiroSession().setAttribute(WebConstants._MENUS, trees);
            //加载页面样式
            WebUtil.getShiroSession().setAttribute("_pageModel", EnvContextConfig.get("iartisan.page.model", PageModelEnum.iframe.name()));
            return bean;
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("登录异常：", e);
            throw new AuthenticationException("系统繁忙");
        }
    }

    @Override
    protected Set<String> getPermissions(String userId) {
        //通过userId 获取可操作权限列表
        return resourceSupportService.getPermissionsByUserId(userId);
    }
}
