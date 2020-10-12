package org.gen.code.parse;

import org.gen.code.config.Config;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


public class AbstractParser {
    protected Config config;
    protected Set<Class<?>> klasses;
    protected Map<String, Class<?>> querys;


    public AbstractParser(Config config, Set<Class<?>> klasses,
                          Map<String, Class<?>> querys) {
        super();
        this.config = config;
        this.klasses = klasses;
        this.querys = querys;
    }
    public boolean filterSerialVersionUID(Field field) {
        return !(field.getName().equals("serialVersionUID"));
    }
}
