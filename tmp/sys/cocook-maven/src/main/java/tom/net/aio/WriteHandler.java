package tom.net.aio;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.Semaphore;


public class WriteHandler implements CompletionHandler<Integer, AioSession> {
	private Semaphore semaphore = new Semaphore(1, true);
	
	public WriteHandler() {
	}

	@Override
	public void completed(Integer length, AioSession session) {
		try{
			if(length == 0) return;
			if(length == -1) {
				close(session);
				return;
			}
		}finally{
			release();
		}
		
	}

	void  close(AioSession session){
		if(session!=null){
			session.close();
			session =null;
		}
	}
	
	@Override
	public void failed(Throwable exc, AioSession session) {
		if (session != null) {
			session.catchError(exc);
		} else {
			exc.printStackTrace();
		}
	}
	
	public void release(){
		semaphore.release();
	}
	
	public void acquire() throws InterruptedException{
		semaphore.acquire();
	}

}
