/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

public enum RequestType {
    ALL("ALL"), POST("POST"), GET("GET"), PUT("PUT"), DELETE("DELETE");
    private String value;

    private RequestType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
