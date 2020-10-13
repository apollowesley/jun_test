package com.jplee.boot.controller;

import com.jplee.boot.config.properties.ProjectProperties;
import com.jplee.boot.util.CaptchaUtil;
import com.jplee.boot.util.SpringContextUtil;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jplee
 * @date 2019/1/25
 */
@RestController
public class LoginController {

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public ModelAndView toLogin(ModelAndView model) {
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        model.addObject("isCaptcha", properties.isCaptchaOpen());
        model.setViewName("/login");
        return model;

    }

    /**
     * 跳转到登录页面
     */
    /*@GetMapping("/login")
    public String toLogin(Model model) {
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        model.addAttribute("isCaptcha", properties.isCaptchaOpen());
        return "/login";
    }*/

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rememberMe") String rememberMe, HttpSession httpSession) {

        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            httpSession.setAttribute("userSession", username);
            String name = username;
            System.out.println(name);
            Map<String, Object> map = new HashMap<>();
            map.put("name",name);
            map.put("code","200");
            return map;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("msg","密码错误");
            map.put("code","400");
            return map;
        }
    }

    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");

        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }


}
