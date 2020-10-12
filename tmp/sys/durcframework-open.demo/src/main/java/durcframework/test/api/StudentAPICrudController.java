package durcframework.test.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.durcframework.core.GridResult;
import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.durcframework.open.OpenUtil;
import org.durcframework.open.annotation.ApiMethod;
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
public class StudentAPICrudController extends CrudController<Student, StudentService> {
	
	// 查询
	// http://localhost/openTest/listApiStudent.do?appId=test&timestamp=330523156522&schName=Jim&sign=45FF792F8281667B3F8EF1CAC75FFAA0DDBD31E2
	@RequestMapping("/listApiStudent.do")
	@ApiMethod(needTimeout=false)
	public @ResponseBody
	GridResult listApiStudent(SearchStudentEntity searchStudentEntity) {
//		if(searchStudentEntity.getSchName().equals("Jim")) {
//			throw new DurcException("您不能查询Jim用户");
//		}
		return this.query(searchStudentEntity);
	}
	
	// 查询,带忽略字段
	// http://localhost/openTest/listApiStudent2.do?schStuNo=NO0000001&appId=test&timestamp=330523156522&schName=Jim&sign=45FF792F8281667B3F8EF1CAC75FFAA0DDBD31E2
	@RequestMapping("/listApiStudent2.do")
	@ApiMethod(needTimeout=false,ignoreParamNames={"schStuNo"}) // schStuNo不会加入到签名算法中去
	public @ResponseBody
	GridResult listApiStudent2(SearchStudentEntity searchStudentEntity) {
		return this.query(searchStudentEntity);
	}
	
	/*
	 * 没有加@ApiMethod注解的方法,调用时会报错
	 */
	// http://localhost/openTest/listApiStudentNoAnno.do?appId=test&timestamp=330523156522&schName=Jim&sign=E3508A36A8843610DCF77E1B13C32F408D5E05A8
	@RequestMapping("/listApiStudentNoAnno.do")
	public @ResponseBody
	GridResult listApiStudentNoAnno(SearchStudentEntity searchStudentEntity) {
		return this.query(searchStudentEntity);
	}
	
	// 修改id=1的学生名,name改成Jim
	// http://localhost/openTest/updateApiStudent.do?appId=test&timestamp=440523156521&id=1&name=Jim&registDate=2015-01-01&sign=6F80BBB6D2C9B32A66229D3D229E257B731012F4
	@RequestMapping("/updateApiStudent.do")
	@ApiMethod
	public @ResponseBody
	MessageResult updateApiStudent(Student student) {
		return this.update(student);
	}
	
	
	public static void main(String[] args) throws IOException {
		
		Map<String,String> map = new HashMap<String, String>();
		long timestamp = System.currentTimeMillis();
		//这里为了方便测试给了一个固定时间
		//实际开发中应该使用System.currentTimeMillis();
		String appId = "test";
		String secret = "123456";
		
		String schName = "Jim";
		
		map.put("appId", appId);
		map.put("timestamp", timestamp+"");
		map.put("schName", schName);
		
		String sign = OpenUtil.buildSign(map,secret,null);
		
		System.out.println("http://localhost/openTest/listApiStudent.do?appId=test&timestamp="+timestamp+"&schName=Jim&sign=" + sign);
		System.out.println("http://localhost/openTest/updateApiStudent.do?appId=test&timestamp="+timestamp+"&id=1&name=Jim&registDate=2015-01-01&sign=" + sign);
	}
	
	
}
