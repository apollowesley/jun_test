package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.Dept;
import com.cdh.springboot.mapper.DeptMapper;
import com.cdh.springboot.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
