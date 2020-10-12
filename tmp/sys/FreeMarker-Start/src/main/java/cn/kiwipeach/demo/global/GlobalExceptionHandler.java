/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.global;

import cn.kiwipeach.demo.BusinessException;
import cn.kiwipeach.demo.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Create Date: 2018/01/16
 * Description: 全局异常处理器
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String DEFAULT_SYSCODE_EXCEPTION_VALUE = "SYS_10000";
    public static final String DEFAULT_BUSCODE_EXCEPTION_VALUE = "BUS_10000";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse<Exception> defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
        ResultResponse<Exception> responseResult = null;
        // 1.业务异常,使用业务编码和业务信息进行显示
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            String businessCode = businessException.getCode();
            String businessMessage = businessException.getMessage();
            //1.1 业务异常含有编码信息
            if (businessCode!= null) {
                responseResult = ResultResponse.fail(businessCode, businessMessage);
                //1.2 业务异常没有编码信息
            } else {
                responseResult = ResultResponse.fail(DEFAULT_BUSCODE_EXCEPTION_VALUE, businessMessage);
            }
            logger.error("业务系统异常:",ex);
            // 2.系统异常，使用系统默认编码，默认异常信息进行显示
        } else {
            logger.error("严重的系统异常:",ex);
            responseResult = ResultResponse.fail(DEFAULT_SYSCODE_EXCEPTION_VALUE, "请联系系统管理员解决此问题:"+ex.getMessage());
        }
        responseResult.setData(ex);
        return responseResult;
    }


}
