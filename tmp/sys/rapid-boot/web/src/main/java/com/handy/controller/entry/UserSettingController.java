package com.handy.controller.entry;

import com.handy.service.service.sys.ISysAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {用户管理}
 * @date 2019/8/28 9:42
 */
@Controller
@RequestMapping("/entry/userSetting")
@ApiIgnore()
public class UserSettingController {
    @Autowired
    private ISysAccountService sysAccountService;

    /**
     * 更改密码页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/password")
    public String password(Model model, Long id) {
        model.addAttribute("id", id);
        return "entry/password";
    }

    /**
     * 用户编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/setting")
    public String setting(Model model, Long id) {
        val sysAccount = sysAccountService.getById(id);
        model.addAttribute("sysAccount", sysAccount);
        return "entry/setting";
    }
}
