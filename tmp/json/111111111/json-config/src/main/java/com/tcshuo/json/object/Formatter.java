/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.object;

import com.tcshuo.json.node.Node;

/**
 *
 * @author tengda
 */
public interface Formatter {
    
    public Object format(Node node);

}
