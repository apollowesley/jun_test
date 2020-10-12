package com.slavic.hors.admin.api.dto.portal.resource.request;

import com.slavic.veles.base.request.PagerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PagerRequest {

    private String resourceName;

    private String resourceDesc;

    private Long roleId;

}
