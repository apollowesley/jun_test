package com.sise.school.teach.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页显示集合
 * @author idea
 * @data 2018/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRespVO<T> {

    private Integer total;

    private List<T> list;
}
