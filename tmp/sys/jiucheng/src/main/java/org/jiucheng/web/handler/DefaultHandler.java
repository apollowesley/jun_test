package org.jiucheng.web.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.util.WebUtil;

public class DefaultHandler extends AbstractHandler {
	public void invoke(WebWrapper webWrapper) throws ServletException {
        Object ctrl = BeanFactory.get(webWrapper.getCtrl().getBeanName());
        Method method = webWrapper.getCtrl().getMethod();
        method.setAccessible(true);
        try {
			Object obj = method.invoke(ctrl, getArgs(method, webWrapper).toArray());
			Out out = BeanFactory.getInstance(webWrapper.getCtrl().getRequestMapping().out());
			out.invoke(webWrapper, obj);
		} catch (IllegalAccessException e) {
			throw new ServletException(e);
		} catch (IllegalArgumentException e) {
			throw new ServletException(e);
		} catch (InvocationTargetException e) {
        	Throwable t;
        	if(e instanceof InvocationTargetException) {
        		t = ((InvocationTargetException) e).getTargetException();
        	}else {
        		t = e;
        	}
        	throw new ServletException(t);
		}
	}
	
   public List<Object> getArgs(Method method, WebWrapper webWrapper) {
        List<Object> args = new ArrayList<Object>();
        Class<?>[] classes = method.getParameterTypes();
        Annotation[][] anns = method.getParameterAnnotations();
        for(int i = 0; i < classes.length; i ++) {
            args.add(getObjectByClass(anns[i], classes[i], webWrapper));
        }
        return args;
    }
   
   private Object getObjectByClass(Annotation[] ann, Class<?> clazz, WebWrapper webWrapper) {
       Param param = null;
       if(null != ann) {
           for(Annotation a : ann) {
               if(a instanceof Param) {
                   param = (Param) a;
               }
           }
       }
       if(null != param) {
           return getParamValue(param, clazz);
       }
       Object r = null;
       if(clazz.equals(StringUtil.NULL_ARRAY.getClass())) {
           Matcher matcher = webWrapper.getMatcher();
           if(null != matcher) {
               int groupCount = matcher.groupCount();
               String[] stres = new String[groupCount + 1];
               for( int i = 0; i <= groupCount; i ++) {
                   stres[i] = matcher.group(i);
               }
               r = stres;
           }else {
               r = new String[]{webWrapper.getCtrl().getRoute()};
           }
       }else if(clazz.equals(Map.class)) {
           r = WebUtil.getRequest().getParameterMap();
       }else if(clazz.equals(HttpServletRequest.class)) {
           r = WebUtil.getRequest();
       }else if(clazz.equals(HttpServletResponse.class)) {
           r = WebUtil.getResponse();
       }else if(clazz.equals(HttpSession.class)) {
           r = WebUtil.getRequest().getSession();
       }
       return r;
   }
   
   private Object getParamValue(Param param, Class<?> clazz) {
       String name = param.value();
       if(clazz.isArray()) {
           clazz = clazz.getComponentType();
           String[] args = WebUtil.getRequest().getParameterValues(name);
           if(clazz == String.class) {
               return args;
           }
           if(isJdkBaseClass(clazz)) {
               List<Object> l = new ArrayList<Object>(args.length);
               for(String arg : args) {
                   l.add(ObjectUtil.toThis(clazz, arg));
               }
               return l.toArray();
           }
       }else {
           String arg = WebUtil.getRequest().getParameter(name);
           if(isJdkBaseClass(clazz)) {
               return ObjectUtil.toThis(clazz, arg);
           }
       }
       if(clazz.getCanonicalName().startsWith(DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_SCANNER_PACKAGE))) {
           Object obj = null;
           try {
                obj = clazz.newInstance();
                List<Field> fieldList = ClassUtil.listField(clazz);
                Object value;
                boolean isNull = ("".equals(name) || name == null) ? true : false;
                String p;
                for(Field field : fieldList) {
                    p = isNull ? field.getName() : name.concat(".").concat(field.getName());
                    value = WebUtil.getRequest().getParameter(p);
                    if(value != null) {
                        field.setAccessible(true);
                        field.set(obj, ObjectUtil.toThis(field.getType(), value));
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return obj;
       }
       return null;
   }
   
   public boolean isJdkBaseClass(Class<?> clazz) {
       if(clazz == String.class || clazz == Boolean.class
               || clazz == Byte.class || clazz == Character.class
               || clazz == Short.class || clazz == Integer.class
               || clazz == Long.class || clazz == Float.class
               || clazz == Double.class || clazz == BigDecimal.class
               || clazz == Date.class) {
           return true;
       }
       return false;
   }
}
