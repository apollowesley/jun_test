/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import com.tcshuo.json.object.Formatter;
import com.tcshuo.json.object.JavaType;
import com.tcshuo.json.object.ObjectConstructor;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author tengda
 */
public abstract class Node implements java.io.Serializable {

    public abstract String toJson();

    protected Formatter formatter = null;

    protected ObjectConstructor constructor = null;

    public abstract Node getChildNode(String index);

    public Object toObject() {
        return toObject((Class) null);
    }

    public Object toObject(Type clazz) {
        if (clazz == null) {
            return toObject((JavaType) null);
        }
        return this.toObject(new JavaType(clazz));
    }

    public Object toObject(JavaType type) {
        if (this.getFormatter() != null) {
            return this.getFormatter().format(this);
        }
        return this.toObjectByType(type);
    }

    protected abstract Object toObjectByType(JavaType type);

    public static Node formJson(String json) throws ParseException {
        try {
            json = StringEscapeUtils.unescapeHtml4(json);
            JsContext context = new JsContext();
            ScriptContext scriptContext = context.executeContext("var json = " + json + ";");
            return _parser(scriptContext.getAttribute("json"));

        } catch (ScriptException ex) {
            throw new ParseException(json, ex.getLineNumber());
        }
    }

    private static Node _parser(Object o) {
        if (o == null) {
            return new NullNode();
        }
        if (o instanceof CharSequence) {
            return new TextNode(o.toString());
        }
        if (o instanceof Date) {
            return new TextNode(o.toString());
        }
        if (o instanceof Number) {
            return new NumberNode((Number) o);
        }
        if (o instanceof Boolean) {
            return new BooleanNode((Boolean) o);
        }
        if (o instanceof List) {
            return _parserNativeArray((List) o);
        }
        if (o instanceof Map) {
            return _parserNativeObject((Map) o);
        }

        return new TextNode(o.toString());

    }

    private static Node _parserNativeObject(Map<String, Object> map) {

        ArrayList list = new ArrayList();
        for (int i = 0; i < map.size(); i++) {
            String key = i + "";
            if (map.containsKey(key)) {
                list.add(map.get(key));
            }
        }
        if (list.size() == map.size()) {
            return _parserNativeArray(list);
        }

        ObjectNode node = new ObjectNode();

        for (String key : map.keySet()) {
            System.out.println();
            node.pushProperty(key, _parser(map.get(key)));
        }
        return node;
    }

    private static Node _parserNativeArray(List list) {
        ArrayNode node = new ArrayNode();

        for (int i = 0; i < list.size(); i++) {
            node.add(_parser(list.get(i)));
        }

        return node;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public ObjectConstructor getConstructor() {
        return constructor;
    }

    public void setConstructor(ObjectConstructor constructor) {
        this.constructor = constructor;
    }

}
