package org.iartisan.admin.template.config.aspect;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author King
 * @since 2018/3/19
 */
@ControllerAdvice(basePackages = {"org.iartisan.admin.template.controller.support.page"})
public class SysControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = AuthorizationException.class)
    public String doAuthorizationException(AuthorizationException ex) {
        logger.error("AuthorizationException:{}", ex.getMessage());
        return "_error/unauthorized";
    }
}
