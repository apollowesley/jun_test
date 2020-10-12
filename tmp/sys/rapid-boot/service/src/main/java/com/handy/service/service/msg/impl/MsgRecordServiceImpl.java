package com.handy.service.service.msg.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.msg.MsgRecord;
import com.handy.service.mapper.msg.MsgRecordMapper;
import com.handy.service.service.msg.IMsgRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/28 14:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgRecordServiceImpl extends ServiceImpl<MsgRecordMapper, MsgRecord> implements IMsgRecordService {
}
