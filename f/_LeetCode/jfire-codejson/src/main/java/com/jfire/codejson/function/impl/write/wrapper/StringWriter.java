package com.jfire.codejson.function.impl.write.wrapper;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class StringWriter extends WriterAdapter
{
    
    public void write(Object target, StringCache cache)
    {
        cache.append('\"').append((String) target).append('\"');
    }
    
}
