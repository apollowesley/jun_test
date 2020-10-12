package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @deprecated 考虑用于服务器上的web接口来实现，而不是远程直接调用脚本,since:2014-08-07 17:10:12
 * @author Steve
 *
 */
public class RemoteConnector {

	public static void main(String[] args) throws JSchException, IOException {

		JSch jsch = new JSch();
		// jsch.setKnownHosts("C:\\Users/Steve/.ssh/known_hosts");
		Session session = jsch.getSession("itms", "10.3.5.250", 22);
		session.setPassword("itms");
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(30000);

		Channel channel = session.openChannel("exec");
		channel.setXForwarding(false);

		((ChannelExec) channel)
				.setCommand("bash /home/itms/ITMS_HOME/bin/startUIServer2.sh;");

		// ((ChannelExec) channel)
		// .setCommand("date");

		channel.setInputStream(null);

		InputStream in = channel.getInputStream();
		((ChannelExec) channel).setErrStream(new FileOutputStream(new File(
				("d:\\tmp\\error.txt"))));

		channel.connect();
		// byte[] tmp = new byte[1024];
		// IOUtils.read(in, tmp);
		// logger.info(new String(tmp, "utf-8"));

		// FileUtils.copyInputStreamToFile(in, new File(
		// ("d:\\tmp\\test.txt"));

		FileOutputStream writer = new FileOutputStream(new File(
				("d:\\tmp\\test.txt")));
		// Writer writer = new OutputStreamWriter(System.out );
		IOUtils.copy(in, writer);
		writer.flush();

		// System.out.println(new String(tmp, "utf-8"));
		//
		channel.disconnect();
		System.out.println(channel.getExitStatus());
		session.disconnect();

	}
}
