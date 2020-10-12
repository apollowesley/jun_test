package cn.backflow.admin.common.secure;

import cn.backflow.lib.util.JsonMap;
import cn.backflow.lib.util.JsonUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统全局异常处理器
 * Created by Nandy on 2016/6/23.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ExceptionHandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
     * 捕获所有系统内部错误
     */
    @ExceptionHandler(Throwable.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        JsonMap json = new JsonMap();
        String msg = e.getLocalizedMessage();

        Throwable throwable = ExceptionUtils.getRootCause(e);
        if (throwable != null) {
            msg = throwable.getLocalizedMessage();
        }

        logger.error("GlobalExceptionHandler", e);
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        if (e instanceof MissingServletRequestParameterException) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            if (msg == null) {
                msg = "Missing parameter!";
            }
        }
        if (e instanceof NoHandlerFoundException) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            if (msg == null) {
                msg = "Not found!";
            }
        }
        if (e instanceof PermissionDeniedException) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            if (msg == null) {
                msg = "Permission denied!";
            }
        }
        if (e instanceof UnauthorizedException) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            if (msg == null) {
                msg = "Unauthorized!";
            }
        }
        json.msg(msg);
        return new ModelAndView(new MappingJackson2JsonView(JsonUtil.mapper), json);
    }
}