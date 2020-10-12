package com.sise.school.teach.bussiness.user.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

}
