/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.web.Ctrl;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.annotation.RequestMapping;
import org.jiucheng.web.annotation.RequestMethod;
import org.jiucheng.web.util.WebUtil;

public class HandlerMapping {
    private static final Map<String, List<Ctrl>> NOT_REGEX_CTRL = new HashMap<String, List<Ctrl>>();
    private static final Map<String, List<Ctrl>> REGEX_CTRL = new HashMap<String, List<Ctrl>>();
    
    public static final void put(String v, RequestMapping rm, String h, String b, Method m) {
        Ctrl ctrl = new Ctrl();
        ctrl.setRoute(v);
        ctrl.setMethod(m);
        ctrl.setRequestMapping(rm);
        ctrl.setBeanName(b);
        ctrl.setHandlerBeanName(h);
        if(v.matches(".*\\(.+\\).*") || v.indexOf("?") != -1) {
            if(!REGEX_CTRL.containsKey(v)) {
                REGEX_CTRL.put(v, new ArrayList<Ctrl>(4));
            }
            REGEX_CTRL.get(v).add(ctrl);
        }else {
            if(!NOT_REGEX_CTRL.containsKey(v)) {
                NOT_REGEX_CTRL.put(v, new ArrayList<Ctrl>(4));
            }
            NOT_REGEX_CTRL.get(v).add(ctrl);
        }
    }
    
    public static WebWrapper getWebWrapper() {
        return createWebHandler(WebUtil.getRequest(), WebUtil.getResponse(), WebUtil.getPathInfo());
    }
    
    private static WebWrapper createWebHandler(HttpServletRequest request, HttpServletResponse response, String pathInfo) {
        String m = request.getMethod();
        List<Ctrl> ctrls = NOT_REGEX_CTRL.get(pathInfo);
        Ctrl defCtrl = null;
        if(ctrls != null) {
            for(Ctrl ctrl : ctrls) {
                RequestMethod[] rms = ctrl.getRequestMapping().method();
                if(rms.length == 0) {
                    defCtrl = ctrl;
                    continue;
                }
                for(RequestMethod rm : rms) {
                    if(rm.toString().equalsIgnoreCase(m)) {
                        WebWrapper ww = new WebWrapper();
                        ww.setCtrl(ctrl);
                        return ww;
                    }
                }
            }
            if(defCtrl != null) {
                WebWrapper ww = new WebWrapper();
                ww.setCtrl(defCtrl);
                return ww;
            }
        }
        Matcher matcher;
        for(String k : REGEX_CTRL.keySet()) {
            matcher = Pattern.compile(k).matcher(pathInfo);
            if(matcher.matches()) {
                List<Ctrl> v = REGEX_CTRL.get(k);
                for(Ctrl c : v) {
                    RequestMethod[] rms = c.getRequestMapping().method();
                    if(rms.length == 0) {
                        defCtrl = c;
                    }
                    for(RequestMethod rm : rms) {
                        if(rm.toString().equalsIgnoreCase(m)) {
                            WebWrapper ww = new WebWrapper();
                            ww.setCtrl(c);
                            ww.setMatcher(matcher);
                            return ww;
                        }
                    }
                }
                if(defCtrl != null) {
                    WebWrapper ww = new WebWrapper();
                    ww.setCtrl(defCtrl);
                    ww.setMatcher(matcher);
                    return ww;
                }
            }
        }
        return null;
    }
}
