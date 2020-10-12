package cn.backflow.admin.controller;

import cn.backflow.admin.common.Configuration;
import cn.backflow.admin.common.Constants;
import cn.backflow.admin.common.cache.CacheService;
import cn.backflow.admin.common.secure.UnauthorizedException;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.entity.ResetRecord;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.DictService;
import cn.backflow.admin.service.UserService;
import cn.backflow.lib.util.EmailUtil;
import cn.backflow.lib.util.JsonMap;
import cn.backflow.lib.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseSpringController {

    private final CacheManager cacheManager;
    private final UserService userService;
    private final DictService dictService;

    @Autowired
    public IndexController(CacheManager cacheManager, UserService userService, DictService dictService) {
        this.cacheManager = cacheManager;
        this.userService = userService;
        this.dictService = dictService;
    }

    /* 前端请求该方法验证用户是否登录 */
    @RequestMapping("authorization-check")
    public Object authorizationCheck(HttpSession session, HttpServletResponse response) throws Exception {
        if (session.getAttribute(Constants.SESSION_USER_KEY) == null) {
            throw new UnauthorizedException("会话已过期, 请重新登录.");
        }
        return JsonMap.succeed().put("permissions", session.getAttribute("permissions"));
    }

    /**
     * 是否是验证码登录
     *
     * @param username 用户名
     * @param fail     计数加1
     * @param clean    计数清零
     */
    public static boolean isLoginWithValidateCode(String username, boolean fail, boolean clean) {
        Map<String, Integer> loginFailMap = CacheService.get(Constants.LOGIN_FAILURE_MAP);
        if (loginFailMap == null) {
            loginFailMap = new HashMap<>();
            CacheService.put(Constants.LOGIN_FAILURE_MAP, loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(username);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (fail) {
            loginFailNum++;
            loginFailMap.put(username, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(username);
        }
        return loginFailNum >= 3;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(String name, String pass, HttpSession session) {
        /* 校验登录验证码 *
        if (isLoginWithValidateCode(token.getName(), false, false)) {
			String code = (String) request.getSession().getAttribute(ImageServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}
        */
        JsonMap json = JsonMap.fail("用户名输入错误或未注册.");
        User user;

        if (StringUtil.isEmail(name)) {
            user = userService.getByEmail(name);
            json.msg("邮箱帐号输入错误或未注册.");
        } else if (StringUtil.isMobile(name)) {
            user = userService.getByPhone(name);
            json.msg("手机号码输入错误或未注册.");
        } else {
            user = userService.getByName(name);
        }
        if (user == null) {
            return json.msg("用户不存在！");
        }
        if (!StringUtil.md5(pass, user.getName()).equalsIgnoreCase(user.getPass())) {
            return json.msg("密码输入不正确!");
        }
        if (Constants.USER_STATUS_LOCKED == user.getStatus()) {
            return json.msg("用户[" + name + "]账户已被锁定, 禁止登录");
        }
        List<String> permissions = userService.findPermissionByUser(user);
        session.setAttribute("permissions", permissions);
        session.setAttribute(Constants.SESSION_USER_KEY, user);
        userService.updateVisited(user.getId());
        user.setPass(null);
        return json.success(true).put("user", user);
    }


    @RequestMapping("logout")
    public Object logout(HttpSession session) throws Exception {
        session.removeAttribute(Constants.SESSION_USER_KEY);
        return JsonMap.succeed();
    }

    @RequestMapping(value = "unlock", method = RequestMethod.POST)
    public Object unlock(String password, HttpServletRequest request) {
        JsonMap json = JsonMap.fail(null);
        User user = getCurrentUser(request);
        if (user == null)
            json.put("expired", true);
        else if (!StringUtil.md5(password, user.getName()).equals(user.getPass()))
            json.put("msg", "密码不正确 !");
        else {
            request.getSession().removeAttribute(Constants.USER_SCREEN_LOCKED);
            json.put("success", true);
        }
        return json;
    }


    @RequestMapping("exists")
    public Object exists(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "phone", required = false) String phone,
                         @RequestParam(value = "email", required = false) String email,
                         HttpServletResponse response) {
        User user = null;
        String msg = "用户名已被注册!";
        if (StringUtil.isNotBlank(name)) {
            user = userService.getByName(name);
        } else if (StringUtil.isNotBlank(email)) {
            user = userService.getByEmail(email);
            msg = "邮箱已被注册";
        } else if (StringUtil.isNotBlank(phone)) {
            user = userService.getByPhone(phone);
            msg = "手机号已被注册";
        }
        boolean valid = user == null;
        JsonMap json = new JsonMap();
        json.put("valid", valid);
        if (!valid) {
            json.msg(msg);
        }
        // Set response result for VeeValidate
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate"); // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT"); // Set to expire far in the past.
        response.setHeader("Pragma", "no-cache"); // Set standard HTTP/1.0 no-cache header.
        return json;
    }


    /* 重置密码 */
    @RequestMapping(value = "forgot", method = RequestMethod.POST)
    public Object forgot(HttpSession session,
                         @RequestParam(value = "email", required = true) String email,
                         @RequestParam(value = "kaptcha", required = false) String kaptcha) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> err = new HashMap<>();
        Object sessionKaptcha = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtil.isBlank(email)) {
            err.put("email", "请输入邮箱帐号.");
        } else if (sessionKaptcha != null && !sessionKaptcha.equals(kaptcha)) {
            err.put("kaptcha", "验证码错误.");
        } else {
            User user = userService.getByEmail(email);
            if (user == null)
                err.put("email", "您输入的邮箱不存在.");
            else {
                try {
                    // 发送邮件
                    saveRecordAndSendEmail(user);
                    map.put("loginUrl", getEmailLoginUrl(email));
                    map.put("success", true);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", e.getMessage() + "邮件发送出错, 请稍后重试.");
                }
            }
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }
        if (err.size() > 0)
            map.put("errors", err);
        return map;
    }

    /**
     * 根据邮箱域名获得登录地址
     *
     * @param email 邮箱域名
     * @return 登录地址，找不到返回null
     */
    private String getEmailLoginUrl(String email) {
        String arr[] = email.split("@");
        if (arr.length != 2) return null;
        Dict dict = dictService.findMapByCode("email_login_url").get(arr[1]);
        return dict == null ? null : dict.getValue();
    }

    /**
     * 保存申请记录并发送密码重置链接邮件
     *
     * @param user 申请用户
     */
    private void saveRecordAndSendEmail(User user) throws Exception {
        ResetRecord resetRecord = userService.saveResetResord(user);
        String email = resetRecord.getEmail();
        String resetUrl = String.format("%s?email=%s&key=%s", Configuration.PASSWORD_RESET_URL, email, resetRecord.getResetKey());
        EmailUtil.sendEmail(
                String.format("来自%s的密码重置邮件", Configuration.APP_NAME),
                MessageFormat.format(Configuration.PASSWORD_RESET_EMAIL_TEMPLATE, email, resetUrl),
                email
        );
    }

    @RequestMapping("password_reset")
    public String passwordReset(RedirectAttributes redirectAttributes,
                                @RequestParam("email") String email,
                                @RequestParam("key") String key) throws Exception {
        JsonMap json = JsonMap.succeed();
        boolean valid = userService.isResetRecordValid(email, key);
        if (!valid) {
            redirectAttributes.addFlashAttribute("invalid", true);
        }
        json.put("email", email);
        return valid ? "reset" : "redirect:/guest/forgot";
    }


    @RequestMapping(value = "password_reset", method = RequestMethod.POST)
    public Object passwordReset(@RequestParam("email") String email,
                                @RequestParam("key") String key,
                                @RequestParam("password") String password,
                                @RequestParam("password_again") String passwordAgain) throws Exception {
        Map<String, Object> json = new HashMap<>();
        if (StringUtil.isBlank(password))
            json.put("msg", "密码不能为空或全为空格.");
        else if (!password.equals(passwordAgain))
            json.put("msg", "两次输入的密码不一致.");
        else if (password.length() < 5 || password.length() > 15)
            json.put("msg", "密码长度应为5至15位.");
        else {
            boolean valid = userService.isResetRecordValid(email, key);
            if (valid) {
                userService.resetPass(email, email, password, key);
                json.put("success", true);
            } else
                json.put("msg", "重置密码参数错误或已过期, 请重新申请重置.");
        }
        return json;
    }

    @RequestMapping("reload-config")
    public Object reloadConfig() {
        try {
            Configuration.reload();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return JsonMap.fail(e.getMessage());
        }
        return JsonMap.succeed().msg("系统配置重载成功!");
    }


    @ResponseBody
    @RequestMapping("clean-cache")
    public Object cleanCache(@RequestParam(value = "name", required = false, defaultValue = Constants.SYS_CACHE) String name) {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (name == null) {
                cache.clear();
                continue;
            }
            if (cacheName.equalsIgnoreCase(name)) {
                cache.clear();
                break;
            }
        }
        return JsonMap.succeed().msg("系统缓存清理成功!");
    }
}

