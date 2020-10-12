package com.dtdream.rdic.saas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanUtils {
    protected final static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * 对于复杂对象，必须手动构造对象然后拷贝对象
     * @param map
     * @param to
     * @return
     */
    public static<T> T copy(Map<String, String[]> map, T to) {
        if (null == to)
            return null;
        try {
            org.apache.commons.beanutils.BeanUtils.populate(to, map);
            return to;
        } catch (IllegalAccessException e) {
            logger.error("根据类型实例化失败", e);
        } catch (InvocationTargetException e) {
            logger.error("根据类型实例化失败", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T> T copy(Map<String, String[]> map, Class<T> clazz) {
        T objTarget = null;
        if (null == objTarget) {
            try {
                objTarget = clazz.getConstructor().newInstance();
                org.apache.commons.beanutils.BeanUtils.populate(objTarget, map);
                return objTarget;
            } catch (InstantiationException e1) {
                logger.error("根据类型实例化失败", e1);
            } catch (IllegalAccessException e1) {
                logger.error("根据类型实例化失败", e1);
            } catch (IllegalArgumentException e1) {
                logger.error("根据类型实例化失败", e1);
            } catch (InvocationTargetException e1) {
                logger.error("根据类型实例化失败", e1);
            } catch (NoSuchMethodException e1) {
                logger.error("根据类型实例化失败", e1);
            } catch (SecurityException e1) {
                logger.error("根据类型实例化失败", e1);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<F,T> T convert (F from, Class<T> clazz) {
        T objTarget = null;
        try {
            Constructor<?> objConstructor = clazz.getConstructor();
            objTarget = (T) objConstructor.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(objTarget, from);
         } catch (InstantiationException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         } catch (IllegalAccessException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         } catch (IllegalArgumentException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         } catch (InvocationTargetException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         } catch (NoSuchMethodException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         } catch (SecurityException e1) {
             objTarget = null;
             logger.error("根据类型实例化失败", e1);
         }

        return objTarget;
    }

    public static<F,T> T convert (F from, T to) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(to, from);
            return to;
        } catch (IllegalAccessException e1) {
            logger.error("根据类型实例化失败", e1);
        } catch (InvocationTargetException e1) {
            logger.error("根据类型实例化失败", e1);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T> T instance(Class<T> type) {
        T objTarget = null;
        try {
            Constructor<?> objConstructor = type.getConstructor();
            objTarget = (T) objConstructor.newInstance();
            return objTarget;
        } catch (InstantiationException e1) {
             logger.error("根据类型实例化失败", e1);
        } catch (IllegalAccessException e1) {
             logger.error("根据类型实例化失败", e1);
        } catch (IllegalArgumentException e1) {
             logger.error("根据类型实例化失败", e1);
        } catch (InvocationTargetException e1) {
             logger.error("根据类型实例化失败", e1);
        } catch (NoSuchMethodException e1) {
             logger.error("根据类型实例化失败", e1);
        } catch (SecurityException e1) {
             logger.error("根据类型实例化失败", e1);
        }
        return null;
    }

    public static<T> T clone (T src) {
        try {
            return (T) org.apache.commons.beanutils.BeanUtils.cloneBean(src);
        } catch (IllegalAccessException e) {
            logger.error("克隆：非法访问", e);
        } catch (InstantiationException e) {
            logger.error("克隆：无法实例化", e);
        } catch (InvocationTargetException e) {
            logger.error("克隆：方法调用失败", e);
        } catch (NoSuchMethodException e) {
            logger.error("克隆：方法不存在", e);
        }
        return null;
    }

    public static<T> List<T> clone (T src, int count) {
        List<T> results = new ArrayList<>();
        for (int i = 0; i < count; ++ i) {
            results.add(clone(src));
        }
        return results;
    }

    /**
     *
     * @param clsorobj  clsorobj是类名时
     * @param method
     * @param values
     * @return
     */
    public static Object invoke (ClassLoader cl, Object clsorobj, String method, Object... values) {
        Class<?> clazz;
        Object object;
        String clsname;
        if (clsorobj instanceof String) {
            clsname = (String) clsorobj;
            object = null;
        } else {
            clsname = clsorobj.getClass().getName();
            object = clsorobj;
        }
        Method m;
        Class<?>[] classes = new Class<?>[values.length];
        for (int i = 0; i < values.length; ++ i)
            classes[i] = values[i].getClass();
        ClassLoader loader = (null != cl) ? cl : ClassLoader.getSystemClassLoader();
        while (null != loader) {
            try {
                clazz = Class.forName(clsname, true, loader);
                m = clazz.getMethod(method, classes);
                return m.invoke(object, values);
            } catch (ClassNotFoundException e) {
                URL res =loader.getResource("");
                String path = res != null ? res.getPath() : "Bootstrap";
                logger.debug("类".concat(clsname).concat("在加载器").concat(path).concat("中不存在"));
            } catch (NoSuchMethodException e) {
                URL res =loader.getResource("");
                String path = res != null ? res.getPath() : "Bootstrap";
                logger.debug
                    (
                        "方法".concat(method)
                            .concat("在加载器")
                            .concat(path)
                            .concat("的类")
                            .concat(clsname)
                            .concat("中不存在")
                    );
            } catch (IllegalAccessException e) {
                logger.debug("无权访问方法".concat(method));
            } catch (InvocationTargetException e) {
                logger.debug("调用方法".concat(method).concat("失败"));
            }
            loader = loader.getParent();
        }
        return null;
    }
}
