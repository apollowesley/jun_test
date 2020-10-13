package com.jfire.codejson.methodinfo.impl.read;

import java.lang.reflect.Method;
import com.jfire.codejson.strategy.ReadStrategy;
import com.jfire.codejson.util.NameTool;

public class SetWrapperMethodInfo extends AbstractReadMethodInfo
{
    
    public SetWrapperMethodInfo(Method method, ReadStrategy strategy)
    {
        super(method, strategy);
        String name = "W" + getParamType().getSimpleName().substring(0, 1).toUpperCase() + getParamType().getSimpleName().substring(1);
        str = "if(json.contains(\"" + NameTool.getNameFromMethod(method, strategy) + "\"))\n";
        str += "{\n";
        str += "\t" + entityName + method.getName() + "(json.get" + name + "(\"" + NameTool.getNameFromMethod(method, strategy) + "\"));\n";
        str += "}\n";
    }
    
}
