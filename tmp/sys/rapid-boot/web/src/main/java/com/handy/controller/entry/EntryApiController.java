package com.handy.controller.entry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.handy.captcha.CaptchaEnum;
import com.handy.captcha.CaptchaUtil;
import com.handy.common.annotation.AddLoginLog;
import com.handy.common.constants.Constants;
import com.handy.common.constants.LogLoginCategoryEnum;
import com.handy.common.vo.ResultVO;
import com.handy.controller.BaseController;
import com.handy.service.entity.msg.MsgRecord;
import com.handy.service.entity.sys.SysAccount;
import com.handy.service.entity.sys.SysPermission;
import com.handy.service.entity.sys.SysRolesAccount;
import com.handy.service.service.log.ILogLoginService;
import com.handy.service.service.msg.IMsgRecordService;
import com.handy.service.service.sys.ISysAccountService;
import com.handy.service.service.sys.ISysPermissionService;
import com.handy.service.service.sys.ISysResourceService;
import com.handy.service.service.sys.ISysRolesAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author handy
 * @Description: {登录注册模块}
 * @date 2019/8/22 14:30
 */
@RestController
@RequestMapping("/api/entry/entry")
@Api(value = "登录注册模块")
public class EntryApiController extends BaseController {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private ISysRolesAccountService sysRolesAccountService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysResourceService sysResourceService;
    @Autowired
    private IMsgRecordService msgRecordService;
    @Autowired
    private ILogLoginService logLoginService;
    @Resource(name = "CaptchaUtil")
    private CaptchaUtil captchaUtil;

