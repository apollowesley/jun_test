package com.baomidou.crab.sys.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleResourceDTO implements Serializable {

    private Long roleId;
    private List<Long> resourceIds;

}
