package durcframework.test.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.BaseController;
import org.durcframework.open.OpenUtil;
import org.durcframework.open.annotation.ApiMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import durcframework.test.student.entity.SearchStudentEntity;
import durcframework.test.student.entity.Student;

@Controller
public class ApiMethodController extends BaseController {

	// 最基本的请求
	// http://localhost/openTest/test1.do?appId=test&timestamp=330523156522&schName=Jim&sign=45FF792F8281667B3F8EF1CAC75FFAA0DDBD31E2
	@RequestMapping("test1.do")
	@ApiMethod
	public @ResponseBody MessageResult test1(String schName) {
		return success("最基本的请求," + schName);
	}
	
	// 不需要签名检查
	// http://localhost/openTest/test2.do?schName=Jim2
	@RequestMapping("test2.do")
	@ApiMethod(needSign=false)
	public @ResponseBody MessageResult test2(String schName) {
		return success("不需要签名检查," + schName);
	}
	
	// 自定义参数名
	// http://localhost/openTest/test3.do?siteId=test&dateStr=330523156522&schName=Jim&flag=32CCBF261E655ADC7C267746CFB7124D7063E699
	@RequestMapping("test3.do")
	@ApiMethod(needTimeout=false,appIdName="siteId",signName="flag",timestampName="dateStr")
	public @ResponseBody MessageResult test3(String schName) {
		return success("自定义参数名," + schName);
	}
	
	// 带忽略字段
	// http://localhost/openTest/test4.do?schStuNo=NO0000001&appId=test&timestamp=330523156522&schName=Jim&sign=45FF792F8281667B3F8EF1CAC75FFAA0DDBD31E2
	@RequestMapping("test4.do")
	@ApiMethod(needTimeout=false,ignoreParamNames={"schStuNo"}) // schStuNo不会加入到签名算法中去
	public @ResponseBody
	MessageResult test4(SearchStudentEntity searchStudentEntity) {
		return success("带忽略字段," + searchStudentEntity.getSchName());
	}
	
	// http://localhost/openTest/test5.do?appId=test&timestamp=330523156522&name=Jim&sign=D7DBC0B31E20F7DD00254D76C9B787C615663266
	@RequestMapping("test5.do")
	@ApiMethod(needTimeout=false)
	public @ResponseBody
	MessageResult test5(Student student) {
		return success("带忽略字段," + student.getName());
	}
	
	
	public static void main(String[] args) throws IOException {
		
		Map<String,String> map = new HashMap<String, String>();
		//long timestamp = System.currentTimeMillis();
		//这里为了方便测试给了一个固定时间
		//实际开发中应该使用System.currentTimeMillis();
		String appId = "test";
		String secret = "123456";
		String timestamp = "330523156522";
		
		String schName = "Jim";
		
		map.put("appId", appId);
		map.put("timestamp", timestamp);
		map.put("name", schName);
		
		String sign = OpenUtil.buildSign(map,secret,null);
		
		System.out.println("http://localhost/openTest/test5.do?appId=test&timestamp=330523156522&name="+schName+"&sign=" + sign);
	}
	
}
