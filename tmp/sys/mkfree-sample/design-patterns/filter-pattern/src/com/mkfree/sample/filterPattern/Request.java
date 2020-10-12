package com.mkfree.sample.filterPattern;

import java.util.HashMap;
import java.util.Map;

public class Request {

    /**
     * 访问url
     */
    private String url;

    /**
     * 请求参数
     */
    private Map<String, String> params = new HashMap<>();
    /**
     * 请求头
     */
    private Map<String, String> headers = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
