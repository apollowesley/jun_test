package com.foo.common.base.utils.laboratory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ConcurrencyService extends AbstractIdleService {

	private int x = 5;

	public static void main(String[] args) {

		final ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(1));
		ListenableFuture<ConcurrencyService> explosion = service
				.submit(new Callable<ConcurrencyService>() {
					public ConcurrencyService call() throws Exception {
						ConcurrencyService con = new ConcurrencyService();
						con.startUp();
						return con;
					}
				});
		Futures.addCallback(explosion,
				new FutureCallback<ConcurrencyService>() {

					// we want this handler to run immediately after we push the
					// big red
					// button!
					public void onSuccess(ConcurrencyService xxx) {
						System.out.println("success");
						System.out.println(xxx.getX());
						service.shutdown();
					}

					public void onFailure(Throwable thrown) {
						System.out.println("failure");
						// battleArchNemesis(); // escaped the explosion!
					}
				});

	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	@Override
	protected void startUp() throws Exception {
		while (x < 100) {
			x++;
			if (x == 12) {
				shutDown();
				return;
			}
			System.out.println("x is【" + x + "】");
		}
	}

	@Override
	protected void shutDown() throws Exception {
		System.out.println("Invoke shutdown");
	}
}
