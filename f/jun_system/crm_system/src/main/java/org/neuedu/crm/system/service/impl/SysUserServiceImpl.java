package org.neuedu.crm.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.neuedu.crm.system.entity.SysUser;
import org.neuedu.crm.system.mapper.SysUserMapper;
import org.neuedu.crm.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService, UserDetailsService {

    @Override
    public List<SysUser> findUserByRoleId(Integer roleId) {
        return baseMapper.findUserByRoleId(roleId);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return baseMapper.findUserDetailByLoginName(s);
    }
}
