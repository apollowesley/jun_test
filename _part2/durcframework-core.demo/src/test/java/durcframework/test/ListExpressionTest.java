package durcframework.test;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.ValueConvert;
import org.durcframework.core.expression.subexpression.ListExpression;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

public class ListExpressionTest extends TestBase {

	@Autowired
	private StudentService studentService;
	
	
	@Test
	public void testListExre(){
		Student stu1 = new Student();
		stu1.setId(1);
		Student stu2 = new Student();
		stu2.setId(2);
		Student stu3 = new Student();
		stu3.setId(3);
		
		List<Student> list = Arrays.asList(stu1,stu2,stu3);
		
		ExpressionQuery query = new ExpressionQuery();
		query.add(new ListExpression("id", list, new ValueConvert<Student>() {
			@Override
			public Object convert(Student obj) {
				return obj.getId();
			}
		}));
		
		List<Student> result = studentService.find(query);
		
		Assert.assertNotNull(result);
		
		System.out.println(result);
		
	}
	
}
