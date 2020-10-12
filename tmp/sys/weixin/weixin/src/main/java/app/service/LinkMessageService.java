package app.service;

import org.springframework.stereotype.Service;

import weixin.core.message.BaseRespMessage;
import weixin.core.message.ReqLinkMessage;
import weixin.core.message.RespTextMessage;
import weixin.core.util.MessageUtil;

/**
 * 处理连接
 */
@Service
public class LinkMessageService implements MessageService<ReqLinkMessage> {

	@Override
	public BaseRespMessage service(ReqLinkMessage reqMessage) {
		RespTextMessage textMessage = new RespTextMessage();

		textMessage.setToUserName(reqMessage.getFromUserName());
		textMessage.setFromUserName(reqMessage.getToUserName());
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);

		textMessage.setContent("这是链接消息/::)\n您发的连接为:" + reqMessage.getUrl());

		return textMessage;
	}

}
