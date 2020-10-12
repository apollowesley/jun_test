package com.star.test.controller;

import com.star.test.dto.input.FormFieldsDataInputDTO;
import com.star.test.dto.input.FormInputDTO;
import com.star.test.dto.output.CommonOutPutDTO;
import com.star.test.dto.output.OutData;
import com.star.test.service.FormService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FormController {

    @Autowired
    private FormService formService;

    @ApiOperation(value = "创建表单", notes = "创建表单接口文档")
    @PostMapping(value = "/createForm")
    public CommonOutPutDTO createForm(@RequestBody @Valid FormInputDTO inputDTO) {
        long formId = formService.createForm(inputDTO.getFormName(), inputDTO.getFields());
        return new CommonOutPutDTO(OutData.builder().key("formId").value(formId).build());
    }

    @ApiOperation(value = "查看表单", notes = "查看表单接口文档")
    @GetMapping(value = "/selectForm")
    public CommonOutPutDTO selectForm(@RequestParam @Valid long formId) {
        return new CommonOutPutDTO(formService.queryFormInfo(formId));
    }


    @ApiOperation(value = "填写表单", notes = "填写表单接口")
    @PostMapping(value = "/fillFormData")
    public CommonOutPutDTO fillFormData(@RequestBody @Valid FormFieldsDataInputDTO inputDTO) {
        return new CommonOutPutDTO(OutData.builder().key("formDataId").value(formService.addFormFieldsData(inputDTO.getFormId(), inputDTO.getFieldsData())).build());
    }

    @ApiOperation(value = "查看表单数据", notes = "查看表单数据接口文档")
    @GetMapping(value = "/selectFormData")
    public CommonOutPutDTO selectFormData(@RequestParam @Valid long formDataId) {
        return new CommonOutPutDTO(formService.queryFormFieldValueByFieldValueId(formDataId));
    }


}
