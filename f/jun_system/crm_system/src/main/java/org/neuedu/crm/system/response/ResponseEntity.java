package org.neuedu.crm.system.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 220019/12/13-19:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("前端统一返回对象")
public class ResponseEntity {
    @ApiModelProperty("成功失败标识符")
    private int code;
    @ApiModelProperty("响应字符串数据")
    private String msg;
    @ApiModelProperty("分页查询总条数")
    private Long total;
    @ApiModelProperty("查询响应数据")
    private Object data;

    public static ResponseEntity ok() {
        return new ResponseEntity(ResponseCode.SUCCESS.code, null, null, null);
    }

    public static ResponseEntity ok(String msg) {
        return new ResponseEntity(ResponseCode.SUCCESS.code, msg, null, null);
    }
    public static ResponseEntity ok(String msg,Object obj) {
        return new ResponseEntity(ResponseCode.SUCCESS.code, msg, null, obj);
    }
    public static ResponseEntity data(Object obj) {
        return new ResponseEntity(ResponseCode.SUCCESS.code, null, null, obj);
    }

    public static ResponseEntity page(long total, Object obj) {
        return new ResponseEntity(ResponseCode.SUCCESS.code, null, total, obj);
    }

    public static ResponseEntity failure(String msg) {
        return new ResponseEntity(ResponseCode.FAILURE.code, msg, null, null);
    }

    public static ResponseEntity error(String msg) {
        return new ResponseEntity(ResponseCode.FAILURE.code, msg, null, null);
    }

    public static ResponseEntity exception(ResponseCode responseCode) {
        return new ResponseEntity(responseCode.code, responseCode.message, null, null);
    }

    public static ResponseEntity exception(ResponseCode responseCode,String message) {
        return new ResponseEntity(responseCode.code, message, null, null);
    }

    public static boolean isSuccess(ResponseEntity responseEntity) {
        return responseEntity.getCode() == ResponseCode.SUCCESS.code;
    }

}
