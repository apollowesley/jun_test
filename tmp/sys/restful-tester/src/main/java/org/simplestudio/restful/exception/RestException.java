package org.simplestudio.restful.exception;

public class RestException extends RuntimeException {

    private static final long serialVersionUID = 9116828223642648967L;

    public RestException(String message) {
        super(message);
    }

}
