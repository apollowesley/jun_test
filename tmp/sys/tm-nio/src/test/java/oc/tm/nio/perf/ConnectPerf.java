package oc.tm.nio.perf;

import java.io.IOException;

import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.http.MessageClient;

public class ConnectPerf {

	public static void main(String[] args) throws Exception {
		final SelectorGroup group = new SelectorGroup();
		group.selectorCount(1);

		Perf perf = new Perf() {
			@Override
			public Task buildTask() {
				Task task = new Task() {
					@Override
					public void doTask() throws Exception { 
						MessageClient client = new MessageClient("127.0.0.1:15555", group);
						client.connectSync();
						client.close();
					} 

				};
				return task;
			}

			@Override
			public void close() throws IOException {
				group.close();
			}
		};
		perf.threadCount = 16;
		perf.loopCount = 10000;
		perf.logInterval = 10000;

		perf.run();

		perf.close();
	}
}
