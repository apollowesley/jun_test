package com.sise.school.teach.bussiness.book.po;

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
public class BookPO {

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
