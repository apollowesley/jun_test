package com.star.test;

import com.alibaba.fastjson.JSONObject;
import com.star.test.exception.TwException;
import com.star.test.mapper.FormFieldValueMapper;
import com.star.test.mapper.FormMapper;
import com.star.test.model.FormFieldValue;
import com.star.test.model.FormInfo;
import com.star.test.service.FormService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDynamicFormServiceTests {

    @Mock
    private FormMapper formMapper;

    @Mock
    private FormFieldValueMapper formFieldValueMapper;

    @InjectMocks
    private FormService formService;

    @Test
    public void should_create_form_be_success() {
        FormInfo formInfo = FormInfo.builder().id(123L).name("test").fields("s,ss").build();
        List<String> list = Arrays.asList(formInfo.getFields().split(","));
        when(formMapper.insert(formInfo)).thenReturn(1);
        assertThrows(NullPointerException.class,()->formService.createForm(formInfo.getName(),list));
    }

    @Test
    public void should_create_form_be_exception(){
        assertThrows(TwException.class,()->formService.createForm("",new ArrayList<>()));
    }


    @Test
    public void should_select_form_be_success_and_exception() {
        FormInfo formInfo = FormInfo.builder().id(123L).name("test").fields("s,ss").build();
        when(formMapper.selectOne(any())).thenReturn(formInfo);
        assertEquals(formInfo, formService.queryFormInfo(formInfo.getId()));
        assertThrows(TwException.class, () -> formService.queryFormInfo(0L));
    }


    @Test
    public void should_add_form_fields_data_be_exception() {
        assertThrows(TwException.class, () -> formService.addFormFieldsData(0L, new JSONObject()));

        when(formMapper.selectOne(any())).thenReturn(null);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("s","123");
        assertThrows(TwException.class, () -> formService.addFormFieldsData(1L, jsonObject));

    }

    @Test
    public void should_add_form_fields_data_be_success() {
        FormInfo formInfo = FormInfo.builder().id(123L).name("test").fields("[id,name,password]").build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "123");
        jsonObject.put("name", "star");
        jsonObject.put("password", "123456");

        when(formMapper.selectOne(any())).thenReturn(formInfo);
        when(formFieldValueMapper.insert(any())).thenReturn(1);

        assertNotNull(formService.addFormFieldsData(formInfo.getId(), jsonObject));
    }

    @Test
    public void should_select_form_field_data_by_field_id_be_exception() {
        assertThrows(TwException.class, () -> formService.queryFormFieldValueByFieldValueId(0L));

        when(formFieldValueMapper.selectOne(any())).thenReturn(null);
        assertThrows(TwException.class, () -> formService.queryFormFieldValueByFieldValueId(0L));
    }

    @Test
    public void should_select_form_field_data_by_field_id_be_success() {
        FormFieldValue fieldValue = mock(FormFieldValue.class);
        when(formFieldValueMapper.selectOne(any())).thenReturn(fieldValue);
        assertEquals(fieldValue,formService.queryFormFieldValueByFieldValueId(1L));
    }


}
