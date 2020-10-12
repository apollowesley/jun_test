package org.jiucheng.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;

class LoggerManager {
    
    private static Map<String, Logger> pool = new ConcurrentHashMap<String, Logger>();
    private static Object lock = new Object();
    private static boolean inited = false;
    private static Properties prop = new Properties();
    private static Handler[] handlers = new Handler[0];
    
    public static Logger getLogger(String name) {
        initConfig();
        Logger logger = pool.get(name);
        if(null != logger) {
            return logger;
        }
        synchronized (lock) {
            logger = new Logger(name);
            java.util.logging.Logger g = logger.getLogger();
            g.setUseParentHandlers(false);
            if(null != handlers && handlers.length > 0) {
                Handler[] hds = g.getHandlers();
                if(null != hds) {
                    for(Handler hd : hds) {
                        g.removeHandler(hd);
                    }
                }
                for(Handler h : handlers) {
                    g.addHandler(h);
                }
            }else {
                g.addHandler(StandardHandler.getInstance());
            }
            g.setLevel(getLevel(name + ".level", Level.INFO));
            pool.put(name, logger);
        }
        return logger;
    }
    
    private static void initConfig() {
        if(inited) {
            return;
        }
        synchronized (lock) {
            if(!inited) {
                InputStream is = Logger.class.getResourceAsStream("/logging.properties");
                if(null != is) {
                    try {
                        prop.load(is);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    handlers = getHandlers();
                }
                
            }
            inited = true;
        }
    }
    
    private static Handler[] getHandlers() {
        String handlers = prop.getProperty("handlers");
        if(null != handlers && !"".equals(handlers.trim())) {
            List<Handler> hds = new ArrayList<Handler>();
            String[] handlerArr = handlers.split(",");
            for(String handler : handlerArr) {
                handler = handler.trim();
                if(!"".equals(handler)) {
                    try {
                        Class<?> clazz = LoggerManager.class.getClassLoader().loadClass(handler.trim());
                        Handler h = (Handler)clazz.newInstance();
                        if(handler.startsWith("java.util.logging")) {
                            h.setFormatter(getFormatter(handler + ".formatter", StandardFormatter.getInstance()));
                        }
                        hds.add(h);
                    } catch (ClassNotFoundException e) {
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            if(hds.size() > 0) {
                return (Handler[]) hds.toArray(LoggerManager.handlers);
            }
        }
        return null;
    }
    
    public static Level getLevel(String name, Level defaultValue) {
        String levelValue = prop.getProperty(name);
        Level level = null;
        if(null != levelValue) {
            try {
                level = Level.parse(levelValue.trim());
            }catch(IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        return null != level ? level : defaultValue;
    }
    
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
    
    public static Formatter getFormatter(String name, Formatter defaultValue) {
        String className = prop.getProperty(name);
        if(null != className && "".equals((className = className.trim()))) {
            try {
                Class<?> clazz = LoggerManager.class.getClassLoader().loadClass(className);
                return (Formatter) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (InstantiationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            }
        }
        return defaultValue;
    }
    
    public static Filter getFilter(String name, Filter defaultValue) {
        String className = prop.getProperty(name);
        if(null != className && "".equals((className = className.trim()))) {
            try {
                Class<?> clazz = LoggerManager.class.getClassLoader().loadClass(className);
                return (Filter) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (InstantiationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            }
        }
        return defaultValue;
    }
}
