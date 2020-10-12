package org.jiucheng.orm;

import java.util.EventListener;


public interface CloseEventListener extends EventListener {
    public void closeFailure(CloseEvent closeEvent);
}
