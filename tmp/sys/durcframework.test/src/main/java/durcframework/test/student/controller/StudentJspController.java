package durcframework.test.student.controller;

import java.util.HashMap;
import java.util.Map;

import org.durcframework.controller.SearchController;
import org.durcframework.entity.ResultHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 不返回json格式
// 返回List结果集到jsp页面
@Controller
public class StudentJspController extends SearchController<Student, StudentService> {

	// http://localhost/durcframeworkTest/jspList.do
	@RequestMapping("jspList.do")
	public ModelAndView jspList(SearchStudentEntity searchStudentEntity){
		
		ResultHolder resultHolder = this.queryToResult(searchStudentEntity);
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 把查询参数带到前台页面
		map.put("searchData", searchStudentEntity);
		// 把查询的结果传到页面,通过resultHolder.xx来访问,如resultHolder.list
		map.put("resultHolder", resultHolder);
		
		// new ModelAndView("student", resultHolder); // 如果只需要查询结果信息,这样返回也可以
		return new ModelAndView("student",map); // WBE-INF/jsp/student.jsp
	}
	
}
