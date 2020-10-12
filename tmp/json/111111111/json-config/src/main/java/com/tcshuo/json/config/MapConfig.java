/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tengda
 */
public class MapConfig extends BeanConfig {

    protected Map map;

    public MapConfig(Map target) {
        super(target);
        this.map = target;
    }

    /**
     * 获取字段值
     *
     * @param name
     * @return
     */
    protected Object get(String name) {
        return this.map.get(name);
    }

    @Override
    protected boolean isSimple(String name) {
        Class type = this.getType(name);
        return type == null || Utils.isSimpleProperty(type);
    }

    @Override
    protected Class getType(String name) {
        Object value = this.map.get(name);
        return value == null ? null : value.getClass();
    }

    /**
     * 获取所有字段
     *
     * @return
     */
    protected List<String> getPropertys() {
        List<String> list = new ArrayList();
        list.addAll(this.map.keySet());
        return list;

    }

}
