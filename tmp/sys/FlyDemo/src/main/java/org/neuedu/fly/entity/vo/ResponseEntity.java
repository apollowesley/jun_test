package org.neuedu.fly.entity.vo;

import lombok.Data;

import javax.xml.ws.RespectBinding;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/16-14:21
 **/
@Data
public class ResponseEntity {
    private Integer code;
    private String msg;
    private Integer count;
    private Object data;

    public ResponseEntity() {}

    public ResponseEntity(Integer code, String msg, Integer count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public static ResponseEntity ok(){
        return new ResponseEntity(0,null,null,null);
    }

    public static ResponseEntity ok(String msg){
        return new ResponseEntity(0,msg,null,null);
    }

    public static ResponseEntity ok(Object data){
        return new ResponseEntity(0,null,null,data);
    }

    public static ResponseEntity ok(String msg,Object data){
        return new ResponseEntity(0,msg,null,data);
    }

    public static ResponseEntity page(Integer count, Object data){
        return new ResponseEntity(0,null,count,data);
    }

    public static ResponseEntity fail(String msg){
        return new ResponseEntity(1,msg,null,null);
    }

}
