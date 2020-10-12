package com.foo.common.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * Ftp快速测试类，用于确认ftp服务是否可行
 * 
 * 服务器ftp配置可参考表：ITMS_FILE_SERVER
 * 
 * @author Steve
 * 
 */
public enum FtpHelper {

	instance;

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(FtpHelper.class);

	public static void main(String[] args) {
		try {
			getServerLibsSnapshot();
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 实时读取服务器上的lib目录下的文件
	 * 
	 * @throws Exception
	 */
	public static Set<String> getServerLibsSnapshot() throws Exception {

		Set<String> libNames = Sets.newHashSet();

		Properties p = FooUtils
				.getPropertiesFromResourceFile(("common-patch.properties"));

		String artifactId = Strings.nullToEmpty(p.getProperty("artifactId"));
		String server = Strings
				.nullToEmpty(p.getProperty("server." + artifactId + ".server"));

		Preconditions.checkArgument(artifactId.equals("tenmalife-platform"),
				"function test,support tenmalife-platform only.");

		Preconditions.checkArgument(ValidationHelper.isInetAddressValid(server),
				"server address error.");

		logger.info("artifactId is:{} and server is:{}", artifactId, server);

		FTPClient ftp = new FTPClient();
		String path = "/tenmalife-platform/WEB-INF/lib";
		int reply;
		ftp.connect(server);
		ftp.login("administrator", "feiYnn2015");
		logger.info("Connected to server of:{} successful. ", server);

		/*
		 * After connection attempt, you should check the reply code to verify success.
		 */
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			logger.info("FTP server refused connection.");
			System.exit(1);
		}
		// ftp.enterLocalPassiveMode();

		for (FTPFile ftpFile : ftp.listFiles(path)) {
			logger.debug("name is:{} and size is:{}", ftpFile.getName(),
					ftpFile.getSize());
			libNames.add(ftpFile.getName());
		}

		logger.info("Connected to server of:{} successful. ", server);
		ftp.logout();
		return libNames;
	}

	public void a() throws Exception {
		Properties p = FooUtils
				.getPropertiesFromResourceFile(("common-patch.properties"));

		String artifactId = p.getProperty("artifactId");
		String server = p.getProperty("server." + artifactId + ".server");

		FTPClient ftp = new FTPClient();
		String savePath = "/home/itmscs/patch/SC_ITMS_2012.07.14.zip";
		// String savePath = "home/ftpitms/";
		boolean error = false;
		try {
			int reply;
			ftp.connect(server);
			ftp.login(FooUtils.decrypt("303cf02dd0a1e2d54325ad51b97f49bd"),
					FooUtils.decrypt("db5c7734f502b56ae1de463da0a1b631"));
			System.out.println("Connected to " + server + ".");

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.info("FTP server refused connection.");
				System.exit(1);
			}
			ftp.enterLocalPassiveMode();

			// FTPFile[] ftpFileArray = ftp.listFiles(savePath);

			for (FTPFile ftpFile : ftp.listFiles(savePath)) {
				System.out.println(
						ftpFile.getName() + " and size:" + ftpFile.getSize());
			}

			// ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean saveFileSuccessful = ftp.storeFile(savePath + "test1.txt",
					new FileInputStream(
							new File("c:\\Users\\Steve\\Desktop\\1.txt")));
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
