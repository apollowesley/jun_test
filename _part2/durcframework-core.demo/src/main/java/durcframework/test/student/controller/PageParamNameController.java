package durcframework.test.student.controller;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.SearchController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.common.MyResultHolder;
import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

@Controller
public class PageParamNameController extends
		SearchController<Student, StudentService> {

	// 重写父类方法,返回自定义的结果类
	// json序列化时将根据自定义的get方法来序列化
	@Override
	protected GridResult getGridResult() {
		return new MyResultHolder();
	}

	/*
	 * {"list":[{...},{...}],"total_count":36,"page_index":1,"page_length":10,"page_count":3}
	 */
	// http://localhost/durcframeworkTest/pageParamNameList.do
	@RequestMapping("/pageParamNameList.do")
	public @ResponseBody
	GridResult pageParamNameList(SearchStudentEntity searchStudentEntity) {
		return this.query(searchStudentEntity);
	}

}
