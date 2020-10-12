package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.RoleUser;
import com.cdh.springboot.mapper.RoleUserMapper;
import com.cdh.springboot.service.IRoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色用户关系表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements IRoleUserService {

}
