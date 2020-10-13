package durcframework.test.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StringController {
	
	@RequestMapping(value="listString",produces="text/plain")
	public @ResponseBody String listString() {
		return "hello,世界";
	}
	
}
