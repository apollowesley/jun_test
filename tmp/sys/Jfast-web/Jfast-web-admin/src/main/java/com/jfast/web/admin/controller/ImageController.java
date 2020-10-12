package com.jfast.web.admin.controller;

import com.jfast.common.utils.ImageUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/7 11:27
 */
@RestController
public class ImageController {

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @RequestMapping("/image")
    public void image(HttpServletRequest request, HttpServletResponse response){
        ImageUtil imageUtil = new ImageUtil();
        imageUtil.render(request, response);
    }
}
