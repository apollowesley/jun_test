/**
 * Copyright (c) 2013-2015 www.javahih.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hihsoft.baseclass.web.util;

import org.apache.commons.beanutils.Converter;

public class StringConverter implements Converter {

	public StringConverter() {
	}

	@SuppressWarnings("rawtypes")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString())) {
			return (String) null;
		} else {
			return value.toString();
		}
	}

}
