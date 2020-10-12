package tom.net.http;

import java.io.IOException;

import tom.net.Session;

public interface MessageHandler { 
	public void handleMessage(HttpMessage msg, Session sess) throws IOException;   
}
