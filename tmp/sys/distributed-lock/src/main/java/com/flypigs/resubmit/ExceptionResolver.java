package com.flypigs.resubmit;

import java.lang.reflect.Method;

public class ExceptionResolver implements ResubmitResolver {
    @Override
    public boolean isReturn() {
        return false;
    }

    @Override
    public Object resolve(Method method) {
        throw new RuntimeException("不能重复提交");
    }
}
