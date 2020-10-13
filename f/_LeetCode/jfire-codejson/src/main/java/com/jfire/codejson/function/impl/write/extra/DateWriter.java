package com.jfire.codejson.function.impl.write.extra;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.WriterAdapter;

public class DateWriter extends WriterAdapter
{
    private static ThreadLocal<SimpleDateFormat> formats = new ThreadLocal<SimpleDateFormat>() {
                                                             protected SimpleDateFormat initialValue()
                                                             {
                                                                 return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                             }
                                                         };
    
    @Override
    public void write(Object target, StringCache cache)
    {
        cache.append('\"').append(formats.get().format((Date) target)).append('\"');
    }
    
}
