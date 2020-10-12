package me.nibo.webframework.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 设置
 * Created by nibo on 2015/7/21.
 */
@Controller
public class SettingController {

    /**
     *
     * @return 响应页面 "WEB-INF/pages/setting/setting.ftl"
     */
    @RequestMapping("/setting")
    public String setting() {
        return "setting/setting";
    }
}
