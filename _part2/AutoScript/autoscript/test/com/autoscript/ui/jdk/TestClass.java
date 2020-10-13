/**
 * 
 */
package com.autoscript.ui.jdk;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * 作者:龙色波
 * 日期:2013-10-25
 */
public class TestClass {
	@Test
	public void testExpression(){
		String retCode="ESA01";
		String status="00";
		assertTrue(retCode.length()==5 && retCode.startsWith("E") &&!"00".equals(status));
	}
}
