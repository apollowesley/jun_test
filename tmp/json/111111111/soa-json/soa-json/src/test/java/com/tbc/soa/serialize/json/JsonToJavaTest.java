package com.tbc.soa.serialize.json;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.tbc.soa.json.JSONDeserializer;
import com.tbc.soa.json.JSONSerializer;

public class JsonToJavaTest {
	String simpleJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}}";
	String complexJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"},\"users\":[{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}]}";

	String objectWithClassProperty = "{\"class\":\"com.tbc.soa.serialize.json.ObjectWithClassProperty\",\"type\":\"java.lang.Integer\",\"typeArray\":[\"java.lang.String\",\"java.lang.Integer\"]}";
	String mapProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"Key\":\"Value\"}}";
	String mapWithClass = "{\"myKey\":\"myValue\",\"class\":\"com.tbc.soa.serialize.json.Person\"}";
	String mapStringToUserProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"mapStringToUser\":{\"Key\":{\"class\":\"com.tbc.soa.serialize.json.User\"}}}";
	String enumProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"myEnum\":\"A\"}";
	@Test
	public void testJsonToJava() {

		Person p = (Person) new JSONDeserializer().deserialize(complexJson);
		JSONSerializer serializer = new JSONSerializer();
		String deepSerialize = serializer.deepSerialize(p);
		System.out.println(deepSerialize);
		assertEquals(complexJson, deepSerialize);
	}
	
	@Test
	public void testJsonToJava_MapOnly() throws SecurityException, NoSuchMethodException {
		
		String json = "{\"KeyString\":\"StringValue\",\"KeyInteger\":1}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("KeyString", "StringValue");
		map.put("KeyInteger", 1);
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(map);
		assertEquals(json, serialize);
		
		
		JSONDeserializer deserialier = new JSONDeserializer();
		
		Object deserialize = deserialier.deserialize(json);
		
		System.out.println(deserialize);
		
		Map<String, Object> deserializeMap = (Map<String, Object>)deserialize;
		
		assertEquals("StringValue", deserializeMap.get("KeyString"));
		assertEquals(1, deserializeMap.get("KeyInteger"));
		
		Map<String, Object> deserialize2 = (Map<String, Object>) deserialier.deserialize(json, this.getClass().getMethod("funReturnMap").getGenericReturnType());
		
		
	}

	
	public Map<String, Object> funReturnMap() {
		
		return null;
		
	}

	@Test
	public void testPerformance() {
		int n = 10000;
		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		JSONSerializer serializer = new JSONSerializer();
		long start = System.currentTimeMillis();

		String deepSerialize = null;
		Person p = null;
		for (int i = 0; i < n; i++) {
			p = (Person) somJSONDeserializer.deserialize(complexJson);
			// System.out.println(deepSerialize);
			deepSerialize = serializer.deepSerialize(p);
		}
		assertEquals(complexJson, deepSerialize);
		long spent = System.currentTimeMillis() - start;

		System.out.println("spent " + spent + " ms, speed: " + n * 1000 / spent
				+ " tps");
	}

	@Test
	public void testJsonToJavaWithClassProperty() {
		JSONSerializer serializer = new JSONSerializer();

		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		Object deserialize = somJSONDeserializer.deserialize(objectWithClassProperty);
		System.out.println(deserialize);
		assertEquals(objectWithClassProperty, serializer.deepSerialize(deserialize));
		
	}

	@Test
	public void testJsonToJavaWithMapProperty() {
		JSONSerializer serializer = new JSONSerializer();

		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		Person deserialize = (Person) somJSONDeserializer.deserialize(mapProperty);
		System.out.println(deserialize);
		assertEquals(mapProperty, serializer.deepSerialize(deserialize));
		
		System.out.println(deserialize.getMap());
	}

	@Test
	public void testJsonToJavaWithMapStringToUserProperty() {
		JSONSerializer serializer = new JSONSerializer();

		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		Person deserialize = (Person) somJSONDeserializer.deserialize(mapStringToUserProperty);
		System.out.println(deserialize);
		assertEquals(mapStringToUserProperty, serializer.deepSerialize(deserialize));
		
		System.out.println(deserialize.getMapStringToUser());
	}

	@Test
	public void testJsonToJavaWithEnumProperty() {
		JSONSerializer serializer = new JSONSerializer();

		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		Person deserialize = (Person) somJSONDeserializer.deserialize(enumProperty);
		System.out.println(deserialize);
		assertEquals(enumProperty, serializer.deepSerialize(deserialize));
		
		System.out.println(deserialize.getMyEnum());
	}


	@Test
	public void testParameterized() throws SecurityException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		Class<?> class1 = Class.forName("com.tbc.soa.serialize.json.Member");
		// Case 1
		Field field = class1.getField("userList");
		Class<?> type = field.getType();
		System.out.println(type);
		Type genericType = field.getGenericType();
		System.out.println(genericType);

		// Case 2
		Method method = class1.getMethod("getUserList", new Class[0]);
		Type genericReturnType = method.getGenericReturnType();
		System.out.println(genericReturnType);

		// Case 3
		Method method2 = class1.getMethod("getUserList",
				new Class[] { Integer.class });
		Type genericReturnType2 = method2.getGenericReturnType();
		System.out.println(genericReturnType2);
	}
	
	public void fun(int i) {
		System.out.println("i = " + i);
	}
	
	@Test
	public void testMethod() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method method = JsonToJavaTest.class.getMethod("fun", Integer.TYPE);
		
		System.out.println(method);
		
		fun(new Integer(1));
		
		Object o = new Integer(1);
		method.invoke(this, o);
		
	}
	
	
	
	@Test
	public void testJsonToJavaMapWithClass() {
		JSONSerializer serializer = new JSONSerializer();

		JSONDeserializer somJSONDeserializer = new JSONDeserializer();
		HashMap deserialize = (HashMap) somJSONDeserializer.deserialize(mapWithClass, Map.class);
		assertEquals(mapWithClass, serializer.deepSerialize(deserialize));
		
	}

}
