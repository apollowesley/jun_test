package com.tbc.soa.json.factories;


import java.lang.reflect.Type;

import com.tbc.soa.json.JSONException;
import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;

public class EnumObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof String ) {
            return Enum.valueOf( (Class)targetType, value.toString() );
        } else {
            throw new JSONException( String.format("%s:  Don't know how to convert %s to enumerated constant of %s", context.getCurrentPath(), value, targetType ) );
        }
    }
}
