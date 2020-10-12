package durcframework.test.api;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.durcframework.core.GridResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.SearchController;
import org.durcframework.open.annotation.ApiMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
public class JsonController extends SearchController<Student, StudentService>{
	@RequestMapping("/jsonTest.do")
	@ApiMethod(needSign=false)
	public 
	@ResponseBody
	Student xmlTest(){
		
		Student stu = new Student();
		stu.setAddress("杭州");
		stu.setBirthday(new Date());
		stu.setId(1);
		
		return stu;
	}
	
	@RequestMapping("/successReq.do")
	@ApiMethod(needSign=false)
	public @ResponseBody MessageResult successReq(){
		return success();
	}
	
	@RequestMapping("/errorReq.do")
	@ApiMethod(needSign=false)
	public @ResponseBody MessageResult errorReq(){
		return error("error msg...");
	}
	
	@RequestMapping("/jsonArrTest.do")
	@ApiMethod(needSign=false)
	public @ResponseBody GridResult xmlArrTest(){
		
		Student stu = new Student();
		stu.setAddress("aaaa");
		stu.setBirthday(new Date());
		stu.setId(1);
		
		Student stu2 = new Student();
		stu2.setAddress("bbbb");
		stu2.setBirthday(new Date());
		stu2.setId(2);
		
		List<Student> list = Arrays.asList(stu,stu2);
		
		return success(list);
	}
	
}
