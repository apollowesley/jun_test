package tom.net.aio;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer,  AioSession>{

	public ReadHandler(){

	}

	@Override
	public void completed(Integer length, AioSession session) {
		if(length == 0) return;
		if(length == -1) {
			close(session);
			return;
		}
		try {
			session.doRead();
		} finally{
			if(session !=null)  {
				session.read();
			}
		}
	}

	@Override
	public void failed(Throwable exc,  AioSession session) {
		session.catchError(exc);
	}
	
	void  close(AioSession session){
		if(session!=null){
			session.close();
			session =null;
		}
	}
}
