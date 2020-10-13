package org.apache.center.web.controller.systemmanage;

import org.apache.center.dto.UserDto;
import org.apache.center.model.User;
import org.apache.center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.domain.EasyuiClientMessage;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("user/")
public class UserController extends BaseController {
	
	private static String VIEWS_PATH = "systemmanage/user/";
	
	@Reference(registry = "centerRegistry", timeout = 3000)
	private UserService userService; //服务层

    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("user")
	public String toFind() {
		return VIEWS_PATH+"find";
	}
	
	/**
	 *  查询数据
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	@Permission("user_find")
	public Object find(User user) {
		EntityWrapper<User> wrapper = new EntityWrapper<User>(user);
		String userNameLike = getParameter("userNameLike");
		if (StringUtils.isNotBlank(userNameLike)) {
			wrapper.like("user_name", userNameLike);
		}
		Page<User> page = getEasyuiPage();
		return EasyuiClientMessage.success(userService.selectPage(page , wrapper), UserDto.class);
	}

    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("user_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("user", new User());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("user_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    User user = userService.selectById(id);
	    modelMap.put("user", user);
		return VIEWS_PATH+"edit";
	}
	
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("user_add")
	public Object add(User user, BindingResult br) {
		// 数据验证
		if (validate(user, br)) {
			return getResult(userService.save(user));
		} else {
			return getFailResult(br.getFieldError().getDefaultMessage());
		}
	}
	
	
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("user_update")
	public Object update(User user) {
		return getResult(userService.updateById(user));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("user_delete")
	public Object delete(@RequestParam("id") Long id) {
	    User user = new User();
	    user.setId(id);
	    user.setIsDelete("Y");
		return getResult(userService.updateById(user));
	} 
	 
}