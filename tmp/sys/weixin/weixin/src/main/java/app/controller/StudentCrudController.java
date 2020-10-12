package app.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.entity.SearchStudentEntity;
import app.entity.Student;
import app.service.StudentService;

//@Controller
public class StudentCrudController extends CrudController<Student, StudentService> {
	
	@RequestMapping("/addStudent.do")
	public @ResponseBody MessageResult addStudent(Student student) {
		return this.save(student);
	}
	
	@RequestMapping("/listStudent.do")
	public @ResponseBody GridResult listStudent(SearchStudentEntity searchStudentEntity) {
		return this.query(searchStudentEntity);
	}
	
	@RequestMapping("/updateStudent.do")
	public @ResponseBody MessageResult updateStudent(Student student) {
		return this.update(student);
	}
	
	// 传一个id值即可,根据主键删除
	@RequestMapping("/delStudent.do")
	public @ResponseBody MessageResult delStudent(Student student) {
		return this.delete(student);
	}
	
	
}
