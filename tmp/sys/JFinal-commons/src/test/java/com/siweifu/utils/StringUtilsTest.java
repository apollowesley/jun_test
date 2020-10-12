package com.siweifu.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testFormat1() {
		String ss = StringUtils.format("my name is {0}, and i like {1}!", "L.cm", "java");

		String s  = "my name is L.cm, and i like java!";

		Assert.assertEquals("ok?", ss, s);
	}

	@Test
	public void testFormat2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "L.cm");
		map.put("like", "java");

		String ss = StringUtils.format("my name is ${name}, and i like ${like}!", map);
		String s  = "my name is L.cm, and i like java!";

		Assert.assertEquals("ok?", ss, s);
	}

	@Test
	public void testFormat3() {
		String ss = StringUtils.format("my name is {0}, I'm {0}!", "L.cm");
		
		String s  = "my name is L.cm, Im {0}!";
		Assert.assertEquals("ok?", ss, s);
	}

	@Test
	public void testFormat4() {
		long currentTimeMillis = System.currentTimeMillis();
		
		String ss = StringUtils.format("currentTimeMillis={0}", currentTimeMillis);

		String s  = "currentTimeMillis=" + currentTimeMillis;
		Assert.assertNotSame("ok?", ss, s);
	}

	@Test
	public void testFormat5() {
		long currentTimeMillis = System.currentTimeMillis();
		
		String ss = StringUtils.format("currentTimeMillis={0,number,#}", currentTimeMillis);

		String s  = "currentTimeMillis=" + currentTimeMillis;
		Assert.assertEquals("ok?", ss, s);
	}

}
