package com.jfire.codejson.methodinfo.impl.write;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.util.NameTool;

/**
 * 用于处理对基础类的包装类，同时也包含对String的处理
 * 
 * @author linbin
 * 
 */
public class ReturnWrapperMethodInfo extends AbstractWriteMethodInfo
{
    public ReturnWrapperMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        Class<?> returnType = method.getReturnType();
        String fieldName = NameTool.getNameFromMethod(method, strategy);
        str = "" + returnType.getSimpleName() + " " + fieldName + " = " + getValue + ";\n";
        str += "if(" + fieldName + "!=null)\n{\n";
        String key = method.getDeclaringClass().getName() + '.' + fieldName;
        if (strategy != null && (strategy.containsType(returnType) || strategy.containsField(key)))
        {
            str += "\tcache.append(\"\\\"" + fieldName + "\\\":\");\n";
            if (strategy.containsField(key))
            {
                str += "\tJsonWriter writer = writeStrategy.getWriterByField(\"" + key + "\");\n";
            }
            else
            {
                str += "\tJsonWriter writer = writeStrategy.getWriter(" + fieldName + ".getClass());\n";
            }
            str += "\twriter.write(" + fieldName + ",cache);\n";
            str += "\tcache.append(',');\n";
            str += "}\n";
        }
        else
        {
            if (returnType.equals(String.class) || returnType.equals(Character.class))
            {
                str += "\tcache.append(\"\\\"" + fieldName + "\\\":\\\"\").append(" + fieldName + ").append(\"\\\",\");\n";
            }
            else
            {
                str += "\tcache.append(\"\\\"" + fieldName + "\\\":\").append(" + fieldName + ").append(',');\n";
            }
            str += "}\n";
        }
    }
    
}
