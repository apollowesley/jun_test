package com.dx.messageboard.service;

import com.dx.messageboard.biz.UserImpl;
import com.dx.messageboard.common.Const;
import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.UserLoginReqDto;
import com.dx.messageboard.dto.UserRegisterReqDto;
import com.dx.messageboard.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户接口实现
 * Create by zhoushiyu
 */
@RestController
public class UserService implements IUser{

    @Autowired
    UserImpl userImpl;



    @Override
    public ServerResponse<User> userLogin(UserLoginReqDto userLoginReqDto, HttpSession session) {
        ServerResponse<User> response =userImpl.userLogin(userLoginReqDto.getUserName(), userLoginReqDto.getUserPassword());
        if(response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }


    @Override
    public ServerResponse<User> userRegister(UserRegisterReqDto userRegisterReqDto, HttpSession session) {
        ServerResponse<User> response =userImpl.userRegister(userRegisterReqDto);
        if(response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @Override
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
}
