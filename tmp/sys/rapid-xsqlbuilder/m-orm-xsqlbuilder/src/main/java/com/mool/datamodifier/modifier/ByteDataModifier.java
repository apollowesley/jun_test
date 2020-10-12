package com.mool.datamodifier.modifier;

import com.mool.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class ByteDataModifier implements DataModifier
{
    public Object modify(Object value, String modifierArgument)
    {
        if (value == null)
            return null;
        if (value instanceof Byte)
            return value;
        return new Byte(value.toString());
    }
}
