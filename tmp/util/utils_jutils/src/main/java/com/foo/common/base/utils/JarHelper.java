package com.foo.common.base.utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.pojo.JarHelperCallable;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 工作原理：读取目录下的java源文件，解析源文件，找到对应的class，最后打包到位于同一目录下的jar包里面。
 *
 */
public enum JarHelper {

	INSTANCE;

	public static final Logger logger = LoggerFactory.getLogger("JarHelper");

	private final static String myJarName = "acsserver";
	private final static String jar4UpdateName = "acsserver.jar";
	private final static String workingDirectory = "D:\\tmp\\zznode\\"
			+ myJarName;
	private final static String sourceDirectory = "D:\\tools\\mySvn\\itms2016\\trunk\\ITMS_DEV\\target\\classes";

	public static void main(String[] args) throws IOException {

		logger.info("jar update task start.");

		FooClassCompileHelper classHelper = FooClassCompileHelper.custom()
				.setWorkingDirectory(workingDirectory)
				.setSourceDirectory(sourceDirectory).build();

		int result = classHelper.compileAndCopyClass();

		logger.info(
				"class compile success,now ready to update jar here.Class size:{}",
				result);

		ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(10));

		// String pathForUpdate = "iptv.jar";
		// String pathForUpdate = "acsserver.jar";
		// String pathForUpdate = "iptv2.jar";
		// String pathForUpdate = "wband2.jar";

		List<JarHelperCallable> myTaskList = Lists.newArrayList();
		myTaskList.add(new JarHelperCallable(classHelper.getWorkingDirectory(),
				jar4UpdateName));
				// myTaskList.add(new JarHelperCallable(pathForUpdate2));

		// For future
		List<ListenableFuture<JarHelperCallable>> myList = Lists.newArrayList();

		for (JarHelperCallable JarHelperCallable : myTaskList) {
			myList.add(service.submit(JarHelperCallable));
		}

		ListenableFuture<List<JarHelperCallable>> successfulQueries = Futures
				.successfulAsList(myList);

		Futures.addCallback(successfulQueries,
				new FutureCallback<List<JarHelperCallable>>() {
					@Override
					public void onSuccess(List<JarHelperCallable> result) {
						List<String> successJarsNames = Lists.newArrayList();
						for (JarHelperCallable listenableFuture : result) {
							if (listenableFuture.getRet() == 0) {
								successJarsNames.add(
										listenableFuture.getJar4UpdateName());
							} else {
								logger.info("fail update jar:{}",
										listenableFuture.getJar4UpdateName());

							}
						}
						logger.info("task end, successJarsNames is:{}",
								successJarsNames.toString());
						System.exit(0);
					}

					@Override
					public void onFailure(Throwable t) {
					}
				});

	}
}
