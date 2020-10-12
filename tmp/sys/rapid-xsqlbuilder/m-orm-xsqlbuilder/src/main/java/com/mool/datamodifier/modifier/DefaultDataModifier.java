package com.mool.datamodifier.modifier;

import com.mool.datamodifier.DataModifier;
import com.mool.datamodifier.DefaultUtils;

/**
 * @author badqiu
 */
public class DefaultDataModifier implements DataModifier
{
    private static final String DEFAULT_STRING = "";
    
    public Object modify(Object value, String modifierArgument)
    {
        if (value == null)
            return DefaultUtils.defaultString(modifierArgument, DEFAULT_STRING);
        return value;
    }
}
