package com.handy.service.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.sys.SysAccount;
import com.handy.service.mapper.sys.SysAccountMapper;
import com.handy.service.service.sys.ISysAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {
    @Autowired
    private SysAccountMapper sysAccountMapper;

    /**
     * 登录
     *
     * @param sysAccount
     * @return
     */
    @Override
    public SysAccount login(SysAccount sysAccount) {
        return sysAccountMapper.selectOne(getWrapper(sysAccount));
    }

    /**
     * 注册
     *
     * @param sysAccount
     * @return
     */
    @Override
    public SysAccount register(SysAccount sysAccount) {
        val rst = sysAccountMapper.insert(sysAccount);
        if (rst > 0) {
            val account = sysAccountMapper.selectById(sysAccount.getId());
            return account;
        }
        return null;
    }

    private QueryWrapper getWrapper(SysAccount sysAccount) {
        val wrapper = new QueryWrapper<SysAccount>();
        LambdaQueryWrapper<SysAccount> queryWrapper = wrapper.lambda();
        if (sysAccount != null) {
            if (StrUtil.isNotBlank(sysAccount.getCode())) {
                queryWrapper.eq(SysAccount::getCode, sysAccount.getCode());
            }
            if (StrUtil.isNotBlank(sysAccount.getPassword())) {
                queryWrapper.eq(SysAccount::getPassword, sysAccount.getPassword());
            }
        }
        return wrapper;
    }
}
