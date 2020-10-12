package org.jiucheng.log;

import java.util.logging.Level;

public class Logger {
    
    private java.util.logging.Logger logger;
    private String className;
    
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
    
    public static Logger getLogger(String name) {
        return LoggerManager.getLogger(name);
    }
    
    Logger(String name) {
        className = name;
        logger = java.util.logging.Logger.getLogger(name);
    }
    
    java.util.logging.Logger getLogger() {
        return logger;
    }
    
    public void debug(String message) {
        logger.logp(Level.FINE, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message);
    }
    
    public void debug(String message,  Throwable t) {
        logger.logp(Level.FINE, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message, t);
    }
    
    public void info(String message) {
        logger.logp(Level.INFO, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message);
    }
    
    public void info(String message, Throwable t) {
        logger.logp(Level.INFO, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message, t);
    }
    
    public void warn(String message) {
        logger.logp(Level.WARNING, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message);
    }
    
    public void warn(String message, Throwable t) {
        logger.logp(Level.WARNING, message, Thread.currentThread().getStackTrace()[2].getMethodName(), message, t);
    }
    
    public void error(String message) {
        logger.logp(Level.SEVERE, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message);
    }
    
    public void error(String message, Throwable t) {
        logger.logp(Level.SEVERE, className, Thread.currentThread().getStackTrace()[2].getMethodName(), message, t);
    }
    
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }
}
