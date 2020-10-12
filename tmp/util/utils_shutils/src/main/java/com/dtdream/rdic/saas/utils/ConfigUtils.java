package com.dtdream.rdic.saas.utils;

import java.util.HashMap;

/**
 * Created by Ozz8 on 2015/07/10.
 */
public class ConfigUtils {
    private HashMap<String, Object> configurations = new HashMap<>(0);
    public ConfigUtils() {

    }

    public Object config (String property) {
        return configurations.get(property);
    }

    public Object config (String property, Object value) {
        return configurations.put(property, value);
    }

    public HashMap<String, Object> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(HashMap<String, Object> configurations) {
        this.configurations = configurations;
    }
}
