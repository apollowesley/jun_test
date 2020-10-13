package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class LongArrayWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        long[] array = (long[]) target;
        cache.append('[');
        for (long each : array)
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
