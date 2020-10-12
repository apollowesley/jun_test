package com.handy.controller;

import com.handy.common.constants.Constants;
import com.handy.service.entity.sys.SysAccount;
import lombok.val;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/28 14:24
 */
public abstract class BaseController implements ServletContextAware {
    @Override
    public void setServletContext(ServletContext servletContext) {

    }

    /**
     * 获取当前登录用户
     *
     * @param session
     * @return
     */
    protected SysAccount getLoginUser(HttpSession session) {
        val userVo = session.getAttribute(Constants.USERSESSION);
        if (userVo == null) {
            throw new RuntimeException("没有登录信息");
        } else {
            return (SysAccount) userVo;
        }
    }

}
