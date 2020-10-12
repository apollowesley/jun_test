package com.tbc.soa.json.factories;


import java.lang.reflect.Type;
import java.util.Collection;
import java.util.TreeSet;

import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;

public class SortedSetObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof Collection) {
            return context.bindIntoCollection( (Collection)value, new TreeSet(), targetType);
        } else {
            TreeSet<Object> set = new TreeSet<Object>();
            set.add( context.bind( value ) );
            return set;
        }
    }
}
