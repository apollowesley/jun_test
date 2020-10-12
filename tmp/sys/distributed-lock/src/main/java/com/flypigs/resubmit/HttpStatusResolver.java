package com.flypigs.resubmit;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class HttpStatusResolver implements ResubmitResolver {
    @Override
    public boolean isReturn() {
        return true;
    }

    @Override
    public Object resolve(Method method) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            response.sendError(HttpStatus.LOCKED.value(), "Locked");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
