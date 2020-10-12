/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.object;

import com.tcshuo.json.config.EnumConfig;
import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.ObjectNode;
import com.tcshuo.json.node.TextNode;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.apache.commons.beanutils.MethodUtils;

/**
 *
 * @author tengda
 */
public class JavaType {

    protected Class main;

    protected Class[] classes;

    private ObjectConstructor constructor = new ObjectConstructor() {

        public Object createObject(Node node) {

            try {
                try {
                    if (main.isEnum()) {
                        for (Object enumObject : main.getEnumConstants()) {
                            String enumName = MethodUtils.invokeMethod(enumObject, "name", new Object[0]).toString();
                            String name = null;
                            if (node instanceof TextNode) {
                                TextNode textNode = (TextNode) node;
                                name = textNode.getText();
                            } else {
                                ObjectNode objectNode = (ObjectNode) node;
                                name = objectNode.getProperty(EnumConfig.JAVA_ENUM_NAME).toString();
                            }
                            if (enumName.equals(name)) {
                                return enumObject;
                            }
                        }
                    }
                } catch (Exception e) {
                    return null;
                }
                if (main.isInterface()) {
                    if (List.class.isAssignableFrom(main)) {
                        return new ArrayList();
                    }
                    if (Set.class.isAssignableFrom(main)) {
                        return new HashSet();
                    }
                    if (Map.class.isAssignableFrom(main)) {
                        return new HashMap();
                    }
                    if (Queue.class.isAssignableFrom(main)) {
                        return new LinkedList();
                    }
                    if (Collection.class.isAssignableFrom(main)) {
                        return new ArrayList();
                    }

                    throw new RuntimeException("不能创建一个接口实现:" + main);

                }
                return main.newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    public JavaType() {

    }

    public JavaType(Type main) {
        if (main == null) {
            throw new NullPointerException("main is null");
        }
        if (main instanceof ParameterizedType) {
            this.main = (Class) ((ParameterizedType) main).getRawType();
        } else {
            this.main = (Class) main;
        }

        this.initGenricType(main);
    }

    public JavaType(Type main, Class... classes) {
        if (main == null) {
            throw new NullPointerException("main is null");
        }
        if (main instanceof ParameterizedType) {
            this.main = (Class) ((ParameterizedType) main).getRawType();
        } else {
            this.main = (Class) main;
        }
        this.classes = classes;
        if (classes == null || classes.length == 0) {
            this.initGenricType(main);
        }
    }

    public Class getMain() {
        return main;
    }

    public Class[] getClasses() {
        return classes;
    }

    public ObjectConstructor getConstructor() {
        return constructor;
    }

    public JavaType setConstructor(ObjectConstructor constructor) {
        if(constructor==null){
            return this;
        }
        this.constructor = constructor;
        return this;
    }

    private void initGenricType(Type mapMainType) {
        
        // 为了确保安全转换，使用instanceof   
        if (mapMainType instanceof ParameterizedType) {
            // 执行强制类型转换   
            ParameterizedType parameterizedType = (ParameterizedType) mapMainType;
            // 获取泛型类型的泛型参数   
            Type[] types = parameterizedType.getActualTypeArguments();
            classes = new Class[types.length];
            for (int i = 0; i < classes.length; i++) {
                classes[i] = (Class) types[i];
            }
        } else {
            Class c = (Class) mapMainType;
            classes = new Class[]{c.getComponentType()};
        }
    }

}
