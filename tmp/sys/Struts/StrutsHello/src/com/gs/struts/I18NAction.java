package com.gs.struts;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.I18nInterceptor;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * Created by WangGenshen on 4/8/16.
 */
public class I18NAction extends ActionSupport implements RequestAware, SessionAware {

    private Map<String, Object> request;
    private Map<String, Object> session;

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toI18NPage() {
        System.out.println(getText("username")); // Action中可以使用getName(String)方法获取配置的国际化资源
        return SUCCESS;
    }

    public String change() {
//        Object obj = request.get(I18nInterceptor.DEFAULT_PARAMETER); // request_locale
//        if (obj != null) {
//            session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, obj); // WW_TRANS_I18N_LOCALE
//        }
        return SUCCESS;
    }

    @Override
    public void setRequest(Map<String, Object> map) {
        this.request = map;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
