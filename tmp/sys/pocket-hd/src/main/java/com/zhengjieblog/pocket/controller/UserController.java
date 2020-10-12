package com.zhengjieblog.pocket.controller;

import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.service.UserService;
import com.zhengjieblog.pocket.util.Jwt;
import com.zhengjieblog.pocket.util.ResponseData;
import com.zhengjieblog.pocket.util.Weicat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018-7-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Weicat weicat;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResponseData login(@RequestParam(value = "code",required = true) String code) throws Exception{
        String openid = weicat.getOpenid(code);
        ResponseData responseData = ResponseData.ok();
        String token = null;
        User user1 = new User();
        if(openid!=null){
            User user = null;
            user = userService.getUserByOpenid(openid);
            if(user != null){
                //token过期时间1个小时

                user1.setUuid(user.getUuid());
                responseData.putDataValue("user",user);
                token = Jwt.sign(user1,60L*1000L*60L);
            } else {
                user = userService.createUser(openid);
                user1.setUuid(user.getUuid());
                token = Jwt.sign(user1,60L*1000L*60L);
            }

            responseData.putDataValue("user",user);
            responseData.putDataValue("token",token);
        } else {
            responseData = ResponseData.badRequest();
        }
        return responseData;
    }

    @RequestMapping(value = "/authorization",method = RequestMethod.POST)
    public ResponseData authorization(@RequestParam(value = "code",required = true) String code){
        String openid = weicat.getOpenid(code);
        ResponseData responseData = ResponseData.ok();
        if(openid!=null){
            User user = userService.createUser(openid);
            User user1 = new User();
            user1.setUuid(user.getUuid());
            String token = Jwt.sign(user1,60L*1000L*60L);
            responseData.putDataValue("user",user);
            responseData.putDataValue("token",token);
        } else {
            responseData = ResponseData.unauthorized();
        }
        return responseData;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData findAll(@RequestParam Long userID){
        ResponseData responseData = ResponseData.ok();
        List<User> users = userService.findAll();
        responseData.putDataValue("users",users);
        return responseData;
    }
}
