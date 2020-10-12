package app.service;

import org.springframework.stereotype.Service;

import weixin.core.message.BaseRespMessage;
import weixin.core.message.ReqVoiceMessage;
import weixin.core.message.RespTextMessage;
import weixin.core.util.MessageUtil;

/**
 * 处理语音
 */
@Service
public class VoiceMessageService implements MessageService<ReqVoiceMessage> {

	@Override
	public BaseRespMessage service(ReqVoiceMessage reqMessage) {
		RespTextMessage textMessage = new RespTextMessage();

		textMessage.setToUserName(reqMessage.getFromUserName());
		textMessage.setFromUserName(reqMessage.getToUserName());
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);

		textMessage.setContent("这是语音消息/::)\n媒体ID:" + reqMessage.getMediaId());

		return textMessage;
	}

}
