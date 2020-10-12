package com.jfast.web.admin.controller;

import com.jfast.common.annotation.Param;
import com.jfast.common.annotation.ParamsType;
import com.jfast.common.annotation.ParamsValidate;
import com.jfast.common.base.BaseController;
import com.jfast.common.constants.Constants;
import com.jfast.common.model.JwtToken;
import com.jfast.common.model.UserSession;
import com.jfast.common.utils.ResultCode;
import com.jfast.web.admin.service.system.SystemAdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/12 20:38
 */
@RestController
@RequestMapping("/system")
public class LoginController extends BaseController {

    @Autowired
    private SystemAdminService systemAdminService;
    private final JwtToken jwtToken = JwtToken.getJwtToken(Constants.SECRET_KEY);

    /**
     * 管理员登录
     * @param params
     * @return
     */
    @ParamsValidate(params = {
        @Param(name = "userName", message = "用户名不能为空"),
        @Param(name = "password", message = "密码不能为空"),
        @Param(name = "code", message = "验证码不能为空"),
    }, paramsType = ParamsType.JSON_DATA)
    @PostMapping("login")
    @ApiOperation(value="登录接口", notes="用户登录接口")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    public Map login(HttpSession session, @RequestBody Map params) {
        String loginName = (String)params.get("userName");
        String password = (String)params.get("password");
        String code = (String)params.get("code");
        String sessionCode = (String)session.getAttribute(Constants.IMAGE_CODE);
        if (!code.equalsIgnoreCase(sessionCode)) {
            params.put("code", ResultCode.FAIL);
            params.put("message", "验证码错误");
            return params;
        }
        ResultCode resultCode = systemAdminService.login(loginName, password);
        if (resultCode.getCode() == ResultCode.SUCCESS) { // 登录成功 加载用户权限
            String token = jwtToken.createToken(systemAdminService.getUserId(), 24 * 60 * 60 * 1000 * 7); // 默认缓存7天
            UserSession userSession = systemAdminService.getUserSession();
            systemAdminService.loadPermission(userSession);
            params.put("token", token);
            Map userInfo = new HashMap<>();
            userInfo.put("id", userSession.getUserId());
            userInfo.put("login_name", userSession.getUserMap().get("login_name"));
            params.put("name", userSession.getUserMap().get("name"));
            params.put("userInfo", userInfo);
            params.put("userPermission", userSession.getPermissionList());
            systemAdminService.saveSystemLog(loginName + "登录系统");
        }
        params.put("code", resultCode.getCode());
        params.put("message", resultCode.getMessage());
        return params;
    }

    @GetMapping("unAuth")
    public ResultCode unAuth() {
        return new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "用户未认证");
    }

    /**
     * 系统退出
     * @return
     */
    @PostMapping("logout")
    public ResultCode logout() {
        systemAdminService.saveSystemLog(systemAdminService.getUser().get("login_name") + "退出系统");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultCode(ResultCode.SUCCESS, "退出成功");
    }

}
