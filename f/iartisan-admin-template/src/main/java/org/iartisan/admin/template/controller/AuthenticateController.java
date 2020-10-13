package org.iartisan.admin.template.controller;


import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.iartisan.runtime.utils.StringUtils;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.authentication.RealmBean;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.contants.WebConstants;
import org.iartisan.runtime.web.utils.WebUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 权限认证请求
 *
 * @author King
 * @since 2017/4/13
 */
@Controller
public class AuthenticateController {

    @Value("${iartisan.admin.authenticate.success:index}")
    private String authenticateSuccessPage;

    @Value("${iartisan.admin.authenticate.error:login}")
    private String authenticateErrorPage;

    @ResponseBody
    @PostMapping(ReqContants.REQ_AUTHENTICATE)
    public WebR authenticate(RealmBean realmBean, String vercode) {
        WebR r = new WebR();
        /*if (null == WebUtil.getShiroSession().getAttribute(Constants.KAPTCHA_SESSION_KEY)) {
            r.isError("验证码已过期，请刷新重试");
            return r;
        }*/
        //判断用户名和密码是否正确
        if (StringUtils.isEmpty(realmBean.getUserName()) || StringUtils.isEmpty(realmBean.getUserPwd())) {
            r.isError("用户名或者密码不能为空!!");
            return r;
        }
        /*//验证验证码
        //判断验证码是否正确
        if (StringUtils.isEmpty(vercode) ||
                !WebUtil.getShiroSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString().equals(vercode)) {
            r.isError("验证码错误");
            return r;
        }*/
        Subject subject = SecurityUtils.getSubject();
        //sha256加密
        UsernamePasswordToken token = new UsernamePasswordToken(realmBean.getUserName(), realmBean.getUserPwd());
        try {
            subject.login(token);
            r.setMessage(authenticateSuccessPage);
        } catch (AuthenticationException e) {
            r.isError(e.getMessage());
        }
        return r;
    }
}
