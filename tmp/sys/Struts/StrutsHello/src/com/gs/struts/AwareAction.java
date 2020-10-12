package com.gs.struts;

import org.apache.struts2.interceptor.*;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by WangGenshen on 2/26/16.
 */
public class AwareAction implements ApplicationAware, RequestAware, SessionAware, ParameterAware, ServletRequestAware, ServletResponseAware, ServletContextAware {

    private Map<String, Object> application;
    private Map<String, Object> request;
    private Map<String, Object> session;
    private Map<String, String[]> params;

    private HttpServletRequest servletRequest;
    private HttpServletResponse servletResponse;
    private ServletContext servletContext;

    @Override
    public void setApplication(Map<String, Object> map) {
        this.application = map;
    }

    @Override
    public void setRequest(Map<String, Object> map) {
        this.request = map;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    @Override
    public void setParameters(Map<String, String[]> map) {
        this.params = map;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.servletRequest = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String execute() {
        application.put("applicationKey", "applicationValue");
        session.put("sessionKey", "sessionValue");
        request.put("requestKey", "requestValue");
        servletRequest.setAttribute("requestKey", "requestValue1");
        servletContext.setAttribute("applicationKey", "applicationValue1");
        System.out.println(params.get("pname")[0]);
        return "success";
    }

}
