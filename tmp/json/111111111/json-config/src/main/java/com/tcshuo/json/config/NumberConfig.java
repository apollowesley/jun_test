/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.NumberNode;
import com.tcshuo.json.node.TextNode;
import java.text.DecimalFormat;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author tengda
 */
public class NumberConfig extends JsonConfig {

    protected Number number;

    public NumberConfig(Number target) {
        super(target);
        this.number = target;
    }

    @Override
    public Node toObjectNode() {
        if (StringUtils.isBlank(format)) {
           return new NumberNode(number);
        }
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return new TextNode(decimalFormat.format(target));
    }
}
