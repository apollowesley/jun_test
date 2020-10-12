package com.cnhuashao.rapiddevelopment.core.demo4.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类 {@code XssFilter} Xss防止注入拦截器 <br> 用于过滤web请求中关于xss相关攻击的特定字符.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/5 19:10
 */
@Slf4j
public class XssFilter implements Filter {

    //private Logger log = LoggerFactory.getLogger(XssFilter.class);

    /**
     * 是否过滤富文本内容
     */
    private static boolean IS_INCLUDE_RICH_TEXT = false;

    /**
     * 预设定白名单地址
     * 将根据该变量中设置的相关目录进行直接放行操作。
     */
    public List<String> excludes = new ArrayList<>();

    /**
     * 拦截器核心处理单元
     * 用于处理所有需要过滤的请求，在此进行确认其合法性
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
        if(log.isDebugEnabled()){
            log.debug("-------------------- get into xss filter --------------------");
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //进行白名单过滤，如符合白名单，则直接放行
        if(handleExcludeUrl(req, resp)){
            filterChain.doFilter(request, response);
            return;
        }
        //开始进行深度过滤，判定其携带参数是否合法
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,IS_INCLUDE_RICH_TEXT);
        filterChain.doFilter(xssRequest, response);
    }

    /**
     *  白名单过滤器
     * @param request 拦截的请求
     * @param response 拦截的响应
     * @return  是否符合白名单
     */
    private boolean handleExcludeUrl(HttpServletRequest request, HttpServletResponse response) {
        //白名单为空时直接返回false，使其向下执行
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        //提取访问的URL地址
        String url = request.getServletPath();
        log.info("开始进行过滤{} {}",new Date(),url);
        //开始根据白名单地址进行判定，如符合则直接放行
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 初始化拦截器配置
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(log.isDebugEnabled()){
            log.debug("----------------- xss filter init -----------------");
        }
        //获取其初始化时预设置的深度过滤开关，根据其预设的true、false进行确定其是否开启深度拦截
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if(StringUtils.isNotBlank(isIncludeRichText)){
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
        }
        //获取其初始化时预设置的白名单字符串，根据【，】符号进行截取存储。
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }
}
