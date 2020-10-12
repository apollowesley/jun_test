package com.foo.common.base.enums;

/**
 * 完全同java的field的type
 * 
 * @author think
 *
 */
public enum JavaFieldType {
	Date("Date"), String("String"), Int("int"), Double("double");
	private String javaFieldType;

	private JavaFieldType(String javaFieldType) {
		this.javaFieldType = javaFieldType;
	}

	@Override
	public java.lang.String toString() {
		return javaFieldType;
	}

	public static void main(String[] args) {
		System.out.println(JavaFieldType.Date);
	}
}
