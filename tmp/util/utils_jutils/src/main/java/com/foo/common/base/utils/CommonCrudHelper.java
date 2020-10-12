package com.foo.common.base.utils;

import com.foo.common.base.annotations.ColumnCrud;
import com.foo.common.base.annotations.EntityCrud;
import com.foo.common.base.annotations.JspCrud;
import com.foo.common.base.annotations.MgrCrud;
import com.foo.common.base.enums.FormFieldType;
import com.foo.common.base.enums.FormFieldValidation;
import com.foo.common.base.utils.CommonCrudBuilder;

import java.util.Date;

/**
 * Crud自动化类
 * <p>
 * <p>
 * 表单form顺序：text两个一行，textarea单独一行，img单独一行
 *
 * @author Steve
 */
@EntityCrud(modelUpperCamelName = "DmcDefaultSpace", jspTitleName = "项目空间管理")
@MgrCrud(id2bean = true)
public class CommonCrudHelper {

    @ColumnCrud(comment = "name", length = 36)
    @JspCrud(formFieldType = FormFieldType.text, displayOnTable = true, displayOnForm = true, cnName = "空间名称", validation = {
            FormFieldValidation.required})
    private String name;


    public static void main(String[] args) throws Exception {
        CommonCrudBuilder.INSTANCE.execute();
    }

}
