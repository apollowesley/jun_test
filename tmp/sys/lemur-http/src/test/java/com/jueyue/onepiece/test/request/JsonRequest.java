package com.jueyue.onepiece.test.request;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.entity.enums.RequestDataTypeEnum;

import java.util.Map;

/**
 * @author by jueyue on 18-8-2.
 */
@IRequest
public interface JsonRequest {

    @IRequestMethod(dataType = RequestDataTypeEnum.JSON, url = "${test.url}")
    public String test(Map<String, String> map);
}
