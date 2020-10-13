package com.jfire.codejson.methodinfo.impl.read;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.ReadStrategy;
import com.jfire.codejson.util.NameTool;

public class SetBaseMethodInfo extends AbstractReadMethodInfo
{
    
    public SetBaseMethodInfo(Method method, ReadStrategy strategy)
    {
        super(method, strategy);
        String name = getParamType().getName().substring(0, 1).toUpperCase() + getParamType().getName().substring(1);
        str = "if(json.contains(\"" + NameTool.getNameFromMethod(method, strategy) + "\"))\n";
        str += "{\n";
        str += "\t" + entityName + method.getName() + "(json.get" + name + "(\"" + NameTool.getNameFromMethod(method, strategy) + "\"));\n";
        str += "}\n";
    }
    
}
