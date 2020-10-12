package com.markbro.dzd.sso.client.web.demo;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.filter.SsoHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController  {
    /*private static final String ERROR_PATH = "/error1";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        throw new RuntimeException("404,page not find!");
    }

    @Override
    public String getErrorPath() {

        return ERROR_PATH;
    }*/
    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        SsoUser ssoUser = (SsoUser) request.getAttribute(SsoConf.SSO_USER);
        model.addAttribute("ssoUser", ssoUser);
        return "/demo/index";
    }
    private static String getSsologoutRedirectUrl(HttpServletRequest request){
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // hostname.com
        int serverPort = request.getServerPort();        // 80
        String contextPath = request.getContextPath();

        StringBuffer url =  new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort).append("/").append(contextPath);
        }

        return url.toString()+"/";
    }
    @RequestMapping("/sso/logout")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        SsoHelper.logout(request,response);
        String s=SsoConf.SSO_SERVER+"/"+SsoConf.SSO_LOGIN_PATH+"?"+SsoConf.REDIRECT_URL+"="+getSsologoutRedirectUrl(request);
        return "redirect:"+s;
    }
    @RequestMapping("/json")
    @ResponseBody
    public ReturnT<SsoUser> json(Model model, HttpServletRequest request) {
        SsoUser ssoUser = (SsoUser) request.getAttribute(SsoConf.SSO_USER);
        return new ReturnT(ssoUser);
    }
}
