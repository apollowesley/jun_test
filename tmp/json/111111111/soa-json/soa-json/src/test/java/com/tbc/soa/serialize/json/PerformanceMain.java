package com.tbc.soa.serialize.json;

import com.tbc.soa.json.JSONDeserializer;
import com.tbc.soa.json.JSONSerializer;
import com.tbc.soa.json.ObjectBinder;
import com.tbc.soa.json.TransformerUtil;


public class PerformanceMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PerformanceMain().testPerformance();

	}

	@SuppressWarnings("unchecked")
	public void testPerformance() {
		String complexJson = "{\"birthDay\":1282555532182,\"class\":\"flexjson.Person\",\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"class\":\"flexjson.User\",\"name\":\"myName\"},\"users\":[{\"class\":\"flexjson.User\",\"name\":\"myName\"}]}";

		int n = 10000;
		JSONSerializer serializer = new JSONSerializer();
		JSONDeserializer jsonDeserializer = new JSONDeserializer();
		Class c = ObjectBinder.class;
		Class c2 = TransformerUtil.class;
		Person p = null;
		// warm up
		p = (Person) jsonDeserializer.deserialize(complexJson);
		serializer.deepSerialize(p);

		long start = System.currentTimeMillis();

		for (int i = 0; i < n; i++) {
			p = (Person) jsonDeserializer.deserialize(complexJson);
			serializer.deepSerialize(p);
		}
		// assertEquals(complexJson, deepSerialize);
		long spent = System.currentTimeMillis() - start;

		System.out.println("spent " + spent + " ms, speed: " + n * 1000 / spent
				+ " tps");
	}
}
