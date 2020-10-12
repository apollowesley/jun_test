package com.sise.school.teach.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
@AllArgsConstructor
public class ResponseVO {

    @ApiModelProperty(value = "是否成功", example = "true", position = 1)
    public boolean success;

    @ApiModelProperty(value = "响应代码", example = "000000", position = 2)
    public String code;

    @ApiModelProperty(value = "响应信息", example = "操作成功", position = 3)
    public String msg;
}
