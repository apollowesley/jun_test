/*
 * @(#)HessianServiceImpl.java 2014-12-18 下午1:43:21
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.school.hessian.service.impl;

import com.school.hessian.bean.HessianModel;
import com.school.hessian.service.HessianService;

/**
 * <p>File：HessianServiceImpl.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-18 下午1:43:21</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class HessianServiceImpl implements HessianService
{

	/* (non-Javadoc)
	 * @see com.school.hessian.service.HessianService#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String username)
	{
		return "sayHello..." + username;
	}

	/* (non-Javadoc)
	 * @see com.school.hessian.service.HessianService#getHessianModel(java.lang.String, java.lang.String)
	 */
	@Override
	public HessianModel getHessianModel(String username, String password)
	{
		return new HessianModel(username, password);
	}

}
