package tom.net;


import java.io.Closeable;
import java.io.IOException;

public interface Server extends Closeable{ 
	void start() throws Exception;
	void setIoAdaptor(IoAdaptor ioAdaptor);
	void close() throws IOException ;
}
