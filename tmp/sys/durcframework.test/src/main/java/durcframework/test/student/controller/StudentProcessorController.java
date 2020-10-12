package durcframework.test.student.controller;

import org.durcframework.controller.CrudController;
import org.durcframework.expression.ExpressionQuery;
import org.durcframework.processor.JsonObjProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import durcframework.test.student.entity.Student;
import durcframework.test.student.service.StudentService;

// 处理结果集
/*
 * 有些查询出来的数据并不是我们想要在页面上显示的,
 * 需要通过二次处理,比如性别字段,数据库中保存的是0,1.
 * 页面上显示的是女,男
 */
@Controller
public class StudentProcessorController extends CrudController<Student, StudentService> {

	// http://localhost/durcframeworkTest/listProcessor.do
	@RequestMapping("/listProcessor.do")
	public ModelAndView listPage() {
		ExpressionQuery query = new ExpressionQuery();
		// 第一页
		query.setPageIndex(1);
		// 每页显示10条
		query.setPageSize(10);
		
		// 添加排序
		query.setSortname("regist_date");
		query.setSortorder("desc");
		
		return this.queryWithProcessor(query, new JsonObjProcessor<Student>() {
			// jsonObject是当前记录的json对象,可以对其增加键值对
			@Override
			public void process(Student entity, JSONObject jsonObject) {
				// 在返回的json中会增加myGender:'男' 或  myGender:'女'
				if(entity.getGender() == 1) {
					jsonObject.put("myGender", "男");
				}else{
					jsonObject.put("myGender", "女");
				}
			}
			
		});
	}
	
	
}
