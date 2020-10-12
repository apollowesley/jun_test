package com.slavic.veles.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PagerRequest extends BaseRequest {

    private Integer currentPage = 1;

    private Integer pageSize = 10;

}
