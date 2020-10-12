package com.jfast.web.zuul;

import com.alibaba.fastjson.JSONObject;
import com.jfast.web.common.core.constants.Constants;
import com.jfast.web.common.core.utils.ResultCode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/10/1 21:11
 */
@Component
public class JfastZuulFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //四种类型：pre,routing,error,post
    //pre：主要用在路由映射的阶段是寻找路由映射表的
    //routing:具体的路由转发过滤器是在routing路由器，具体的请求转发的时候会调用
    //error:一旦前面的过滤器出错了，会调用error过滤器。
    //post:当routing，error运行完后才会调用该过滤器，是在最后阶段的
    @Override
    public String filterType() {
        return "pre";
    }

    //自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    //控制过滤器生效不生效，可以在里面写一串逻辑来控制
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //执行过滤逻辑
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String targetUrl = getRequestUrl(request);
        if (Constants.OAUTH2_LOGGIN_URL.equals(targetUrl)) {
            checkCode(requestContext, request);
        }
        return null;
    }

    /**
     * 验证码校验
     * @param requestContext
     * @param request
     */
    private void checkCode(RequestContext requestContext, HttpServletRequest request) {
        String code = request.getParameter("code");
        String key = request.getParameter("key");
        String sessionImageCode = stringRedisTemplate.opsForValue().get(Constants.IMAGE_CODE + key);
        if (code != null && !code.equalsIgnoreCase(sessionImageCode)) {
            renderJson(requestContext, new ResultCode(ResultCode.FAIL, "验证码输入错误"));
        }
    }

    private void renderJson(RequestContext requestContext, ResultCode resultCode) {
        requestContext.setSendZuulResponse(false);
        // 解决json 乱码
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseStatusCode(HttpStatus.SC_OK); // 设置响应码
        requestContext.setResponseBody(JSONObject.toJSONString(resultCode));
    }

    private String getRequestUrl(HttpServletRequest request) {
        String contentPath = request.getServletContext().getContextPath();
        int contentPathLength = contentPath.length();
        String target = request.getRequestURI();
        if (contentPathLength != 0){
            target = target.substring(contentPathLength);
        }
        return target;
    }
}
