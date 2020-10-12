package com.tbc.soa.json.factories;


import java.lang.reflect.Type;

import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;

public class StringObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        return value;
    }
}
