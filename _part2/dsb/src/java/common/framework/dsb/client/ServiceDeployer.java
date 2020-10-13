package common.framework.dsb.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarFile;

import common.framework.dsb.proxy.ServiceProxy;

public class ServiceDeployer {

	public static void main(String[] args) {
		System.out.println("");
		if (args == null || args.length < 3) {
			System.out.println("");
			System.out.println("Usage: deploy <host> <port> <jar file>");
			System.out.println("             --host: DSB server");
			System.out.println("             --port: DSB service port");
			System.out.println("             --jar file: DSB jar file name");
			System.out.println("");
			return;
		}
		String host = args[0];
		String sport = args[1];
		int port = 8585;
		try {
			port = Integer.parseInt(sport);
		} catch (NumberFormatException e) {
			System.out.println("Invalid port number: " + port);
			System.out.println("");
			return;
		}
		String fileName = args[2];
		try {
			JarFile jar = new JarFile(fileName);
		} catch (IOException e) {
			System.out.println("Invalid jar file: " + fileName);
			System.out.println("");
			return;
		}
		ServiceFactory serviceFactory = new DefaultServiceFactory();
		ServiceProxy serviceProxy = null;
		try {
			serviceProxy = serviceFactory._lookup(host, port);
		} catch (Exception e) {
			System.out.println("Failed to lookup DSB server: " + e.getLocalizedMessage());
			System.out.println("");
			return;
		}
		byte[] buffer = new byte[1024];
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
			System.out.println("");
		}

		try {
			String sessionID = serviceProxy.openSession(new File(fileName).getName());
			int size = 0;
			do {
				size = fin.read(buffer);
				if (size > 0) {
					serviceProxy.upload(sessionID, buffer, size);
				}
			} while (size > 0);
			serviceProxy.closeSession(sessionID);
			System.out.println("Success deploy DSB jar: " + fileName);
		} catch (Exception e) {
			System.out.println("Failed to upload DSB jar: " + fileName);
			System.out.println("");
			e.printStackTrace(System.out);
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
			}
		}

		// end
	}
}
