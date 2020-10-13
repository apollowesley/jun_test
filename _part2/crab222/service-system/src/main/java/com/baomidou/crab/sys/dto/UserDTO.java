package com.baomidou.crab.sys.dto;

import java.util.List;

import com.baomidou.crab.sys.entity.User;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户 DTO
 * </p>
 *
 * @author jobob
 * @since 2018-10-05
 */
@Data
@Accessors(chain = true)
public class UserDTO extends User {

    /**
     * 角色 ID 集合
     */
    List<Long> roleIds;

}
