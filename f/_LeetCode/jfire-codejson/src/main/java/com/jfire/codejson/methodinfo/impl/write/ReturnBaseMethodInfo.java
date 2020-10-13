package com.jfire.codejson.methodinfo.impl.write;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.util.NameTool;

/**
 * 用于对基础类的处理
 * 
 * @author linbin
 * 
 */
public class ReturnBaseMethodInfo extends AbstractWriteMethodInfo
{
    
    public ReturnBaseMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        Class<?> returnType = method.getReturnType();
        String fieldName = NameTool.getNameFromMethod(method, strategy);
        str = "cache.append(\"\\\"" + fieldName + "\\\":\");\n";
        String key = method.getDeclaringClass().getName() + '.' + fieldName;
        if (strategy != null && (strategy.containsType(returnType) || strategy.containsField(key)))
        {
            if (strategy.containsField(key))
            {
                str += "writeStrategy.getWriterByField(\"" + key + "\").write(" + getValue + ",cache);\n";
            }
            else
            {
                str += "writeStrategy.getWriter(" + returnType.getName() + ".class).write(" + getValue + ",cache);\n";
            }
            str += "cache.append(',');\n";
        }
        else
        {
            if (returnType.equals(char.class))
            {
                str += "cache.append('\"').append(" + getValue + ").append('\"').append(',');\n";
            }
            else
            {
                str += "cache.append(" + getValue + ").append(',');\n";
            }
        }
    }
}
