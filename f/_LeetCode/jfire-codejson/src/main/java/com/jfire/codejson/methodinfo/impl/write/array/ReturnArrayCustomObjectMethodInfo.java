package com.jfire.codejson.methodinfo.impl.write.array;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;

public class ReturnArrayCustomObjectMethodInfo extends AbstractWriteArrayMethodInfo
{
    
    public ReturnArrayCustomObjectMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
    }
    
    @Override
    protected void writeOneDim(Class<?> rootType, String bk)
    {
        str += bk + "if(array1[i1]!=null)\n";
        str += bk + "{\n";
        if (strategy != null)
        {
            str += bk + "\twriteStrategy.getWriter(array1[i1].getClass()).write(array1[i1],cache);\n";
        }
        else
        {
            str += bk + '\t' + "WriterContext.write(array1[i1],cache);\n";
        }
        str += bk + '\t' + "cache.append(',');\n";
        str += bk + "}\n";
    }
}
