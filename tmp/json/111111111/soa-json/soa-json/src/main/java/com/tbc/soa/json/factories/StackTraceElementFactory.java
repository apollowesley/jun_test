package com.tbc.soa.json.factories;

import java.lang.reflect.Type;
import java.util.Map;

import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.ObjectFactory;


public class StackTraceElementFactory implements ObjectFactory {
    @SuppressWarnings("unchecked")
	public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
    	Map<String,Object> map = (Map<String,Object>) value;
    	String className = (String) map.get("className");
    	Integer lineNumber = (Integer) map.get("lineNumber");
    	String methodName = (String) map.get("methodName");
    	String fileName = (String) map.get("fileName");
    	
    	if (lineNumber == null) {//prevent null pointer exception.
    		lineNumber = -1;
    	}
    	
        return new StackTraceElement(className,methodName, fileName, lineNumber);
    }
}
