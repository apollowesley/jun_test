package com.jfire.codejson.function;

import com.jfire.baseutil.collection.StringCache;

public abstract class WriterAdapter implements JsonWriter
{
    
    @Override
    public void write(Object target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(int target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(float target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(double target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(long target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(byte target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(char target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(short target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
    @Override
    public void write(boolean target, StringCache cache)
    {
        throw new RuntimeException("没有实现");
    }
    
}
