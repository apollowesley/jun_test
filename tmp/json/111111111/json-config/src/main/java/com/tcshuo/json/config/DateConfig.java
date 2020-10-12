/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.TextNode;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author tengda
 */
public class DateConfig  extends JsonConfig{
 
     
    protected Date date ;

    public DateConfig(Date target) {
        super(target);
        this.date = target;
    }

    @Override
    public Node toObjectNode() {
        String f = StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format;
        return new TextNode(DateFormatUtils.format(date, f));
    }
    
    
    
}
