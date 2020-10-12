package weixin.core.message;

import java.util.HashMap;
import java.util.Map;

import weixin.core.message.BaseReqMessage;
import weixin.core.message.ReqTextMessage;
import weixin.core.util.MessageUtil;
import weixin.core.util.ReflectionUtils;

/**
 * 微信消息工厂,根据微信发来的请求构建不同的消息
 */
public class ReqMessageFactory {
	
	private static final String MSG_TYPE_NAME = "MsgType";
	private static Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	
	static {
		classMap.put(MessageUtil.REQ_MESSAGE_TYPE_TEXT, ReqTextMessage.class);
		classMap.put(MessageUtil.REQ_MESSAGE_TYPE_IMAGE, ReqImageMessage.class);
		classMap.put(MessageUtil.REQ_MESSAGE_TYPE_LINK, ReqLinkMessage.class);
		classMap.put(MessageUtil.REQ_MESSAGE_TYPE_LOCATION, ReqLocationMessage.class);
		classMap.put(MessageUtil.REQ_MESSAGE_TYPE_VOICE, ReqVoiceMessage.class);
	}
	
	public static <T extends BaseReqMessage> T build(Class<T> clazz,
			Map<String, String> xmlMap) {
		return ReflectionUtils.convertMap2Obj(clazz, xmlMap);
	}
	
	/**
	 * 将微信发来的请求解析成消息对象
	 * @param xmlMap
	 * @return 返回消息对象
	 */
	public static BaseReqMessage build(Map<String, String> xmlMap) {
		String msgType = xmlMap.get(MSG_TYPE_NAME);
		Class<?> clazz = classMap.get(msgType);
		return (BaseReqMessage) ReflectionUtils.convertMap2Obj(clazz, xmlMap);
	}
	
}
