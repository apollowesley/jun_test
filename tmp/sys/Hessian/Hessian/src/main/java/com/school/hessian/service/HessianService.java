/*
 * @(#)HessianService.java 2014-12-18 下午1:42:20
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.school.hessian.service;

import com.school.hessian.bean.HessianModel;

/**
 * <p>File：HessianService.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-18 下午1:42:20</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public interface HessianService
{

	public String sayHello(String username);  
	
    public HessianModel getHessianModel(String username, String password); 
	
}
