package com.bodsite.demo.service;

import com.bodsite.common.exception.ArgumentNotValidException;
import com.bodsite.demo.vo.DemoVo;



public interface HellowService {
	String sayHellow(String name);
	DemoVo findDemo(Integer demoId)throws ArgumentNotValidException;
	int insertDemo(DemoVo demoVo);
	void LockTwo();
}
