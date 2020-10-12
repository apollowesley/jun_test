package cn.backflow.lib.util;


import java.util.LinkedHashMap;

/**
 * Object for JSON response
 * Created by backflow on 2015/12/24 16:26
 */
public class JsonMap extends LinkedHashMap<String, Object> {

    private static final String successKey = "success";
    private static final String msgKey = "msg";
    private JsonMap root; // 转换JSON时要以该元素做为要根元素

    public JsonMap() {
        root = this;
    }

    private JsonMap(JsonMap root) {
        this.root = root;
    }

    public JsonMap(boolean success) {
        put(successKey, success);
    }

    public JsonMap(boolean success, String msg) {
        put(successKey, success);
        put(msgKey, msg);
    }

    public static JsonMap create() { return new JsonMap(); }

    public static JsonMap succeed() {
        return new JsonMap(true);
    }

    public static JsonMap fail(String msg) {
        return new JsonMap(false).msg(msg);
    }

    public JsonMap success(boolean success) {
        return put(successKey, success).put(msgKey,null);
    }

    @Override
    public JsonMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }


    public JsonMap child(String name) {
        JsonMap child = (JsonMap) get(name);
        if (child == null) {
            child = new JsonMap(root);
            this.put(name, child);
        }
        return child;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(root);
    }

    public String msg() {
        return get(msgKey).toString();
    }

    public JsonMap msg(Object msg) {
        return put(msgKey, msg);
    }

    public JsonMap msg(String format, Object... args) {
        return put(msgKey, String.format(format, args));
    }
}
