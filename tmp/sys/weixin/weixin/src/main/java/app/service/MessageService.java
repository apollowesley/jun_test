package app.service;

import weixin.core.message.BaseReqMessage;
import weixin.core.message.BaseRespMessage;

public interface MessageService<ReqMessage extends BaseReqMessage> {
	BaseRespMessage service(ReqMessage reqMessage);
}
