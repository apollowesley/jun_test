package com.markbro.dzd.sso.server.web;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.filter.SsoHelper;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.store.ISsoLoginStore;
import com.markbro.dzd.sso.core.store.ISsoSessionIdHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.user.SsoUserInfo;
import com.markbro.dzd.sso.core.util.CookieUtil;
import com.markbro.dzd.sso.server.service.SsoUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Administrator on 2018/12/3.
 */
@Controller
public class SsoController{

    @Autowired
    ISsoLoginStore ssoLoginStore;
    @Autowired
    ISsoSessionIdHelper ssoSessionIdHelper;
    @Autowired
    ISsoLoginHelper ssoLoginHelper;

    @Autowired
    SsoUserService ssoUserService;

    @Value("${sso.cookie.key.username}")
    private String KEY_COOKIE_USERNAME;
    @Value("${sso.cookie.key.password}")
    private String KEY_COOKIE_PASSWORD;
    @Value("${sso.cookie.key.remember}")
    private String KEY_COOKIE_REMEMBER;

    @RequestMapping (value = {"/sso", "/sso/"})
    public String toIndex(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {

        SsoUser ssoUser = ssoLoginHelper.loginCheck(request,response);
        if(ssoUser!=null){
            model.addAttribute("ssoUser", ssoUser);
            return "/sso/index";
        }
        model.addAttribute("KEY_COOKIE_USERNAME",KEY_COOKIE_USERNAME);
        model.addAttribute("KEY_COOKIE_PASSWORD",KEY_COOKIE_PASSWORD);
        model.addAttribute("KEY_COOKIE_REMEMBER",KEY_COOKIE_REMEMBER);
        return SsoConf.SSO_LOGIN_PATH;
    }



    //login page
    @RequestMapping (value = {"/login", "/sso/login"})
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
        SsoUser ssoUser = ssoLoginHelper.loginCheck(request,response);
        if(ssoUser!=null){
            // success redirect
            String redirectUrl = request.getParameter(SsoConf.REDIRECT_URL);
            if (redirectUrl!=null && redirectUrl.trim().length()>0) {
                String sessionId = ssoLoginHelper.getSessionId(request);
                String redirectUrlFinal = redirectUrl + "?" + SsoConf.SSO_SESSIONID + "=" + sessionId;;
                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/sso";
            }
        }
        String msg = request.getParameter("msg");
        if(StringUtils.isNotEmpty(msg)){
            model.addAttribute("msg",msg);
        }
        String url =  request.getParameter(SsoConf.REDIRECT_URL);
        if(StringUtils.isNotEmpty(url)){
            model.addAttribute(SsoConf.REDIRECT_URL, url);
        }
        model.addAttribute("KEY_COOKIE_USERNAME",KEY_COOKIE_USERNAME);
        model.addAttribute("KEY_COOKIE_PASSWORD",KEY_COOKIE_PASSWORD);
        model.addAttribute("KEY_COOKIE_REMEMBER",KEY_COOKIE_REMEMBER);
        return SsoConf.SSO_LOGIN_PATH;
    }

    //login
    @RequestMapping(value = "/sso/doLogin", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {

        String username=request.getParameter(SsoConf.KEY_USER_NAME);
        String password=request.getParameter(SsoConf.KEY_PASSWORD);
        String ifRemember=request.getParameter(SsoConf.KEY_REMEMBER);
        boolean ifRem = (ifRemember!=null&&("on".equals(ifRemember) || "1".equals(ifRemember)))?true:false;

        // valid login
        ReturnT<SsoUserInfo> result = ssoUserService.findUser(username, password);
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            redirectAttributes.addFlashAttribute("msg", result.getMsg());
            redirectAttributes.addFlashAttribute(SsoConf.REDIRECT_URL, request.getParameter(SsoConf.REDIRECT_URL));
            return "redirect:/sso/login";
        }
        SsoUserInfo ssoUserInfo= result.getData();

        ssoUserService.afterValidateSuccess(ssoUserInfo);

        //sso 单点登录
        // 1、make xxl-sso user
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(ssoUserInfo.getUserid());
        ssoUser.setUsername(ssoUserInfo.getUsername());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinite(ssoLoginStore.getExpireMinite());
        ssoUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、make session id
        String sessionId = ssoSessionIdHelper.makeSessionId(ssoUser);

        // 3、login, store storeKey + cookie sessionId
        ssoLoginHelper.login(request,response, sessionId, ssoUser, ifRem);

        //记住用户名密码
        int age=3600*24*30;//cookie一个月时长
        CookieUtil.set(response, KEY_COOKIE_USERNAME, username, age);
        if(ifRem){
            CookieUtil.set(response, KEY_COOKIE_PASSWORD, password, age);
            CookieUtil.set(response, KEY_COOKIE_REMEMBER, "1", age);
        }else{
            CookieUtil.remove(request, response, KEY_COOKIE_PASSWORD);
            CookieUtil.set(response, KEY_COOKIE_REMEMBER, "0", age);
        }
        request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);

        String redirectUrl = request.getParameter(SsoConf.REDIRECT_URL);
        if (redirectUrl!=null && redirectUrl.trim().length()>0) {
            String redirectUrlFinal = redirectUrl + "?" + SsoConf.SSO_SESSIONID + "=" + sessionId;
            return "redirect:" + redirectUrlFinal;
        } else {
            return "redirect:/sso";
        }
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/sso/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
        ssoLoginHelper.logout(request,response);
        redirectAttributes.addAttribute(SsoConf.REDIRECT_URL, request.getParameter(SsoConf.REDIRECT_URL));
        return "redirect:/sso/login";
    }
    /**
     * 检测登录状态
     * @return
     */
    @RequestMapping(value = {"/sso/loginState", "/sso/api/loginState"})
    @ResponseBody
    public Object ssoLoginState(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
        SsoUser ssoUser = ssoLoginHelper.loginCheck(request,response);
        if (ssoUser == null) {
            return new ReturnT<SsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<SsoUser>(ssoUser);
    }
    /**
     * 人工清除session(踢出登录用户)
     * @return
     */
    @RequestMapping(value = {"/sso/invalidSession", "/sso/api/invalidSession"})
    @ResponseBody
    public Object invalidSession(HttpServletRequest request,HttpServletResponse response){
        String sessionId= request.getParameter(SsoConf.SSO_SESSIONID);
        if(StringUtils.isEmpty(sessionId)){
            return new ReturnT<String>(ReturnT.FAIL_CODE, "argument sso_sessionid is required!");
        }
        boolean flag = ssoLoginHelper.invalidSession(sessionId);
        if (!flag) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "操作失败");
        }
        return new ReturnT<String>("操作成功");
    }
    //会话管理页面
    @RequestMapping("/sso/session")
    public String sessionList(){
        return "/sso/session/list";
    }
    @ResponseBody
    @RequestMapping("/sso/session/json/find")
    public Object session_list(){

        return null;
    }
}
