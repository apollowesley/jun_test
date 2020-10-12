package com.foo.common.base.pojo;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.utils.FooUtils;
import com.google.common.io.ByteStreams;

public class JarHelperCallable implements Callable<JarHelperCallable> {
	public static final Logger logger = LoggerFactory
			.getLogger(JarHelperCallable.class);

	private String workingDirectory;
	private String jar4UpdateName;
	private int ret;
	private long oldLength;
	private long newLength;

	public JarHelperCallable(String workingDirectory, String jar4UpdateName) {
		this.workingDirectory = workingDirectory;
		this.jar4UpdateName = jar4UpdateName;
	}

	public JarHelperCallable() {
	}

	@Override
	public JarHelperCallable call() {
		JarHelperCallable result = new JarHelperCallable();

		// class compile path
		String updateSourcePath = FilenameUtils.concat(workingDirectory,
				"target");
		// class root folder
		String updateSourceDir = "com";

		String jar4UpdatePath = FilenameUtils.concat(workingDirectory,
				jar4UpdateName);

		File myJar = new File(jar4UpdatePath);
		// logger.info("Now ready to update jar file with name:{},size:{}",
		// myJar.getName(), myJar.length());
		String myCommand = "jar uf " + jar4UpdatePath + " -C "
				+ updateSourcePath + " " + updateSourceDir;
		logger.info("Update command【{}】", myCommand);

		result.setOldLength(myJar.length());

		try {
			Process p = Runtime.getRuntime().exec(myCommand);

			ByteStreams.copy(p.getInputStream(), System.out);
			ByteStreams.copy(p.getErrorStream(), System.err);

			p.waitFor();

			result.setNewLength(new File(jar4UpdatePath).length());
			result.setJar4UpdateName(jar4UpdateName);

			if (result.getOldLength() == result.getNewLength()) {
				result.setRet(1);
			} else {
				result.setRet(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setRet(1);
		}
		logger.info("updateJarEnd:{},oldLength:{},newLength:{}",
				FooUtils.toDateFromYear2Second(new DateTime()),
				result.getOldLength(), result.getNewLength());
		return result;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	@Override
	public String toString() {
		return "updateJars:" + getJar4UpdateName() + " && exitStatus:"
				+ getRet();
	}

	public long getOldLength() {
		return oldLength;
	}

	public void setOldLength(long oldLength) {
		this.oldLength = oldLength;
	}

	public long getNewLength() {
		return newLength;
	}

	public void setNewLength(long newLength) {
		this.newLength = newLength;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public String getJar4UpdateName() {
		return jar4UpdateName;
	}

	public void setJar4UpdateName(String jar4UpdateName) {
		this.jar4UpdateName = jar4UpdateName;
	}

}
