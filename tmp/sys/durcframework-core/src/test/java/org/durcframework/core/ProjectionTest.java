package org.durcframework.core;

import java.util.List;
import java.util.Map;

import org.durcframework.core.expression.projection.Projection;
import org.durcframework.core.expression.projection.ProjectionList;
import org.durcframework.core.expression.projection.ProjectionQuery;
import org.durcframework.core.expression.projection.Projections;
import org.durcframework.core.expression.subexpression.ValueExpression;
import org.junit.Test;
import org.springframework.util.Assert;

import junit.framework.TestCase;

public class ProjectionTest extends TestCase {

	@Test
	public void testProjection() {
		ProjectionQuery query = new ProjectionQuery();
		query.addProjection("id");
		query.addProjection(Projections.sum("gender","myGender"));
		query.addGroupBy("id");
		
		ProjectionList projections = query.getProjectionList();
		ProjectionList groupBys = query.getGroupByList();
		
		String sql = "SELECT ";
		
		int i=0;
		for (Projection projection : projections.getProjections()) {
			if(i>0) {
				sql +=",";
			}
			sql+=projection.getSql();
			i++;
		}
		
		sql += " FROM student ";
		sql += " GROUP BY ";
		
		for (Projection projection : groupBys.getProjections()) {
			sql+=projection.getSql();
		}
		
		System.out.println(sql);
	}
	
	/*
	 * 查询部门中年纪最小的
	 * SELECT 
		  DEPARTMENT AS dept,
		  MAX(BIRTHDAY) AS birth 
		FROM
		  student t 
		WHERE id > 2
		GROUP BY (dept) 
		HAVING 1 = 1 
		  AND dept = 16
	 */
	@Test
	public void testProjection2() {

		ProjectionQuery query = new ProjectionQuery();
		// 添加列
		query.addProjection(Projections.column("DEPARTMENT", "dept"));
		query.addProjection(Projections.max("BIRTHDAY", "birth"));
		// 添加where
		query.add(new ValueExpression("id", ">",2));
		// 添加group by
		query.addGroupBy("dept");
		// 添加having
		query.addHaving(new ValueExpression("dept", 16));

//		List<Map<String, Object>> list = dao.findProjection(query);
//
//		Assert.notEmpty(list);
//
//		System.out.println(list.size());
	}
}
