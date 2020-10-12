package com.slavic.hors.admin.api.dto.portal.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReRolesRequest {

    @NotNull(message = "用户ID不允许为空")
    private Long id;

    private List<Long> roleIds;
}
