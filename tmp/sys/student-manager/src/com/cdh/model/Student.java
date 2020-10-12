package com.cdh.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Data
public class Student {
    private Integer stuId;
    private String stuName;
    private Integer claId;
    private Integer userId;
    private LocalDateTime createTime;

}
