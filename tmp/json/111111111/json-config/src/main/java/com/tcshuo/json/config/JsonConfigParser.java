/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.ArrayNode;
import com.tcshuo.json.node.Node;
import com.tcshuo.json.node.ObjectNode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author tengda
 */
public abstract class JsonConfigParser {

    /**
     *
     * json输出配置
     *
     * {ignore:'c'}忽略复杂字段 {ignore:'n'} 不忽略字段 {ignore:'na'} 忽略不在allows允许列表的字段
     * {ignores:['dept','post']}凡在ignores的一律不输出 保证不输出dept、post字段
     * {allows:['id','name']}凡在allows的一律输出 保证输出字段id、name
     * {allows:['id','name'],ignore:'na'}只输出字段id、name {}默认：不输出复杂字段、集合或数组字段
     * {ignore:'c'，allows:['dept']}除dept的复杂对象都不输出
     * {alias:{id:'value'}}字段别名输出，id字段输出为value字段
     * --------------------------------------------------------------------------------
     * { allows:['id','name','date','dept'] ,childs :{
     * date:{format:'yyyy-MM-dd'} ,dept:{allows:['name']} }
     * }输出id、name、date、dept、字段，其中date字段使用yyyy-MM-dd格式化
     *
     * --------------------------------------------------------------------------------
     *
     *
     *
     * @param config
     * @param target
     * @return
     */
    public static JsonConfig parser(String config, Object target) {

        if (StringUtils.isBlank(config) || target == null) {
            return _parser(null, target);
        }

        ObjectNode rootNode = null;

        try {
            rootNode = (ObjectNode) Node.formJson(config);
        } catch (Exception ex) {
            ex.printStackTrace();
            return _parser(null, target);
        }
        return _parser(rootNode, target);
    }



    static JsonConfig _parser(ObjectNode rootNode, Object target) {

        JsonConfig result = null;
        if (target == null) {
            result = new NullConfig();
        } else if (target.getClass().isEnum()) {
            result = new EnumConfig(target);
        } else if (target instanceof Date) {
            result = new DateConfig((Date) target);
        } else if (target instanceof Calendar) {
            result = new DateConfig(((Calendar) target).getTime());
        } else if (target instanceof Number) {
            result = new NumberConfig((Number) target);
        } else if (target instanceof Boolean) {
            result = new BooleanConfig((Boolean) target);
        }  else if (target instanceof Collection) {
            result = new CollectionConfig((Collection) target, rootNode);
        } else if (target.getClass().isArray()) {
            result = new ArrayConfig(target, rootNode);
        } else if (target instanceof Map) {
            result = new MapConfig((Map) target);
        } else if (Utils.isSimpleValueType(target.getClass())) {
            result = new SimpleConfig(target);
        } else {
            result = new BeanConfig(target);
        }
        if (rootNode == null) {
            return result;
        }

        if (rootNode.has("allows")) {
            ArrayNode allows = (ArrayNode) rootNode.getProperty("allows");
            for (int i = 0; i < allows.getLength(); i++) {
                result.getAllows().add(StringUtils.trim(allows.get(i).toString()));
            }
        }
        if (rootNode.has("ignores")) {
            ArrayNode ignores = (ArrayNode) rootNode.getProperty("ignores");
            for (int i = 0; i < ignores.getLength(); i++) {
                result.getIgnores().add(StringUtils.trim(ignores.get(i).toString()));
            }
        }
        if (rootNode.has("ignore")) {
            result.setIgnore(rootNode.getProperty("ignore").toString());
        }

        if (rootNode.has("format")) {
            result.setFormat(rootNode.getProperty("format").toString());
        }

        if (rootNode.has("alias")) {

            ObjectNode alias = (ObjectNode) rootNode.getProperty("alias");
            for (Iterator<String> ite = alias.getPropertyNames().iterator(); ite.hasNext();) {
                String name = ite.next();
                result.getAlias().put(name, alias.getProperty(name).toString());
            }
        }

        if (result instanceof ArrayConfig) {
            return result;
        }

        if (rootNode.has("childs")) {

            ObjectNode childs = (ObjectNode) rootNode.getProperty("childs");
            for (Iterator<String> ite = childs.getPropertyNames().iterator(); ite.hasNext();) {
                String name = ite.next();
                Object childValue = null;
                try {
                    childValue = PropertyUtils.getProperty(target, name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (childValue == null) {
                    continue;
                }
                ObjectNode childNode = (ObjectNode) childs.getProperty(name);
                result.getChilds().put(name, _parser(childNode, childValue));
            }
        }
        return result;
    }
    

}
