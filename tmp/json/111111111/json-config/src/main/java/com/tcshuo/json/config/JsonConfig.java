/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * json 输出配置
 *
 * @author tengda
 */
public abstract class JsonConfig {

    /**
     * 忽略复杂字段
     */
    public final static String IGNORE_COMPLICATE = "c";

    /**
     * 不忽略
     */
    public final static String IGNORE_NO = "n";

    /**
     * 忽略不在allows允许列表的字段
     */
    public final static String IGNORE_NO_ALLOW = "na";

    /**
     * 输出格式化参数
     */
    protected String format;

    /**
     * 子配置
     */
    protected Map<String, JsonConfig> childs = new HashMap<String, JsonConfig>();

    /**
     * 忽略模式
     */
    protected String ignore = IGNORE_COMPLICATE;

    /**
     * 允许输出列表 凡在allows的一律输出
     */
    protected Set<String> allows = new HashSet<String>();

    /**
     * 忽略输出列表 凡在ignores的一律不输出
     */
    protected Set<String> ignores = new HashSet<String>();

    protected Map<String, String> alias = new HashMap<String, String>();

    /**
     * 目标对象
     */
    protected Object target;

    public JsonConfig(Object target) {
        this.target = target;
        this.ignores.add("class");
    }

    /**
     * 转化为jsonNode对象
     *
     * @return
     */
    protected abstract Node toObjectNode();
    
     /**
     * 输出json
     *
     * @return
     */
    public  String toJson(){
        return this.toObjectNode().toJson();
    }
 
    /**
     * 是否被指定忽略某字段
     *
     * @param name
     * @return
     */
    public boolean isIgnore(String name) {
        return this.getIgnores().contains(name);
    }

    /**
     * 是否被指定允许某字段
     *
     * @param name
     * @return
     */
    public boolean isAllow(String name) {
        return this.allows.contains(name);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<String, JsonConfig> getChilds() {
        return childs;
    }

    public void setChilds(Map<String, JsonConfig> childs) {
        this.childs = childs;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public Set<String> getAllows() {
        return allows;
    }

    public void setAllows(Set<String> allows) {
        this.allows = allows;
    }

    public Set<String> getIgnores() {
        return ignores;
    }

    public void setIgnores(Set<String> ignores) {
        this.ignores = ignores;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Map<String, String> getAlias() {
        return alias;
    }

    public void setAlias(Map<String, String> alias) {
        this.alias = alias;
    }

}
