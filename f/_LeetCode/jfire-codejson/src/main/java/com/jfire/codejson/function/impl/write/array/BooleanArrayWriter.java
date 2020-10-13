package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class BooleanArrayWriter extends WriterAdapter
{

    @Override
    public void write(Object target, StringCache cache)
    {
        boolean[] array = (boolean[]) target;
        cache.append('[');
        for (boolean each : array)
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
