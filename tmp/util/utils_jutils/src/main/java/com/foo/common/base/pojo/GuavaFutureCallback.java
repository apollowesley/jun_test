package com.foo.common.base.pojo;

import com.google.common.util.concurrent.FutureCallback;

public class GuavaFutureCallback implements FutureCallback<Integer> {

	@Override
	public void onSuccess(Integer result) {
		if (result == 0) {
			System.out.println("Update successful.");
		} else if (result == 1) {
			System.out.println("There seems no changing here.");
		} else {
			throw new UnsupportedOperationException("system wrong...");
		}
	}

	@Override
	public void onFailure(Throwable t) {
		System.err.print(t);
	}

}
