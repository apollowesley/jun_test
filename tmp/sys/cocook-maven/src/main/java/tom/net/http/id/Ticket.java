package tom.net.http.id;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import tom.kit.ObjectId;
import tom.net.http.Callback.ResultCallback;
import tom.net.http.id.TicketManager.Id;

public class Ticket {

	private CountDownLatch latch = null;
	private String id = "";
	private Id request = null;
	private Id response = null;
	private ResultCallback callback = null;

	protected long timeout = 10000;
	public long startTime = System.currentTimeMillis();

	public Ticket(Id request, long timeout,ResultCallback callback) {
		this.id = uuidTicket();
		if (request != null) {
			request.setMsgId(this.id);
		}

		this.request = request;
		this.timeout = timeout;
		this.callback = callback;
		if(callback == null){
			this.latch = new CountDownLatch(1);
		}
	}

	public static String uuidTicket() {
		return "" + new ObjectId().toLong();
	}

	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		boolean status = this.latch.await(timeout, unit);
		return status;
	}

	public void expired() {
		this.countDown();
	}

	private void countDown() {
		if (this.latch != null) {
			this.latch.countDown();
		}
	}

	public boolean isDone() {
		if (this.latch != null) {
			return this.latch.getCount() == 0;
		}
		return false;
	}

	public void notifyResponse(Id response) {
		this.response = response;
		this.countDown();
		if (this.callback != null) 	this.callback.onCompleted(response);
	}

	public ResultCallback getCallback() {
		return callback;
	}

	public void setCallback(ResultCallback callback) {
		this.callback = callback;
	}

	public String getId() {
		return id;
	}

	public Id request() {
		return this.request;
	}

	public Id response() {
		return this.response;
	}

}