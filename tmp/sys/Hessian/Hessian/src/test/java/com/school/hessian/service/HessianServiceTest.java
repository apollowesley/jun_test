/*
 * @(#)HessianServiceTest.java 2014-12-18 下午2:08:56
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.school.hessian.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>File：HessianServiceTest.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-18 下午2:08:56</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class HessianServiceTest
{

	private ConfigurableApplicationContext context;
	
//	@Before
//	public void init(){
//		context = new ClassPathXmlApplicationContext(
//				"classpath:Hessian-client.xml");
//	}
	
	/**
	 * Test method for {@link com.school.hessian.service.HessianService#sayHello(java.lang.String)}.
	 */
	@Test
	public void testSayHello()
	{
//		HessianService hs = context.getBean(HessianService.class);
//		hs.sayHello("");
		System.out.println(22);
	}

	/**
	 * Test method for {@link com.school.hessian.service.HessianService#getHessianModel(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetHessianModel()
	{
		fail("Not yet implemented");
	}

	public static void main(String[] args)
	{
		System.out.println("ss");
	}
	
}
