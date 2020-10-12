/*
 * Copyright 2008-2012 the original author or authors.
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
package org.gen.testmongo.model;

import java.io.Serializable;

public class QueryModel implements Serializable {

	
	private static final long serialVersionUID = 8280485938848398236L;

	private Integer pageNumber;
	private Integer pageSize;
	private String orderBy;
	private Direction direction;

	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Long getOffset() {
		if(pageNumber == null || pageSize == null) {
			return null;
		}
		return ((long)pageNumber) * pageSize;
	}
	public String getOrderByAndDirection() {
		if(StringUtils.isBlank(orderBy)) {
			return null;
		}
		if(StringUtils.containsAny(orderBy, ';', ',', '\'', '"')) {
			return null;
		}
		String orderByStr = orderBy; //TODO check
		String directionStr = direction == null ? "desc" : direction.toString().toLowerCase();
		return orderByStr + " " + directionStr;
	}
}
