package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.AclModule;
import com.cdh.springboot.mapper.AclModuleMapper;
import com.cdh.springboot.service.IAclModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限模块表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class AclModuleServiceImpl extends ServiceImpl<AclModuleMapper, AclModule> implements IAclModuleService {

}
