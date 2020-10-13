package durcframework.test.student.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.subexpression.LikeDoubleExpression;
import org.durcframework.core.expression.subexpression.LikeLeftExpression;
import org.durcframework.core.expression.subexpression.LikeRightExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.SearchStudentEntity2;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;


// like查询
@Controller
public class StudentLikeController extends CrudController<Student, StudentService> {

	
	// http://localhost/durcframeworkTest/likeRightExpression.do
	// 查询手机号开头为133的学生
	@RequestMapping("/likeRightExpression.do")
	public @ResponseBody GridResult likeRightExpression() {
		ExpressionQuery query = new ExpressionQuery();
		query.add(new LikeRightExpression("mobile", "133"));
		return this.query(query);
	}
	
	// http://localhost/durcframeworkTest/likeLeftExpression.do
	// 查询手机号尾号567的学生
	@RequestMapping("/likeLeftExpression.do")
	public @ResponseBody GridResult likeLeftExpression() {
		ExpressionQuery query = new ExpressionQuery();
		query.add(new LikeLeftExpression("mobile", "567"));
		return this.query(query);
	}
	
	// http://localhost/durcframeworkTest/likeDoubleExpression.do
	// 查询地区为开封的学生
	@RequestMapping("/likeDoubleExpression.do")
	public @ResponseBody GridResult likeDoubleExpression() {
		ExpressionQuery query = new ExpressionQuery();
		query.add(new LikeDoubleExpression("address", "开封"));
		return this.query(query);
	}
	
	// http://localhost/durcframeworkTest/likeSearch.do?schMobile=133
	// http://localhost/durcframeworkTest/likeSearch.do?schAddress=开封
	// http://localhost/durcframeworkTest/likeSearch.do?name=德华
	@RequestMapping("/likeSearch.do")
	public @ResponseBody GridResult likeSearch(SearchStudentEntity2 studentEntity) {
		return this.query(studentEntity);
	}
	
	
}
