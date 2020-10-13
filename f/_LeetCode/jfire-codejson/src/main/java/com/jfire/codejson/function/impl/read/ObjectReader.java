package com.jfire.codejson.function.impl.read;

import java.lang.reflect.Type;
import com.jfire.codejson.function.JsonReader;

public class ObjectReader implements JsonReader
{
    
    @Override
    public Object read(Type entityType, Object value)
    {
        return value;
    }
    
}
