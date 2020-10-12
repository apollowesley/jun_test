package org.durcframework.core.expression.projection;

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
