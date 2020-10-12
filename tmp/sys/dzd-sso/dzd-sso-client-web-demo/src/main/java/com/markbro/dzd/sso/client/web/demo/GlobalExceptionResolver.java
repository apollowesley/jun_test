package com.markbro.dzd.sso.client.web.demo;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.entity.SsoException;
import com.markbro.dzd.sso.core.entity.SsoNotLoginException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 继承AbstractHandlerMethodExceptionResolver的方式和该类的异常处理方式不能同时用
 */
@ControllerAdvice
public class GlobalExceptionResolver {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex, HandlerMethod handlerMethod) {
        //当然也可以直接返回ModelAndView等类型，然后跳转相应的错误页面，这都根据实际的需要进行使用

        boolean isJson = isAjax(request,handlerMethod);
        int responseCode=200;
        // error result
        ReturnT<String> errorResult = null;
        if (ex instanceof SsoException) {
            responseCode=ReturnT.FAIL_CODE;
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.getMessage());
        }else if(ex instanceof SsoNotLoginException) {
            responseCode=SsoConf.SSO_LOGIN_FAIL_RESULT.getCode();
            errorResult = new ReturnT<String>(SsoConf.SSO_LOGIN_FAIL_RESULT.getCode(), ex.getMessage());
        }else if(ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {//404
            responseCode=404;
            errorResult = new ReturnT<String>(404, ex.getMessage());
        }else if(ex instanceof Throwable){
            responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            errorResult = new ReturnT<String>(responseCode, ex.getMessage());
        }else{
            responseCode=ReturnT.FAIL_CODE;
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.toString().replaceAll("\n", "<br/>"));
        }
        response.setStatus(responseCode);
        // response
        ModelAndView mv = new ModelAndView();
        if (isJson) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print("{\"code\":"+errorResult.getCode()+", \"msg\":\""+ errorResult.getMsg() +"\"}");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return mv;
        } else { //返回VIEW
            mv.addObject("code", responseCode);
            mv.addObject("msg", errorResult.getMsg());
            mv.setViewName("/common/error");
            return mv;
        }
    }

    private static boolean isAjax(HttpServletRequest request, HandlerMethod handlerMethod){
        if(handlerMethod.getMethodAnnotation(ResponseBody.class) != null||((request.getHeader("accept").contains("application/json")  || (request.getHeader("X-Requested-With")!= null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest") )))){
            return true;
        }
        return false;
    }
}
