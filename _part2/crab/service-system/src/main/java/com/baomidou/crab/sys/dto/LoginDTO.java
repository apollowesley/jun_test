package com.baomidou.crab.sys.dto;

import com.baomidou.crab.core.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDTO extends BaseEntity {

    private String username;
    private String password;
    private String code;

}
