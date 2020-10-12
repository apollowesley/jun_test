/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcshuo.json.config;

import com.tcshuo.json.node.ObjectNode;
import java.util.Collection;

/**
 *
 *  
 * @author tengda
 */
public class CollectionConfig extends ArrayConfig {
 
    public CollectionConfig(Collection target,ObjectNode configNode) {
        super(target.toArray(),configNode);
    }
    
}
