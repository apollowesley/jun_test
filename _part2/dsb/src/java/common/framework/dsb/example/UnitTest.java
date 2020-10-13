package common.framework.dsb.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import common.framework.dsb.client.DefaultServiceFactory;
import common.framework.dsb.client.ServiceFactory;

public class UnitTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {

		for (int i = 0; i < 10000; i++) {

			String host = "10.4.251.131";
			int timeout = 100;

			try {
				InetAddress address = InetAddress.getByName(host);
				boolean flag = address.isReachable(timeout);
				System.out.println("ping " + host + " " + flag);
			} catch (UnknownHostException e) {
				e.printStackTrace(System.out);
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
	}
}
