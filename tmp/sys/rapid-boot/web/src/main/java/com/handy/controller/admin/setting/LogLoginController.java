package com.handy.controller.admin.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/3 16:29
 */
@Controller
@RequestMapping("/admin/setting/logLogin")
@ApiIgnore()
public class LogLoginController {

    @GetMapping("/list")
    public String list() {
        return "admin/setting/log/list";
    }
}
