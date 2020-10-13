package com.imzdx.user;

import com.imzdx.common.model.User;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;


public class UserValidator extends Validator {

    //private static final Log log = Log.getLog(UserValidator.class);

    protected void validate(Controller controller) {
        validateRequiredString("user.username", "usernameMsg", "请输入用户名!");
        validateRequiredString("user.password", "passwordMsg", "请输入密码!");
        validateRequiredString("user.nickname", "nicknameMsg", "请输入昵称!");
    }

    protected void handleError(Controller controller) {
        controller.keepModel(User.class);

        String actionKey = getActionKey();
        if (actionKey.equals("/user/save"))
            controller.render("userAdd.html");
        else if (actionKey.equals("/user/update"))
            controller.render("userEdit.html");
    }

}