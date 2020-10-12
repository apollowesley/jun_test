/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.BooleanNode;
import com.tcshuo.json.node.Node;

/**
 *
 * @author tengda
 */
public class BooleanConfig extends JsonConfig {

    private Boolean booleanObject;

    public BooleanConfig(Object booleanObject) {
        super(booleanObject);
        this.booleanObject = (Boolean)booleanObject;
    }

    @Override
    protected Node toObjectNode() {
        return new BooleanNode(this.booleanObject);
    }

}
