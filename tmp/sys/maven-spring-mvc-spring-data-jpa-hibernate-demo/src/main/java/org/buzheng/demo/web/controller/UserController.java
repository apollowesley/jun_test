package org.buzheng.demo.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.buzheng.demo.model.User;
import org.buzheng.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mv = new ModelAndView("user-list");
		List<User> userList = this.userService.findAll();
		mv.addObject("userList", userList);

		return mv;

	}
	
	@RequestMapping(value = "/list/page/{pageNo}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("pageNo") Integer pageNo) {

		ModelAndView mv = new ModelAndView("user-list-page");
		Page<User> page = this.userService.findAll(pageNo);
		mv.addObject("page", page);

		return mv;

	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editPage(@PathVariable("id") Integer id) {
		User user = this.userService.findById(id);
		ModelAndView mv = new ModelAndView("user-edit");
		mv.addObject("user", user);
		return mv;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editUserAndReturnToListPage(User user,
			final RedirectAttributes redirectAttributes) {
		
		this.userService.update(user);

		String message = "Save successfully.";
		redirectAttributes.addFlashAttribute("message", message);

		ModelAndView mv = new ModelAndView("redirect:/user/list");	
		return mv;
	}
	
//	@RequestMapping(value = "/edit", method = RequestMethod.POST)
//	public ModelAndView editUserAndReturnToEditPage(User user,
//			final RedirectAttributes redirectAttributes) {
//		
//		this.userService.update(user);
//
//		redirectAttributes.addAttribute("userId", user.getId()).addFlashAttribute("message", "Save successfully.");
//
//		ModelAndView mv = new ModelAndView("redirect:/user/edit/{userId}");
//		return mv;
//	}

}
