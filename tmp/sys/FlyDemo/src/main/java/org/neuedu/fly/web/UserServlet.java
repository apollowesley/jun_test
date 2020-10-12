package org.neuedu.fly.web;

import com.aliyuncs.CommonResponse;
import org.neuedu.fly.entity.vo.ResponseEntity;
import org.neuedu.fly.util.Const;
import org.neuedu.fly.util.SendSmsUtil;

import javax.servlet.annotation.WebServlet;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/21-10:01
 **/
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //图形验证码验证
    public ResponseEntity verifyImgCode(){
        String vimCode = request.getParameter("vimCode");
        String sessionVerifyCode= (String) session.getAttribute(Const.VERIFY_CODE);
        if(!vimCode.equalsIgnoreCase(sessionVerifyCode)){
            return ResponseEntity.fail("验证码输入错误！");
        }
        return ResponseEntity.ok();
    }


    //短信发送
    public ResponseEntity sendSms(){
        String tel = request.getParameter("tel");
        String code = String.valueOf((int)((Math.random()+1)*1000));
        CommonResponse response = SendSmsUtil.sendSms(tel, code);
        System.out.println("阿里云反馈信息："+response.getData());
        session.setAttribute(Const.TEL_VERIFY_CODE,code);
        return ResponseEntity.ok("发送短信成功~");
    }

    public ResponseEntity register(){
        //判断验证码是否正确

        //判断短信验证码是否正确

        //手机号是否注册过

        //插入

        return ResponseEntity.ok();
    }


}
