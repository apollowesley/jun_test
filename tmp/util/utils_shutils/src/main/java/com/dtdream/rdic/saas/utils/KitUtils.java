package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.Result;

import java.util.Collection;
import java.util.Date;

public class KitUtils {
    /**
     * 仅当传入的全部字符串都不为null且不是空字符串时返回真
     * @param s
     * @return
     */
    public static Result check(String... s) {
        if (null == s)
            return Results.FAIL_NULL;
        for (int i = 0; i < s.length; ++ i)
            if (null == s[i])
                return Results.FAIL_NULL;
            else if (s[i].length() <= 0)
                return Results.FAIL_EMPTY;
        return Results.SUCCESS;
    }
    public static Result check(Date... o) {
        if (null == o)
            return Results.FAIL_NULL;
        for (int i = 0; i < o.length; ++ i)
            if (null == o[i])
                return Results.FAIL_NULL;
        return Results.SUCCESS;
    }
    public static Result check(boolean notnull, Double... d) {
        if (null == d)
            if (notnull)
                return Results.FAIL_NULL;
            else
                return Results.SUCCESS;
        for (int i = 0; i < d.length; ++ i)
            if (0.0 >= d[i])
                return Results.FAIL_NUMBER_LEQ_ZERO;
        return Results.SUCCESS;
    }

    public static Result check(Collection list, boolean nullable) {
        if (null == list)
            if (nullable)
                return Result.SUCCESS;
            else
                return Results.FAIL_NULL;
        else
            if (list.size() <= 0)
                return Result.FAILURE_EMPTY;
            else
                return Result.SUCCESS;
    }

    public static Result check (boolean nullable, Long... longs) {
        if (null == longs)
            return nullable ? Result.SUCCESS : Results.FAIL_NULL;
        for (int i = 0; i < longs.length; ++ i)
            if (null == longs[i] || longs[i].compareTo(0L) < 0)
                return Result.FAILURE;
        return Result.SUCCESS;
    }
}
