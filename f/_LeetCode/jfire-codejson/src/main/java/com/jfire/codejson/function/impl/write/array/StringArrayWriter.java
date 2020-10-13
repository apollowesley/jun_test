package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class StringArrayWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        String[] array = (String[]) target;
        cache.append('[');
        for (String each : array)
        {
            cache.append('"').append(each).append("\",");
        }
        if (cache.isCommaLast())
        {
            cache.deleteLast();
        }
        cache.append(']');
    }
    
}
