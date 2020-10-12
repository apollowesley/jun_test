/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.NullNode;
import com.tcshuo.json.node.ObjectNode;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * java bean json输出配置
 *
 * @author tengda
 */
public class BeanConfig extends JsonConfig {

    public BeanConfig(Object target) {
        super(target);
    }

    @Override
    public Node toObjectNode() {
        ObjectNode node = new ObjectNode();
        List<String> propertys = getPropertys();
        for (String name : propertys) {
            if (!canWrit(name)) {
                continue;
            }
            if (this.getChilds().containsKey(name)) {
                Node childNode = this.getChilds().get(name).toObjectNode();
                if (childNode == null || (childNode instanceof NullNode)) {
                    continue;
                }
                node.pushProperty(getWritName(name), childNode);
            } else {
                Node childNode = JsonConfigParser.parser(null, this.get(name)).toObjectNode();
                
                
                if (childNode == null || (childNode instanceof NullNode)) {
                    continue;
                }
                node.pushProperty(getWritName(name), childNode);

            }
        }
        return node;
    }

    /**
     * 获取字段输出名
     * @return 
     */
    protected String getWritName(String name) {
        return this.alias.containsKey(name) ?  this.alias.get(name) : name ;
    }

    /**
     * 是否允许输出某字段
     *
     * @param name
     * @return
     */
    public boolean canWrit(String name) {

        //忽略列表中的字段直接不输出
        if (isIgnore(name)) {
            return false;
        }
        //允许列表中的字段直接输出
        if (isAllow(name)) {
            return true;
        }
        //当前忽略模式未“IGNORE_NO”则直接输出
        if (IGNORE_NO.equals(this.ignore)) {
            return true;
        }
        
        //只输出允许列表的情况下
        if(IGNORE_NO_ALLOW.contains(this.ignore)){
            //只输出允许列表中的
            return this.allows.contains(name);
        }
        
        
        //没有命中“允许列表”的简单字段， 在“IGNORE_NO_ALLOW”下不输出，其他情况下输出
        if (this.isSimple(name)) {
            return !IGNORE_NO_ALLOW.equals(this.ignore);
        } else {
            //复杂对象在  在“IGNORE_COMPLICATE”下不输出，其他情况下输出
            return !IGNORE_COMPLICATE.equals(this.ignore);
        }
    }

    /**
     * 获取所有字段
     *
     * @return
     */
    protected List<String> getPropertys() {
        List<String> list = new ArrayList();
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(target.getClass());
        for (PropertyDescriptor pd : pds) {
            if (pd.getReadMethod() != null) {
                list.add(pd.getName());
            }
        }
        return list;

    }

    /**
     * 获取字段值
     *
     * @param name
     * @return
     */
    protected Object get(String name) {
        try {
            return PropertyUtils.getProperty(target, name);
        } catch (Exception ex) {
            Logger.getLogger(BeanConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 获取字段类型
     *
     * @param name
     * @return
     */
    protected Class getType(String name) {
        try {
            return PropertyUtils.getPropertyDescriptor(this.target, name).getPropertyType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 是否为简单对象
     *
     * @param name
     * @return
     */
    protected boolean isSimple(String name) {
       return Utils.isSimpleProperty(getType(name));
    }

}
