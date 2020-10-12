package app.service;

import org.springframework.stereotype.Service;

import weixin.core.message.BaseRespMessage;
import weixin.core.message.ReqLocationMessage;
import weixin.core.message.RespTextMessage;
import weixin.core.util.MessageUtil;

/**
 * 处理地理位置
 */
@Service
public class LocationMessageService implements MessageService<ReqLocationMessage> {

	@Override
	public BaseRespMessage service(ReqLocationMessage reqMessage) {
		RespTextMessage textMessage = new RespTextMessage();

		textMessage.setToUserName(reqMessage.getFromUserName());
		textMessage.setFromUserName(reqMessage.getToUserName());
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent("这是地理位置消息/::),\n您所在的位置为X:" + reqMessage.getLocation_X() + ",Y:" + reqMessage.getLocation_Y());

		return textMessage;
	}

}
