package tom.net;

import java.io.IOException;

public interface Session {

	long id(); 
	
	String getRemoteAddress();
	
	String getLocalAddress();
	
	void write(Object msg);
	
	void writeAndFlush(Object msg);
	
	void read();
	
	void flush();
	
	boolean isActive();
	
	boolean isServer();
	
	void asyncClose() throws IOException;
	
	void catchError(Throwable e);
	
	<V> V attr(String key);
	
	<V> void attr(String key, V value);


}
