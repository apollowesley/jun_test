package com.jfire.codejson.methodinfo.impl.write.array;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;

public class ReturnArrayWrapperMethodInfo extends AbstractWriteArrayMethodInfo
{
    public ReturnArrayWrapperMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        
    }
    
    @Override
    protected void writeOneDim(Class<?> rootType, String bk)
    {
        if (strategy != null && strategy.containsType(rootType))
        {
            str += bk + "baseWriter.write(array1[i1],cache);\n";
            str += bk + "cache.append(',');\n";
        }
        else
        {
            if (rootType.equals(Character.class) || rootType.equals(String.class))
            {
                str += bk + "cache.append('\"').append(array1[i1]).append('\"').append(',');\n";
            }
            else
            {
                str += bk + "cache.append(array1[i1]).append(',');\n";
            }
        }
    }
    
}
