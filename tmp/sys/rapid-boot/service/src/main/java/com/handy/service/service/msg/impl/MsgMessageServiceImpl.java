package com.handy.service.service.msg.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.msg.MsgMessage;
import com.handy.service.mapper.msg.MsgMessageMapper;
import com.handy.service.service.msg.IMsgMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author handy
 * @since 2019-09-06
 */
@Service("msgMessageService")
@Transactional(rollbackFor = Exception.class)
public class MsgMessageServiceImpl extends ServiceImpl<MsgMessageMapper, MsgMessage> implements IMsgMessageService {

}
