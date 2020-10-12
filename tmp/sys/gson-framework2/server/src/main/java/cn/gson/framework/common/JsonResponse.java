package cn.gson.framework.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.common</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private Boolean success = true;

    private String msg;

    private Object data;

    public static JsonResponse success() {
        return success("操作成功！");
    }

    public static JsonResponse success(String msg) {
        return new JsonResponse(true, msg, null);
    }

    public static JsonResponse failure(String msg) {
        return new JsonResponse(false, msg, null);
    }

    public static JsonResponse success(String msg, Object data) {
        return new JsonResponse(true, msg, data);
    }

    public static JsonResponse failure(String msg, Object data) {
        return new JsonResponse(false, msg, data);
    }

    public JsonResponse put(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }

        if (data instanceof Map) {
            ((Map) data).put(key, value);
        }

        return this;
    }
}
