package com.jfire.codejson.function.impl.read.wrapper;

import java.lang.reflect.Type;
import com.jfire.codejson.function.JsonReader;

public class FloatReader implements JsonReader
{
    
    @Override
    public Object read(Type entityClass, Object value)
    {
        return Float.valueOf((String) value);
    }
    
}
