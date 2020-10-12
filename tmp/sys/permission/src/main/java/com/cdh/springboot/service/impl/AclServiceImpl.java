package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.Acl;
import com.cdh.springboot.mapper.AclMapper;
import com.cdh.springboot.service.IAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class AclServiceImpl extends ServiceImpl<AclMapper, Acl> implements IAclService {

}
