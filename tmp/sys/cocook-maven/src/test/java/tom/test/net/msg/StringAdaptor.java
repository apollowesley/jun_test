package tom.test.net.msg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import tom.net.IoAdaptor;
import tom.net.IoBuffer;
import tom.net.Session;

public class StringAdaptor extends IoAdaptor { 
	static long startTime = 0;
	private AtomicLong counter;
	
	public StringAdaptor(AtomicLong counter) {
		this.counter = counter;
	}
	
	public IoBuffer encode(Object obj) { 
		String msg = (String)obj;   
		byte[] b = null;
		try {
			b = msg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			b = msg.getBytes();
		}
		IoBuffer buf = IoBuffer.allocate(4+b.length);
		buf.writeInt(b.length);
		buf.writeBytes(b);
		
		buf.flip();
		return buf; 
	}
    
	public Object decode(IoBuffer buf) {  
		if(buf.remaining() < 4) return null;
		int len = buf.readInt();
		if(buf.remaining() < len){
			return null;
		}
		byte[] b = new byte[len];
		buf.readBytes(b);
		return new String(b);
	}

	
	@Override
	public void onMessage(Object msg, Session sess) throws IOException {
		if (counter != null) {
			if(startTime==0)
			startTime = System.currentTimeMillis();
			counter.incrementAndGet();
			if (counter.get() % 1000 == 0) {
				double qps = counter.get() * 1000.0 / (System.currentTimeMillis() - startTime);
				System.out.format(counter.get() + "==QPS: %.2f\n", qps);
			}
		}
		if(sess.isServer()){
			sess.write(msg);
		}
	}

}