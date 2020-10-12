package com.dtdream.rdic.saas.utils;

import java.math.BigDecimal;

/**
 * Created by Ozz8 on 2015/07/02.
 */
public class MathUtils {
    public static double add (double value1, double value2){
        BigDecimal v1 = new BigDecimal(Double.toString(value1));
        BigDecimal v2 = new BigDecimal(Double.toString(value2));
        return v1.add(v2).doubleValue();
    }

    public static Long max (Long l, Long r) {
        if (l < r)
            return r;
        return l;
    }
    public static Integer max (Integer l, Integer r) {
        return max(l.longValue(), r.longValue()).intValue();
    }

    public static Long min (Long l, Long r) {
        if (l < r)
            return l;
        return r;
    }
}
