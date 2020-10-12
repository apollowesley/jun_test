package com.tbc.soa.serialize.json;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.tbc.soa.json.JSONDeserializer;
import com.tbc.soa.json.JSONSerializer;
import com.tbc.soa.json.TransformerUtil;
import com.tbc.soa.json.transformer.Transformer;
import com.tbc.soa.json.transformer.TransformerWrapper;
import com.tbc.soa.json.transformer.TypeTransformerMap;

public class JavaToJsonTest {

	@Test
	public void testJavaToJson() {
		Person p = new Person();
		Person p2 = new Person();
		p.p = p2;
		User[] users = new User[1000];
		for (int i = 0; i < users.length; i++) {
			users[i] = new User();
		}

		p.users = users;
		// p2.p = p;
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.serialize(p);
		System.out.println(serialize);

		serialize = serializer.deepSerialize(p);
		System.out.println(serialize);
	}

	@Test
	public void testJavaToJson_Class() {
		Class clazz = Integer.class;
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(clazz);
		System.out.println(serialize);

		ObjectWithClassProperty objectWithClassProperty = new ObjectWithClassProperty();
		objectWithClassProperty.setType(clazz);
		objectWithClassProperty.setTypeArray(new Class[] { String.class });

		serialize = serializer.deepSerialize(objectWithClassProperty);
		System.out.println(serialize);

	}

	@Test
	public void testJavaToJson_ByteArray() {
		byte[] byteArray = new byte[] { 96, 97 };
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.serialize(byteArray);
		Assert.assertEquals("\"YGE=\"", serialize);
	}

	@Test
	public void testJavaToJson_Map() {

		Person persion = new Person();
		Map map = new HashMap<String, String>();
		map.put("Key", "Value");
		persion.setMap(map);
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(persion);
		System.out.println(serialize);

	}

	@Test
	public void testJavaToJson_MapOnly() {
		
		String json = "{\"KeyString\":\"StringValue\",\"KeyInteger\":1}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("KeyString", "StringValue");
		map.put("KeyInteger", 1);
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(map);
		assertEquals(json, serialize);
		
	}
	
	@Test
	public void testJavaToJson_MapStringToUser() {

		Person person = new Person();
		Map map = new HashMap<String, User>();
		map.put("Key", new User());
		person.setMapStringToUser(map);
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(person);
		System.out.println(serialize);

	}
	
	@Test
	public void testJavaToJson_Enum() {

		Person person = new Person();
		person.setMyEnum(MyEnum.A);
		
		JSONSerializer serializer = new JSONSerializer();
		String serialize = serializer.deepSerialize(person);
		System.out.println(serialize);

	}
	
	
	@Test
	public void testJavaToJson_Exception() {
//
//		Exception e = new Exception("Message") ;
//	
//		JSONSerializer serializer = new JSONSerializer();
//		String serialize = serializer.deepSerialize(e);
//		System.out.println(serialize);
		
		TypeTransformerMap typeTransformerMap = TransformerUtil.getDefaultTypeTransformers();

		TransformerWrapper transformer = (TransformerWrapper) typeTransformerMap.getTransformer(new SQLException());
		System.out.println(transformer.getTransformer());
	
	}
	
	@Test
	public void testJavaToJson_Null() {

		
		JSONSerializer serializer = new JSONSerializer();
		
		String deepSerialize = serializer.deepSerialize(null);
		System.out.println(deepSerialize);
		assertNotNull(deepSerialize);
		assertEquals("null", deepSerialize);
	
	}
	
	@Test
	public void testJavaToJson_Boolean() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		JSONSerializer serializer = new JSONSerializer();
		JSONDeserializer deserializer = new JSONDeserializer();
		
		ObjectWithBoolean target = new ObjectWithBoolean();
		
		target.setSmallBoolean(true);
		Field field = target.getClass().getDeclaredField("smallBoolean");
		field.setAccessible(true);
		Object object = field.get(target);
		field.set(target, false);
		object = field.get(target);
		System.out.println("boolean value: " + object);
		
		String deepSerialize = serializer.deepSerialize(target);
		System.out.println(deepSerialize);
		Object deserialize = deserializer.deserialize(deepSerialize);
		
		String deepSerialize2 = serializer.deepSerialize(deserialize);
		
		assertEquals(deepSerialize, deepSerialize2);
		
	}


}
