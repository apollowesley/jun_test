package durcframework.test;

import java.util.Arrays;
import java.util.List;

import org.durcframework.core.SpringContext;
import org.durcframework.core.expression.QBC;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import durcframework.test.student.dao.StudentDao;
import durcframework.test.student.service.StudentService;

public class QBCTest extends TestBase {

	@Autowired
	private StudentDao dao;

	@Test
	public void testQbc() {
		// 查询姓名为Jim,并且id是20和25的学生
		// 查询结果以name字段升序

		// SELECT * FROM student t WHERE name = 'Jim' AND id IN ( 20,25 ) ORDER
		// BY
		// name ASC LIMIT 0,10
		QBC qbc = QBC.create(dao);
		List list = qbc.eq("name", "Jim").in("id", Arrays.asList(20, 25))
				.sort("name").list();

		Assert.notEmpty(list);

	}

	@Test
	public void testQbc2() {
		// 查询姓名为Jim,并且id是20和25的学生,并且部门ID为16
		// 查询结果以name字段升序

		// SELECT * FROM student t INNER JOIN department t2 ON
		// t.DEPARTMENT=t2.ID WHERE name = 'Jim' AND t2.ID = 16 AND t.id IN ( 20,25 )
		// ORDER BY name ASC
		QBC qbc = QBC.create(dao);
		List list = qbc.innerJoin("department", "t2", "DEPARTMENT", "ID")
				.eq("name", "Jim").in("t.id", Arrays.asList(20, 25))
				.eq("t2.ID", 16).sort("name").listAll();

		Assert.notEmpty(list);

	}

}
