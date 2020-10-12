package com.team.blogs.common.utils;

import lombok.Getter;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/6/27
 * @Version: 1.0
 */
@Getter
public class ResultEnum {

    private String status;

    private String msg;

    ResultEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
