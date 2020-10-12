package com.slavic.hors.admin.api.dto.portal.role.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReResourcesRequest {

    @NotNull(message = "角色ID不允许为空")
    private Long id;

    private List<Long> resourceIds;
}
