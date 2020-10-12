package com.cdh.springboot.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    private int code; //0 成功标识  1失败标识
    private String msg;
    private Long count;  //分页的数据总条数
    private Object data;

    public static ResponseData ok(String msg){
        return new ResponseData(0,msg,null,null);
    }

    public static ResponseData ok(Object data){
        return new ResponseData(0,null,null,data);
    }

    public static ResponseData page(long count, Object data ){
        return new ResponseData(0,null,count,data);
    }

    public static ResponseData fail(String msg){
        return new ResponseData(1,msg,null,null);
    }

    public static ResponseData error(ResponseCode code,Object data){
        return new ResponseData(code.getCode(),code.getMsg(),null,data);
    }

    public static ResponseData error(int code, String msg){
        return new ResponseData(code,msg,null,null);
    }

}
