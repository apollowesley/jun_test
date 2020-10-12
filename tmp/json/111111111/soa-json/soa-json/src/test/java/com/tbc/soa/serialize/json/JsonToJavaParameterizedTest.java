package com.tbc.soa.serialize.json;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tbc.soa.json.JSONDeserializer;
import com.tbc.soa.json.JSONSerializer;

public class JsonToJavaParameterizedTest {
	String simpleJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}}";
	String complexJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"},\"users\":[{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}]}";
	String complexJsonWithoutClassHint = "{\"birthDay\":1282555532182,\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"name\":\"myName\"},\"users\":[{\"name\":\"myName\"}]}";

	String objectWithClassProperty = "{\"class\":\"com.tbc.soa.serialize.json.ObjectWithClassProperty\",\"type\":\"java.lang.Integer\",\"typeArray\":[\"java.lang.String\",\"java.lang.Integer\"]}";
	String mapProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"Key\":\"Value\"}}";
	String mapStringToUserProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"mapStringToUser\":{\"Key\":{\"class\":\"com.tbc.soa.serialize.json.User\"}}}";
	String enumProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"myEnum\":\"A\"}";
	String pageJsonClass = "{\"class\":\"com.tbc.soa.serialize.json.Page\",\"rows\":[{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"},\"users\":[{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}]}],\"stringList\":[\"String1\",\"String2\"]}";
	String pageJsonWithoutClass = "{\"rows\":[{\"birthDay\":1282555532182,\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"name\":\"myName\"},\"users\":[{\"name\":\"myName\"}]}],\"stringList\":[\"String1\",\"String2\"]}";
	//String pageJsonWithoutClass = "{\"rows\":[{\"birthDay\":1282555532182,\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"name\":\"myName\"},\"users\":[{\"name\":\"myName\"}]}]}";
	@Test
	public void test() {

	}

	@Test
	public void testJsonToJavaParameterized() throws SecurityException, NoSuchMethodException {
		
		Method method = getClass().getMethod("myFun", new Class[]{});
		Page<Person> page = myFun();
		
		String pageJson = new JSONSerializer().deepSerialize(page);
		//System.out.println(pageJsonWithClass);
		Type type = method.getGenericReturnType();
		Page<Person> p = (Page<Person>) new JSONDeserializer().deserialize(pageJsonWithoutClass, type);

		
		//System.out.println(p.getRows().get(0).getBirthDay();
		
		JSONSerializer serializer = new JSONSerializer();
		String deepSerialize = serializer.deepSerialize(p);
		System.out.println(deepSerialize);
		assertEquals(pageJsonClass, deepSerialize);
		
		
		Page p2 = (Page) new JSONDeserializer().deserialize(deepSerialize, type);
		assertEquals(deepSerialize,serializer.deepSerialize(p2));
		
	}

	public Page<Person> myFun() {
		Page<Person> page = new Page<Person>();
		
		List<String> stringList = new ArrayList<String>();
		stringList.add("String1");
		stringList.add("String2");
		page.setStringList(stringList);
		
		List<Person> list = new ArrayList<Person>();
		Person person = (Person)new JSONDeserializer().deserialize(complexJson);

		list.add(person);
		page.setRows(list);

		return page;
	}

}
