package com.dtdream.rdic.saas.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ozz8 on 2015/07/08.
 */
public class ScheUtils {
    private static volatile ConcurrentHashMap<Object, Object[]> jobs = new ConcurrentHashMap<>();
}
