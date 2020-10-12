package com.foo.common.base.pojo;

import java.util.concurrent.Callable;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.utils.FooUtils;
import com.foo.common.base.utils.SvnHelper;
import com.google.common.io.ByteStreams;

public class GuavaCallable implements Callable<GuavaCallable> {
	public static final Logger logger = LoggerFactory
			.getLogger(SvnHelper.class);

	private String updatePath;
	private int ret;

	public GuavaCallable(String updatePath) {
		setUpdatePath(updatePath);
	}

	public GuavaCallable() {
	}

	@Override
	public GuavaCallable call() throws Exception {
		String pathForUpdate = getUpdatePath();
		logger.info("updateStart:{}",
				FooUtils.toDateFromYear2Second(new DateTime()));
		String myCommand = "TortoiseProc.exe /command:update /path:\""
				+ pathForUpdate + "\" /closeonend:0";

		GuavaCallable result = new GuavaCallable();
		result.setUpdatePath(getUpdatePath());

		try {
			Process p = Runtime.getRuntime().exec(myCommand);

			ByteStreams.copy(p.getInputStream(), System.out);
			ByteStreams.copy(p.getErrorStream(), System.err);

			p.waitFor();
			result.setRet(0);

		} catch (Exception e) {
			e.printStackTrace();
			result.setRet(1);
		}
		logger.info("updateEnd:{}",
				FooUtils.toDateFromYear2Second(new DateTime()));
		return result;
	}

	public String getUpdatePath() {
		return updatePath;
	}

	public void setUpdatePath(String updatePath) {
		this.updatePath = updatePath;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	@Override
	public String toString() {
		return "updatePath:" + getUpdatePath() + " && exitStatus:" + getRet();
	}
}
