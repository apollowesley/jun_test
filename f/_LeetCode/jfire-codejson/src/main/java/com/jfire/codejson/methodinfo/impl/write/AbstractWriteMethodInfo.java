package com.jfire.codejson.methodinfo.impl.write;

import java.lang.reflect.Method;
import com.jfire.codejson.methodinfo.WriteMethodInfo;
import com.jfire.codejson.strategy.WriteStrategy;

public abstract class AbstractWriteMethodInfo implements WriteMethodInfo
{
    protected String        str;
    protected String        getValue;
    protected WriteStrategy strategy;
    
    public AbstractWriteMethodInfo(Method method, WriteStrategy strategy)
    {
        this.strategy = strategy;
        getValue = "entity." + method.getName() + "()";
    }
    
    public String getOutput()
    {
        return str;
    }
}
