package tom.net.aio;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
@Deprecated
public class WriteBlockQuery extends Thread {

	private final LinkedBlockingQueue<ByteBuffer> writeBufferQ = new LinkedBlockingQueue<ByteBuffer>();
//	private final LinkedBlockingQueue<ByteBuffer> writeBufferQ = new LinkedBlockingQueue<ByteBuffer>();
	private AioSession session;
	final  LinkedBlockingQueue<Integer> selector = new LinkedBlockingQueue<>();
	final Integer i = 0;
	
	public WriteBlockQuery() {
	}

	@Override
	public void run() {
		try {
			doWrite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doWrite() throws InterruptedException {
		while (!isInterrupted()) {
			synchronized (session) {

				while(true){
					ByteBuffer buf = writeBufferQ.take();
//					if (buf == null) {
//						break;
//					}
					// log.debug("doWrite-->{}", buf);
					this.session.syncWrite(buf);
					// int wbytes = this.channel.write(buf).get();

					// if(wbytes == 0 && buf.remaining() > 0){
					// break;
					// }
					//
					// n += wbytes;
//					if (buf.remaining() == 0) { // 全部 write 结束才会 remove, 如果没有结束, 继续 peek write
//						writeBufferQ.remove();
//						continue;
//					} else {
//						break;
//					}
				}
				
			
			}
		}
	}

	public boolean hasNext() throws InterruptedException {
		selector.poll();
		return true;
	}
	
	public void signal() {
		selector.offer(i);
    }

	public void setSession(AioSession session) {
		this.session = session;
	}

	public boolean offer(ByteBuffer bytebuf) {
		return writeBufferQ.offer(bytebuf);
	}

	public int size() {
		return writeBufferQ.size();
	}

	public ByteBuffer peek() {
		return writeBufferQ.peek();
	}

	public ByteBuffer remove() {
		return writeBufferQ.remove();
	}

}
