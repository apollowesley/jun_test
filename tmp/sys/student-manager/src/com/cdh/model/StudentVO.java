package com.cdh.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StudentVO {
    private Integer stuId;
    private String stuName;
    private String logName;
    private String logStatus;
    private String userSex;
    private String claName;
    private LocalDateTime createTime;
}
