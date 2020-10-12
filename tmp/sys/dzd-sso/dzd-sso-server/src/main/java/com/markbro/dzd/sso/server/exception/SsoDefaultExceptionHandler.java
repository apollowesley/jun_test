package com.markbro.dzd.sso.server.exception;



import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.entity.SsoException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *凡是方法标识@ResponseBody的遇到异常都返回为JSON格式
 */
@Component
public class SsoDefaultExceptionHandler extends AbstractHandlerMethodExceptionResolver {

    private boolean isAjax(HttpServletRequest request, HandlerMethod handlerMethod){
        if(handlerMethod.getMethodAnnotation(ResponseBody.class) != null||((request.getHeader("accept").contains("application/json")  || (request.getHeader("X-Requested-With")!= null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest") )))){
            return true;
        }
        return false;
    }
    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
        boolean isJson = isAjax(request,handlerMethod);

        // error result
        ReturnT<String> errorResult = null;
        if (ex instanceof SsoException) {
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.getMessage());
        } else {
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.toString().replaceAll("\n", "<br/>"));
        }

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
        } else {

            /*mv.addObject("exceptionMsg", errorResult.getMsg());
            mv.setViewName("/common/common.exception");*/
            try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print("{\"code\":"+errorResult.getCode()+", \"msg\":\""+ errorResult.getMsg() +"\"}");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return mv;
        }
    }

}
