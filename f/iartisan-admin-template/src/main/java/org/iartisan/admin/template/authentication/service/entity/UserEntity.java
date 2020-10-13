package org.iartisan.admin.template.authentication.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author King
 * @since 2018/2/26
 */
@Data
public class UserEntity {

    private String userId;

    private String userName;

    private String userStatus;

    private String userPwd;

    private Date createDate;

    private String roles;

    private String staffId;
}
