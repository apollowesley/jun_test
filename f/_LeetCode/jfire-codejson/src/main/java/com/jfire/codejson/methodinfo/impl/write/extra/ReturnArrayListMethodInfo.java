package com.jfire.codejson.methodinfo.impl.write.extra;

import java.lang.reflect.Method;
import com.jfire.codejson.methodinfo.impl.write.AbstractWriteMethodInfo;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.util.NameTool;

public class ReturnArrayListMethodInfo extends AbstractWriteMethodInfo
{
    
    public ReturnArrayListMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        String fieldName = NameTool.getNameFromMethod(method, strategy);
        str = "java.util.ArrayList " + fieldName + " = " + getValue + ";\n";
        str += "if(" + fieldName + "!=null)\n{\n";
        String key = method.getDeclaringClass().getName() + '.' + fieldName;
        if (strategy != null && strategy.containsField(key))
        {
            str += "\tcache.append(\"\\\"" + fieldName + "\\\":\");\n";
            str += "\tJsonWriter writer = writeStrategy.getWriterByField(\"" + key + "\");\n";
            str += "\twriter.write(" + fieldName + ",cache);\n";
            str += "\tcache.append(',');\n";
            str += "}\n";
        }
        else
        {
            str += "\tcache.append(\"\\\"" + fieldName + "\\\":[\");\n";
            str += "\tint size = " + fieldName + ".size();\n";
            str += "\tObject valueTmp = null;\n";
            str += "\tfor(int i=0;i<size;i++)\n";
            str += "\t{\n";
            str += "\t\tif((valueTmp=" + fieldName + ".get(i))!=null)\n";
            str += "\t\t{\n";
            str += "\t\t\tif(valueTmp instanceof String)\n";
            str += "\t\t\t{\n";
            str += "\t\t\t\tcache.append('\\\"').append((String)valueTmp).append('\\\"');\n";
            str += "\t\t\t}\n";
            str += "\t\t\telse\n";
            str += "\t\t\t{\n";
            if (strategy != null)
            {
                str += "\t\t\t\twriteStrategy.getWriter(valueTmp.getClass()).write(valueTmp,cache);\n";
            }
            else
            {
                str += "\t\t\t\tWriterContext.write(valueTmp,cache);\n";
            }
            str += "\t\t\t}\n";
            str += "\t\t\tcache.append(',');\n";
            str += "\t\t}\n";
            str += "\t}\n";
            str += "\tif(cache.isCommaLast()){cache.deleteLast();}\n";
            str += "\tcache.append(\"],\");\n";
            str += "}\n";
        }
    }
    
}
