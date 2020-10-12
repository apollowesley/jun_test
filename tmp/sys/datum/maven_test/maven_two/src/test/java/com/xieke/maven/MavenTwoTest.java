package com.xieke.maven;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class MavenTwoTest
{

	@Test
	public void testHello()
	{
		MavenTwo mt = new MavenTwo();
		String result = mt.hello("so easy!");
		
		assertEquals("MAVEN:so easy!", result);
	}
	
}
