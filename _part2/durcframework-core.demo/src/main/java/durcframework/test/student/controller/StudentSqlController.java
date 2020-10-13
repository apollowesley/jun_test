package durcframework.test.student.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.SqlContent;
import org.durcframework.core.expression.subexpression.SqlExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 使用自定义SQL语句拼接查询条件
@Controller
public class StudentSqlController  extends CrudController<Student, StudentService> {

	// http://localhost/durcframeworkTest/likeSqlExpression.do
	// 查询性别为女或id为19,20的学生
	// SQL:SELECT * FROM student t WHERE (gender=0 or id in (19,20)) ORDER BY ID desc LIMIT 0,10
	@RequestMapping("/likeSqlExpression.do")
	public @ResponseBody GridResult likeSqlExpression() {
		ExpressionQuery query = new ExpressionQuery();
		query.add(new SqlExpression("gender=0 or id in (19,20)"));
		return this.query(query);
	}
}
