package com.slavic.veles.base.response;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.function.Function;

@Data
public class ResponseEntity<T> implements Serializable {

    private static final String FAIL_DEFAULT_MESSAGE = "请求失败";

    private EntryHeader header = EntryHeader.HEADER_OK;

    private T body;

    public static <T> ResponseEntity<T> SUCCESS() {
        return SUCCESS(null);
    }

    public static <T> ResponseEntity<T> SUCCESS(T data) {
        ResponseEntity<T> result = new ResponseEntity<>();
        result.setBody(data);
        return result;
    }

    public static <T> ResponseEntity<T> FAIL() {
        return FAIL(FAIL_DEFAULT_MESSAGE);
    }

    public static <T> ResponseEntity<T> FAIL(String message) {
        ResponseEntity<T> result = new ResponseEntity<>();
        result.setHeader(EntryHeader.APPLY_ERROR(message));
        return result;
    }

    public static <T> ResponseEntity<T> FAIL(EntryHeader header) {
        ResponseEntity<T> result = new ResponseEntity<>();
        result.setHeader(header);
        return result;
    }

    public static <T> ResponseEntity<T> CONDITION(boolean condition) {
        return CONDITION(condition, null);
    }

    public static <T> ResponseEntity<T> CONDITION(boolean condition, String failDefaultMessage) {
        return CONDITION(condition, null, failDefaultMessage);
    }

    public static <T> ResponseEntity<T> CONDITION(boolean condition, T successData, String failDefaultMessage) {
        return CONDITION(condition, successData, Function.identity(), failDefaultMessage);
    }

    public static <T, E> ResponseEntity<T> CONDITION(boolean condition, E preset, Function<? super E, T> function, String failDefaultMessage) {
        if (!condition) {
            if (StringUtils.isEmpty(failDefaultMessage)) {
                return FAIL();
            }
            return FAIL(failDefaultMessage);
        }
        return SUCCESS(function.apply(preset));
    }
}