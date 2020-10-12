package com.dx.messageboard.service;

import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.UserLoginReqDto;
import com.dx.messageboard.dto.UserRegisterReqDto;
import com.dx.messageboard.vo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户接口
 * Create by zhoushiyu
 */
@RequestMapping("/user")
public interface IUser {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<User> userLogin(@RequestBody UserLoginReqDto userLoginReqDto, HttpSession httpSession);


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<User> userRegister(@RequestBody UserRegisterReqDto userRegisterReqDto, HttpSession session);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    ServerResponse<String> logout(HttpSession session);


}
