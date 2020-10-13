package durcframework.test.student.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.durcframework.core.WebContext;
import org.durcframework.core.controller.ExportController;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.subexpression.ValueExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 导出功能
// 大数据量导出可能会慢
@Controller
public class StudentExportController extends ExportController<Student, StudentService> {

	@Override
	public String getTemplateFilePath() {
		HttpServletRequest request = WebContext.getInstance().getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		// D:/xx/durcframework.test/src/main/webapp/webapp/login/student/student_export.xls
		// 全路径
		return path + "login/student/student_export.xls";
	}
	
	// http://localhost/durcframeworkTest/exportStudent.do
	@RequestMapping("exportStudent.do")
	public void export(HttpServletResponse response){
		doExport(getList(), response);
	}
	
	// 导出性别为男的,带请求参数
	// http://localhost/durcframeworkTest/exportStudent2.do?&schGender=1
	@RequestMapping("exportStudent2.do")
	public void export2(SearchStudentEntity searchStudentEntity,HttpServletResponse response){
		this.exportBySearchEntity(searchStudentEntity, response);
	}
	
	// 导出性别为女的,服务器端写死
	// http://localhost/durcframeworkTest/exportStudent3.do
	@RequestMapping("exportStudent3.do")
	public void export3(HttpServletResponse response){
		ExpressionQuery query = new ExpressionQuery();
		
		query.setQueryAll(true); // 查询全部
		query.add(new ValueExpression("gender", 0));
		
		this.exportByQuery(query, response);
	}
	
	
	
	@Override
	protected Map<String, Object> getModelMap() {
		Map<String, Object> map = super.getModelMap();
		map.put("title", "学生信息");
		return map;
	}
	
	public List<?> getList(){
		List list = new ArrayList();
		Date date = new Date();
		Student s;
		for (int i = 0; i < 50; i++) {
			s = new Student();
			s.setAddress("address"+i);
			s.setBirthday(date);
			s.setDepartment(16);
			s.setGender((byte)1);
			s.setId(i);
			s.setMobile("mobile"+i);
			s.setName("name"+i);
			s.setNationality("汉族");
			s.setPoliticsStatus(1);
			s.setRegistDate(date);
			s.setStuNo("stuNo"+i);
			
			list.add(s);
		}
		return list;
	}

}
