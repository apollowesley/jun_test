package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class DoubleArrayWriter extends WriterAdapter
{
    @Override
    public void write(Object target, StringCache cache)
    {
        double[] array = (double[]) target;
        cache.append('[');
        for (double each : array)
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
