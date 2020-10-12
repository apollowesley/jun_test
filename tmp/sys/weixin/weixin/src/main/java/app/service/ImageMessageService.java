package app.service;

import org.springframework.stereotype.Service;

import weixin.core.message.BaseRespMessage;
import weixin.core.message.ReqImageMessage;
import weixin.core.message.RespTextMessage;
import weixin.core.util.MessageUtil;

/**
 * 处理图片
 */
@Service
public class ImageMessageService implements MessageService<ReqImageMessage> {

	@Override
	public BaseRespMessage service(ReqImageMessage reqMessage) {
		RespTextMessage textMessage = new RespTextMessage();

		textMessage.setToUserName(reqMessage.getFromUserName());
		textMessage.setFromUserName(reqMessage.getToUserName());
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);

		textMessage.setContent("这是图片消息/::)\n图片地址:" + reqMessage.getPicUrl());

		return textMessage;
	}

}
