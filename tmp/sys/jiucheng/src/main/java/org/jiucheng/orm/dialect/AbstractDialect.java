package org.jiucheng.orm.dialect;

import org.jiucheng.log.Logger;

public abstract class AbstractDialect implements Dialect {
    
    protected abstract Logger getLogger();
    
    public String forColumnLabel(String columnLabel) {
        return columnLabel;
    }
}
