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

package net.oschina.durcframework.easymybatis.handler;

public class EnumTypeHandler<E extends Enum<?> & BaseEnum<?>> extends TypeHandlerAdapter<E> {
	private Class<E> clazz;

	public EnumTypeHandler(Class<E> enumType) {
		if (enumType == null)
			throw new IllegalArgumentException("Type argument cannot be null");

		this.clazz = enumType;
	}

	@Override
	protected Object getFillValue(E defaultValue) {
		return defaultValue.getCode();
	}

	@Override
	protected E convertValue(Object columnValue) {
		if (columnValue == null) {
			return null;
		}
		E[] enumConstants = clazz.getEnumConstants();
		for (E e : enumConstants) {
			if (e.getCode().toString().equals(columnValue.toString()))
				return e;
		}
		return null;
	}

}