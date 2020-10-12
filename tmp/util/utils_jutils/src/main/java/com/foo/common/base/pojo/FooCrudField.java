package com.foo.common.base.pojo;

import com.foo.common.base.enums.FormFieldType;
import com.foo.common.base.enums.FormFieldValidation;
import com.google.common.base.MoreObjects;

public class FooCrudField {

	private FormFieldType formFieldType;// 决定了form的input类型，比如textarea，或者是date。如果为hidden，那么这些字段就是不可编辑的

	private String cnName = "";// 决定了form的字段的label的描述，与dataTables的列名。该字段是必须的。为中文描述。enName是对应filed的名字的

	private boolean validateOnForm = false;// 是否验证字段,默认需要验证

	private boolean displayOnTable = false;// 是否在前台的dataTables里面显示该字段
	private boolean displayOnForm = false;// 是否在前台的form里面显示该字段

	private String fieldName = "";
	private String fieldType = "";// 同java类型，目前支持：Date,int,String
	private String filedGetAndSetStr = "";// filed的get和set字串，例如：model.setContent(Strings.nullToEmpty(request.getParameter("content")));

	private FormFieldValidation[] formFieldValidationArray;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("formFieldType", formFieldType).add("cnName", cnName)
				.add("validateOnForm", validateOnForm)
				.add("displayOnTable", displayOnTable)
				.add("filedName", fieldName).add("fieldType", fieldType)
				.toString();
	}

	public FormFieldType getFormFieldType() {
		return formFieldType;
	}

	public void setFormFieldType(FormFieldType formFieldType) {
		this.formFieldType = formFieldType;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public boolean isValidateOnForm() {
		return validateOnForm;
	}

	public void setValidateOnForm(boolean validateOnForm) {
		this.validateOnForm = validateOnForm;
	}

	public boolean isDisplayOnTable() {
		return displayOnTable;
	}

	public void setDisplayOnTable(boolean displayOnTable) {
		this.displayOnTable = displayOnTable;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFiledGetAndSetStr() {
		return filedGetAndSetStr;
	}

	public void setFiledGetAndSetStr(String filedGetAndSetStr) {
		this.filedGetAndSetStr = filedGetAndSetStr;
	}

	public FormFieldValidation[] getFormFieldValidationArray() {
		return formFieldValidationArray;
	}

	public void setFormFieldValidationArray(
			FormFieldValidation[] formFieldValidationArray) {
		this.formFieldValidationArray = formFieldValidationArray;
	}

	public boolean isDisplayOnForm() {
		return displayOnForm;
	}

	public void setDisplayOnForm(boolean displayOnForm) {
		this.displayOnForm = displayOnForm;
	}

}
