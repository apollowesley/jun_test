package com.website.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@NoArgsConstructor
public class Result implements Serializable {

    private boolean flag;
    private String code;
    private String message;
    private Object data;

    public Result(boolean flag, String code, String message, Object data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, String code, String message) {
        super();
        this.flag = flag;
        this.message = message;
        this.code = code;
    }
}
