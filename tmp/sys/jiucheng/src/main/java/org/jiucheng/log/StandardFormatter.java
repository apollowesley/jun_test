/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class StandardFormatter extends Formatter {
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static Formatter instance;
    
    private Date date = new Date();
    
    static Formatter getInstance() {
        if(null == instance) {
            instance = new StandardFormatter();
        }
        return instance;
    }
    
    public String inferCaller() {
        StackTraceElement[] els = Thread.currentThread().getStackTrace();
        boolean lookingForLogger = true;
        for(StackTraceElement el : els) {
            String cname = el.getClassName();
            boolean isLoggerImpl = isLoggerImplFrame(cname);
            if (lookingForLogger) {
                // Skip all frames until we have found the first logger frame.
                if (isLoggerImpl) {
                    lookingForLogger = false;
                }
            } else {
                if (!isLoggerImpl) {
                    // skip reflection call
                    if (!cname.startsWith("java.lang.reflect.") && !cname.startsWith("sun.reflect.")) {
                       // We've found the relevant frame.
                       //setSourceClassName(cname);
                       //setSourceMethodName(frame.getMethodName());
                       return el.toString();
                    }
                }
            }
        }
        return "";
    }
        
    public boolean isLoggerImplFrame(String cname) {
        // the log record could be created for a platform logger
        return (cname.equals("org.jiucheng.log.Logger") || cname.equals("java.util.logging.Logger") ||
                cname.startsWith("java.util.logging.LoggingProxyImpl") ||
                cname.startsWith("sun.util.logging."));
    }
    
    @Override
    public synchronized String format(LogRecord record) {
//        String source = inferCaller();
        date.setTime(record.getMillis());
        String message = formatMessage(record);
        String throwable = "";
        if (null != record.getThrown()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        String newLine = System.getProperty("line.separator");
        if(!throwable.endsWith(newLine)) {
            throwable += newLine;
        }
        return String.format("%s %7s %s %s - <%s>%s", simpleDateFormat.format(date), record.getLevel(), record.getSourceClassName(), record.getSourceMethodName(),  message, throwable);
    }
}
