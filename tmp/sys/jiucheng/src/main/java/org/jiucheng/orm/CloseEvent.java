package org.jiucheng.orm;

import java.sql.SQLException;
import java.util.EventObject;

public class CloseEvent extends EventObject {

    /**
     * 
     */
    private static final long serialVersionUID = -4421854660445789184L;

    private SQLException exception;

    public CloseEvent(PoolConnection poolConnection, SQLException exception){
        super(poolConnection);
        this.exception = exception;
    }

    public SQLException getException() {
        return exception;
    }
}
