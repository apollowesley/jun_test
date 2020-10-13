package com.jfire.codejson.function.impl.write;

import java.util.Iterator;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;
import com.jfire.codejson.function.WriterContext;

public class IteratorWriter extends WriterAdapter
{
    
    @SuppressWarnings("rawtypes")
    @Override
    public void write(Object target, StringCache cache)
    {
        cache.append('[');
        Iterator it = ((Iterable) target).iterator();
        Object value = null;
        while (it.hasNext())
        {
            if ((value = it.next()) != null)
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
