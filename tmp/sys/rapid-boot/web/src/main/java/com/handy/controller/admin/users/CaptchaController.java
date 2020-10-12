package com.handy.controller.admin.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/29 15:58
 */
@Controller
@RequestMapping("/admin/users/captcha")
@ApiIgnore()
public class CaptchaController {
    /**
     * 验证码查询
     *
     * @return
     */
    @GetMapping("/list")
    public String list() {
        return "admin/users/captcha/list";
    }
}
