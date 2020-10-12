package org.neuedu.fly.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @auther: Lily
 * @date: 2019/10/17-10:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity {
    private int code;
    private String msg;
    private Long count;
    private Object data;

    public static ResponseEntity ok(){
        return new ResponseEntity(0,null,null,null);
    }
    public static ResponseEntity ok(String msg){
        return new ResponseEntity(0,msg,null,null);
    }
    public static ResponseEntity error(String msg){
        return new ResponseEntity(1,msg,null,null);
    }

    public static ResponseEntity data(Object obj){
        return new ResponseEntity(0,null,null,obj);
    }

    public static ResponseEntity page(long count,Object obj){
        return new ResponseEntity(0,null,count,obj);
    }

    public static boolean isSuccess(ResponseEntity responseEntity){
        return responseEntity.getCode() == 0;
    }


}
