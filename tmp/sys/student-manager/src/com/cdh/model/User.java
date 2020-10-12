package com.cdh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private Integer userId;
    private String logName;
    private String logPwd;
    private Integer logStatus;
    private String userSex;
    private String userType;
    private LocalDateTime createTime;
}
