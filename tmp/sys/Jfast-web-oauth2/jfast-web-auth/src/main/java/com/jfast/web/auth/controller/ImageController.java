package com.jfast.web.auth.controller;

import com.jfast.web.common.core.model.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/7 11:27
 */
@RestController
@Api(tags = "验证码管理接口")
public class ImageController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @GetMapping("/image")
    @ApiOperation("生成验证码接口")
    @ApiImplicitParam(name = "key", value = "验证码key值")
    public void image(@ApiParam(hidden = true) HttpServletRequest request,
                      @ApiParam(hidden = true) HttpServletResponse response){
        String key = request.getParameter("key");
        Captcha captcha = new Captcha(stringRedisTemplate, key);
        captcha.render(request, response);
    }
}
