package org.buzheng.demo.web.controller;

import java.util.Arrays;
import java.util.List;

import org.buzheng.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/data-binding")
public class DataBindingController {
	
	private static Logger logger = LoggerFactory.getLogger(DataBindingController.class);
	
	/*
	 * 绑定参数值
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/request-param?username=buzheng&password=
	 */
	@RequestMapping("/request-param")
	@ResponseBody
	public String requestParam(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", defaultValue="default") String password) {
		
		logger.debug("username: {}", username);
		logger.debug("password: {}", password);
		
		return String.format("username: %s, password: %s", username, password);
	}
	
	/*
	 * 绑定多个同名参数
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/request-param-2?city=beijing&city=shenzhen
	 */
	@RequestMapping("/request-param-2")
	@ResponseBody
	public String requestParam2(@RequestParam("city") String cities) {
		return String.format("cities: %s", cities);
	}
	
	/*
	 * 绑定多个同名参数
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/request-param-3?city=beijing&city=shenzhen
	 */
	@RequestMapping("/request-param-3")
	@ResponseBody
	public String requestParam3(@RequestParam("city") String[] cities) {
		return String.format("cities: %s", Arrays.toString(cities));
	}
	
	/*
	 * 绑定多个同名参数
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/request-param-4?city=beijing&city=shenzhen
	 */
	@RequestMapping("/request-param-4")
	@ResponseBody
	public String requestParam4(@RequestParam("city") List<String> cities) {
		return String.format("cities: %s", cities.toString());
	}
	
	/* URL绑定变量
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/path-variable/test
	 */
	@RequestMapping("/path-variable/{value}")
	@ResponseBody
	public String pathVariable(@PathVariable("value") String value) {
		return String.format("value: %s", value);
	}
	
	/* URL绑定变量
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/path-variable-2/category/business/paga/10
	 */
	@RequestMapping("/path-variable-2/category/{category}/paga/{pageNo}")
	@ResponseBody
	public String pathVariable2(@PathVariable("category") String category,
			@PathVariable("pageNo") Integer pageNo) {
		return String.format("category: %s, pageNo: %s", category, pageNo);
	}
	
	/*
	 * 帮顶cookie值
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/cookie-value
	 */
	@RequestMapping("/cookie-value")
	@ResponseBody
	public String cookieValue(@CookieValue("JSESSIONID") String jSessionId) {
		return String.format("JSESSIONID: %s", jSessionId);
	}
	
	/*
	 * 请求头
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/request-header
	 */
	@RequestMapping("/request-header")
	@ResponseBody
	public String requestHeader(
			@RequestHeader("Host") String host,
			@RequestHeader("Connection") String connection,
			@RequestHeader("Cache-Control") String cacheControl,
			@RequestHeader("Accept") String accept,
			@RequestHeader("User-Agent") String userAgent,
			@RequestHeader("Accept-Encoding") String acceptEncoding,
			@RequestHeader("Accept-Language") String acceptLanguage,
			@RequestHeader("Cookie") String cookie) {
		
		String pattern = "Host: %s <br/>"
				+ "Connection: %s <br/>"
				+ "Cache-Control: %s <br/>"
				+ "Accept: %s <br/>"
				+ "User-Agent: %s <br/>"
				+ "Accept-Encoding: %s <br/>"
				+ "Accept-Language: %s <br/>"
				+ "Cookie: %s <br/>";
		
		return String.format(pattern, host, connection, cacheControl, 
				accept, userAgent, acceptEncoding, acceptLanguage, cookie);
	}
	
	/*
	 * 绑定参数到对象属性。
	 * 请求参数会自动绑定到方法参数对象的属性上去
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/param-2-model?username=zhjiun&password=test
	 */
	@RequestMapping("/param-2-model")
	@ResponseBody
	public String param2Model(User user) {
		return String.format("username: %s, password: %s", user.getUsername(), user.getPassword());
	}
	
	/*
	 * 绑定参数到对象属性。这是一个复杂的对象
	 * 请求参数会自动绑定到方法参数对象的属性上去。
	 * 
	 * http://localhost:8080/maven-spring-mvc-demo/data-binding/complex-model?username=buzheng&password=pass&attributes[age]=30&attributes[height]=180&attributes[weight]=80&books[0]=book1&books[1]=books2
	 */
	@RequestMapping("/complex-model")
	@ResponseBody
	public String ComplexModel(User user) {
		return String.format("user: %s", user);
	}
	
}
