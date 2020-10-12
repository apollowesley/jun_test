package com.iotechn.iot.device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-09-17
 * Time: 下午2:09
 */
public class Const {

    public static final int PARAM_CACHE_DB_THRESHOLD_VALUE = 60 * 5;

    public static final List<Class> IGNORE_PARAM_LIST = new ArrayList<>();

    static {
        IGNORE_PARAM_LIST.add(Integer.class);
        IGNORE_PARAM_LIST.add(Long.class);
        IGNORE_PARAM_LIST.add(String.class);
        IGNORE_PARAM_LIST.add(Float.class);
        IGNORE_PARAM_LIST.add(Double.class);
        IGNORE_PARAM_LIST.add(Boolean.class);
        IGNORE_PARAM_LIST.add(Character.class);
        IGNORE_PARAM_LIST.add(Byte.class);
    }
}
