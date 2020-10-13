package durcframework.test.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.SearchController;
import org.durcframework.core.expression.Expression;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.subexpression.LikeRightExpression;
import org.durcframework.core.expression.subexpression.ValueExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
public class StudentListExpresstionController extends
		SearchController<Student, StudentService> {

	// 查询名为JIM并且手机号133开头的学生
	// http://localhost/durcframeworkTest/listExpresstions.do
	@RequestMapping("/listExpresstions.do")
	public @ResponseBody GridResult listExpresstions(SearchStudentEntity entity) {
		// 追加条件
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new ValueExpression("name", "JIM"));
		expressions.add(new LikeRightExpression("mobile", "133"));
		
		return this.query(entity,expressions);
	}
	
	// 查询名为JIM并且手机号133开头的学生,方式2
	// http://localhost/durcframeworkTest/listExpresstions.do
	@RequestMapping("/listExpresstions2.do")
	public @ResponseBody GridResult listExpresstions2() {
		ExpressionQuery query = new ExpressionQuery();
		// 追加条件
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new ValueExpression("name", "JIM"));
		expressions.add(new LikeRightExpression("mobile", "133"));
		
		query.addAll(expressions);
		
		return this.query(query);
	}
}
