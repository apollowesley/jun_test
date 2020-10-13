package org.iartisan.admin.template.config.aspect;

import org.apache.shiro.authz.AuthorizationException;
import org.iartisan.runtime.exception.NotAllowedException;
import org.iartisan.runtime.web.WebR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author King
 * @since 2018/3/19
 */
@RestControllerAdvice(basePackages = {"org.iartisan.admin.template.controller.support.rest"})
@ResponseBody
public class SysRestControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = AuthorizationException.class)
    public WebR doAuthorizationException(AuthorizationException ex) {
        logger.error("AuthorizationException:{}", ex.getMessage());
        WebR r = new WebR();
        r.isError("没有权限");
        return r;
    }

    @ExceptionHandler(value = NotAllowedException.class)
    public WebR doNotAllowedException(NotAllowedException ex) {
        logger.error("NotAllowedException:{}", ex.getMessage());
        WebR r = new WebR();
        r.isError(ex.getMessage());
        return r;
    }
}
