package org.neuedu.fly.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/21-9:36
 **/
@Data
public class User {
    private Integer id;
    private String tel;
    private String nikeName;
    private String pwd;
    private String sex;
    private LocalDateTime createTime;

}
