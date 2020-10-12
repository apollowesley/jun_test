package com.dtdream.rdic.saas.hibernate;

import com.dtdream.rdic.saas.def.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Ozz8 on 2015/07/07.
 */
public class HibernateDeletor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public final static HibernateDeletor deletor = new HibernateDeletor();
    public void delete (Object object) {
        if (null == object)
            return;
        try {
            Method deletor = object.getClass().getMethod("setIsdel", Integer.class);
            deletor.invoke(object, Integer.valueOf(Constant.YES));
        } catch (NoSuchMethodException e) {
            logger.error("删除方法不存在：", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("调用方法出错：", e);
        }
    }
}
