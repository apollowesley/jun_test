package org.well.wellrecord;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.well.wellrecord.annotation.RecordAnno;
import org.well.wellrecord.dto.TailDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * <a href="${pageContext.request.contextPath}/test.do">简单的测试跳转</a>
	 */
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String test() {
		
		logger.info("-- Test -- 简单的测试跳转");
		
		return "login";
	}
	
	@RecordAnno(isParsing=true, title="测试", text="这个单独收费都是{0}多少度{1}发生的规范化的[{tailNo@2}]蛋糕房的人{tailNos@2}")
	@RequestMapping(value="/testRecordAnno.do")
	public ModelAndView testRecordAnno(String tailNo, String tailNos, TailDto tailDto) {
		
		logger.info("获取到的变量是：");
		logger.info("{0} => " + tailNo);
		logger.info("{1} => " + tailNos);
		logger.info("{tailNo@2} => " + tailDto.getTailNo());
		logger.info("{tailNos@2} => " + tailDto.getTailNos());
		
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("isLogin", false);
		mv.addObject("tailNo", tailNo);
		mv.addObject("tailNos", tailNos);
		mv.addObject("tailNoINTailDto", tailDto.getTailNo());
		mv.addObject("tailNosINTailDto", tailDto.getTailNos());
		
		return mv;
		
	}
	
}
