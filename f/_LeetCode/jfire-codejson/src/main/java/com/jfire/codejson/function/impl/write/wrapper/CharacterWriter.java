package com.jfire.codejson.function.impl.write.wrapper;

import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class CharacterWriter extends WriterAdapter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        cache.append('"').append((Character) target).append('"');
    }
    
}
