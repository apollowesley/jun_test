package org.durcframework.open.test;

import org.durcframework.open.TestBase;

public class OpenEntityTest extends TestBase {

	// 实体类作为参数
	public static void main(String[] args) {
		SearchStudentEntity entity = new SearchStudentEntity();
		entity.setSchName("Jim");
		
		String s = getClient().postJSON("http://localhost/openApi/listApiStudent.do", entity);
		
		System.out.println(s);
	}

}
