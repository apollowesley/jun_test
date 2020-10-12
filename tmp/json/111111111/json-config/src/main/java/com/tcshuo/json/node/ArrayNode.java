/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.Formatter;
import com.tcshuo.json.object.JavaType;
import com.tcshuo.json.object.ObjectConstructor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author tengda
 */
public class ArrayNode extends Node {

    private ArrayList<Node> array = new ArrayList<Node>();

    public void add(Node node) {
        this.array.add(node);
    }

    public void clear() {
        this.array.clear();
    }

    public int getLength() {
        return this.array.size();
    }

    public Node get(int index) {
        return this.array.get(index);
    }

    @Override
    public String toJson() {

        String code = "[";
        for (Node node : array) {
            if (code.length() > 1) {
                code += ",";
            }
            code += node.toJson();

        }
        code += "]";
        return code;

    }

    @Override
    protected Object toObjectByType(JavaType type) {
        if (type == null) {
            Object arrayObject = new Object[this.getLength()];
            for (int i = 0; i < this.getLength(); i++) {
                Array.set(arrayObject, i, this.get(i).toObject());
            }
            return arrayObject;

        }

        Class innerClass = type.getClasses() == null ? Object.class : type.getClasses()[0];

        if (Collection.class.isAssignableFrom(type.getMain())) {
            Object arrayObject = type.getConstructor().createObject(this);
            Collection collection = (Collection) arrayObject;
            for (int i = 0; i < this.getLength(); i++) {
                collection.add(this.get(i).toObject(innerClass));
            }
            return arrayObject;

        } else if (type.getMain().isArray()) {
            Object arrayObject = Array.newInstance(type.getMain().getComponentType(), this.getLength());
            for (int i = 0; i < this.getLength(); i++) {
                Array.set(arrayObject, i, this.get(i).toObject(innerClass));
            }
            return arrayObject;
        }
        throw new RuntimeException("数组类型" + this.toJson() + "不能转换为:" + type.getMain());
    }

    @Override
    public Node getChildNode(String index) {
        ArrayNode result = new ArrayNode();
        for (Node node : array) {
            Node c = node.getChildNode(index);
            if (c != null) {
                result.add(c);
            }
        }

        return result;
    }

    public Formatter getFormatter() {
        return null;
    }

    public void setFormatter(Formatter formatter) {
        for (Node node : array) {
            node.setFormatter(formatter);
        }
    }

    public ObjectConstructor getConstructor() {
        return null;
    }

    public void setConstructor(ObjectConstructor constructor) {
        for (Node node : array) {
            node.setConstructor(constructor);
        }
    }

}
