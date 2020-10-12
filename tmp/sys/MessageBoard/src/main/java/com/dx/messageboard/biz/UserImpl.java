package com.dx.messageboard.biz;

import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.UserRegisterReqDto;
import com.dx.messageboard.mapper.UserMapper;
import com.dx.messageboard.util.MD5Util;
import com.dx.messageboard.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户模块方法
 * Create by zhoushiyu
 */

@Component
public class UserImpl {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户登录方法
     * @param userName
     * @param userPassword
     * @return
     */
    public ServerResponse<User> userLogin(String userName, String userPassword) {
        //获取用户输入的用户名及账号在数据库中进行查找
        int resultCount = userMapper.checkUserName(userName);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //密码登录MD5

        String md5Password = MD5Util.MD5EncodeUtf8(userPassword);
        User user = userMapper.selectByUserNameAndPassword(userName, md5Password);
        if(user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        //把密码赋为空值
        user.setUserPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }


    /**
     * 用户注册方法
     */
    @Transactional
    public ServerResponse<User> userRegister(UserRegisterReqDto userRegisterReqDto){
        //查询是否存在相同的用户账号，若存在则不可以进行注册
        User user = new User();
        user.setUserName(userRegisterReqDto.getUserName());

        int resultCount  = userMapper.checkUserName(user.getUserName());
        if(resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        //MD5加密
        user.setUserPassword(MD5Util.MD5EncodeUtf8(userRegisterReqDto.getUserPassword()));
        resultCount = userMapper.insert(user);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        //把密码赋为空值
        user.setUserPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("注册成功",user);
    }
}
