package com.handy.controller.entry;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.handy.common.vo.ResultVO;
import com.handy.service.entity.sys.SysAccount;
import com.handy.service.service.sys.ISysAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author handy
 * @Description: {首页用户管理}
 * @date 2019/8/26 11:56
 */
@RestController
@RequestMapping("/api/entry/userSetting")
@Api(value = "首页用户管理")
public class UserSettingApiController {
    @Autowired
    private ISysAccountService sysAccountService;

    @PutMapping("/edit")
    @ApiOperation(value = "用户信息更新")
    public ResultVO edit(SysAccount sysAccount) {
        val rst = sysAccountService.updateById(sysAccount);
        return rst ? ResultVO.success("更新成功") : ResultVO.success("更新失败");
    }

    @PutMapping("/edit/password")
    @ApiOperation(value = "密码更新")
    public ResultVO editPassword(@ApiParam(name = "id", value = "ID") Long id,
                                 @ApiParam(name = "oldPassword", value = "旧密码") String oldPassword,
                                 @ApiParam(name = "password", value = "新密码") String password,
                                 @ApiParam(name = "passwordTwo", value = "重复新密码") String passwordTwo) {
        if (password != null && !password.equals(passwordTwo)) {
            return ResultVO.failure("俩次密码不一致");
        }
        val wrapper = new QueryWrapper<SysAccount>();
        LambdaQueryWrapper<SysAccount> queryWrapper = wrapper.lambda();
        queryWrapper.eq(SysAccount::getId, id).eq(SysAccount::getPassword, SecureUtil.md5(oldPassword));
        SysAccount sysAccount = sysAccountService.getOne(wrapper);
        if (sysAccount == null) {
            return ResultVO.failure("旧密码填写错误");
        }
        sysAccount.setPassword(SecureUtil.md5(password));
        val rst = sysAccountService.updateById(sysAccount);
        return rst ? ResultVO.success("更改密码成功") : ResultVO.success("更改密码失败");
    }
}
