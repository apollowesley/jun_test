package com.globalClass;

public final class GlobalVariable {
	private static String website_name = "";/* 网站名称 名字*/
	private static String website_template = "";/* pc模板路径 */
	private static String website_mobile = "";/* 手机模板路径 */
	private static String website_title = "";/* 网站标题 名字的说明*/

	public static String getWebsite_name() {
		return website_name;
	}

	public static void setWebsite_name(String website_name) {
		GlobalVariable.website_name = website_name;
	}

	public static String getWebsite_template() {
		return website_template;
	}

	public static void setWebsite_template(String website_template) {
		GlobalVariable.website_template = website_template;
	}

	public static String getWebsite_mobile() {
		return website_mobile;
	}

	public static void setWebsite_mobile(String website_mobile) {
		GlobalVariable.website_mobile = website_mobile;
	}

	public static String getWebsite_title() {
		return website_title;
	}

	public static void setWebsite_title(String website_title) {
		GlobalVariable.website_title = website_title;
	}

}
