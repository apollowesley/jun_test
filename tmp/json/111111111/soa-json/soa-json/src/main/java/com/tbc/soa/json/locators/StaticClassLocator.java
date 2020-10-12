package com.tbc.soa.json.locators;


import java.util.Map;

import com.tbc.soa.json.ClassLocator;
import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.Path;

/**
 * Simple implementation for translating an object path to a single class.
 * Normally you would not use this class directly and use the
 * {@link com.tbc.soa.json.JSONDeserializer#use(String, Class)} method
 * instead.
 */
public class StaticClassLocator implements ClassLocator {
    private Class target;

    public StaticClassLocator(Class clazz) {
        target = clazz;
    }

    public Class locate(ObjectBinder context, Path currentPath) {
        return target;
    }
}
