package com.jfire.codejson.strategy;

public interface Strategy
{
    public String getRename(String fieldName);
    
    public boolean containsRename(String fieldName);
}
