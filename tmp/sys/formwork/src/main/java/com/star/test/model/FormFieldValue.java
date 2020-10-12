package com.star.test.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "form_field_value")
public class FormFieldValue {

    private long formId;
    private long fieldValueId;
    private String fieldValue;
    private String createTime;
    private String updateTime;

}
