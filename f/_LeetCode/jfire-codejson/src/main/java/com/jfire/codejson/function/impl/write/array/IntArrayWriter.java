package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class IntArrayWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        int[] array = (int[]) target;
        cache.append('[');
        for (int each : array)
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
