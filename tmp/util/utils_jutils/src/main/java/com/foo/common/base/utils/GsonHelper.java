package com.foo.common.base.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.foo.common.base.gson.GsonAnnotationExclusionStrategy;
import com.foo.common.base.gson.GsonPojo;
import com.foo.common.base.pojo.Area;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonHelper {

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(GsonHelper.class);

	public static void main(String[] args) throws IOException {
		// mainOnExclusionStrategy();
		// fromJsonObjectOfGenericType();
		testFieldNamingStrategy();
	}

	public static void fromJsonList(String source) {
		Gson gson = new GsonBuilder().create();
		List<GsonHelper> result = gson.fromJson(source,
				new TypeToken<List<GsonHelper>>() {
				}.getType());
		logger.info("result is:{}", result);
	}

	/**
	 * 基于确定类型的反解析
	 * 
	 * @param source
	 */
	public static void fromJsonObject(String source) {
		Gson gson = new GsonBuilder().create();
		GsonHelper result = gson.fromJson(source, GsonHelper.class);
		logger.info("result is:{}", result);
	}

	/**
	 * 基于泛型的反解析
	 * 
	 * @throws IOException
	 */
	public static void fromJsonObjectOfGenericType() throws IOException {
		Gson gson = new GsonBuilder().create();
		String source = IOUtils.toString(FooUtils
				.getClassPathResourceInputStream("cityData.json"));
		List<Area> areas = gson.fromJson(source, new TypeToken<List<Area>>() {
		}.getType());
		logger.info("result is:{}", areas);
	}

	/**
	 * 基于注解的gson排除规则
	 */
	public static void testFieldNamingStrategy() {
		Gson gson = new GsonBuilder().setFieldNamingStrategy(
				new FieldNamingStrategy() {
					@Override
					public String translateName(Field f) {
						if (f.getName().equals("myStr1")) {
							return "myStr1_after_change";
						}
						return f.getName();
					}
				}).create();
		GsonPojo gsonPojo = new GsonPojo();
		gsonPojo.setMyStr1(RandomStringUtils.randomAlphabetic(5));
		gsonPojo.setMyTransientInt1(999);
		gsonPojo.setMyDate1(new Date());
		String result = gson.toJson(gsonPojo);
		logger.info("result is:{}", result);
	}

	/**
	 * 基于注解的gson排除规则
	 */
	public static void mainOnExclusionStrategy() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.setExclusionStrategies(new GsonAnnotationExclusionStrategy())
				.create();
		GsonPojo gsonPojo = new GsonPojo();
		gsonPojo.setMyStr1(RandomStringUtils.randomAlphabetic(5));
		gsonPojo.setMyTransientInt1(999);
		gsonPojo.setMyDate1(new Date());
		String result = gson.toJson(gsonPojo);
		logger.info("result is:{}", result);
	}

	public static void main2(String[] args) {

		GsonPojo gsonPojo = new GsonPojo();
		gsonPojo.setMyStr1("dadaddd");
		gsonPojo.setMyTransientInt1(999);

		Gson gson = new GsonBuilder().create();
		logger.info("result is:{}", gson.toJson(gsonPojo));

		gson = new GsonBuilder().serializeNulls().setPrettyPrinting()
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes arg0) {
						if (arg0.getName().equals("hehe")) {
							logger.info("trigger");
							return true;
						}
						return false;
					}

					@Override
					public boolean shouldSkipClass(Class<?> arg0) {
						return false;
					}
				}).setVersion(0).create();
		logger.info("result is:{}", gson.toJson(gsonPojo));

		gson = new GsonBuilder().serializeNulls().setVersion(1).create();
		logger.info("result is:{}", gson.toJson(gsonPojo));

	}
}
