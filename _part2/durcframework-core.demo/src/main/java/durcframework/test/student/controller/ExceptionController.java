package durcframework.test.student.controller;

import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.SearchController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
public class ExceptionController extends SearchController<Student, StudentService> {

	@RequestMapping("/exceptionStudent.do")
	public @ResponseBody MessageResult addStudent(Student student) {
		int i = 1/0;
		return success();
	}
}
