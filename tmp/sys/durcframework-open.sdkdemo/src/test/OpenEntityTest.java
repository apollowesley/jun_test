package test;

import common.SearchStudentEntity;
import common.TestBase;

public class OpenEntityTest extends TestBase {

	// 实体类作为参数
	public static void main(String[] args) {
		SearchStudentEntity entity = new SearchStudentEntity();
		entity.setSchName("Jim");
		
		String s = getClient().post("http://localhost/openTest/listApiStudent.do", entity);
		
		System.out.println(s);
	}

}
