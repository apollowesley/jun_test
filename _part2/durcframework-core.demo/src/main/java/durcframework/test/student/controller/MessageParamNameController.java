package durcframework.test.student.controller;

import java.util.Arrays;
import java.util.List;

import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.SearchController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.common.MyMessageHolder;
import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
public class MessageParamNameController extends
		SearchController<Student, StudentService> {
	
	// 重写消息类,json将根据返回的类来序列化
	// 默认返回org.durcframework.core.DefaultMessageResult对象
	@Override
	protected MessageResult getMessageResult() {
		return new MyMessageHolder();
	}

	@RequestMapping("/messageParamName.do")
	public @ResponseBody
	MessageResult messageParamName(SearchStudentEntity searchStudentEntity) {
		return this.success("成功!");
	}
	
	@RequestMapping("/messageParamName2.do")
	public @ResponseBody
	MessageResult messageParamName2(SearchStudentEntity searchStudentEntity) {
		return this.error("失败!");
	}
	
	@RequestMapping("/messageParamName3.do")
	public @ResponseBody
	MessageResult messageParamName3(SearchStudentEntity searchStudentEntity) {
		List<String> errorList = Arrays.asList("失败原因1","失败原因2");
		return this.error("失败!",errorList);
	}
}
