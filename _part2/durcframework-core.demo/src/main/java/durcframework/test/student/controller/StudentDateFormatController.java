package durcframework.test.student.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

/**
 * 接收时间参数
 * @author hc.tang
 * 2013年12月10日
 *
 */
@Controller
public class StudentDateFormatController extends CrudController<Student, StudentService> {
	
	/**
	 * 重写父类方法,使得时间参数精确到时分秒
	 * 默认不重写的话默认只精确到天,即yyyy-MM-dd
	 */
	@InitBinder
	@Override
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	// 查询注册时间为2011-12-02 12:11:00的学生
	// SearchStudentEntity类中的schRegistDate字段接收具体到时分秒的日期
	
	// http://localhost/durcframeworkTest/listDateFormat.do?&schRegistDate=2011-12-02%2012:11:00
	@RequestMapping("/listDateFormat.do")
	public @ResponseBody GridResult listDateFormat(SearchStudentEntity searchStudentEntity) {
		
		return this.query(searchStudentEntity);
	}
	
	
	
}
