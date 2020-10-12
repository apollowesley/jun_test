package me.nibo.webframework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * Created by nibo on 2015/7/21.
 */
@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    /**
     * 跳转到首页，响应页面 "WEB-INF/pages/index.ftl"
     *
     */
    @RequestMapping(value = {"/index", "/"})
    public String index() {
        LOGGER.debug("跳转到首页");
        return "index";
    }
}
