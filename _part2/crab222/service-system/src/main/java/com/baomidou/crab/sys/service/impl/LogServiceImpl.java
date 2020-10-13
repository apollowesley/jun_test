package com.baomidou.crab.sys.service.impl;

import com.baomidou.crab.sys.entity.Log;
import com.baomidou.crab.sys.mapper.LogMapper;
import com.baomidou.crab.sys.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-06
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
