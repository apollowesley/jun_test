package durcframework.test.student.controller;

import java.util.HashMap;
import java.util.Map;

import org.durcframework.core.DefaultMessageResult;
import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
@RequestMapping("jsp")
public class SitemeshJspController extends CrudController<Student, StudentService> {
	@RequestMapping("studentManager.do")
	public ModelAndView studentManager(SearchStudentEntity searchStudentEntity){
		
		GridResult resultHolder = this.query(searchStudentEntity);
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 把查询参数带到前台页面
		map.put("searchData", searchStudentEntity);
		// 把查询的结果传到页面,通过resultHolder.xx来访问,如resultHolder.list
		map.put("resultHolder", resultHolder);
		
		return new ModelAndView("studentManager",map); // WBE-INF/jsp/studentManager.jsp
	}
	
	@RequestMapping("editStudent.do")
	public ModelAndView editStudent(int id) {
		Student stu = this.getService().get(id);
		return new ModelAndView("editStudent","stu",stu);
	}
	
	@RequestMapping("saveStudent.do")
	public ModelAndView saveStudent(Student student) {
		DefaultMessageResult message = (DefaultMessageResult)this.update(student);
		if(message.getSuccess()){
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("backUrl", "studentManager.do");
			return new ModelAndView("optSuccess",map);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stu", student);
			map.put("error", message);
			return new ModelAndView("editStudent",map); 
		}
	}
	
	@RequestMapping("delStu.do")
	public ModelAndView delStu(int id) {
		Student stu = new Student();
		stu.setId(id);
		this.getService().del(stu);
		
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("backUrl", "studentManager.do");
		return new ModelAndView("optSuccess",map);
	}
}
