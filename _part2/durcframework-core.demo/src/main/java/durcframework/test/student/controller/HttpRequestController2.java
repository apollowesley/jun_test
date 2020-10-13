package durcframework.test.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.durcframework.core.WebContext;
import org.durcframework.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HttpRequestController2 extends BaseController {
	
	//http://localhost/durcframeworkTest/testRequest2.do?name=123123
	/*
	this.getRequest():org.apache.catalina.connector.RequestFacade@226c260
	test(HttpServletRequest request2):org.apache.catalina.connector.RequestFacade@226c260
	WebContext.getInstance().getRequest():org.apache.catalina.connector.RequestFacade@226c260
	true
	true
	123123
	123123
	*/
	@RequestMapping("testRequest2.do")
	public @ResponseBody String test(HttpServletRequest request2,HttpServletResponse  response){
		
		System.out.println(this.getResponse() == response); // true
		
		StringBuilder sb = new StringBuilder();
		sb.append("this.getRequest():"+this.getRequest()).append("<br>")
		.append("test(HttpServletRequest request2):"+request2).append("<br>")
		.append("WebContext.getInstance().getRequest():"+WebContext.getInstance().getRequest()).append("<br>")
		.append(this.getRequest() == request2).append("<br>")
		.append(this.getRequest().getSession() == request2.getSession()).append("<br>")
		.append(this.getRequest().getParameter("name")).append("<br>")
		.append(request2.getParameter("name")).append("<br>")
;		
		
		return sb.toString();
	}
}
