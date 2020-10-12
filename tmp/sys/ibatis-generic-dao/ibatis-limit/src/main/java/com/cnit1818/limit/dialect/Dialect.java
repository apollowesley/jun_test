package com.cnit1818.limit.dialect;

public abstract class Dialect {
    public abstract boolean supportsLimit();

    public abstract boolean supportsLimitOffset();

    public abstract String getLimitString(String paramString, int paramInt1, int paramInt2);
}

