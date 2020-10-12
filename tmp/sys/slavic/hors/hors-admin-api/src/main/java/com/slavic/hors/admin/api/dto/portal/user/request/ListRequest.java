package com.slavic.hors.admin.api.dto.portal.user.request;

import com.slavic.veles.base.request.PagerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PagerRequest {

    private String username;

    private String nickName;

    private Integer userStatus;

    private List<String> dateArr;

}
