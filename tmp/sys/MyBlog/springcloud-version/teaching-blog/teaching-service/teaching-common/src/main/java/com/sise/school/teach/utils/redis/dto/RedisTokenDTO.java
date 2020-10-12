package com.sise.school.teach.utils.redis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 从redis中获取的token存储dto
 * @author idea
 * @data 2018/12/2
 */
@Data
@NoArgsConstructor
public class RedisTokenDTO {

    private String account;

    private String unionCode;

    private String headImg;

    private String tel;

    private String email;
}
