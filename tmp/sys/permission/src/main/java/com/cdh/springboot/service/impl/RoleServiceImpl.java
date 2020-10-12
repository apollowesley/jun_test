package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.Role;
import com.cdh.springboot.mapper.RoleMapper;
import com.cdh.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
