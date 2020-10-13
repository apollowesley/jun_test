package durcframework.test.student.controller;

import java.util.List;

import org.durcframework.core.DefaultMessageResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.form.FormModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.department.entity.Department;
import durcframework.test.student.entity.Student;

// 请求参数绑定
@Controller
public class DataBindController {

	// http://localhost/durcframeworkTest/bindList.do?nums[0]=1&nums[1]=2
	@RequestMapping("bindList.do")
	public 
	@ResponseBody
	MessageResult bindList(@FormModel("nums") List<Integer> nums) {
		for (Integer i : nums) {
			System.out.println(i);
		}
		return DefaultMessageResult.success();
	}
	
	// http://localhost/durcframeworkTest/bindListStu.do?stus[0].mobile=13712345678&stus[0].name=jim&stus[1].mobile=13987654321&stus[1].name=tom
	@RequestMapping("bindListStu.do")
	public @ResponseBody
	MessageResult bindListStu(@FormModel("stus") List<Student> stus) {
		for (Student stu : stus) {
			System.out.println(stu.getMobile() + "  " + stu.getName());
		}
		return DefaultMessageResult.success();
	}
	
	// http://localhost/durcframeworkTest/bindObj.do?stu.id=1&dept.id=2&stu.name=jim
	@RequestMapping("bindObj.do")
	public @ResponseBody
	MessageResult bindLibindObjstStu(@FormModel("stu") Student student,@FormModel("dept")Department dept) {
		System.out.println("stu.id=" + student.getId() + " stu.name=" + student.getName());
		System.out.println("dept.id=" + dept.getId());
		return DefaultMessageResult.success();
	}


}
