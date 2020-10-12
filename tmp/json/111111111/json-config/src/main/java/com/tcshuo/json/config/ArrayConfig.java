/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcshuo.json.config;

import com.tcshuo.json.node.ArrayNode;
import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.ObjectNode;
import java.lang.reflect.Array;
 
/**
 *
 * @author tengda
 */
public class ArrayConfig extends JsonConfig {

    protected ObjectNode configNode;

    public ArrayConfig(Object target, ObjectNode configNode) {
        super(target);
        this.configNode = configNode;
        
    }
    
    @Override
    public Node toObjectNode() {
        ArrayNode arrayNode = new ArrayNode();
        for (int i = 0; i < Array.getLength(target); i++) {
            Object value = Array.get(target, i);
            JsonConfig config = JsonConfigParser._parser(configNode, value);
            config.setAlias(alias);
            config.setAllows(allows);
            config.setFormat(format);
            config.setIgnore(ignore);
            config.setIgnores(ignores);
            arrayNode.add(config.toObjectNode());
        }
        return arrayNode;
    }
    
}
