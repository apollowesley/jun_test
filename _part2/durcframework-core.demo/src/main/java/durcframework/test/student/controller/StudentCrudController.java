package durcframework.test.student.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.durcframework.core.GridResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

/**
 * 增删改查
 * @author hc.tang
 * 2013年11月28日
 *
 */
@Controller
public class StudentCrudController extends CrudController<Student, StudentService> {
	
	// 模拟表单提交
	// http://localhost/durcframeworkTest/addStudent.do?name=Lucy&stuNo=No00001971&address=USA&mobile=13345678951&birthday=1987-07-06
	@RequestMapping("/addStudent.do")
	public @ResponseBody MessageResult addStudent(Student student) {
		MessageResult ret = this.save(student);
		System.out.println("添加后的主键ID:"+ student.getId());
		return ret;
	}
	
	// http://localhost/durcframeworkTest/listStudent.do
	@RequestMapping("/listStudent.do")
	public @ResponseBody GridResult listStudent(SearchStudentEntity searchStudentEntity) {
		return this.query(searchStudentEntity);
	}
	
	// 模拟表单提交,修改姓名为Lily
	// http://localhost/durcframeworkTest/updateStudent.do?id=39&name=Lily&stuNo=36251111
	@RequestMapping("/updateStudent.do")
	public @ResponseBody MessageResult updateStudent(Student student) {
		return this.update(student);
	}
	
	// 传一个id值即可,根据主键删除
	// http://localhost/durcframeworkTest/delStudent.do?id=39
	@RequestMapping("/delStudent.do")
	public @ResponseBody MessageResult delStudent(Student student) {
		// 通过主键查询某一条记录
		System.out.println(this.getService().get(student.getId()));
		return this.delete(student);
	}
	
	public static void main(String[] args) {
		try {
			String s = URLEncoder.encode("1987-07-06", "UTF-8");
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
}
