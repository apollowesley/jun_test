package org.beetl.json.performance;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.JsonTool;
import org.beetl.json.util.NoLockStringWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestSimple {
	static JsonTool tool = new JsonTool();
	public static void main(String[] args) {
		// 预热
		System.out.println("test-simple");
		testSimple(10000, false);
		testSimple(10000000, true);
		System.out.println("test-list");
		testList(10000, false);
		testList(1000000, true);

	}

	public static void testList(int max, boolean log) {

		List List = getJsonData();

		TimeLog.key1Start();
		for (int i = 0; i < max; i++) {
			String json = tool.serialize(List);
		}
		TimeLog.key1End();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(false);
		TimeLog.key2Start();
		for (int i = 0; i < max; i++) {
			String json = JSON.toJSONString(List, mapping);
		}
		TimeLog.key2End();

		ObjectMapper objectMapper = new ObjectMapper();
		TimeLog.key3Start();
		for (int i = 0; i < max; i++) {
			NoLockStringWriter sw = new NoLockStringWriter();
			try {
				objectMapper.writeValue(sw, List);
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
			String json =gson.toJson(List);
		}
		TimeLog.key4End();

		if (log) {
			TimeLog.display("beetl-json", "fastjson", "jackson","gson");
		} else {
			TimeLog.reset();
		}

	}

	private static List<Object> getJsonData() {
		List list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			list.add(new SimpleObject());
		}
		return list;
	}

	public static void testSimple(int max, boolean log) {
		SimpleObject so = new SimpleObject();

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
			String json =gson.toJson(so);
		}
		TimeLog.key4End();

		if (log) {
			TimeLog.display("beetl-json", "fastjson", "jackson","gson");

		} else {
			TimeLog.reset();
		}

	}

}
