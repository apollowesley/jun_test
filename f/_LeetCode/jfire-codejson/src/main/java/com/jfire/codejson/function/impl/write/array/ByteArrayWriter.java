package com.jfire.codejson.function.impl.write.array;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class ByteArrayWriter extends WriterAdapter
{
    @Override
    public void write(Object target, StringCache cache)
    {
        byte[] array = (byte[]) target;
        cache.append('[');
        for (byte each : array)
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
