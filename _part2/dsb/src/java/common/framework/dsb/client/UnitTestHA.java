package common.framework.dsb.client;


public class UnitTestHA {

	public static void main(String[] args) throws Exception {
		System.setProperty("dsb_ha_1", "localhost:10000");
		System.setProperty("dsb.socket.timeout", "3000");

		ServiceFactory serviceFactory = new DefaultServiceFactory();
		for (int i = 0; i < 100000; i++) {
			try {

				//UserService userService = serviceFactory.lookup_ha("DSB/UserService", "dsb_ha_1");
				//userService.getAllUsers();

			} catch (Throwable ex) {
				ex.printStackTrace(System.out);
			}
			// Thread.sleep(100);
		}
	}
}
