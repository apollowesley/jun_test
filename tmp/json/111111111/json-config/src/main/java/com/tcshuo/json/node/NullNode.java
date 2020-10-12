/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.JavaType;

/**
 *
 * @author tengda
 */
public class NullNode extends Node {

    @Override
    public String toJson() {
        return "null";
    }

    @Override
    protected Object toObjectByType(JavaType type) {
        return null;
    }

    @Override
    public Node getChildNode(String index) {
        return  null ;
    }

    @Override
    public String toString() {
        return null;
    }
    
    

}
