package com.handy.service.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.sys.SysRolesAccount;
import com.handy.service.mapper.sys.SysRolesAccountMapper;
import com.handy.service.service.sys.ISysRolesAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRolesAccountServiceImpl extends ServiceImpl<SysRolesAccountMapper, SysRolesAccount> implements ISysRolesAccountService {
    @Autowired
    private SysRolesAccountMapper sysRolesAccountMapper;

    /**
     * 根据用户id查询对应角色
     *
     * @param accountId
     * @return
     */
    @Override
    public List<SysRolesAccount> findByAccountId(Long accountId) {
        val sysRolesAccount = new SysRolesAccount();
        sysRolesAccount.setAccountId(accountId);
        return sysRolesAccountMapper.selectList(getWrapper(sysRolesAccount));
    }

    private QueryWrapper getWrapper(SysRolesAccount sysRolesAccount) {
        val wrapper = new QueryWrapper<SysRolesAccount>();
        LambdaQueryWrapper<SysRolesAccount> queryWrapper = wrapper.lambda();
        if (sysRolesAccount != null) {
            if (sysRolesAccount.getAccountId() != null) {
                queryWrapper.eq(SysRolesAccount::getAccountId, sysRolesAccount.getAccountId());
            }
        }
        return wrapper;
    }
}
