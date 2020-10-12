package com.cdh.service;

import com.cdh.dao.UserDAO;
import com.cdh.model.User;
import com.cdh.util.MD5Util;
import com.cdh.util.ResponseData;

import java.util.Objects;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public ResponseData login(String logName, String logPwd, String userType){
        User user = User.builder().logPwd(MD5Util.getMD5(logPwd)).logName(logName).userType(userType).build();
        System.out.println(user);
        user = userDAO.login(user);
        if(Objects.isNull(user)){
            return ResponseData.fail("用户名或密码输入有误！！");
        }else if(user.getLogStatus() == 0 ){
            return ResponseData.fail("你已被禁用，请联系管理员！！");
        }else{
            user.setLogPwd(null);
            return ResponseData.okData(user);
        }
    }

    /**
     * 旧密码验证
     * @param oldPwdVal
     * @param userId
     * @return
     */
    public boolean oldPwdVerify(String oldPwdVal, Integer userId) {
        User user = userDAO.findById(userId);
        return Objects.equals(user.getLogPwd(),MD5Util.getMD5(oldPwdVal));
    }

    /**
     * 根据用户ID修改用户密码
     * @param newPwdVal
     * @param userId
     * @return
     */
    public boolean updatePwdByUserId(String newPwdVal, Integer userId) {
        return userDAO.updatePwdByUserId(newPwdVal,userId);
    }
}
