/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class StandardHandler extends StreamHandler {
    
    private static StandardHandler instance;
    
    public static StandardHandler getInstance() {
        if(null == instance) {
            instance = new StandardHandler();
        }
        return instance;
    }
    
    public StandardHandler() {
        configure();
        setOutputStream(System.out);
    }
    
    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

    private void configure() {
        String className = getClass().getName();
        setLevel(LoggerManager.getLevel(className + ".level", Level.INFO));
        setFilter(LoggerManager.getFilter(className + ".filter", null));
        setFormatter(LoggerManager.getFormatter(className + ".formatter", StandardFormatter.getInstance()));
        try {
            setEncoding(LoggerManager.getProperty(className + ".encoding", null));
        } catch (Exception e) {
            try {
                setEncoding(null);
            } catch (Exception e1) {
            }
        }
    }
}
