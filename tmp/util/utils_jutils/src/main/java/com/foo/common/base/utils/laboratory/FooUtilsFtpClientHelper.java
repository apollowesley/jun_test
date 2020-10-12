package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.core.io.ClassPathResource;

/**
 * Ftp快速测试类，用于确认ftp服务是否可行
 * 
 * 服务器ftp配置可参考表：ITMS_FILE_SERVER
 * 
 * @author Steve
 * 
 */
public class FooUtilsFtpClientHelper {
	public static void main(String[] args) throws IOException {

		ClassPathResource myPath = new ClassPathResource(
				"target-oracle-112.properties");
		Properties p = new Properties();
		p.load(myPath.getInputStream());

		FTPClient ftp = new FTPClient();
		String server = p.getProperty("hibernate.connection.ip");
		String savePath = "/home/itmscs/patch/SC_ITMS_2012.07.14.zip";
		// String savePath = "home/ftpitms/";
		boolean error = false;
		try {
			int reply;
			ftp.connect(server);
			// ftp.login("ftp", "itms%cs");
			ftp.login("itmscs", "itmscs");
			// ftp.login("ftpitms", "ftpitms");
			System.out.println("Connected to " + server + ".");

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
			ftp.enterLocalPassiveMode();

			// FTPFile[] ftpFileArray = ftp.listFiles(savePath);

			for (FTPFile ftpFile : ftp.listFiles(savePath)) {
				System.out.println(ftpFile.getName() + " and size:"
						+ ftpFile.getSize());
			}

			// ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean saveFileSuccessful = ftp.storeFile(savePath + "test1.txt",
					new FileInputStream(new File(
							"c:\\Users\\Steve\\Desktop\\1.txt")));
			// transfer files here
			System.out.println("Save file result is " + saveFileSuccessful);
			ftp.logout();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
			System.exit(error ? 1 : 0);
		}
	}
}
