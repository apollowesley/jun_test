package com.star.test.dto.input;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class FormFieldsDataInputDTO {
    @Min(value = 1L,message = "formId不能小于1")
    private Long formId;
    @NotNull(message = "fields数据不能为空")
    private JSONObject fieldsData;
}
