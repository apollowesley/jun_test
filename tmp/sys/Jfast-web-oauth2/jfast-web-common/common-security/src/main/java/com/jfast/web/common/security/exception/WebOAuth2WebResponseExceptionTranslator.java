package com.jfast.web.common.security.exception;

import com.jfast.web.common.core.config.BusinessException;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.core.utils.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @author Pan Weilong
 * @date 2019/7/10 9:39
 * @description: 登录抛异常 比如用户或者密码/scope报错接住的异常
 */
@Component
public class WebOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        // 异常栈获取 OAuth2Exception 异常
        Exception exception = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ObjectUtils.isNotEmpty(exception)) {
            return handleOAuth2Exception((OAuth2Exception) exception);
        }

        // 刷新token失败
        exception = (OAuth2Exception)throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (ObjectUtils.isNotEmpty(exception)) {
            return handleOAuth2Exception((OAuth2Exception) exception);
        }

        exception = (Exception) throwableAnalyzer.getFirstThrowableOfType(WebOAuth2Exception.class, causeChain);
        if (ObjectUtils.isNotEmpty(exception)) {
            return handleOAuth2Exception((OAuth2Exception) exception);
        }

        // 异常栈中有OAuth2Exception
        if (exception != null) {
            return handleOAuth2Exception((OAuth2Exception) exception);
        }

        exception = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,causeChain);
        if (exception != null) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        exception = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (exception instanceof AccessDeniedException) {
            return handleOAuth2Exception(new ForbiddenException(exception.getMessage(), exception));
        }

        exception = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new ForbiddenException(exception.getMessage(), exception));
        }

        // 不包含上述异常则服务器内部错误
        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }
        //客户端异常直接返回客户端,不然无法解析
        if (e instanceof ClientAuthenticationException) {
            return new ResponseEntity<>(e, headers,
                    HttpStatus.valueOf(status));
        } else if (e instanceof WebOAuth2Exception) {
            return new ResponseEntity(((WebOAuth2Exception) e).getResultCode(), headers,
                    HttpStatus.valueOf(status));
        } else if (e instanceof InvalidGrantException) {
            return new ResponseEntity(ResultCode.TOKEN_INVALID, headers,
                    HttpStatus.valueOf(status));
        }
        String message = e.getMessage();
        WebOAuth2Exception exception = new WebOAuth2Exception(message, e);
        return new ResponseEntity<OAuth2Exception>(exception, headers, HttpStatus.valueOf(status));
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }
        @Override
        public int getHttpErrorCode() {
            return 401;
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }
        @Override
        public int getHttpErrorCode() {
            return 500;
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }
        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }
        @Override
        public int getHttpErrorCode() {
            return 403;
        }
    }

}
