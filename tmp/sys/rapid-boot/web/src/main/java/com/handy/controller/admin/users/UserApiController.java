package com.handy.controller.admin.users;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.common.vo.PageVO;
import com.handy.common.vo.ResultVO;
import com.handy.service.entity.sys.SysAccount;
import com.handy.service.entity.sys.SysRolesAccount;
import com.handy.service.service.sys.ISysAccountService;
import com.handy.service.service.sys.ISysRolesAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author handy
 * @Description: {后台管理用户}
 * @date 2019/8/26 11:56
 */
@RestController
@RequestMapping("/api/admin/users/user")
@Api(value = "用户管理")
public class UserApiController {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private ISysRolesAccountService sysRolesAccountService;

    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public PageVO list(SysAccount sysAccount,
                       @ApiParam(name = "page", value = "每页页数") Long page,
                       @ApiParam(name = "limit", value = "每页条数") Long limit) {
        val wrapper = new QueryWrapper<SysAccount>();
        if (sysAccount != null && StrUtil.isNotBlank(sysAccount.getCode())) {
            LambdaQueryWrapper<SysAccount> queryWrapper = wrapper.lambda();
            queryWrapper.eq(SysAccount::getCode, sysAccount.getCode());
        }
        IPage<SysAccount> sysAccountIPage = sysAccountService.page(new Page<>(page, limit), wrapper);
        return PageVO.pageVO(sysAccountIPage.getRecords(), sysAccountIPage.getTotal());
    }

    @PostMapping("/confirm/add")
    @ApiOperation(value = "用户新增")
    public ResultVO confirmAdd(SysAccount sysAccount) {
        sysAccount.setIsLocked(false);
        val rst = sysAccountService.save(sysAccount);
        // 添加对应角色
        if (rst && StrUtil.isNotBlank(sysAccount.getRoles())) {
            val sysRolesAccount = new SysRolesAccount();
            sysRolesAccount.setAccountId(sysAccount.getId());
            String[] roles = StrUtil.split(sysAccount.getRoles(), ",");
            for (String roleId : roles) {
                sysRolesAccount.setRoleId(Long.valueOf(roleId));
                sysRolesAccountService.save(sysRolesAccount);
            }
        }
        return rst ? ResultVO.success("新增成功") : ResultVO.success("新增失败");
    }

    @PutMapping("/confirm/edit")
    @ApiOperation(value = "用户编辑")
    public ResultVO confirmEdit(SysAccount sysAccount) {
        val rst = sysAccountService.updateById(sysAccount);
        // 添加对应角色
        if (rst) {
            // 删除旧角色
            val wrapper = new QueryWrapper<SysRolesAccount>();
            LambdaQueryWrapper<SysRolesAccount> queryWrapper = wrapper.lambda();
            queryWrapper.eq(SysRolesAccount::getAccountId, sysAccount.getId());
            sysRolesAccountService.remove(wrapper);
            // 添加新角色
            String[] roles = StrUtil.split(sysAccount.getRoles(), ",");
            if (roles != null && roles.length > 0) {
                val sysRolesAccount = new SysRolesAccount();
                sysRolesAccount.setAccountId(sysAccount.getId());
                for (String roleId : roles) {
                    sysRolesAccount.setRoleId(Long.valueOf(roleId));
                    sysRolesAccountService.save(sysRolesAccount);
                }
            }
        }
        return rst ? ResultVO.success("更新成功") : ResultVO.success("更新失败");
    }

    @PutMapping("/confirm/edit/locked")
    @ApiOperation(value = "用户锁定状态更改")
    public ResultVO confirmEditLocked(SysAccount sysAccount) {
        val rst = sysAccountService.updateById(sysAccount);
        return rst ? ResultVO.success("更新状态成功") : ResultVO.success("更新状态失败");
    }

    @DeleteMapping("/confirm/delete/{id}")
    @ApiOperation(value = "用户删除")
    public ResultVO confirmDelete(@ApiParam(name = "id", value = "用户id") Long id) {
        val rst = sysAccountService.removeById(id);
        // 删除角色
        if (rst) {
            val wrapper = new QueryWrapper<SysRolesAccount>();
            LambdaQueryWrapper<SysRolesAccount> queryWrapper = wrapper.lambda();
            queryWrapper.eq(SysRolesAccount::getAccountId, id);
            sysRolesAccountService.remove(wrapper);
        }
        return rst ? ResultVO.success("删除成功") : ResultVO.success("删除失败");
    }


}
