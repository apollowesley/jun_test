package com.flypigs.resubmit;

import java.lang.reflect.Method;

public interface ResubmitResolver {

    boolean isReturn();

    Object resolve(Method method);

    default boolean isMatch(Resubmit resubmit) {
        return this.getClass() == resubmit.resolver();
    }
}
