package cn.gson.framework.controller;

import cn.gson.framework.common.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.controller</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月30日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Slf4j
@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(Exception.class)
    public JsonResponse defaultExceptionHandler(Exception e) {
        log.error("Error", e);
        return JsonResponse.failure(e.getMessage());
    }
}
