package org.scau.mystudio.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.scau.mystudio.web.bean.User;
import org.scau.mystudio.web.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author jinglun
 * @create 2016-09-10 11:28
 */
@Controller
@Scope("prototype")
public class UserAction extends ActionSupport {

    //private static final long serialVersionUID = 1L;
    //private UserForm user;

    //属性user，用于接収从用户界面输入的用户信息
    protected User user;
    //属性userService，用于帮助action完成相关的操作
    protected UserService userService;
    //execute方法，用于完成用户登录工作
    public String execute() throws Exception{
        User u = userService.validateUser(user.getUserName(),user.getPassword());
        if(u!=null){
            Map session = ActionContext.getContext().getSession();
            session.put("user",u);
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }

    //用户注册，由service层帮助完成
    public String register() throws Exception{
        userService.saveUser(user);
        return SUCCESS;
    }

    //属性user的set方法
    public void setUser(User user){
        this.user = user;
    }
    //属性user的get方法
    public User getUser(){
        return this.user;
    }

    //属性userService的set方法
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    //属性userService的get方法
    public UserService getUserService(){
        return this.userService;
    }

    /**@Autowired
    private UserServiceImpl userServiceImpl;

    public UserForm getUser(){
        return user;
    }

    public void setUser(UserForm user){
        this.user = user;
    }

    public String execute() {
        try {
            userServiceImpl.regUser(user);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    */
}
