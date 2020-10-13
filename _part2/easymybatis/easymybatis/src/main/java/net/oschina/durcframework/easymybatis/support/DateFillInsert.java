/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.support;

import java.util.Date;

import net.oschina.durcframework.easymybatis.handler.FillHandler;
import net.oschina.durcframework.easymybatis.handler.FillType;

/**
 * insert时的字段填充<br>
 * 在做insert操作时,如果表里面有gmt_create字段,则自动填充时间
 * @author tanghc
 *
 */
public class DateFillInsert extends FillHandler<Date> {

	private String columnName = "gmt_create";

	public DateFillInsert() {
		super();
	}

	public DateFillInsert(String columnName) {
		super();
		this.columnName = columnName;
	}

	@Override
	public FillType getFillType() {
		return FillType.INSERT;
	}

	@Override
	public Date getFillValue(Date defaultValue) {
		if(defaultValue == null) {
			defaultValue = new Date();
		}
		return defaultValue;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

}
