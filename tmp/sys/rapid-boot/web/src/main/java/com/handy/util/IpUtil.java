package com.handy.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hs
 * @Description: {}
 * @date 2019/12/4 17:58
 */
public class IpUtil {

    /**
     * 获取ip
     *
     * @param req
     * @return
     */
    public static String getIp(HttpServletRequest req) {
        String Xip = req.getHeader("X-Real-IP");
        String XFor = req.getHeader("X-Forwarded-For");
        if (StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("HTTP_CLIENT_IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getRemoteAddr();
        }
        //ip地址最长50
        if (XFor != null && XFor.length() > 50) {
            XFor = XFor.substring(0, 50);
        }
        return XFor;
    }
}
