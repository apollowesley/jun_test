/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.JavaType;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author object
 */
public class ObjectNode extends Node {

    protected Map<String, Node> propertys = new HashMap<String, Node>();

    public ObjectNode() {

    }

    public void setPropertys(Map<String, Node> propertys) {
        this.propertys = propertys;
    }

    public Map<String, Node> getPropertys() {
        return propertys;
    }

    public boolean has(String key) {
        return this.propertys.containsKey(key);
    }

    public void pushProperty(String key, Node node) {
        this.propertys.put(key, node);

    }

    public Node getProperty(String key) {
        return this.propertys.get(key);
    }

    public String getString(String key) {
        return this.propertys.get(key).toString();
    }

    public String getString(String key, String exceptionValue) {
        try {
            return this.getString(key);
        } catch (Exception e) {
            return exceptionValue;
        }
    }

    public Number getNumber(String key) {
        Node node = this.getProperty(key);
        if (node instanceof NumberNode) {
            return ((NumberNode) this.propertys.get(key)).getNumber();
        } else {
            return Double.parseDouble(node.toString());
        }
    }

    public Number getNumber(String key, Number exceptionValue) {
        try {
            return this.getNumber(key);
        } catch (Exception e) {
            return exceptionValue;
        }

    }

    public Node removeProperty(String key) {
        return this.propertys.remove(key);
    }

    public Collection<String> getPropertyNames() {
        return this.propertys.keySet();
    }

    @Override
    public String toJson() {
        String code = "{";
        for (String key : propertys.keySet()) {
            if (code.length() > 1) {
                code += ",";
            }
            code += "\"" + key + "\":" + propertys.get(key).toJson();

        }
        code += "}";
        return code;
    }

    @Override
    protected Object toObjectByType(JavaType type) {
        if (type == null) {
            type = new JavaType(HashMap.class);
        }
        type.setConstructor(this.getConstructor());
        if (Map.class.isAssignableFrom(type.getMain())) {
            Map bean = (Map) type.getConstructor().createObject(this);
            for (String name : this.getPropertyNames()) {

                Node propertyNode = this.getProperty(name);
                Object value = null;

                if (propertyNode != null) {
                    value = propertyNode.toObject();
                }
                bean.put(name, value);
            }
            return bean;
        } else {

            Object bean = type.getConstructor().createObject(this);

            for (String name : this.getPropertyNames()) {
                Method setter = getSetter(bean, name);

                if (setter == null) {
                    continue;
                }
                Node propertyNode = this.getProperty(name);
                Object value = null;

                if (propertyNode != null) {

                    value = propertyNode.toObject(setter.getGenericParameterTypes()[0]);
                }
                try {
                    invoke(setter, bean, value);
                } catch (Exception e) {
                    throw new RuntimeException("不能调用" + setter.getName() + "参数:" + value);
                }

            }
            return bean;
        }
    }

    private static void invoke(Method method, Object bean, Object... params) {
        try {
            method.invoke(bean, params);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Method getSetter(Object bean, String name) {
        try {
            PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                return null;
            }
            return descriptor.getWriteMethod();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Method getGetter(Object bean, String name) {
        try {
            PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, name);

            return descriptor.getReadMethod();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Node getChildNode(String index) {
        return this.getProperty(index);
    }

}
