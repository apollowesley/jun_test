package com.slavic.veles.utils;

import org.springframework.beans.BeanUtils;

public class BeanUtil extends BeanUtils {

    public static <T> T copyObject(Object source, T target) {
        BeanUtils.copyProperties(source,target);
        return target;
    }
}
