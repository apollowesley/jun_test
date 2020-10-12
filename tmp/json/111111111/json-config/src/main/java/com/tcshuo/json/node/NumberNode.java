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
public class NumberNode extends Node {

    private Number number;

    public NumberNode(Number number) {
        this.number = number;
    }

    public NumberNode() {
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    @Override
    public String toJson() {
        return this.number.toString();
    }

    @Override
    public String toString() {
         return this.number.toString();
    }
    
    

    @Override
    protected Object toObjectByType(JavaType type) {
        if (type == null || type.getMain() == null) {
            return this.number;
        }
        return Converter.converter(this.number, type.getMain());
    }

    @Override
    public Node getChildNode(String index) {
        return null;
    }

}
