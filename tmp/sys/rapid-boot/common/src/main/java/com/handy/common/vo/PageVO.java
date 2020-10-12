package com.handy.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVO implements Serializable {
    @ApiModelProperty(value = "状态码")
    private Long code;
    @ApiModelProperty(value = "返回消息")
    private String msg;
    @ApiModelProperty(value = "总条数")
    private Long count;
    @ApiModelProperty(value = "Json参数")
    private List data;


    private PageVO(List list, Long count) {
        this.code = 0L;
        this.msg = "";
        this.count = count;
        this.data = list;
    }

    public static PageVO pageVO(List list, Long count) {
        return new PageVO(list, count);
    }
}