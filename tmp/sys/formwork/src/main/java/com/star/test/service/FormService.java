package com.star.test.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.star.test.constant.FormConstant;
import com.star.test.exception.TwException;
import com.star.test.mapper.FormFieldValueMapper;
import com.star.test.mapper.FormMapper;
import com.star.test.model.FormFieldValue;
import com.star.test.model.FormInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FormService {

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormFieldValueMapper formFieldValueMapper;

    public long createForm(String formName, List<String> fields) {
        if (StringUtils.isEmpty(formName) || fields == null || fields.size() == 0) {
            throw new TwException(FormConstant.ARGUMENT_ERROR_MSG);
        }
        FormInfo formInfo = FormInfo.builder().name(formName).fields(fields.toString()).createTime(getLocalDateTime()).updateTime(getLocalDateTime()).build();
        formMapper.insert(formInfo);
        return formInfo.getId();
    }

    public FormInfo queryFormInfo(long id) {
        if (id == 0L) {
            throw new TwException(FormConstant.ARGUMENT_ERROR_MSG);
        }
        return formMapper.selectOne(new QueryWrapper<FormInfo>().eq("id", id));
    }

    public long addFormFieldsData(long formId, JSONObject formData) {
        if (formId == 0L || formData == null) {
            throw new TwException(FormConstant.ARGUMENT_ERROR_MSG);
        }

        HashMap<String, String> hashMap = JSONObject.parseObject(formData.toJSONString(), new TypeReference<HashMap<String, String>>() {
        });

        FormInfo formInfo = formMapper.selectOne(new QueryWrapper<FormInfo>().eq("id", formId));
        if (formInfo == null) {
            throw new TwException(FormConstant.FIELD_ID_NOT_EXIST);
        }

        String str = formInfo.getFields().replaceAll(" ", "");
        Set<String> formFields = new HashSet<>(Arrays.asList(str.substring(1, str.length() - 1).split(",")));
        Set<String> hashFields = new HashSet<>(hashMap.keySet());

        judgeFormFieldKeySame(formFields, hashFields);

        FormFieldValue formFieldValue = FormFieldValue.builder().fieldValueId(System.currentTimeMillis()).formId(formId).fieldValue(formData.toString()).createTime(getLocalDateTime()).updateTime(getLocalDateTime()
        ).build();
        formFieldValueMapper.insert(formFieldValue);
        return formFieldValue.getFieldValueId();
    }

    public FormFieldValue queryFormFieldValueByFieldValueId(long fieldDataId) {
        if (fieldDataId == 0L) {
            throw new TwException(FormConstant.FIELD_DATA_ID_NOT_EXIST);
        }
        FormFieldValue formFieldValue = formFieldValueMapper.selectOne(new QueryWrapper<FormFieldValue>().eq("field_value_id", fieldDataId));
        if (formFieldValue == null) {
            throw new TwException(FormConstant.FIELD_DATA_NOT_EXIST);
        }
        return formFieldValue;
    }

    private String getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }

    private void judgeFormFieldKeySame(Set<String> set1, Set<String> set2) {

        if (set1.size() != set2.size()) {
            throw new TwException(FormConstant.FIELD_NAME_ERROR_MSG);
        }

        for (String str1 : set1) {
            if (!set2.contains(str1)) {
                throw new TwException(FormConstant.FIELD_NAME_ERROR_MSG);
            }
        }
    }


}
