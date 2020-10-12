package com.foo.common.base.utils;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

public class GuavaHelper {

	public static void main(String[] args) {
		String k1 = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
				"MhoSkillType");
		System.out.println(k1);
		System.out.println(k1);

		String actionPath = "/" + StringUtils.replace(CaseFormat.UPPER_CAMEL
				.to(CaseFormat.LOWER_UNDERSCORE, "MhoSkillType"), "_", "/");

		System.out.println(actionPath);
		System.out.println(
				FooUtils.generateCreateDatabaseSql("industry-platform"));
	}

}
