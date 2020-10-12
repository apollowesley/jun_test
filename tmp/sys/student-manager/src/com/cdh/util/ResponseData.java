package com.cdh.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseData {
    private Integer code;
    private String msg;
    private Integer pageCount;
    private Object data;
    private Object[][] pageData;

    public ResponseData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseData ok(String msg){
        return new ResponseData(1,msg,null);
    }

    public static ResponseData fail(String msg){
        return new ResponseData(0,msg,null);
    }

    public static ResponseData okData(Object data){
        return new ResponseData(1,null,data);
    }

    public static ResponseData okPage(Object[][] pageData,Integer pageCount){
        return new ResponseData(1,null,pageCount,null,pageData);
    }

}
