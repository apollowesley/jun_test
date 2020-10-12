package tom.net.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tom.net.Session;

public class MessageAdaptorServer extends MessageAdaptor {
	protected Map<String, MessageHandler> handlerMap = new ConcurrentHashMap<String, MessageHandler>();
	
	{
		this.registerHandler(HttpMessage.HEARTBEAT, new MessageHandler() { 
			public void handleMessage(HttpMessage msg, Session sess) throws IOException { 
				//ignore
				//System.out.println("HEARTBEAT=="+sess.id());
			}
		});
	}
	

	public void registerHandler(String command, MessageHandler handler) {
		this.handlerMap.put(command, handler);
	}

	public String findHandlerKey(HttpMessage msg) {
		return msg.getCommand();
	}

	public void onMessage(Object obj, Session sess) throws IOException {
		HttpMessage msg = (HttpMessage) obj;
		if(msg.isStatus200()){  // 200 server 不处理
			return;
		}
		
		String cmd = findHandlerKey(msg);
		if (cmd == null) {
			HttpMessage res = new HttpMessage();
			res.setMsgId(msg.getMsgId());
			res.setStatus("400");
			res.setBody("Bad format: missing command");
			sess.write(res);
			return;
		}

		MessageHandler handler = handlerMap.get(cmd);
		if (handler != null) {
			handler.handleMessage(msg, sess);
			return;
		}

		HttpMessage res = new HttpMessage();
		res.setMsgId(msg.getMsgId());
		res.setStatus("400");
		String text = String.format("Bad format: command(%s) not support", cmd);
		res.setBody(text);
		sess.write(res);
	}
	

}