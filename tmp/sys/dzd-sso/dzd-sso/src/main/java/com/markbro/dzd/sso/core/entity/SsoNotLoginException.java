package com.markbro.dzd.sso.core.entity;

public class SsoNotLoginException extends RuntimeException {

    public SsoNotLoginException() {
        super("sso not login");
    }

    public SsoNotLoginException(String msg) {
        super(msg);
    }

    public SsoNotLoginException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SsoNotLoginException(Throwable cause) {
        super(cause);
    }

}
