package com.sise.school.teach.bussiness.type.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypePO {

    /**
     * id
     */
    private int id;

    /**
     * 系别名称
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
