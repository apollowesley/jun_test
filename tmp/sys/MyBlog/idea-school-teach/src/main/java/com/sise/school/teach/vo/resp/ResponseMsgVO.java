package com.sise.school.teach.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
public class ResponseMsgVO<T> extends ResponseVO {


    @ApiModelProperty(value = "响应数据", example = "操作成功", position = 4)
    public T data;

    public ResponseMsgVO(boolean success, String code, String msg) {
        super(success,code,msg);
        this.data = null;
    }

}
