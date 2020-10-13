package common.framework.dsb.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CMD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static String runInterfaceInfo() throws IOException {

		String os = System.getProperty("os.name");
		String output = "";
		if (os.startsWith("Windows")) {
			output = runConsoleCommand("ipconfig /all");
		} else if (os.startsWith("Linux")) {
			output = runConsoleCommand("ifconfig -a");
		} else if (os.startsWith("Mac")) {
			output = runConsoleCommand("ifconfig -a");
		} else {
			throw new IOException("operating system: " + os + " not supported");
		}
		return output;
	}

	public static String runConsoleCommand(String command) throws IOException {

		Process p = Runtime.getRuntime().exec(command);
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {

			int c = stdoutStream.read();

			if (c == -1)
				break;

			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

}
