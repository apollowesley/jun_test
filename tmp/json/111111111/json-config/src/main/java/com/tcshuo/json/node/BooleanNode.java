/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.Converter;
import com.tcshuo.json.object.JavaType;

/**
 *
 * @author tengda
 */
public class BooleanNode extends Node {

    private Boolean booleanObject;

    public BooleanNode(Boolean booleanObject) {
        this.booleanObject = booleanObject;
    }

    @Override
    public String toJson() {
        return this.booleanObject.toString();
    }

    @Override
    public String toString() {
        return this.booleanObject.toString();
    }

    @Override
    public Node getChildNode(String index) {
        return null;
    }

    @Override
    protected Object toObjectByType(JavaType type) {
        if (type == null || type.getMain() == null) {
            return this.booleanObject;
        }
        return Converter.converter(this.booleanObject, type.getMain());
    }

}
