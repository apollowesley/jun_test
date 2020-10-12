/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.TextNode;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author tengda
 */
public class SimpleConfig extends JsonConfig {

    public SimpleConfig(Object target) {
        super(target);
    }

    @Override
    public Node toObjectNode() {
        if (StringUtils.isBlank(this.format)) {
            return new TextNode(target.toString());
        }
        return new TextNode(String.format(format, target));
    }

}
