package com.foo.common.base.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import com.foo.common.base.pojo.ClassFieldForMustache;
import com.foo.common.base.pojo.ClassFieldGetAndSetForMustache;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MustacheHelper {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MustacheHelper.class);

	public static void main2(String[] args) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();

		String myPackage = "com.feintek.tenmalife.model";
		String myTableName = "FOO_USER";
		String myClassName = "FooUserExample";
		String myFieldName = "userId";
		String myFieldNameFirstUpper = "UserId";
		String myFieldType = "Date";
		String myColumnName = "USER_ID";

		HashMap<String, Object> scopes = new HashMap<String, Object>();
		scopes.put("myPackage", myPackage);
		scopes.put("myTableName", myTableName);
		scopes.put("myClassName", myClassName);

		List<ClassFieldForMustache> fields = Lists.newArrayList();

		ClassFieldForMustache filed = new ClassFieldForMustache();
		filed.setMyColumnName(myColumnName);
		filed.setMyFieldName(myFieldName);
		filed.setMyFieldType(myFieldType);
		filed.setMyFieldTypeInitExp("new Date()");
		filed.setMyLengthExp("");
		filed.setMyNullable(false);
		fields.add(filed);

		filed = new ClassFieldForMustache();
		filed.setMyColumnName(myColumnName);
		filed.setMyFieldName(myFieldName);
		filed.setMyFieldType(myFieldType);
		filed.setMyFieldTypeInitExp("new Date()");
		filed.setMyLengthExp("");
		filed.setMyNullable(false);
		fields.add(filed);

		List<ClassFieldGetAndSetForMustache> fieldsGetAndSet = Lists
				.newArrayList();
		ClassFieldGetAndSetForMustache fieldGetAndSetObject = new ClassFieldGetAndSetForMustache();
		fieldGetAndSetObject.setMyFieldName(myFieldName);
		fieldGetAndSetObject.setMyFieldNameFirstUpper(myFieldNameFirstUpper);
		fieldGetAndSetObject.setMyFieldType(myFieldType);
		fieldsGetAndSet.add(fieldGetAndSetObject);

		fieldGetAndSetObject = new ClassFieldGetAndSetForMustache();
		fieldGetAndSetObject.setMyFieldName(myFieldName);
		fieldGetAndSetObject.setMyFieldNameFirstUpper(myFieldNameFirstUpper);
		fieldGetAndSetObject.setMyFieldType(myFieldType);
		fieldsGetAndSet.add(fieldGetAndSetObject);

		scopes.put("fields", fields);
		scopes.put("fieldsGetAndSet", fieldsGetAndSet);

		Mustache mustache = mf.compile("template-generic-mysql-model.mustache");

		Writer writer = new StringWriter();

		mustache.execute(writer, scopes);
		writer.flush();

		logger.info("result is:{}", writer.toString());
	}

	public static void main(String[] args) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();

		HashMap<String, Object> scopes = Maps.newHashMap();
		scopes.put("name", "NcHelp");
		scopes.put("hasDateField", true);

		Mustache mustache = mf.compile("template-generic-mysql-model.mustache");

		Writer writer = new StringWriter();

		mustache.execute(writer, scopes);
		writer.flush();

		logger.info("result is:{}", writer.toString());
	}
}
