package com.jfire.codejson.methodinfo.impl.write;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.util.NameTool;

public class ReturnCustomObjectMethodInfo extends AbstractWriteMethodInfo
{
    
    public ReturnCustomObjectMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        Class<?> returnType = method.getReturnType();
        String fieldName = NameTool.getNameFromMethod(method, strategy);
        str = returnType.getName() + " " + fieldName + " = " + getValue + ";\n";
        str += "if(" + fieldName + "!=null)\n{\n";
        str += "\tcache.append(\"\\\"" + fieldName + "\\\":\");\n";
        String key = method.getDeclaringClass().getName() + '.' + fieldName;
        if (strategy != null)
        {
            if (strategy.containsField(key))
            {
                str += "\tJsonWriter writer = writeStrategy.getWriterByField(\"" + key + "\");\n";
            }
            else
            {
                str += "\tJsonWriter writer = writeStrategy.getWriter(" + fieldName + ".getClass());\n";
            }
            str += "\twriter.write(" + fieldName + ",cache);\n";
        }
        else
        {
            str += "\tWriterContext.write(" + fieldName + ",cache);\n";
        }
        str += "\tcache.append(',');\n";
        str += "}\n";
    }
    
}
