package com.jfire.codejson.function.impl.read.array;

import java.lang.reflect.Type;
import com.jfire.codejson.JsonArray;
import com.jfire.codejson.function.JsonReader;

public class ShortArrayReader implements JsonReader
{
    @Override
    public Object read(Type entityType, Object value)
    {
        JsonArray jsonArray = (JsonArray) value;
        int size = jsonArray.size();
        short[] array = new short[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = jsonArray.getShort(i);
        }
        return array;
    }
}
