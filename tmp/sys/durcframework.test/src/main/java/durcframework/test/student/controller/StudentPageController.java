package durcframework.test.student.controller;

import org.durcframework.controller.CrudController;
import org.durcframework.expression.ExpressionQuery;
import org.durcframework.expression.subexpression.ValueExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 分页相关,StudentCrudController中的例子已经封装了分页参数
@Controller
public class StudentPageController extends CrudController<Student, StudentService> {

	@RequestMapping("/listPage.do")
	public ModelAndView listPage() {
		ExpressionQuery query = new ExpressionQuery();
		// 第一页
		query.setPageIndex(1);
		// 每页显示10条
		query.setPageSize(10);
		
		// 筛选性别
		query.add(new ValueExpression("gender",1));
		// 添加排序
		query.setSortname("regist_date");
		query.setSortorder("desc");
		
		return this.query(query);
	}
	
	
}
