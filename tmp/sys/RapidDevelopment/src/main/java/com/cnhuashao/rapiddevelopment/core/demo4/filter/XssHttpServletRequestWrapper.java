package com.cnhuashao.rapiddevelopment.core.demo4.filter;

import com.cnhuashao.rapiddevelopment.core.demo4.util.FilterCoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 类 {@code XssHttpServletRequestWrapper} Xss核心匹配类 <br> .
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/5 19:30
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 需要进行过滤的请求
     */
    HttpServletRequest orgRequest = null;
    /**
     * 是否启用过滤
     */
    private boolean isIncludeRichText = false;

    /**
     * 深度过滤构造方法
     * @param request 需要过滤的请求
     * @param isIncludeRichText 是否进行过滤，默认false
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        Boolean flag = ("content".equals(name) || name.endsWith("WithHtml"));
        if( flag && !isIncludeRichText){
            return super.getParameter(name);
        }
        name = FilterCoreUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = FilterCoreUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if(arr != null){
            for (int i=0;i<arr.length;i++) {
                arr[i] = FilterCoreUtil.clean(arr[i]);
            }
        }
        return arr;
    }


    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = FilterCoreUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = FilterCoreUtil.clean(value);
        }
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }

        return req;
    }
}
