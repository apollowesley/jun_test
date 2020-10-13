package common.framework.dsb.example;

import common.framework.dsb.client.DefaultServiceFactory;
import common.framework.dsb.client.ServiceFactory;

public class ClientExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	private void installService() {
		String host = "127.0.0.1";
		int port = 10000;
		ServiceFactory serviceFactory = new DefaultServiceFactory();
		String jarPath = "D:/myprojects/NetCareInterface/lib/NetCareInterface.jar";
		try {
			serviceFactory.installService(jarPath, host, port);
			System.out.println("Install service success.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
