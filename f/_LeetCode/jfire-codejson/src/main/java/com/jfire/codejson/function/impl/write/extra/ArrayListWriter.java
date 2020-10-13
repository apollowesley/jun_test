package com.jfire.codejson.function.impl.write.extra;

import java.util.ArrayList;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;
import com.jfire.codejson.function.WriterContext;

public class ArrayListWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        ArrayList<?> list = (ArrayList<?>) target;
        cache.append('[');
        int size = list.size();
        Object value = null;
        for (int i = 0; i < size; i++)
        {
            if ((value = list.get(i)) != null)
            {
                if (value instanceof String)
                {
                    cache.append('"').append((String) value).append('"');
                }
                else
                {
                    WriterContext.write(value, cache);
                    
                }
                cache.append(',');
            }
        }
        if (cache.isCommaLast())
        {
            cache.deleteLast();
        }
        cache.append(']');
    }
    
}
