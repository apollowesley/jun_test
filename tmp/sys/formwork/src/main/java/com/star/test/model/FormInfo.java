package com.star.test.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@TableName(value = "form_info")
public class FormInfo {

    private Long id;
    private String name;
    private String fields;
    private String createTime;
    private String updateTime;
}
