package tom.net.aio;

import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import tom.net.IoAdaptor;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {
	IoAdaptor ioAdaptor;
	AioSession session;

	public AcceptHandler(IoAdaptor ioAdaptor) {
		this.ioAdaptor = ioAdaptor;
	}

	@Override
	public void completed(AsynchronousSocketChannel socketChannel, AioServer aioServer) {
		try {
			socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);
			socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 64 * 1024);
			socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
			session = new AioSession(ioAdaptor, socketChannel);
			ioAdaptor.onSessionAccepted(session);
			session.setAioServer(aioServer);
			session.read();
		} catch (Exception e) {
			if (session != null) {
				session.catchError(e);
			}
		}finally{
			aioServer.accept(new AcceptHandler(ioAdaptor));
		}
	}

	@Override
	public void failed(Throwable exc, AioServer attachment) {
		if (session != null) {
			session.catchError(exc);
		} else {
			exc.printStackTrace();
		}
	}

}
