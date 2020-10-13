package com.jfire.codejson.function;

import com.jfire.baseutil.collection.StringCache;

public interface JsonWriter
{
    /**
     * 将target对象以json格式输出到cache中
     * 
     * @param target
     * @return
     */
    public void write(Object target, StringCache cache);
    
    public void write(int target, StringCache cache);
    
    public void write(float target, StringCache cache);
    
    public void write(double target, StringCache cache);
    
    public void write(long target, StringCache cache);
    
    public void write(byte target, StringCache cache);
    
    public void write(char target, StringCache cache);
    
    public void write(short target, StringCache cache);
    
    public void write(boolean target, StringCache cache);
}
