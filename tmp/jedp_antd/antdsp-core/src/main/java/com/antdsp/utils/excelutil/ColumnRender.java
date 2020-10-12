package com.antdsp.utils.excelutil;

public interface ColumnRender {
	
	default String render(String value) {
		return value;
	}
	
	default String render(String value , Object record) {
		return render(value);
	}
}
