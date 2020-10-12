/**
 * Project Name:easyJava
 * File Name:Test.java
 * Package Name:com.ulewo.easyjava
 * Date:2016年7月30日下午7:02:58
 * Copyright (c) 2016, ulewo.com All Rights Reserved.
 *
*/

package com.ulewo.easyjava;

import java.util.List;

import com.ulewo.easyjava.bean.DataTableInfo;
import com.ulewo.easyjava.builder.BuildBean;
import com.ulewo.easyjava.builder.BuildController;
import com.ulewo.easyjava.builder.BuildMapper;
import com.ulewo.easyjava.builder.BuildMapperXml;
import com.ulewo.easyjava.builder.BuildParam;
import com.ulewo.easyjava.builder.BuildService;
import com.ulewo.easyjava.builder.BuildServiceImpl;
import com.ulewo.easyjava.builder.ReadTable;
import com.ulewo.easyjava.framwork.BuildFramework;

/**
 * ClassName:Test <br/>
 * Date:     2016年7月30日 下午7:02:58 <br/>
 * @author   多多洛
 * Copyright (c) 2016, ulewo.com All Rights Reserved. 
 */
public class MainApp {

	public static void main(String[] args) {
		List<DataTableInfo> tableList = ReadTable.readTable();
		for (DataTableInfo table : tableList) {
			BuildFramework.initFramwork(table);
			//创建bean
			BuildBean.buildEntityBean(table);
			//创建参数bean
			BuildParam.buildEntityParam(table);
			//创建mapper
			BuildMapper.buildMapper(table);
			//创建XML
			BuildMapperXml.buildMapperXml(table);
			//创建service
			BuildService.buildService(table);
			//创建serviceimpl
			BuildServiceImpl.buildServiceImpl(table);
			//创建controller
			BuildController.buildController(table);
		}
	}

}
