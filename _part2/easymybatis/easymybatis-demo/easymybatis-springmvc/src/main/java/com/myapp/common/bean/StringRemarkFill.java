package com.myapp.common.bean;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.handler.FillHandler;
import net.oschina.durcframework.easymybatis.handler.FillType;

public class StringRemarkFill extends FillHandler<String> {

	@Override
	public String getColumnName() {
		return "remark";
	}

	@Override
	public Class<?>[] getTargetEntityClasses() {
		return new Class<?>[] { TUser.class };
	}

	@Override
	public FillType getFillType() {
		return FillType.INSERT;
	}

	@Override
	protected Object getFillValue(String defaultValue) {
		return "备注默认内容";
	}

}
