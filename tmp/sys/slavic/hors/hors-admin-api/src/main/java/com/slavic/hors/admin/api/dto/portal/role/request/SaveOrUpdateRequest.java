package com.slavic.hors.admin.api.dto.portal.role.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SaveOrUpdateRequest {

    private Long id;

    @NotEmpty(message = "角色名不允许为空")
    private String roleName;

    @NotEmpty(message = "角色描述不允许为空")
    private String roleDesc;

}
