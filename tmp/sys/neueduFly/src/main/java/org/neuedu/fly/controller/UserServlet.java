package org.neuedu.fly.controller;


import org.neuedu.fly.entity.User;
import org.neuedu.fly.entity.vo.ResponseEntity;
import org.neuedu.fly.service.UserService;
import org.neuedu.fly.util.GsonUtil;
import org.neuedu.fly.util.SendSmsUtil;
import org.neuedu.fly.util.SysUtil;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/26-11:11
 **/
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserService();

    public ResponseEntity login(){
        String tel = request.getParameter("tel");
        String pwd = request.getParameter("pwd");

        ResponseEntity loginEntity = userService.login(tel, pwd);
        //如果成功，则需要把对应的数据存放到session中
        if(ResponseEntity.isSuccess(loginEntity)){
            session.setAttribute(SysUtil.CURRENT_USER,loginEntity.getData());
            return ResponseEntity.ok();
        }
        return loginEntity;
    }

    //验证码校验
    public ResponseEntity checkCodeImg(){
        //获取后端存储的验证码码
        String codeImg = (String) session.getAttribute(SysUtil.VERIFY_CODE);
        //获取前端输入的验证码
        String verifyCode = request.getParameter("verifyCode");
        if(!codeImg.equalsIgnoreCase(verifyCode)){
            return ResponseEntity.error("验证码输入有误！");
        }
        return ResponseEntity.ok();
    }



    //发短信
    public ResponseEntity sendSms(){
        //发送到手机的随机验证码
        String code = String.valueOf(new Double(Math.random()*10000).intValue());
        System.out.println("手机验证码："+code);
        session.setAttribute(SysUtil.TEL_CODE,code);
        //发送
        String tel = request.getParameter("tel");
        String smsResult = SendSmsUtil.sendSms(tel, code);
        System.out.println(smsResult);
        //{"Message":"模板不合法(不存在或被拉黑)","RequestId":"10DDECF9-FFF6-4DFB-888A-1EF44FA6FC28","Code":"isv.SMS_TEMPLATE_ILLEGAL"}
        Map<String,String> map = GsonUtil.fromJson(smsResult, Map.class);
        String message = map.get("Message");
        if(!message.equals("OK")){
            return ResponseEntity.error("发送短信失败！");
        }
        return ResponseEntity.ok("发送短信成功！");
    }


    public ResponseEntity register(){
        //短信校验
        String telCode = request.getParameter("telCode");
        Object sessionTelCode = session.getAttribute(SysUtil.TEL_CODE);
        if(!sessionTelCode.equals(telCode)){
            return ResponseEntity.error("短信验证码校验失败！");
        }

        //手机号，昵称，密码
        String tel = request.getParameter("tel");
        //校验手机号是否已经注册过了
        ResponseEntity checkedTel = userService.checkedTel(tel);
        if(!ResponseEntity.isSuccess(checkedTel)){
            return checkedTel;
        }

        //开始添加到数据库中
        String nickName = request.getParameter("nickName");
        String pwd = request.getParameter("pwd");

        User user = User.builder().tel(tel).nickName(nickName).password(pwd).build();

        return userService.register(user);
    }


}
