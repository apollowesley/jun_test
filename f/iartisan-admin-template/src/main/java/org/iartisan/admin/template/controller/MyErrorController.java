package org.iartisan.admin.template.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * @author King
 * @since 2018/2/28
 */
@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

    @RequestMapping
    public String handleError(HttpServletRequest request) {
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "/_error/404";
        }
        return "";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
