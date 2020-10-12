package durcframework.test.api;

import java.util.Date;

import org.durcframework.core.controller.BaseController;
import org.durcframework.open.annotation.ApiMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.Student;

@Controller
public class XmlController extends BaseController{

	// 使用IE浏览器访问
	// 请求头决定了返回格式
	@RequestMapping("/xmlTest.do")
	@ApiMethod(needSign=false)
	public 
	@ResponseBody Student xmlTest(){
		
		Student stu = new Student();
		stu.setAddress("aaaa");
		stu.setBirthday(new Date());
		stu.setId(1);
		
		return stu;
	}
}
