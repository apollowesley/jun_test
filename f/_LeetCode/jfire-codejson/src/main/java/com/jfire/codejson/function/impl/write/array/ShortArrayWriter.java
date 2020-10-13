package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class ShortArrayWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        short[] array = (short[]) target;
        cache.append('[');
        for (short each : array)
        {
            cache.append(each).append(',');
        }
        if (cache.isCommaLast())
        {
            cache.deleteLast();
        }
        cache.append(']');
    }
    
}
