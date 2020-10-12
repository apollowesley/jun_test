package com.handy.service.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.sys.SysRole;
import com.handy.service.mapper.sys.SysRoleMapper;
import com.handy.service.service.sys.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/25 17:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
}
