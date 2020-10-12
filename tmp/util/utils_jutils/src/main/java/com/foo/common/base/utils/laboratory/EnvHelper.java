package com.foo.common.base.utils.laboratory;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.utils.FooUtils;
import com.google.common.io.ByteStreams;

/**
 * 设置环境变量的顺序：开启administrator账户，执行如下命令：
 * 
 * runas /savecred /user:Administrator \" cmd /k echo Hello! && setx AXIS_HOME D:\\programTool\\axis-1_4 /m \"
 * 
 * 但如此麻烦不如保存一个bat文件，然后以administrator的方式来执行它好了
 * 
 * @author Steve
 *
 */
public class EnvHelper {

	static Logger logger = LoggerFactory.getLogger(EnvHelper.class);

	public static void main(String[] args) {
		FooUtils.printSystemEnv();
	}

	public static void main222(String[] args) throws IOException,
			InterruptedException {

		logger.info("Start.......");

		// String myCommand =
		// "runas /savecred /user:Steve \"cmd /c D:\" && setx AXIS_HOME D:\\programTool\\axis-1_4 /m && echo %JAVA_HOME% ";
		String myCommand = "runas /savecred /user:Steve \" cmd /k echo Hello! && setx AXIS_HOME D:\\programTool\\axis-1_4 /m \"";
		// String myCommand =
		// "cmd && SETX /M AXIS_HOME D:\\programTool\\axis-1_4 ";

		Process p = Runtime.getRuntime().exec(myCommand);
		ByteStreams.copy(p.getInputStream(), System.out);
		ByteStreams.copy(p.getErrorStream(), System.err);
		p.waitFor();

	}
}