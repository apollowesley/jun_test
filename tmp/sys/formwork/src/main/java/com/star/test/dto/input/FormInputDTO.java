package com.star.test.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class FormInputDTO {

    @NotBlank(message = "formName不能为空")
    private String formName;
    @NotEmpty(message = "fieldsName不能为空")
    private List<String> fields;

}
