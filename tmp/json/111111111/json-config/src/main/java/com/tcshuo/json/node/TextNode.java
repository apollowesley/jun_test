/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.Converter;
import com.tcshuo.json.object.JavaType;

/**
 *
 * @author tengda
 */
public class TextNode extends Node {

    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toJson() {
        return "\"" + text + "\"";
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    protected Object toObjectByType(JavaType type) {
        if (type == null || type.getMain() == null) {
            return this.text;
        }
        return Converter.converter(this.text, type.getMain());
    }

    @Override
    public Node getChildNode(String index) {
        return null;
    }

}
