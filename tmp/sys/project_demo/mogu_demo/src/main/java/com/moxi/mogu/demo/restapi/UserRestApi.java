package com.moxi.mogu.demo.restapi;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  用户RestApi
 * </p>
 *
 * @author xuzhixiang
 * @since 22018年11月19日15:12:31
 */
@RestController
@RequestMapping("/user")
@Api(value="用户RestApi",tags={"UserRestApi"})
public class UserRestApi {
	
	/**
	 * hello
	 * @return
	 */
	@ApiOperation(value="hello", notes="hello")
	@GetMapping("/hello")
	public String getPicture() {
		return "hello";

	}
	
}

