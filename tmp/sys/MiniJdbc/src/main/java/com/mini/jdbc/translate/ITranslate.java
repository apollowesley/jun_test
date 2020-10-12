package com.mini.jdbc.translate;

import com.mini.jdbc.dialect.Dialect;

public interface ITranslate
{
    public String translate(String sql, Dialect dialect);
}
