package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class FloatArrayWriter extends WriterAdapter
{
    @Override
    public void write(Object target, StringCache cache)
    {
        float[] array = (float[]) target;
        cache.append('[');
        for (float each : array)
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
