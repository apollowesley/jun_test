package com.markbro.dzd.sso.serverdemo;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.store.ISsoLoginStore;
import com.markbro.dzd.sso.core.store.ISsoSessionIdHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.user.SsoUserInfo;
import com.markbro.dzd.sso.core.util.CookieUtil;
import com.markbro.dzd.sso.server.service.SsoUserService;
import com.markbro.dzd.sso.server.web.SsoController;
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
public class MySsoController extends SsoController {

}
