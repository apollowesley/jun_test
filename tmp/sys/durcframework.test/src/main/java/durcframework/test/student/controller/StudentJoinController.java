package durcframework.test.student.controller;

import org.durcframework.controller.SearchController;
import org.durcframework.expression.ExpressionQuery;
import org.durcframework.expression.subexpression.InnerJoinExpression;
import org.durcframework.expression.subexpression.ValueExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 连接查询
@Controller
public class StudentJoinController extends SearchController<Student, StudentService> {

	/**
	 * 关联DEPARTMENT表,并且查询外语系的学生
	 * 内连接查询
	 * @return
	 */
	// http://localhost/durcframeworkTest/innerJoin.do
	@RequestMapping("/innerJoin.do")
	public ModelAndView innerJoin(){
		ExpressionQuery query = new ExpressionQuery();
		/*
		 * 关联DEPARTMENT表
		 * department:第二张表的名字
		 * t2:department表的别名
		 * DEPARTMENT:主表的字段
		 * ID:第二张表的字段
		 * 
		 * 这样就会拼接成:inner join department t2 on t.DEPARTMENT = t2.ID
		 */
		query.addJoinExpression(new InnerJoinExpression("department", "t2", "DEPARTMENT", "ID"));
		// 查询外语系的学生
		query.add(new ValueExpression("t2.department_name", "外语系"));
		// 返回查询结果
		return this.queryAll(query);
	}
	
	
}
