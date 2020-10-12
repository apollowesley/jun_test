package tom.net.aio;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

public class ConnectedHandler implements CompletionHandler<Void, AioClient>{
	AioSession session = null;

	public ConnectedHandler(AioSession session){
		this.session = session;
	}

	@Override
	public void completed(Void arg1,  AioClient  client) {
		if(session == null) return;
		try {
			session.setServer(false);
			session.finishConnect();
			session.getIoAdaptor().onSessionConnected(session);
			session.read();
		} catch (IOException e) {
			session.catchError(e);
		}
	}

	@Override
	public void failed(Throwable exc,  AioClient client) {
		session.catchError(exc);
		if(exc instanceof IOException){
			session.close();
			session = null;
		}
	}

}
