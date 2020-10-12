package com.foo.common.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.foo.common.base.enums.FormFieldType;
import com.foo.common.base.enums.FormFieldValidation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface JspCrud {

	/**
	 * 决定了form的input类型，默认为text
	 * 
	 * @return
	 */
	FormFieldType formFieldType() default FormFieldType.text;

	/**
	 * 决定了form的字段的label的描述，与dataTables的列名。该字段是必须的。为中文描述
	 * 
	 * @return
	 */
	String cnName();

	/**
	 * 是否验证字段,默认需要验证
	 * 
	 * @return
	 */
	boolean validateOnForm() default false;

	/**
	 * 是否在form显示，作为可编辑的字段
	 * 
	 * @return
	 */
	boolean displayOnForm();

	/**
	 * 是否在前台的dataTables里面显示该字段
	 * 
	 * @return
	 */
	boolean displayOnTable();

	/**
	 * 同Jquery validation的验证规则
	 */
	FormFieldValidation[] validation() default FormFieldValidation.empty;
}
