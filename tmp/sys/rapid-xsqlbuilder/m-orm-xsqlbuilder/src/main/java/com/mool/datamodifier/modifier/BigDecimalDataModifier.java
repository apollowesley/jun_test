package com.mool.datamodifier.modifier;

import java.math.BigDecimal;

import com.mool.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class BigDecimalDataModifier implements DataModifier
{
    public Object modify(Object value, String modifierArgument)
    {
        if (value == null)
            return null;
        if (value instanceof BigDecimal)
            return value;
        return new BigDecimal(value.toString());
    }
}
