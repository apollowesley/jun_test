package com.slavic.hors.admin.api.dto.portal.role.request;

import com.slavic.veles.base.request.PagerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PagerRequest {

    private String roleName;

    private String roleDesc;

}
