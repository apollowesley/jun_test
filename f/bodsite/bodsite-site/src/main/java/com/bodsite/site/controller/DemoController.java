package com.bodsite.site.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bodsite.common.utils.VerificationUtil;
import com.bodsite.core.rest.BaseRestController;
import com.bodsite.core.rest.http.ResponseUtil;
import com.bodsite.demo.exception.DemoException;
import com.bodsite.demo.service.HellowService;
import com.bodsite.demo.vo.DemoVo;


@RestController
@RequestMapping(value = "demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoController extends BaseRestController {

	@Autowired
	private HellowService hellowService;

	@RequestMapping(value = "demo/{id:\\d+}", method = RequestMethod.GET)
	public String demo(@PathVariable Integer id) {

		VerificationUtil.notNull(id);
		DemoVo demoVo = hellowService.findDemo(id);
		VerificationUtil.notNull(demoVo, new DemoException(DemoException.DEMO_EXPECTION.MEMBER_NOT_FOUND));
		List<DemoVo> demoVos = new ArrayList<DemoVo>();
		demoVos.add(demoVo);
		DemoVo demoVo1 = new DemoVo();
		demoVo1.setDemoId(2);
		demoVo1.setName("是多少");
		demoVo1.setSex(1);
		demoVo1.setAge(11);
		demoVos.add(demoVo1);

		DemoVo demoVo2 = new DemoVo();
		demoVo2.setDemoId(3);
		demoVo2.setName("撒打算");
		demoVo2.setSex(1);
		demoVo2.setAge(9);
		demoVos.add(demoVo2);

		DemoVo demoVo3 = new DemoVo();
		demoVo3.setDemoId(4);
		demoVo3.setName("分隔符");
		demoVo3.setSex(2);
		demoVo3.setAge(10);
		demoVos.add(demoVo3);

		return ResponseUtil.buildSuccessJsonStrInclude(demoVos, "demoId", "name");
	}

}
