package org.beetl.json.performance;

import org.beetl.json.JsonTool;
import org.beetl.json.util.NoLockStringWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestOne2Many {
	static JsonTool tool = new JsonTool();
	public static void main(String[] args) {

		System.out.println("test one-many");
		testSimple(100000000, false);
		testSimple(1000000, true);

//		System.out.println("test many attributes");
//		testManyAttribute(10000, false);
//		testManyAttribute(1000000, true);

	}

	public static void testManyAttribute(int max, boolean log) {

		ManyAttrObject so = new ManyAttrObject();

		TimeLog.key1Start();
		for (int i = 0; i < max; i++) {
			String json = tool.serialize(so);
		}
		TimeLog.key1End();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(false);
		TimeLog.key2Start();
		for (int i = 0; i < max; i++) {
			String json = JSON.toJSONString(so, mapping);
		}
		TimeLog.key2End();

		ObjectMapper objectMapper = new ObjectMapper();
		TimeLog.key3Start();
		for (int i = 0; i < max; i++) {
			NoLockStringWriter sw = new NoLockStringWriter();
			try {
				objectMapper.writeValue(sw, so);
				String json = sw.toString();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		TimeLog.key3End();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		TimeLog.key4Start();
		for (int i = 0; i < max; i++) {
			String json = gson.toJson(so);
		}
		TimeLog.key4End();

		if (log) {
			TimeLog.display("beetl-json", "fastjson", "jackson", "gson");

		} else {
			TimeLog.reset();
		}

	}

	public static void testSimple(int max, boolean log) {
		One2ManyObject so = new One2ManyObject();

		TimeLog.key1Start();
		for (int i = 0; i < max; i++) {
			String json = tool.serialize(so);
		}
		TimeLog.key1End();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(false);
		TimeLog.key2Start();
		for (int i = 0; i < max; i++) {
			String json = JSON.toJSONString(so, mapping);
		}
		TimeLog.key2End();

		ObjectMapper objectMapper = new ObjectMapper();
		TimeLog.key3Start();
		for (int i = 0; i < max; i++) {
			NoLockStringWriter sw = new NoLockStringWriter();
			try {
				objectMapper.writeValue(sw, so);
				String json = sw.toString();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		TimeLog.key3End();

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		TimeLog.key4Start();
		for (int i = 0; i < max; i++) {
			String json = gson.toJson(so);
		}
		TimeLog.key4End();

		if (log) {
			TimeLog.display("beetl-json", "fastjson", "jackson", "gson");

		} else {
			TimeLog.reset();
		}

	}

}
