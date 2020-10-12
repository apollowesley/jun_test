package com.sise.school.teach.common.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author idea
 * @data 2018/12/5
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    CHECKING("审核中", 1),
    NOT_UP("未上线", 2),
    IS_UP("已上线", 3),
    IS_DOWN("已下架", 4);

    /**
     * 描述
     */
    private String des;

    /**
     * 代码
     */
    private int code;

}
