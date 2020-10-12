package com.slavic.hors.admin.api.dto.portal.user.response;

import com.slavic.hors.orm.entity.HorsPortalRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private List<HorsPortalRole> all;

    private List<HorsPortalRole> added;

}
