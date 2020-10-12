package com.mkfree.sample.httpserver4;

public enum HttpStatus {
    OK(200, "OK"), NOT_FOUND(404, "Not Found"), FORBIDDEN(403, "Forbidden"), BAD_GATEWAY(502, "Bad Gateway"), FOUND(302, "Found"),
    ;

    public int code;
    public String desc;

    HttpStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
