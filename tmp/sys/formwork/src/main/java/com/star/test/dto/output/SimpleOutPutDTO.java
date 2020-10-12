package com.star.test.dto.output;

import com.star.test.constant.FormConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleOutPutDTO implements Serializable {

    private String code = FormConstant.SUCCESS_CODE;
    private String msg = FormConstant.SUCCESS_MSG;

}
