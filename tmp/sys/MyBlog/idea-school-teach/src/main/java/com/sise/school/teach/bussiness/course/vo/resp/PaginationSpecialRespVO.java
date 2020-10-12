package com.sise.school.teach.bussiness.course.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页的课程信息
 *
 * @author idea
 * @data 2018/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationSpecialRespVO {

    private Integer total;

    private List<SpecialRespVO> specialRespVOList;

}
