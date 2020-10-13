package com.jfire.codejson.methodinfo.impl.write.array;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;

/**
 * 用于处理基本类型的数组情况
 * 
 * @author linbin
 * 
 */
public class ReturnArrayBaseMethodInfo extends AbstractWriteArrayMethodInfo
{
    
    public ReturnArrayBaseMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        
    }
    
    @Override
    protected void writeOneDim(Class<?> rootType, String bk)
    {
        if (strategy != null && strategy.containsType(rootType))
        {
            str += bk + "baseWriter.write(array[i1],cache);\n";
        }
        else
        {
            if (rootType.equals(char.class))
            {
                str += bk + "cache.append('\"').append(array1[i1]).append(\"\\\",\");\n";
            }
            else
            {
                str += bk + "cache.append(array1[i1]).append(',');\n";
            }
        }
    }
    
}
