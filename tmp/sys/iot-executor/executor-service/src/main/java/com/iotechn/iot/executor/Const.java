package com.iotechn.iot.executor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-11
 * Time: 下午11:43
 */
public class Const {

    public static final List<Class> IGNORE_PARAM_LIST = new ArrayList<>();

    static {
        IGNORE_PARAM_LIST.add(Integer.class);
        IGNORE_PARAM_LIST.add(Long.class);
        IGNORE_PARAM_LIST.add(String.class);
        IGNORE_PARAM_LIST.add(Float.class);
        IGNORE_PARAM_LIST.add(Double.class);
        IGNORE_PARAM_LIST.add(Boolean.class);
    }
}
