<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.config;

import com.amez.util.JsonUtils;
import com.amez.util.RegexUtils;
import com.amez.util.ResultVo;
import com.amez.util.ResultVo.Code;
import com.amez.util.exception.AmezException;
import com.amez.util.exception.AmezException.ErrorVo;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

<#include "java_author.include">
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${Prefixes}{isDev:true}")
    private Boolean isDev;

    /***
     *
     * @param e
     * @return 拦截参数不合法错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<?> handleIllegalParamException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String tips = "参数不合法";
        if (allErrors.size() > 0) {
            if (isDev) {
                tips = e.getMessage();
            } else {
                tips = allErrors.get(0).getDefaultMessage();
            }
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, Code.ERROR_PARAMS, null, tips);
    }

    // org.springframework.http.converter.HttpMessageNotReadableException

    /***
     *
     * @param e
     * @return 拦截参数不合法错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVo<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String tips = "参数错误";
        logger.error(tips, e);
        if (isDev) {
            tips = e.getMessage();
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, Code.ERROR_PARAMS, null, tips);
    }

    // org.springframework.web.HttpMediaTypeNotSupportedException: Content type
    // 'text/plain;charset=UTF-8' not supported

    /***
     *
     * @param e
     * @return 拦截参数不合法错误异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultVo<?> handleHttpMessageNotReadableException(HttpMediaTypeNotSupportedException e) {
        String tips = "错误的数据类型";
        logger.error(tips, e);
        if (isDev) {
            tips = e.getMessage();
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, Code.ERROR_PARAMS, null, tips);
    }

    // TODO 拦截其它异常，如无相关权限

    // IllegalArgumentException

    /***
     *
     * @param e
     * @return 断言异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVo<?> handleIllegalArgumentException(IllegalArgumentException e) {
        String tips = "服务器开小差咯,请稍后再试";
        logger.error(tips, e);
        if (isDev) {
            tips = e.getMessage();
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, Code.ERROR_SERVER, null, tips);
    }

    /**
     * @param e
     * @return 拦截feign异常, 包含远程服务器主动或者被抛出的异常信息
     */
    @ExceptionHandler(FeignException.class)
    public ResultVo<?> handleFeignException(FeignException e) {
        Code errorCode = Code.ERROR_SERVER;
        String tips = "服务器开小差咯,请稍后再试";
        logger.error(tips, e);
        String msg = e.getMessage();
        try {
            int index = msg.indexOf("{");
            if (index != -1) {
                msg = msg.substring(msg.indexOf("{"), msg.length());
                Map<String, Object> exceptionMsgMap = JsonUtils.json2map(msg);
                String exceptionMessae = (String) exceptionMsgMap.get("message");
                String exceptionClass = (String) exceptionMsgMap.get("exception");
                if (AmezException.class.getName().equals(exceptionClass)) {
                    ErrorVo errorVo = JsonUtils.json2pojo(exceptionMessae, ErrorVo.class);
                    errorCode = errorVo.getErrorCode();
                    tips = errorVo.getErrorMsg();
                } else if (RegexUtils.hasChinese(exceptionMessae)) {
                    tips = exceptionMessae;
                }
            }
        } catch (Exception e1) {
            logger.error(tips, e);
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, errorCode, null, tips);
    }

    /***
     *
     * @param e
     * @return 拦截自定义异常
     */
    @ExceptionHandler(AmezException.class)
    public ResultVo<?> handleAmezException(AmezException e) {
        ErrorVo errorVo = new ErrorVo(Code.ERROR_SERVER);
        logger.error("", e);
        try {
            errorVo = JsonUtils.json2pojo(e.getMessage(), ErrorVo.class);
            if (errorVo.getErrorCode() == null) {
                errorVo.setErrorCode(Code.ERROR_SERVER);
                errorVo.setErrorMsg(Code.ERROR_SERVER.getLabel());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, errorVo.getErrorCode(), null, errorVo.getErrorMsg());
    }

    /***
     *
     * @param e
     * @return 拦截所有异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<?> handleAllException(Exception e) {
        String tips = "服务器开小差咯,请稍后再试";
        logger.error(tips, e);
        if (isDev) {
            tips = e.getMessage();
        }
        return ResultVo.getErrorVo(ResultVo.Status.ERROR, Code.ERROR_SERVER, null, tips);
    }

}