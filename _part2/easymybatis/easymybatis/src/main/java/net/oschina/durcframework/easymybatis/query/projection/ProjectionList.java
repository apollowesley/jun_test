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

package net.oschina.durcframework.easymybatis.query.projection;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合列
 * @author tanghc
 */
public class ProjectionList {
	private List<Projection> projections = new ArrayList<Projection>();

	public static ProjectionList projectionList() {
		return new ProjectionList();
	}

	public ProjectionList add(Projection projection) {
		projections.add(projection);
		return this;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
	}

}
