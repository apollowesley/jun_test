package com.mool.datamodifier.modifier;

import com.mool.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class BooleanDataModifier implements DataModifier
{
    public Object modify(Object value, String modifierArgument)
    {
        if (value == null)
            return null;
        if (value instanceof Boolean)
            return value;
        return new Boolean(value.toString());
    }
}
