package com.tbc.soa.json.factories;


import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;

public class BigDecimalFactory implements ObjectFactory {

    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof Number ) {
            return new BigDecimal( ((Number)value).doubleValue() );
        } else {
            return new BigDecimal( value.toString() );
        }
    }
}
