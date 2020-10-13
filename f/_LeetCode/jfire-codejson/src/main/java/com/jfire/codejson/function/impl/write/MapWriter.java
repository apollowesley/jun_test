package com.jfire.codejson.function.impl.write;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;
import com.jfire.codejson.function.WriterContext;

public class MapWriter extends WriterAdapter
{
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void write(Object target, StringCache cache)
    {
        cache.append('{');
        Set<Entry> set = ((Map) target).entrySet();
        for (Entry each : set)
        {
            if (each.getKey() != null && each.getValue() != null)
            {
                if (each.getKey() instanceof String)
                {
                    cache.append('"').append((String) each.getKey()).append("\":");
                }
                else
                {
                    cache.append('"');
                    WriterContext.write(each.getKey(), cache);
                    cache.append("\":");
                }
                if (each.getValue() instanceof String)
                {
                    cache.append('"').append((String) each.getValue()).append('"');
                }
                else
                {
                    WriterContext.write(each.getValue(), cache);
                }
                cache.append(',');
            }
        }
        if (cache.isCommaLast())
        {
            cache.deleteLast();
        }
        cache.append('}');
    }
    
}
