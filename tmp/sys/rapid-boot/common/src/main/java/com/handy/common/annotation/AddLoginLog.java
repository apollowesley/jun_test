package com.handy.common.annotation;


import com.handy.common.constants.LogLoginCategoryEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hs
 * @Description: {}
 * @date 2019/12/3 15:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddLoginLog {
    /**
     * 类型
     *
     * @return
     */
    LogLoginCategoryEnum logLoginCategory();
}
