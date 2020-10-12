/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.NullNode;


/**
 *
 * @author tengda
 */
public class NullConfig extends JsonConfig{

    public NullConfig() {
        super(null);
    }

    @Override
    public Node toObjectNode() {
       return new NullNode();
    }
    
}
