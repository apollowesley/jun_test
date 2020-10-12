package com.site.web.controller.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.site.auth.dto.UserDto;
import com.site.auth.model.User;
import com.site.auth.service.AuthService;
import com.site.auth.util.UserEncodes;
import com.site.common.exception.BusinessException;
import com.site.framework.base.Page;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String list(Model model) {
        Page<User> page = new Page<User>();
        page = authService.paginateUser(page);
        model.addAttribute("page", page);
        return "auth/user/user_manager";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        return "auth/user/user_add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> add(Model model, @ModelAttribute("userCommand") UserDto user) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // FIXME 校验未加
            User u = new User();
            u.setEmail(user.getEmail());
            u.setLoginName(user.getLoginName());
            u.setUserName(user.getUserName());
            u.setPassword(user.getPassword());
            UserEncodes.entryptPassword(u);
            boolean flag = authService.insertUser(user);
            if (flag) {
                result.put("status", 1);
                result.put("msg", "新增成功");
            } else {
                result.put("status", 0);
                result.put("msg", "新增失败");
            }
        } catch (BusinessException e) {
            log.error("## 用户新增失败 ,errorCode={} , errorMsg={}", e.getCode(), e.getMessage());
            result.put("status", 0);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("stauts", 0);
            result.put("msg", "新增用户失败");
            log.error("## 用户新增失败 , errorMsg={}", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam("userId") String userId, Model model) {
        User user = authService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "auth/user/user_edit";
    }

}
