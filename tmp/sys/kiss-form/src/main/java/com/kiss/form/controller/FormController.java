package com.kiss.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kiss.web.spring3.controller.MultiActionControllerImpl;

/**
 * @author leyvi
 * @since 2013-8-13下午6:10:01
 */
@Controller
@RequestMapping("/form")
public class FormController extends MultiActionControllerImpl {

	@RequestMapping("/toShowCkeditor")
	public ModelAndView toShowCkeditor() {
		return new ModelAndView("/form/ckeditor_main");
	}
}
