package com.jfire.codejson.function.impl.read.wrapper;

import java.lang.reflect.Type;
import com.jfire.codejson.function.JsonReader;

public class LongReader implements JsonReader
{
    
    @Override
    public Object read(Type entityClass, Object value)
    {
        return Long.valueOf((String) value);
    }
    
}
