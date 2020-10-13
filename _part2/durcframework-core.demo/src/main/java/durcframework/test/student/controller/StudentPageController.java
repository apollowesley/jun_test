package durcframework.test.student.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.subexpression.ValueExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 分页相关,StudentCrudController中的例子已经封装了分页参数
@Controller
public class StudentPageController extends CrudController<Student, StudentService> {

	// http://localhost/durcframeworkTest/listPage.do
	@RequestMapping("/listPage.do")
	public @ResponseBody GridResult listPage() {
		ExpressionQuery query = new ExpressionQuery();
		// 第一页
		query.setStart(0);
		// 每页显示10条
		query.setLimit(10);
		
		// 筛选性别
		query.add(new ValueExpression("gender",1));
		// 添加排序
		query.addSort("regist_date","desc");
		
		return this.query(query);
	}
	
	
}
