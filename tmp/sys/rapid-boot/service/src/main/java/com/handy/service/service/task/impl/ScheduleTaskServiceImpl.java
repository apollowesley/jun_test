package com.handy.service.service.task.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.task.ScheduleTask;
import com.handy.service.mapper.task.ScheduleTaskMapper;
import com.handy.service.service.task.IScheduleTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author handy
 * @since 2019-09-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleTaskServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTask> implements IScheduleTaskService {

}
