package com.xieke.maven;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class MavenOneTest
{

	@Test
	public void testHello()
	{
		MavenOne mo = new MavenOne();
		String result = mo.hello("hello!");
		
		assertEquals("MAVEN:hello!", result);
	}
	
}
