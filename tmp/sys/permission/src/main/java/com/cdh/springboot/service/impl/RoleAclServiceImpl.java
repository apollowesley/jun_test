package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.RoleAcl;
import com.cdh.springboot.mapper.RoleAclMapper;
import com.cdh.springboot.service.IRoleAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class RoleAclServiceImpl extends ServiceImpl<RoleAclMapper, RoleAcl> implements IRoleAclService {

}
