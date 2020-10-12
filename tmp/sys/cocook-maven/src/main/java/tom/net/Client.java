package tom.net;

import java.io.Closeable;
import java.io.IOException;

import tom.net.http.HttpMessage;
import tom.net.http.Callback.ResultCallback;
  
public interface Client extends  Closeable {
	void heartbeat();
	void doConnect() throws IOException;
	public void close() throws IOException ;
    public void invokeAsync(Object req, ResultCallback callback) throws IOException;
    public HttpMessage invokeSync(HttpMessage req) throws IOException, InterruptedException;
    public void send(Object msg) throws IOException;
    void setIoAdaptor(IoAdaptor ioAdaptor);

}
