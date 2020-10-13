package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class CharArrayWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        char[] array = (char[]) target;
        cache.append('[');
        for (char each : array)
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
