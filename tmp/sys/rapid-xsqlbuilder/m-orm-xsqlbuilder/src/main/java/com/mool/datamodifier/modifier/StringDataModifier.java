package com.mool.datamodifier.modifier;

import com.mool.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class StringDataModifier implements DataModifier
{
    public Object modify(Object value, String modifierArgument)
    {
        if (value == null)
            return null;
        if (value instanceof String)
            return value;
        return value.toString();
    }
}
