package com.tbc.soa.json.factories;


import java.lang.reflect.Type;

import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;

public class FloatObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof Number ) {
            return ((Number)value).floatValue();
        } else {
            throw context.cannotConvertValueToTargetType( value, Float.class );
        }
    }
}