    @GetMapping("/login")
    @ApiOperation(value = "密码登录")
    @AddLoginLog(logLoginCategory = LogLoginCategoryEnum.PASSWORD_LOGIN)
    public ResultVO login(HttpServletRequest request,
                          @ApiParam(name = "username", value = "用户名") String username,
                          @ApiParam(name = "password", value = "密码") String password) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultVO.failure("登录帐号和密码不能为空");
        }
        val sysAccount = new SysAccount();
        sysAccount.setCode(username).setPassword(SecureUtil.md5(password));
        val user = sysAccountService.login(sysAccount);
        if (user != null) {
            if (user.getIsLocked()) {
                return ResultVO.failure("该帐号已经被锁定,无法进行登录");
            }
            //用户的资源存session
            setUserResource(request.getSession(), user);
            setLoginCount(user);
            return ResultVO.success("登录成功", "/index");
        } else {
            return ResultVO.failure("该帐号并没有注册,请先去注册后在登录");
        }
    }

    @GetMapping("/captchaLogin")
    @ApiOperation(value = "验证码登录")
    @AddLoginLog(logLoginCategory = LogLoginCategoryEnum.CAPTCHA_LOGIN)
    public ResultVO captchaLogin(HttpServletRequest request,
                                 @ApiParam(name = "captchaLoginUsername", value = "用户名") String captchaLoginUsername,
                                 @ApiParam(name = "captcha", value = "验证码") String captcha) {
        if (StrUtil.isBlank(captchaLoginUsername) || StrUtil.isBlank(captcha)) {
            return ResultVO.failure("登录帐号和验证码不能为空");
        }
        val wrapper = new QueryWrapper<MsgRecord>();
        LambdaQueryWrapper<MsgRecord> queryWrapper = wrapper.lambda();
        queryWrapper.eq(MsgRecord::getMobile, captchaLoginUsername).eq(MsgRecord::getContent, captcha)
                .eq(MsgRecord::getCategory, CaptchaEnum.TEMPLATE_LOGIN.getId())
                .eq(MsgRecord::getResult, true)
                .gt(MsgRecord::getSentTime, DateUtil.offsetMinute(new Date(), -10));
        val msgRecord = msgRecordService.getOne(wrapper);
        if (msgRecord == null) {
            return ResultVO.failure("验证码已过期,请重新发送验证码");
        }
        val sysAccount = new SysAccount();
        sysAccount.setCode(captchaLoginUsername);
        val user = sysAccountService.login(sysAccount);
        if (user != null) {
            if (user.getIsLocked()) {
                return ResultVO.failure("该帐号已经被锁定,无法进行登录");
            }
            //用户的资源存session
            setUserResource(request.getSession(), user);
            setLoginCount(user);
            return ResultVO.success("登录成功", "/index");
        } else {
            return ResultVO.failure("该帐号并没有注册,请先去注册后在登录");
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @AddLoginLog(logLoginCategory = LogLoginCategoryEnum.REGISTER_LOGIN)
    public ResultVO register(HttpServletRequest request,
                             @ApiParam(name = "registerUsername", value = "用户名") String registerUsername,
                             @ApiParam(name = "password", value = "密码") String password,
                             @ApiParam(name = "captcha", value = "验证码") String captcha) {
        if (StrUtil.isBlank(registerUsername) || StrUtil.isBlank(password) || StrUtil.isBlank(captcha)) {
            return ResultVO.failure("必填项不能为空");
        }
        val sysAccountWrapper = new QueryWrapper<SysAccount>();
        LambdaQueryWrapper<SysAccount> sysAccountQueryWrapper = sysAccountWrapper.lambda();
        sysAccountQueryWrapper.eq(SysAccount::getCode, registerUsername);
        val sysAccountList = sysAccountService.list(sysAccountWrapper);
        if (CollUtil.isNotEmpty(sysAccountList)) {
            return ResultVO.failure("该手机号已经被注册");
        }
        val wrapper = new QueryWrapper<MsgRecord>();
        LambdaQueryWrapper<MsgRecord> queryWrapper = wrapper.lambda();
        queryWrapper.eq(MsgRecord::getMobile, registerUsername).eq(MsgRecord::getContent, captcha)
                .eq(MsgRecord::getCategory, CaptchaEnum.TEMPLATE_REG.getId())
                .eq(MsgRecord::getResult, true)
                .gt(MsgRecord::getSentTime, DateUtil.offsetMinute(new Date(), -10));
        val msgRecord = msgRecordService.getOne(wrapper);
        if (msgRecord == null) {
            return ResultVO.failure("验证码错误或者已过期,请重新发送验证码");
        }
        val sysAccount = new SysAccount();
        sysAccount.setCode(registerUsername)
                .setName(registerUsername)
                .setPassword(SecureUtil.md5(password))
                .setIsLocked(false)
                .setLoginCount(1L)
                .setLastLoginTime(new Date())
                .setCreator(registerUsername);
        val user = sysAccountService.register(sysAccount);
        if (user != null) {
            //用户的资源存session
            setUserResource(request.getSession(), user);
        }
        return ResultVO.success("注册成功", "/index");
    }

    /**
     * 用户登出
     *
     * @param session session
     * @return rst
     */
    @GetMapping("/logout")
    @ApiOperation(value = "用户登出")
    public ResultVO logout(@ApiIgnore() HttpSession session) {
        session.invalidate();
        return ResultVO.success("退出登录成功");
    }

    @PostMapping("/send/captcha")
    @ApiOperation(value = "发送验证码登录")
    public ResultVO sendCaptcha(@ApiParam(name = "mobile", value = "用户名") String mobile) {
        val code = RandomUtil.randomInt(100000, 999999);
        JSONObject codeJson = JSONUtil.createObj();
        codeJson.put("code", code);
        val response = captchaUtil.getCaptcha(mobile, codeJson.toString(), CaptchaEnum.TEMPLATE_LOGIN);
        Boolean rst = false;
        if (response != null) {
            val msgRecord = new MsgRecord();
            msgRecord.setCategory(CaptchaEnum.TEMPLATE_LOGIN.getId());
            msgRecord.setCategoryName(CaptchaEnum.TEMPLATE_LOGIN.getName());
            msgRecord.setMobile(mobile);
            msgRecord.setContent(String.valueOf(code));
            msgRecord.setSentTime(new Date());
            Boolean result = false;
            if ("OK".equals(response.getCode())) {
                result = true;
            }
            msgRecord.setResult(result);
            msgRecord.setResultDesc(JSONUtil.toJsonStr(response));
            msgRecord.setCreateTime(new Date());
            rst = msgRecordService.save(msgRecord);
            if (!result) {
                return ResultVO.failure("您今日可发短信已到上限!");
            }
        }
        return rst ? ResultVO.success("发送短信成功") : ResultVO.failure("发送短信异常");
    }

    @PostMapping("/send/register")
    @ApiOperation(value = "发送注册验证码")
    public ResultVO sendRegister(@ApiParam(name = "mobile", value = "用户名") String mobile) {
        val code = RandomUtil.randomInt(100000, 999999);
        JSONObject codeJson = JSONUtil.createObj();
        codeJson.put("code", code);
        val response = captchaUtil.getCaptcha(mobile, codeJson.toString(), CaptchaEnum.TEMPLATE_REG);
        Boolean rst = false;
        if (response != null) {
            val msgRecord = new MsgRecord();
            msgRecord.setCategory(CaptchaEnum.TEMPLATE_REG.getId());
            msgRecord.setCategoryName(CaptchaEnum.TEMPLATE_REG.getName());
            msgRecord.setMobile(mobile);
            msgRecord.setContent(String.valueOf(code));
            msgRecord.setSentTime(new Date());
            Boolean result = false;
            if ("OK".equals(response.getCode())) {
                result = true;
            }
            msgRecord.setResult(result);
            msgRecord.setResultDesc(JSONUtil.toJsonStr(response));
            msgRecord.setCreateTime(new Date());
            rst = msgRecordService.save(msgRecord);
            if (!result) {
                return ResultVO.failure("您今日可发短信已到上限!");
            }
        }
        return rst ? ResultVO.success("发送短信成功") : ResultVO.failure("发送短信异常");
    }

    /**
     * 设置登录权限
     *
     * @param session
     * @param sysAccount
     */
    private void setUserResource(HttpSession session, SysAccount sysAccount) {
        session.setAttribute(Constants.USERSESSION, sysAccount);
        session.setMaxInactiveInterval(Constants.SESSION_TIME_OUT);
        // 用户角色
        val sysRolesAccountList = sysRolesAccountService.findByAccountId(sysAccount.getId());
        List<Long> roleIdList = new ArrayList<>();
        for (SysRolesAccount sysRolesAccount : sysRolesAccountList) {
            roleIdList.add(sysRolesAccount.getRoleId());
        }
        if (roleIdList.size() == 0) {
            return;
        }
        // 角色权限
        val sysPermissionList = sysPermissionService.findByRoleIdList(roleIdList);
        List<Long> idList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            idList.add(sysPermission.getSubmenuId());
        }
        if (idList.size() == 0) {
            return;
        }
        // 权限
        val sysResourceList = sysResourceService.findByIdList(idList);
        session.setAttribute(Constants.USERRESOURCEKEY, sysResourceList);
    }

    /**
     * 设置登录次数和时间
     *
     * @param sysAccount
     */
    private void setLoginCount(SysAccount sysAccount) {
        sysAccount.setLoginCount(sysAccount.getLoginCount() != null ? sysAccount.getLoginCount() + 1 : 1).setLastLoginTime(new Date());
        sysAccountService.updateById(sysAccount);
    }
}
