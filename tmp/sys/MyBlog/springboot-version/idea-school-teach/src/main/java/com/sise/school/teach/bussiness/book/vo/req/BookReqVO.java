package com.sise.school.teach.bussiness.book.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/12/7
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookReqVO {

    private Integer id;

    private String bookCode;

    private String bookName;

    private String bookImg;

    private String bookUrl;

    private Integer bookType;

    private String des;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
